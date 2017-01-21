#!/usr/bin/python -u
# Copyright (c) 2010-2012 OpenStack, LLC.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
# implied.
# See the License for the specific language governing permissions and
# limitations under the License.
import signal
import socket

from errno import EEXIST, ENOENT
from hashlib import md5
from optparse import OptionParser, SUPPRESS_HELP
from os import environ, listdir, makedirs, utime, _exit as os_exit
from os.path import basename, dirname, getmtime, getsize, isdir, join
from Queue import Empty, Queue
from random import shuffle
from sys import argv, exc_info, exit, stderr, stdout
from threading import current_thread, enumerate as threading_enumerate, Thread
from time import sleep, time
from traceback import format_exception
from urllib import quote, unquote

from swiftclient import Connection, ClientException, HTTPException


def get_conn(options):
    """
    Return a connection building it from the options.
    """
    return Connection(options.auth,
                      options.user,
                      options.key,
                      auth_version=options.auth_version,
                      os_options=options.os_options,
                      snet=options.snet)


def mkdirs(path):
    try:
        makedirs(path)
    except OSError, err:
        if err.errno != EEXIST:
            raise


def put_errors_from_threads(threads, error_queue):
    """
    Places any errors from the threads into error_queue.
    :param threads: A list of QueueFunctionThread instances.
    :param error_queue: A queue to put error strings into.
    :returns: True if any errors were found.
    """
    was_error = False
    for thread in threads:
        for info in thread.exc_infos:
            was_error = True
            if isinstance(info[1], ClientException):
                error_queue.put(str(info[1]))
            else:
                error_queue.put(''.join(format_exception(*info)))
    return was_error


def attempt_graceful_exit(signum, frame):
    """
    Try to gracefully shut down. Sets abort=True on all non-main threads.

    More importantly, installs the immediate_exit handler on the
    signal that triggered this handler. If this function is installed
    as a signal handler for SIGINT, then pressing Ctrl-C once will
    cause this program to finish operations in progress, then exit.
    Pressing it again will cause an immediate exit; no cleanup
    handlers will get called.
    """
    stderr.write("Attempting graceful exit. "
                 "Press Ctrl-C again to exit immediately.\n")
    main_thread = current_thread()
    for thread in [t for t in threading_enumerate() if t is not main_thread]:
        thread.abort = True
    signal.signal(signum, immediate_exit)


def immediate_exit(signum, frame):
    os_exit(2)


class QueueFunctionThread(Thread):

    def __init__(self, queue, func, *args, **kwargs):
        """ Calls func for each item in queue; func is called with a queued
            item as the first arg followed by *args and **kwargs. Use the abort
            attribute to have the thread empty the queue (without processing)
            and exit. """
        Thread.__init__(self)
        self.abort = False
        self.queue = queue
        self.func = func
        self.args = args
        self.kwargs = kwargs
        self.exc_infos = []

    def run(self):
        while True:
            try:
                item = self.queue.get_nowait()
            except Empty:
                if self.abort:
                    break
                sleep(0.01)
            else:
                try:
                    if not self.abort:
                        self.func(item, *self.args, **self.kwargs)
                except Exception:
                    self.exc_infos.append(exc_info())
                finally:
                    self.queue.task_done()

st_delete_help = '''
delete [options] --all OR delete container [options] [object] [object] ...
    Deletes everything in the account (with --all), or everything in a
    container, or a list of objects depending on the args given. Segments of
    manifest objects will be deleted as well, unless you specify the
    --leave-segments option.'''.strip('\n')


def st_delete(parser, args, print_queue, error_queue):
    parser.add_option('-a', '--all', action='store_true', dest='yes_all',
        default=False, help='Indicates that you really want to delete '
        'everything in the account')
    parser.add_option('', '--leave-segments', action='store_true',
        dest='leave_segments', default=False, help='Indicates that you want '
        'the segments of manifest objects left alone')
    parser.add_option('', '--object-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'deleting objects')
    parser.add_option('', '--container-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'deleting containers')
    (options, args) = parse_args(parser, args)
    args = args[1:]
    if (not args and not options.yes_all) or (args and options.yes_all):
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_delete_help))
        return

    def _delete_segment((container, obj), conn):
        conn.delete_object(container, obj)
        if options.verbose:
            if conn.attempts > 2:
                print_queue.put('%s/%s [after %d attempts]' %
                                (container, obj, conn.attempts))
            else:
                print_queue.put('%s/%s' % (container, obj))

    object_queue = Queue(10000)

    def _delete_object((container, obj), conn):
        try:
            old_manifest = None
            if not options.leave_segments:
                try:
                    old_manifest = conn.head_object(container, obj).get(
                        'x-object-manifest')
                except ClientException, err:
                    if err.http_status != 404:
                        raise
            conn.delete_object(container, obj)
            if old_manifest:
                segment_queue = Queue(10000)
                scontainer, sprefix = old_manifest.split('/', 1)
                scontainer = unquote(scontainer)
                sprefix = unquote(sprefix)
                for delobj in conn.get_container(scontainer,
                                                 prefix=sprefix)[1]:
                    segment_queue.put((scontainer, delobj['name']))
                if not segment_queue.empty():
                    segment_threads = [QueueFunctionThread(segment_queue,
                        _delete_segment, create_connection()) for _junk in
                        xrange(options.object_threads)]
                    for thread in segment_threads:
                        thread.start()
                    while not segment_queue.empty():
                        sleep(0.01)
                    for thread in segment_threads:
                        thread.abort = True
                        while thread.isAlive():
                            thread.join(0.01)
                    put_errors_from_threads(segment_threads, error_queue)
            if options.verbose:
                path = options.yes_all and join(container, obj) or obj
                if path[:1] in ('/', '\\'):
                    path = path[1:]
                if conn.attempts > 1:
                    print_queue.put('%s [after %d attempts]' %
                                    (path, conn.attempts))
                else:
                    print_queue.put(path)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Object %s not found' %
                            repr('%s/%s' % (container, obj)))

    container_queue = Queue(10000)

    def _delete_container(container, conn):
        try:
            marker = ''
            had_objects = False
            while True:
                objects = [o['name'] for o in
                           conn.get_container(container, marker=marker)[1]]
                if not objects:
                    break
                had_objects = True
                for obj in objects:
                    object_queue.put((container, obj))
                marker = objects[-1]
            if had_objects:
                # By using join() instead of empty() we should avoid most
                # occurrences of 409 below.
                object_queue.join()
            attempts = 1
            while True:
                try:
                    conn.delete_container(container)
                    break
                except ClientException, err:
                    if err.http_status != 409:
                        raise
                    if attempts > 10:
                        raise
                    attempts += 1
                    sleep(1)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Container %s not found' % repr(container))

    create_connection = lambda: get_conn(options)
    object_threads = \
        [QueueFunctionThread(object_queue, _delete_object, create_connection())
         for _junk in xrange(options.object_threads)]
    for thread in object_threads:
        thread.start()
    container_threads = \
        [QueueFunctionThread(container_queue, _delete_container,
                             create_connection())
         for _junk in xrange(options.container_threads)]
    for thread in container_threads:
        thread.start()
    if not args:
        conn = create_connection()
        try:
            marker = ''
            while True:
                containers = \
                    [c['name'] for c in conn.get_account(marker=marker)[1]]
                if not containers:
                    break
                for container in containers:
                    container_queue.put(container)
                marker = containers[-1]
            while not container_queue.empty():
                sleep(0.01)
            while not object_queue.empty():
                sleep(0.01)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Account not found')
    elif len(args) == 1:
        if '/' in args[0]:
            print >> stderr, 'WARNING: / in container name; you might have ' \
                             'meant %r instead of %r.' % \
                             (args[0].replace('/', ' ', 1), args[0])
        conn = create_connection()
        _delete_container(args[0], conn)
    else:
        for obj in args[1:]:
            object_queue.put((args[0], obj))
    while not container_queue.empty():
        sleep(0.01)
    for thread in container_threads:
        thread.abort = True
        while thread.isAlive():
            thread.join(0.01)
    put_errors_from_threads(container_threads, error_queue)
    while not object_queue.empty():
        sleep(0.01)
    for thread in object_threads:
        thread.abort = True
        while thread.isAlive():
            thread.join(0.01)
    put_errors_from_threads(object_threads, error_queue)


st_download_help = '''
download --all OR download container [options] [object] [object] ...
    Downloads everything in the account (with --all), or everything in a
    container, or a list of objects depending on the args given. For a single
    object download, you may use the -o [--output] <filename> option to
    redirect the output to a specific file or if "-" then just redirect to
    stdout.'''.strip('\n')


def st_download(parser, args, print_queue, error_queue):
    parser.add_option('-a', '--all', action='store_true', dest='yes_all',
        default=False, help='Indicates that you really want to download '
        'everything in the account')
    parser.add_option('-m', '--marker', dest='marker',
        default='', help='Marker to use when starting a container or '
        'account download')
    parser.add_option('-o', '--output', dest='out_file', help='For a single '
        'file download, stream the output to an alternate location ')
    parser.add_option('', '--object-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'downloading objects')
    parser.add_option('', '--container-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'listing containers')
    parser.add_option('', '--no-download', action='store_true',
                      default=False, help="Perform download(s), but don't "
                      "actually write anything to disk")
    (options, args) = parse_args(parser, args)
    args = args[1:]
    if options.out_file == '-':
        options.verbose = 0
    if options.out_file and len(args) != 2:
        exit('-o option only allowed for single file downloads')
    if (not args and not options.yes_all) or (args and options.yes_all):
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_download_help))
        return

    object_queue = Queue(10000)

    def _download_object(queue_arg, conn):
        if len(queue_arg) == 2:
            container, obj = queue_arg
            out_file = None
        elif len(queue_arg) == 3:
            container, obj, out_file = queue_arg
        else:
            raise Exception("Invalid queue_arg length of %s" % len(queue_arg))
        try:
            start_time = time()
            headers, body = \
                conn.get_object(container, obj, resp_chunk_size=65536)
            header_receipt = time()
            content_type = headers.get('content-type')
            if 'content-length' in headers:
                content_length = int(headers.get('content-length'))
            else:
                content_length = None
            etag = headers.get('etag')
            path = options.yes_all and join(container, obj) or obj
            if path[:1] in ('/', '\\'):
                path = path[1:]
            md5sum = None
            make_dir = not options.no_download and out_file != "-"
            if content_type.split(';', 1)[0] == 'text/directory':
                if make_dir and not isdir(path):
                    mkdirs(path)
                read_length = 0
                if 'x-object-manifest' not in headers:
                    md5sum = md5()
                for chunk in body:
                    read_length += len(chunk)
                    if md5sum:
                        md5sum.update(chunk)
            else:
                dirpath = dirname(path)
                if make_dir and dirpath and not isdir(dirpath):
                    mkdirs(dirpath)
                if not options.no_download:
                    if out_file == "-":
                        fp = stdout
                    elif out_file:
                        fp = open(out_file, 'wb')
                    else:
                        fp = open(path, 'wb')
                read_length = 0
                if 'x-object-manifest' not in headers:
                    md5sum = md5()
                for chunk in body:
                    if not options.no_download:
                        fp.write(chunk)
                    read_length += len(chunk)
                    if md5sum:
                        md5sum.update(chunk)
                if not options.no_download:
                    fp.close()
            if md5sum and md5sum.hexdigest() != etag:
                error_queue.put('%s: md5sum != etag, %s != %s' %
                                (path, md5sum.hexdigest(), etag))
            if content_length is not None and read_length != content_length:
                error_queue.put('%s: read_length != content_length, %d != %d' %
                                (path, read_length, content_length))
            if 'x-object-meta-mtime' in headers and not options.out_file \
                    and not options.no_download:

                mtime = float(headers['x-object-meta-mtime'])
                utime(path, (mtime, mtime))
            if options.verbose:
                finish_time = time()
                time_str = 'headers %.3fs, total %.3fs, %.3fs MB/s' % (
                    header_receipt - start_time, finish_time - start_time,
                    float(read_length) / (finish_time - start_time) / 1000000)
                if conn.attempts > 1:
                    print_queue.put('%s [%s after %d attempts]' %
                                    (path, time_str, conn.attempts))
                else:
                    print_queue.put('%s [%s]' % (path, time_str))
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Object %s not found' %
                            repr('%s/%s' % (container, obj)))

    container_queue = Queue(10000)

    def _download_container(container, conn):
        try:
            marker = options.marker
            while True:
                objects = [o['name'] for o in
                           conn.get_container(container, marker=marker)[1]]
                if not objects:
                    break
                marker = objects[-1]
                shuffle(objects)
                for obj in objects:
                    object_queue.put((container, obj))
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Container %s not found' % repr(container))

    create_connection = lambda: get_conn(options)
    object_threads = [QueueFunctionThread(object_queue, _download_object,
        create_connection()) for _junk in xrange(options.object_threads)]
    for thread in object_threads:
        thread.start()
    container_threads = [QueueFunctionThread(container_queue,
        _download_container, create_connection())
        for _junk in xrange(options.container_threads)]
    for thread in container_threads:
        thread.start()
    if not args:
        conn = create_connection()
        try:
            marker = options.marker
            while True:
                containers = [c['name']
                              for c in conn.get_account(marker=marker)[1]]
                if not containers:
                    break
                marker = containers[-1]
                shuffle(containers)
                for container in containers:
                    container_queue.put(container)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Account not found')
    elif len(args) == 1:
        if '/' in args[0]:
            print >> stderr, 'WARNING: / in container name; you might have ' \
                             'meant %r instead of %r.' % \
                             (args[0].replace('/', ' ', 1), args[0])
        _download_container(args[0], create_connection())
    else:
        if len(args) == 2:
            obj = args[1]
            object_queue.put((args[0], obj, options.out_file))
        else:
            for obj in args[1:]:
                object_queue.put((args[0], obj))
    while not container_queue.empty():
        sleep(0.01)
    for thread in container_threads:
        thread.abort = True
        while thread.isAlive():
            thread.join(0.01)
    put_errors_from_threads(container_threads, error_queue)
    while not object_queue.empty():
        sleep(0.01)
    for thread in object_threads:
        thread.abort = True
        while thread.isAlive():
            thread.join(0.01)
    put_errors_from_threads(object_threads, error_queue)


st_list_help = '''
list [options] [container]
    Lists the containers for the account or the objects for a container. -p or
    --prefix is an option that will only list items beginning with that prefix.
    -d or --delimiter is option (for container listings only) that will roll up
    items with the given delimiter (see Cloud Files general documentation for
    what this means).
'''.strip('\n')


def st_list(parser, args, print_queue, error_queue):
    parser.add_option('-p', '--prefix', dest='prefix', help='Will only list '
        'items beginning with the prefix')
    parser.add_option('-d', '--delimiter', dest='delimiter', help='Will roll '
        'up items with the given delimiter (see Cloud Files general '
        'documentation for what this means)')
    (options, args) = parse_args(parser, args)
    args = args[1:]
    if options.delimiter and not args:
        exit('-d option only allowed for container listings')
    if len(args) > 1:
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_list_help))
        return

    conn = get_conn(options)
    try:
        marker = ''
        while True:
            if not args:
                items = \
                    conn.get_account(marker=marker, prefix=options.prefix)[1]
            else:
                items = conn.get_container(args[0], marker=marker,
                    prefix=options.prefix, delimiter=options.delimiter)[1]
            if not items:
                break
            for item in items:
                print_queue.put(item.get('name', item.get('subdir')))
            marker = items[-1].get('name', items[-1].get('subdir'))
    except ClientException, err:
        if err.http_status != 404:
            raise
        if not args:
            error_queue.put('Account not found')
        else:
            error_queue.put('Container %s not found' % repr(args[0]))


st_stat_help = '''
stat [container] [object]
    Displays information for the account, container, or object depending on the
    args given (if any).'''.strip('\n')


def st_stat(parser, args, print_queue, error_queue):
    (options, args) = parse_args(parser, args)
    args = args[1:]
    conn = get_conn(options)
    if not args:
        try:
            headers = conn.head_account()
            if options.verbose > 1:
                print_queue.put('''
StorageURL: %s
Auth Token: %s
'''.strip('\n') % (conn.url, conn.token))
            container_count = int(headers.get('x-account-container-count', 0))
            object_count = int(headers.get('x-account-object-count', 0))
            bytes_used = int(headers.get('x-account-bytes-used', 0))
            print_queue.put('''
   Account: %s
Containers: %d
   Objects: %d
     Bytes: %d'''.strip('\n') % (conn.url.rsplit('/', 1)[-1], container_count,
                                 object_count, bytes_used))
            for key, value in headers.items():
                if key.startswith('x-account-meta-'):
                    print_queue.put('%10s: %s' % ('Meta %s' %
                        key[len('x-account-meta-'):].title(), value))
            for key, value in headers.items():
                if not key.startswith('x-account-meta-') and key not in (
                        'content-length', 'date', 'x-account-container-count',
                        'x-account-object-count', 'x-account-bytes-used'):
                    print_queue.put(
                        '%10s: %s' % (key.title(), value))
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Account not found')
    elif len(args) == 1:
        if '/' in args[0]:
            print >> stderr, 'WARNING: / in container name; you might have ' \
                             'meant %r instead of %r.' % \
                             (args[0].replace('/', ' ', 1), args[0])
        try:
            headers = conn.head_container(args[0])
            object_count = int(headers.get('x-container-object-count', 0))
            bytes_used = int(headers.get('x-container-bytes-used', 0))
            print_queue.put('''
  Account: %s
Container: %s
  Objects: %d
    Bytes: %d
 Read ACL: %s
Write ACL: %s
  Sync To: %s
 Sync Key: %s'''.strip('\n') % (conn.url.rsplit('/', 1)[-1], args[0],
                                object_count, bytes_used,
                                headers.get('x-container-read', ''),
                                headers.get('x-container-write', ''),
                                headers.get('x-container-sync-to', ''),
                                headers.get('x-container-sync-key', '')))
            for key, value in headers.items():
                if key.startswith('x-container-meta-'):
                    print_queue.put('%9s: %s' % ('Meta %s' %
                        key[len('x-container-meta-'):].title(), value))
            for key, value in headers.items():
                if not key.startswith('x-container-meta-') and key not in (
                        'content-length', 'date', 'x-container-object-count',
                        'x-container-bytes-used', 'x-container-read',
                        'x-container-write', 'x-container-sync-to',
                        'x-container-sync-key'):
                    print_queue.put(
                        '%9s: %s' % (key.title(), value))
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Container %s not found' % repr(args[0]))
    elif len(args) == 2:
        try:
            headers = conn.head_object(args[0], args[1])
            print_queue.put('''
       Account: %s
     Container: %s
        Object: %s
  Content Type: %s'''.strip('\n') % (conn.url.rsplit('/', 1)[-1], args[0],
                                     args[1], headers.get('content-type')))
            if 'content-length' in headers:
                print_queue.put('Content Length: %s' %
                                headers['content-length'])
            if 'last-modified' in headers:
                print_queue.put(' Last Modified: %s' %
                                headers['last-modified'])
            if 'etag' in headers:
                print_queue.put('          ETag: %s' % headers['etag'])
            if 'x-object-manifest' in headers:
                print_queue.put('      Manifest: %s' %
                                headers['x-object-manifest'])
            for key, value in headers.items():
                if key.startswith('x-object-meta-'):
                    print_queue.put('%14s: %s' % ('Meta %s' %
                        key[len('x-object-meta-'):].title(), value))
            for key, value in headers.items():
                if not key.startswith('x-object-meta-') and key not in (
                        'content-type', 'content-length', 'last-modified',
                        'etag', 'date', 'x-object-manifest'):
                    print_queue.put(
                        '%14s: %s' % (key.title(), value))
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Object %s not found' %
                            repr('%s/%s' % (args[0], args[1])))
    else:
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_stat_help))


st_post_help = '''
post [options] [container] [object]
    Updates meta information for the account, container, or object depending on
    the args given. If the container is not found, it will be created
    automatically; but this is not true for accounts and objects. Containers
    also allow the -r (or --read-acl) and -w (or --write-acl) options. The -m
    or --meta option is allowed on all and used to define the user meta data
    items to set in the form Name:Value. This option can be repeated. Example:
    post -m Color:Blue -m Size:Large'''.strip('\n')


def st_post(parser, args, print_queue, error_queue):
    parser.add_option('-r', '--read-acl', dest='read_acl', help='Sets the '
        'Read ACL for containers. Quick summary of ACL syntax: .r:*, '
        '.r:-.example.com, .r:www.example.com, account1, account2:user2')
    parser.add_option('-w', '--write-acl', dest='write_acl', help='Sets the '
        'Write ACL for containers. Quick summary of ACL syntax: account1, '
        'account2:user2')
    parser.add_option('-t', '--sync-to', dest='sync_to', help='Sets the '
        'Sync To for containers, for multi-cluster replication.')
    parser.add_option('-k', '--sync-key', dest='sync_key', help='Sets the '
        'Sync Key for containers, for multi-cluster replication.')
    parser.add_option('-m', '--meta', action='append', dest='meta', default=[],
        help='Sets a meta data item with the syntax name:value. This option '
        'may be repeated. Example: -m Color:Blue -m Size:Large')
    (options, args) = parse_args(parser, args)
    args = args[1:]
    if (options.read_acl or options.write_acl or options.sync_to or
        options.sync_key) and not args:
        exit('-r, -w, -t, and -k options only allowed for containers')
    conn = get_conn(options)
    if not args:
        headers = split_headers(options.meta, 'X-Account-Meta-', error_queue)
        try:
            conn.post_account(headers=headers)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Account not found')
    elif len(args) == 1:
        if '/' in args[0]:
            print >> stderr, 'WARNING: / in container name; you might have ' \
                             'meant %r instead of %r.' % \
                             (args[0].replace('/', ' ', 1), args[0])
        headers = split_headers(options.meta, 'X-Container-Meta-', error_queue)
        if options.read_acl is not None:
            headers['X-Container-Read'] = options.read_acl
        if options.write_acl is not None:
            headers['X-Container-Write'] = options.write_acl
        if options.sync_to is not None:
            headers['X-Container-Sync-To'] = options.sync_to
        if options.sync_key is not None:
            headers['X-Container-Sync-Key'] = options.sync_key
        try:
            conn.post_container(args[0], headers=headers)
        except ClientException, err:
            if err.http_status != 404:
                raise
            conn.put_container(args[0], headers=headers)
    elif len(args) == 2:
        headers = split_headers(options.meta, 'X-Object-Meta-', error_queue)
        try:
            conn.post_object(args[0], args[1], headers=headers)
        except ClientException, err:
            if err.http_status != 404:
                raise
            error_queue.put('Object %s not found' %
                            repr('%s/%s' % (args[0], args[1])))
    else:
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_post_help))


st_upload_help = '''
upload [options] container file_or_directory [file_or_directory] [...]
    Uploads to the given container the files and directories specified by the
    remaining args. -c or --changed is an option that will only upload files
    that have changed since the last upload. -S <size> or --segment-size <size>
    and --leave-segments are options as well (see --help for more).
'''.strip('\n')


def st_upload(parser, args, print_queue, error_queue):
    parser.add_option('-c', '--changed', action='store_true', dest='changed',
        default=False, help='Will only upload files that have changed since '
        'the last upload')
    parser.add_option('-S', '--segment-size', dest='segment_size', help='Will '
        'upload files in segments no larger than <size> and then create a '
        '"manifest" file that will download all the segments as if it were '
        'the original file. The segments will be uploaded to a '
        '<container>_segments container so as to not pollute the main '
        '<container> listings.')
    parser.add_option('', '--leave-segments', action='store_true',
        dest='leave_segments', default=False, help='Indicates that you want '
        'the older segments of manifest objects left alone (in the case of '
        'overwrites)')
    parser.add_option('', '--object-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'uploading full objects')
    parser.add_option('', '--segment-threads', type=int,
                      default=10, help='Number of threads to use for '
                      'uploading object segments')
    (options, args) = parse_args(parser, args)
    args = args[1:]
    if len(args) < 2:
        error_queue.put('Usage: %s [options] %s' %
                        (basename(argv[0]), st_upload_help))
        return
    object_queue = Queue(10000)

    def _segment_job(job, conn):
        if job.get('delete', False):
            conn.delete_object(job['container'], job['obj'])
        else:
            fp = open(job['path'], 'rb')
            fp.seek(job['segment_start'])
            conn.put_object(job.get('container', args[0] + '_segments'),
                job['obj'], fp, content_length=job['segment_size'])
        if options.verbose and 'log_line' in job:
            if conn.attempts > 1:
                print_queue.put('%s [after %d attempts]' %
                                (job['log_line'], conn.attempts))
            else:
                print_queue.put(job['log_line'])

    def _object_job(job, conn):
        path = job['path']
        container = job.get('container', args[0])
        dir_marker = job.get('dir_marker', False)
        try:
            obj = path
            if obj.startswith('./') or obj.startswith('.\\'):
                obj = obj[2:]
            if obj.startswith('/'):
                obj = obj[1:]
            put_headers = {'x-object-meta-mtime': "%f" % getmtime(path)}
            if dir_marker:
                if options.changed:
                    try:
                        headers = conn.head_object(container, obj)
                        ct = headers.get('content-type')
                        cl = int(headers.get('content-length'))
                        et = headers.get('etag')
                        mt = headers.get('x-object-meta-mtime')
                        if ct.split(';', 1)[0] == 'text/directory' and \
                                cl == 0 and \
                                et == 'd41d8cd98f00b204e9800998ecf8427e' and \
                                mt == put_headers['x-object-meta-mtime']:
                            return
                    except ClientException, err:
                        if err.http_status != 404:
                            raise
                conn.put_object(container, obj, '', content_length=0,
                                content_type='text/directory',
                                headers=put_headers)
            else:
                # We need to HEAD all objects now in case we're overwriting a
                # manifest object and need to delete the old segments
                # ourselves.
                old_manifest = None
                if options.changed or not options.leave_segments:
                    try:
                        headers = conn.head_object(container, obj)
                        cl = int(headers.get('content-length'))
                        mt = headers.get('x-object-meta-mtime')
                        if options.changed and cl == getsize(path) and \
                                mt == put_headers['x-object-meta-mtime']:
                            return
                        if not options.leave_segments:
                            old_manifest = headers.get('x-object-manifest')
                    except ClientException, err:
                        if err.http_status != 404:
                            raise
                if options.segment_size and \
                        getsize(path) < options.segment_size:
                    full_size = getsize(path)
                    segment_queue = Queue(10000)
                    segment_threads = [QueueFunctionThread(segment_queue,
                        _segment_job, create_connection()) for _junk in
                        xrange(options.segment_threads)]
                    for thread in segment_threads:
                        thread.start()
                    segment = 0
                    segment_start = 0
                    while segment_start < full_size:
                        segment_size = int(options.segment_size)
                        if segment_start + segment_size > full_size:
                            segment_size = full_size - segment_start
                        segment_queue.put({'path': path,
                            'obj': '%s/%s/%s/%08d' % (obj,
                                put_headers['x-object-meta-mtime'], full_size,
                                segment),
                            'segment_start': segment_start,
                            'segment_size': segment_size,
                            'log_line': '%s segment %s' % (obj, segment)})
                        segment += 1
                        segment_start += segment_size
                    while not segment_queue.empty():
                        sleep(0.01)
                    for thread in segment_threads:
                        thread.abort = True
                        while thread.isAlive():
                            thread.join(0.01)
                    if put_errors_from_threads(segment_threads, error_queue):
                        raise ClientException('Aborting manifest creation '
                            'because not all segments could be uploaded. %s/%s'
                            % (container, obj))
                    new_object_manifest = '%s_segments/%s/%s/%s/' % (
                        quote(container), quote(obj),
                        put_headers['x-object-meta-mtime'], full_size)
                    if old_manifest == new_object_manifest:
                        old_manifest = None
                    put_headers['x-object-manifest'] = new_object_manifest
                    conn.put_object(container, obj, '', content_length=0,
                                    headers=put_headers)
                else:
                    conn.put_object(container, obj, open(path, 'rb'),
                        content_length=getsize(path), headers=put_headers)
                if old_manifest:
                    segment_queue = Queue(10000)
                    scontainer, sprefix = old_manifest.split('/', 1)
                    scontainer = unquote(scontainer)
                    sprefix = unquote(sprefix)
                    for delobj in conn.get_container(scontainer,
                                                     prefix=sprefix)[1]:
                        segment_queue.put({'delete': True,
                            'container': scontainer, 'obj': delobj['name']})
                    if not segment_queue.empty():
                        segment_threads = [QueueFunctionThread(segment_queue,
                            _segment_job, create_connection()) for _junk in
                            xrange(options.segment_threads)]
                        for thread in segment_threads:
                            thread.start()
                        while not segment_queue.empty():
                            sleep(0.01)
                        for thread in segment_threads:
                            thread.abort = True
                            while thread.isAlive():
                                thread.join(0.01)
                        put_errors_from_threads(segment_threads, error_queue)
            if options.verbose:
                if conn.attempts > 1:
                    print_queue.put(
                        '%s [after %d attempts]' % (obj, conn.attempts))
                else:
                    print_queue.put(obj)
        except OSError, err:
            if err.errno != ENOENT:
                raise
            error_queue.put('Local file %s not found' % repr(path))

    def _upload_dir(path):
        names = listdir(path)
        if not names:
            object_queue.put({'path': path, 'dir_marker': True})
        else:
            for name in listdir(path):
                subpath = join(path, name)
                if isdir(subpath):
                    _upload_dir(subpath)
                else:
                    object_queue.put({'path': subpath})

    create_connection = lambda: get_conn(options)
    object_threads = [QueueFunctionThread(object_queue, _object_job,
        create_connection()) for _junk in xrange(options.object_threads)]
    for thread in object_threads:
        thread.start()
    conn = create_connection()
    # Try to create the container, just in case it doesn't exist. If this
    # fails, it might just be because the user doesn't have container PUT
    # permissions, so we'll ignore any error. If there's really a problem,
    # it'll surface on the first object PUT.
    try:
        conn.put_container(args[0])
        if options.segment_size is not None:
            conn.put_container(args[0] + '_segments')
    except ClientException, err:
        msg = ' '.join(str(x) for x in (err.http_status, err.http_reason))
        if err.http_response_content:
            if msg:
                msg += ': '
            msg += err.http_response_content[:60]
        error_queue.put(
            'Error trying to create container %r: %s' % (args[0], msg))
    except Exception, err:
        error_queue.put(
            'Error trying to create container %r: %s' % (args[0], err))
    try:
        for arg in args[1:]:
            if isdir(arg):
                _upload_dir(arg)
            else:
                object_queue.put({'path': arg})
        while not object_queue.empty():
            sleep(0.01)
        for thread in object_threads:
            thread.abort = True
            while thread.isAlive():
                thread.join(0.01)
        put_errors_from_threads(object_threads, error_queue)
    except ClientException, err:
        if err.http_status != 404:
            raise
        error_queue.put('Account not found')


def split_headers(options, prefix='', error_queue=None):
    """
    Splits 'Key: Value' strings and returns them as a dictionary.

    :param options: An array of 'Key: Value' strings
    :param prefix: String to prepend to all of the keys in the dictionary.
    :param error_queue: Queue for thread safe error reporting.
    """
    headers = {}
    for item in options:
        split_item = item.split(':', 1)
        if len(split_item) == 2:
            headers[prefix + split_item[0]] = split_item[1]
        else:
            error_string = "Metadata parameter %s must contain a ':'.\n%s" \
                           % (item, st_post_help)
            if error_queue:
                error_queue.put(error_string)
            else:
                exit(error_string)
    return headers


def parse_args(parser, args, enforce_requires=True):
    if not args:
        args = ['-h']
    (options, args) = parser.parse_args(args)

    if (not (options.auth and options.user and options.key)):
        # Use 2.0 auth if none of the old args are present
        options.auth_version = '2.0'

    # Use new-style args if old ones not present
    if not options.auth and options.os_auth_url:
        options.auth = options.os_auth_url
    if not options.user and options.os_username:
        options.user = options.os_username
    if not options.key and options.os_password:
        options.key = options.os_password

    # Specific OpenStack options
    options.os_options = {
        'tenant_id': options.os_tenant_id,
        'tenant_name': options.os_tenant_name,
        'service_type': options.os_service_type,
        'endpoint_type': options.os_endpoint_type,
        'auth_token': options.os_auth_token,
        'object_storage_url': options.os_storage_url,
        'region_name': options.os_region_name,
    }

    # Handle trailing '/' in URL
    if options.auth and not options.auth.endswith('/'):
        options.auth += '/'

    if enforce_requires and \
            not (options.auth and options.user and options.key):
        exit('''
Auth version 1.0 requires ST_AUTH, ST_USER, and ST_KEY environment variables
to be set or overridden with -A, -U, or -K.

Auth version 2.0 requires OS_AUTH_URL, OS_USERNAME, OS_PASSWORD, and
OS_TENANT_NAME OS_TENANT_ID to be set or overridden with --os-auth-url,
--os-username, --os-password, --os-tenant-name or os-tenant-id.'''.strip('\n'))
    return options, args


 
parser = None
commands = None
print_thread = None
print_queue  = None
error_count  = None
error_queue  = None
error_thread = None



def parser_prep(d_argv):
    global parser
    global commands
    global print_thread
    global print_queue
    global error_count
    global error_queue
    global error_thread
    print 'here2'	
    parser = OptionParser(version='%prog 1.0', usage='')


    parser.add_option('-s', '--snet', action='store_true', dest='snet',
                      default=False, help='Use SERVICENET internal network')
    parser.add_option('-v', '--verbose', action='count', dest='verbose',
                      default=1, help='Print more info')
    parser.add_option('-q', '--quiet', action='store_const', dest='verbose',
                      const=0, default=1, help='Suppress status output')
    parser.add_option('-A', '--auth', dest='auth',
                      default=environ.get('ST_AUTH'),
                      help='URL for obtaining an auth token')
    parser.add_option('-V', '--auth-version',
                      dest='auth_version',
                      default=environ.get('ST_AUTH_VERSION', '1.0'),
                      type=str,
                      help='Specify a version for authentication. '
                           'Defaults to 1.0.')
    parser.add_option('-U', '--user', dest='user',
                      default=environ.get('ST_USER'),
                      help='User name for obtaining an auth token.')
    parser.add_option('-K', '--key', dest='key',
                      default=environ.get('ST_KEY'),
                      help='Key for obtaining an auth token.')
    parser.add_option('--os-username',
                      metavar='<auth-user-name>',
                      default=environ.get('OS_USERNAME'),
                      help='Openstack username. Defaults to env[OS_USERNAME].')
    parser.add_option('--os_username',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-password',
                      metavar='<auth-password>',
                      default=environ.get('OS_PASSWORD'),
                      help='Openstack password. Defaults to env[OS_PASSWORD].')
    parser.add_option('--os_password',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-tenant-id',
                      metavar='<auth-tenant-id>',
                      default=environ.get('OS_TENANT_ID'),
                      help='OpenStack tenant ID. Defaults to env[OS_TENANT_ID]')
    parser.add_option('--os_tenant_id',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-tenant-name',
                      metavar='<auth-tenant-name>',
                      default=environ.get('OS_TENANT_NAME'),
                      help='Openstack tenant name. '
                           'Defaults to env[OS_TENANT_NAME].')
    parser.add_option('--os_tenant_name',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-auth-url',
                      metavar='<auth-url>',
                      default=environ.get('OS_AUTH_URL'),
                      help='Openstack auth URL. Defaults to env[OS_AUTH_URL].')
    parser.add_option('--os_auth_url',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-auth-token',
                      metavar='<auth-token>',
                      default=environ.get('OS_AUTH_TOKEN'),
                      help='Openstack token. Defaults to env[OS_AUTH_TOKEN]')
    parser.add_option('--os_auth_token',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-storage-url',
                      metavar='<storage-url>',
                      default=environ.get('OS_STORAGE_URL'),
                      help='Openstack storage URL. '
                           'Defaults to env[OS_STORAGE_URL]')
    parser.add_option('--os_storage_url',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-region-name',
                      metavar='<region-name>',
                      default=environ.get('OS_REGION_NAME'),
                      help='Openstack region name. '
                           'Defaults to env[OS_REGION_NAME]')
    parser.add_option('--os_region_name',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-service-type',
                      metavar='<service-type>',
                      default=environ.get('OS_SERVICE_TYPE'),
                      help='Openstack Service type. '
                           'Defaults to env[OS_SERVICE_TYPE]')
    parser.add_option('--os_service_type',
                      help=SUPPRESS_HELP)
    parser.add_option('--os-endpoint-type',
                      metavar='<endpoint-type>',
                      default=environ.get('OS_ENDPOINT_TYPE'),
                      help='Openstack Endpoint type. ' \
                           'Defaults to env[OS_ENDPOINT_TYPE]')
    
    print 'here3'	
    parser.disable_interspersed_args()
    (options, args) = parse_args(parser, d_argv[1:], enforce_requires=False)
    parser.enable_interspersed_args()
    print 'here1'
    commands = ('delete', 'download', 'list', 'post', 'stat', 'upload')
    if not args or args[0] not in commands:
        parser.print_usage()
        if args:
            exit('no such command: %s' % args[0])
        exit()

    signal.signal(signal.SIGINT, attempt_graceful_exit)

    print_queue = Queue(10000)

    def _print(item):
        if isinstance(item, unicode):
            item = item.encode('utf8')
        print item

    print_thread = QueueFunctionThread(print_queue, _print)
    print_thread.start()

    error_count = 0
    error_queue = Queue(10000)

    def _error(item):
        global error_count
        error_count += 1
        if isinstance(item, unicode):
            item = item.encode('utf8')
        print >> stderr, item

    error_thread = QueueFunctionThread(error_queue, _error)
    error_thread.start()

def call_parser(d_args,d_argv):
    global parser
    global commands
    global print_thread
    global print_queue
    global error_count
    global error_queue
    global error_thread
	
    try:
        parser.usage = globals()['st_%s_help' % d_args[0]] 
        try:
            globals()['st_%s' % d_args[0]](parser, d_argv[1:], print_queue,
                                         error_queue)
        except (ClientException, HTTPException, socket.error), err:
            error_queue.put(str(err))
        while not print_queue.empty():
            sleep(0.01)
        print_thread.abort = True
        while print_thread.isAlive():
            print_thread.join(0.01)
        while not error_queue.empty():
            sleep(0.01)
        error_thread.abort = True
        while error_thread.isAlive():
            error_thread.join(0.01)
        if error_count:
            exit(1)
    except (SystemExit, Exception):
        for thread in threading_enumerate():
            thread.abort = True
        raise
if __name__ == '__main__':
  parser_prep(['modified_swift.py', '-A', 'http://10.245.122.31:8080/auth/v1.0', '-U', 'groupX:test1', '-K', 'test1pass', 'upload', 'testcontainer', 'ubuntu-12.04.1-server-i386.iso', '-S', '104857600'])
  print 'here'
  call_parser(['upload', 'testcontainer', 'ubuntu-12.04.1-server-i386.iso', '-S', '104857600'],   ['modified_swift.py', '-A', 'http://10.245.122.31:8080/auth/v1.0', '-U', 'groupX:test1', '-K', 'test1pass', 'upload', 'testcontainer', 'ubuntu-12.04.1-server-i386.iso', '-S', '104857600'])
