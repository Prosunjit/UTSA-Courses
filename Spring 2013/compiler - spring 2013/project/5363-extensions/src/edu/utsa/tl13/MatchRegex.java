package edu.utsa.tl13;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MatchRegex{
	public static String match(String s){

		if (matchSymbol("[1-9][0-9]*|0",s) ) return "num"; 
		if (matchSymbol("false|true",s) ) return "boollit"; 
		if (matchSymbol("[A-Z][A-Z0-9]*",s) ) return "ident";  
		if (matchSymbol("[*]|div|mod",s) ) return "OP2"; 
		if (matchSymbol("[+-]",s) ) return "OP3";   
		if (matchSymbol("[=]|[!][=]|[<>]|[<][=]|[>][=]",s) ) return "OP4";
		if (matchSymbol("[:][=]",s) ) return "ASSGN";
		return "";
	}
	public static boolean matchSymbol(String Regex, String input ){
		Pattern pattern = Pattern.compile(Regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches())
			return true;
		return false;
	}
}