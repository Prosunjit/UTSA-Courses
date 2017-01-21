package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.HashMap;

class Tokenizer{

	ArrayList<Token> TokenList = new ArrayList<Token>();
	public HashMap<String, String> lexicalElements(){
		HashMap<String, String> hm = new HashMap<String, String>();
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
		hm.put("[", new String("ArrayStart"));
		hm.put("]", new String("ArrayEnd"));
		hm.put("exit",new String("EXIT"));
		return hm;
	}

	ArrayList<Token> getTokens(){
		return TokenList;
	}
	public  Tokenizer (String[] words){

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
	public  Tokenizer (ArrayList<String> words){

		HashMap<String, String> hm = lexicalElements();
		String w;
		int index = 0;
		
		while ( index < words.size() && words.get(index) != null ){
			w = words.get(index);
			if( hm.get(w) != null) {
				TokenList.add(new Token( hm.get(w), w));
			}
			else if (!MatchRegex.match(w).equals("")) {
				TokenList.add(new Token(MatchRegex.match(w),w));
			}      
			index ++;
		}

	}
}