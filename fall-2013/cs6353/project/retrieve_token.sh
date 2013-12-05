#!/bin/bash


LIMIT=1000
for i in {1..1000}

  do
    echo 'retrieving token'
    curl -i http://10.245.121.19:5000/v2.0/tokens -X POST -H "Content-Type: application/json" -H "Accept: application/json" -H "User-Agent: python-novaclient" -d '{"auth": {"tenantName": "demo", "passwordCredentials": {"username": "demo1", "password": "admin1"}}}'> token.log

    X=`tail -n 1 token.log` 
    Y=`echo $X > token.log`
    Z=`python MessageFilter.py token.log`
    F=`echo $Z>>keystone_tokens.log`
  done

#echo $TOKEN

