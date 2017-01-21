#!/usr/bin/python
import sys
import socket

def menu():
  actions={'l':'login Regular','L':'Admin Login','u':'Upload Small Object','U':'Upload Large object','I':'lIst Objects'}
  print "**Menu**","\n"
  for k, v in actions.items(): 
    print  'press ',k,' to ', v
  
  choice =  raw_input("Your choice is?")

  if choice == 'l':
    print "your choice is",choice
  elif choice == 'L':
    print "your choice is",choice 
  elif choice == 'u':
    print "your choice is",choice
  elif choice == 'U':
    print "your choice is",choice
  elif choice == 'I':
    print "your choice is",choice
  elif choice == 'D':
    print "your choice is",choice

  

def main():
  menu()

if __name__ == '__main__':
    main()
