package edu.utsa.tl13;

public class Register {
	public static int index = -1;
	
	public Register(){
		//index = -1;
	}
	public static String nextRegister(){
		index ++;
		return "r_"+index;
	}
	public static String getNamedRegister(String name){		
		return "r_"+name;
	}
	
	
}
