package edu.utsa.tl13;

public class Register {
	public static int index = -1;
	public static SymbolTable st;
	public Register(){
		//index = -1;
		st = SymbolTable.getInstance();
		
	}
	public static String nextRegister(){
		if (st == null) st = SymbolTable.getInstance();
		index ++;
		String newRegister = "r"+index;
		st.addToSymbolTable(newRegister, "register");
		return newRegister;
	}
	public static String getNamedRegister(String name){		
		String newRegister = "r_"+name;
		st.addToSymbolTable(newRegister, "register");
		return newRegister;
	}
	
	
}
