package edu.utsa.tl13;

import java.util.ArrayList;

public class LexicalAnalyzer {
	
	public String content;
	
	public LexicalAnalyzer(String s){
		content = s;
	}
	public ArrayList<String> getTokens(){
		
		return processDFA(content);
	}
	public String breakSequence( String s, int index){
		char ch = s.charAt(index);
		
		if (ch == ' ') return "";
		else if (ch == '=')return "=";
		else if (ch == '('){
			 return "(";
		 }else if (ch == ')'){
			 return ")";
		 } else if (ch =='+' ) return "+";
		 else if (ch == '-') return "-";
		 else if (ch == '[') return "[";
		 else if (ch == ']') return "]";
		 else if (ch == '*') return "*";
		 else if (ch == ';') return ";";
		 else if (ch == '<'){
			 if ( s.charAt(index+1) == '=') return "<=";
			 else return "<";			 
		 }
		 else if (ch == '>') {
			 if (s.charAt(index+1) == '=') return ">=";
			 return ">";
		 }
		 else if (ch == ':'){
			 if (s.charAt(index+1) == '=') return ":=";
			 else return ":";
		 }
		 else if ( ch == '!'){
			 if (s.charAt(index+1) == '=') return "!=";
			 return "!";
		 }
		 return null;
	}
	public void debug(String s) {
		//System.out.println(s);
	}
	public ArrayList<String> processDFA(String s){
		
		ArrayList<String> tokens = new ArrayList<String>();
		int index = 0;  
		int state = 0; // s0 is the beginning state.
		String token = "", splitter;
		int commentDepth = 0;
		
		while (index < s.length() ){
			char ch = s.charAt(index);
			switch (state) {
			case 0: { // beginning state
				token = "";
				//debug(""+index + state);
				if ( Character.isLetter(ch) == true){
					state = 1;	
					index -- ; // just change state, don't consume letter.
				} else if(Character.isDigit(ch) == true) {
					state = 2;
					index -- ; // just change state, don't consume letter.
				} else if ( ch == '%' && s.charAt(index+1) == '-'){
					index ++;
					state = 3;
					commentDepth ++;
					
				}else if ((splitter = breakSequence(s,index)) != null){
					if (splitter != null && splitter != "")
						tokens.add(splitter);
					if (splitter.length()>1) 
						index = index + splitter.length() -1 ; // if splitter is >= or <= and so on.
				}
				
				break;
			}
				
			case 1 : { // identifier, variable, token
				if ( Character.isLetter(ch)){
					token += ch;
				}
				else if (Character.isDigit(ch)){
					token += ch;
				}
				else if ( (splitter = breakSequence(s,index)) != null){
					tokens.add(token);
					if (splitter != "") // does not count space as token
						tokens.add(splitter);
					if (splitter.length()>1) 
						index = index + splitter.length() -1 ; // if splitter is >= or <= and so on.
					state = 0;
				}
				break;
				
			}
			case 2: { // Constant
				if ( Character.isDigit(ch)) {
					token += ch;
				}
				else if ( (splitter = breakSequence(s,index)) != null){
					tokens.add(token);
					if (splitter != "")
						tokens.add(splitter);
					if (splitter.length()>1) 
						index = index + splitter.length() -1 ; // if splitter is >= or <= and so on.
					state = 0;
				}
				break;
			}
			case 3: { // multiline comment.
				
				if ( ch == '-' && s.charAt(index+1) == '%') {
					index ++;
					commentDepth --;
					if (commentDepth == 0)
						state = 0;
				}
				else if (ch == '%' && s.charAt(index+1) == '-') {
					commentDepth ++;
					index ++;
				}
				else {
					token += ch;
				}
				break;
			}
			default : 	{
				
			}
			
		}
		index ++;
		
	}
	return tokens;
	//tokens.
}
}
