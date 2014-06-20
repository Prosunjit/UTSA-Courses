package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.HashMap;

class Tokenizer{
	   
	   ArrayList<Token> TokenList = new ArrayList<Token>();
	   public HashMap lexicalElements(){
	    HashMap<String, String> hm = new HashMap();
	    hm.put("if",new String("IF"));
	    hm.put("then",new String("THEN"));
	    hm.put("else",new String("ELSE"));
	    hm.put("begin",new String("BEGIN"));
	    hm.put("end",new String("END"));
	    hm.put("while",new String("WHILE"));
	    hm.put("do",new String("DO"));
	    hm.put("program",new String("PROGRAM"));
	    hm.put("var",new String("VAR"));
	    hm.put("as",new String("AS"));
	    hm.put("int",new String("INT"));
	    hm.put("bool",new String("BOOL"));
	    hm.put("writeInt",new String("WRITEINT"));
	    hm.put("readInt",new String("READINT"));
	    hm.put(";",new String("SC"));
	    hm.put("(",new String("LP"));
	    hm.put(")",new String("RP"));
	   return hm;
	   }
	  
	  ArrayList<Token> getTokens(){
	      return TokenList;
	  }
	  public  Tokenizer (String fileName){
	    
	   //ArrayList<Token> TokenList = new ArrayList<Token>();
	    String []words = FileOperation.readFile(fileName);
	    HashMap<String, String> hm = lexicalElements();
	   
	    for (String w: words){
	      if( hm.get(w) != null) {
	        TokenList.add(new Token( hm.get(w), w));
	      }
	      else if (!MatchRegex.match(w).equals("")) {
	        TokenList.add(new Token(MatchRegex.match(w),w));
	      }      
	      
	    }
	   
	  }
	}