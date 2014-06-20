#!/usr/bin/python
import sys
import socket
import swiftClient


SERVER="http://10.245.122.31:8080/auth/v1.0"
connection=None;
CONTAINER = None;
FILE = None;
TENANT_USER = None;
USER_PASS = None;
SEGMENT_SIZE = None;

def debug(msg):
  print msg

def delete_file():
  print 'test'
  global connection
  file_name=raw_input('Enter object name to delete: ')
  container = raw_input('Enter name of the container: ')
  if connection:
     connection.delete_object(file_name,container)
     print 'Seccessfully deleted. '
  else:
     print 'Ups... connection does not exist'
  menu()    

def regularLogin():
 global connection
 user=raw_input("Enter User Name (tenant:user)?")
 password=raw_input("Enter Password:")
 connection = swiftClient.Connection(SERVER, user, password, 5,None,None,False,1 ,"groupX",None,"1") 
 debug(connection)
 print "user",user, " is is now connected. :-)"
 menu()

def upload_small_objeect():
  global connection
  filename=raw_input("Enter file Name to put: ")
  content=raw_input("Content of the file in text: ")
  container=raw_input("Name of the Container on the server: ")
  connection.put_object(container,filename,content)
  menu()

def upload_large_file():
  global connection
  filename=raw_input("Enter name of the large file: ")
  path=raw_input("Enter absolute path of the file (with / at the end): ")
  container=raw_input("Name of the Container on the server: ")
  try:
    f = open(path+filename)
  except IOError:
    print "Error: can't find file or read data"
  if connection:
    connection.put_object(container,filename,f)
  else:
    print "ups..Connection does not exist"
  menu()

def list_object():
  
  container=raw_input("Enter Container Name: ")
  global connection
  if connection:
    meta,files =  connection.get_container(container) 
    print container, "has these objects:"
    for f in files:
      print f['name'];
    menu()
  else:
    debug('connection does not exist')

def create_container():
  global connection
  container=raw_input("Enter name of the container to create: ")
  if connection: 
    connection.put_container(container)
    print "container ", container, " is created successfully"
  else:
    print "Ups....connection does not exist. Login first." 
  menu()

def menu():
  actions={'l':'login Regular','L':'Admin Login','u':'Upload Small Object','U':'Upload Large object','I':'lIst Objects','q':'Quit','c':'Create Container','d':'Delete Object'}
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
  elif choice == 'D':
    print "your choice is",choice 
  elif choice == 'c':
    create_container();   
  elif choice  == 'd':
    print 'you entered d'
    delete_file()   
  
    
def main():
  menu()
 
def adminLogin():
 x=0
def upload_large_object():
 x=0
def retrieve_small_object():
 x=0
def retrieve_big_object():
 x=0
def delete_object():
 x=0
    
if __name__ == '__main__':
    main()

