#!/bin/sh
DIR="/root/dot_product"
IPS="$1"
COPY="~/.ssh/id_rsa ~/.ssh/id_rsa.pub"
ICMD="pkgin up; pkgin in gcc47-4.7.0nb1; wget http://my.cs.utsa.edu/~ccardena/pkgs/2012Q1/i386/openmpi-1.6nb1.tgz; pkg_add openmpi-1.6nb1.tgz; pkg_add openmpi-1.6nb1.tgz; crle -l /opt/local/lib -u"
MPI="scp $DIR"

for ip in $IPS; do
    echo "Copying SSH Keys to $ip"
    echo "scp"  $COPY "root@$ip:.ssh"
         "scp" $COPY "root@$ip:.ssh"
    echo "Altering Permissions"
    echo ssh root@$ip chmod 600 ".ssh/id_rsa*"
    ssh root@$ip chmod 600 ".ssh/id_rsa*"
    echo "Install Files"
    echo ssh root@$ip $ICMD
    ssh root@$ip $ICMD
    echo "Copying MPI files"
    echo $MPI root@$ip:
    $MPI root@$ip:
done

