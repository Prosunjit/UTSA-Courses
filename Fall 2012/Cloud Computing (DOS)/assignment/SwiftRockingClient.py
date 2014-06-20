#!/usr/bin/python
import sys
import socket
import modified_swift


SERVER="http://10.245.122.31:8080/auth/v1.0"
connection=False;
CONTAINER = None;
FILE = None;
TENANT_USER = None;
USER_PASS = None;
SEGMENT_SIZE = None;

def debug(msg):
  print msg

def lost_connection():
  print "Ups... connection is lost... :("
  menu()

def download_file():
  global connection
  global USER_PASS
  global TENANT_USER
  global FILE
  global CONTAINER
  global SERVER

  FILE = raw_input("Enter single file Name to Download: ")
  CONTAINER  = raw_input("Name of the Container on the server: ")
  if connection:
    try:
       modified_swift.parser_prep(['modified_swift', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'download', CONTAINER, FILE])
       modified_swift.call_parser(['download', CONTAINER, FILE] ,['modified_swift', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'download', CONTAINER, FILE])
       print "File Successfully downloaded :-) "
       menu()
    except Exception as e:
       print "Something bad happened: ", e

    menu()
  else:
    lost_connection()

def delete_file():
  global connection
  global USER_PASS
  global TENANT_USER
  global FILE
  global CONTAINER
  global SERVER

  FILE = raw_input("Enter file Name to Delete: ")
  CONTAINER  = raw_input("Name of the Container on the server: ")
  if connection:
    try:
       modified_swift.parser_prep(['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'delete', CONTAINER, FILE])
       modified_swift.call_parser(['delete', CONTAINER, FILE] ,['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'delete', CONTAINER, FILE])
       print "File Successfully deleted :-) "
       menu()
    except Exception as e:
       print "Something bad happened: ", e

    menu()    
  else:
    lost_connection()

def regularLogin():
 global connection
 global USER_PASS
 global TENANT_USER
 global SERVER
 
 TENANT_USER = raw_input("Enter User Name (tenant:user): ")
 USER_PASS = raw_input("Enter Password:")
 try:
   modified_swift.parser_prep(['modified_swift', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'stat']) 
   modified_swift.call_parser(['stat'], ['modified_swift', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'stat'])
 except Exception as e:
   print "User/password or Connection problem",e
   menu()
 connection = True
 print "user",TENANT_USER, " is is now connected. :-)"
 menu()

def upload_small_objeect():
  global connection
  global USER_PASS
  global TENANT_USER
  global FILE
  global CONTAINER
  global SERVER

  FILE = raw_input("Enter file Name to put: ")
  CONTAINER  = raw_input("Name of the Container on the server: ")
  if connection:
    try:
       modified_swift.parser_prep(['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'upload', CONTAINER, FILE])
       modified_swift.call_parser(['upload', CONTAINER, FILE],['modified_swift.py', '-A', 'http://10.245.122.31:8080/auth/v1.0', '-U', TENANT_USER, '-K', USER_PASS, 'upload', CONTAINER, FILE])
       print "File Successfully uploaded :-) "
       menu()
    except Exception as e:
       print "Something bad happened: ", e

def upload_large_file():
  global connection
  global USER_PASS
  global TENANT_USER
  global FILE
  global CONTAINER
  global SERVER

  filename = raw_input("Enter name of the large file: ")
  path = raw_input("Enter absolute path of the file (with / at the end): ")
  FILE = path + filename
  CONTAINER = raw_input("Name of the Container on the server: ")
  SEGMENT = raw_input("Enter segment size (MB): ")
  SIZE = int(SEGMENT) * pow(2,20)
  debug(SIZE )
  if not connection:
     print "You have to login first."
     menu()
  try:
      modified_swift.parser_prep(['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'upload', CONTAINER, FILE, '-S', SIZE])
      modified_swift.call_parser(['upload', CONTAINER, FILE, '-S', SIZE],['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'upload', CONTAINER, FILE, '-S', SIZE])
      print "Successfully uploaded... ;) "  
  except Exception as e:
    print "Something bad happened. ", e
  
  menu()

def list_object():
  global connection
  global USER_PASS
  global TENANT_USER
  global FILE
  global CONTAINER
  global SERVER
  CONTAINER = raw_input("Name of the Container on the server: ")
  global connection
  if not connection:
    lost_connection()
  print "files in the ", CONTAINER, " are following: "
  try:
     modified_swift.parser_prep(['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'list', CONTAINER])
     modified_swift.call_parser(['list', CONTAINER] , ['modified_swift.py', '-A', SERVER, '-U', TENANT_USER, '-K', USER_PASS, 'list', CONTAINER])
     
  except Exception as e:
    debug('connection does not exist')
    menu()
  menu()

def create_container():
  global connection
  container=raw_input("Enter name of the container to create: ")
  if connection: 
    print "container ", container, " is created successfully"
  else:
    print "Ups....connection does not exist. Login first." 
  menu()

def menu():
  actions={'l':'login Regular','L':'Admin Login','u':'Upload Small Object','U':'Upload Large object','I':'lIst Objects','q':'Quit','c':'Create Container','d':'Delete Object','w':'Download file'}
  print "          **Menu**         ","\n"
  for k, v in actions.items():
    print  'press ',k,' to ', v

  choice =  raw_input("Your choice is?")

  if choice == 'l':
    print "your choice is",choice
    regularLogin()
  elif choice == 'L':
    print "your choice is",choice
  elif choice == 'u':
    upload_small_objeect()
  elif choice == 'U':
     upload_large_file()
  elif choice == 'I': 
    list_object()
  elif choice == 'w':
    download_file() 
  elif choice == 'c':
    create_container();   
  elif choice  == 'd':
    print 'you entered d'
    delete_file()   
  else:
    print ('okay, I am quitting.')  
    sys.exit(0)
def main():
  menu()
 
    
if __name__ == '__main__':
    main()

