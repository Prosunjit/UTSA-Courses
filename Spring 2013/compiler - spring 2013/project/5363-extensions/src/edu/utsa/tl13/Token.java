package edu.utsa.tl13;

import java.util.Arrays;

class Token {

	   // method main(): ALWAYS the APPLICATION entry point
	   public String Token_type;
	   public String Token_value;
	   public String Display_string;
	   
	   public Token(String type, String value){
	     this.Token_type = type;
	     this.Token_value = value;  
	     if ( Arrays.asList("num","ident","boollit").contains(type)) this.Display_string = type + " : " + value;
	     else if ( Arrays.asList("OP2","OP3","OP4","ASSGN","SC","LP","RP").contains(type)) this.Display_string = value;
	     else this.Display_string = type;
	   }
	      
}