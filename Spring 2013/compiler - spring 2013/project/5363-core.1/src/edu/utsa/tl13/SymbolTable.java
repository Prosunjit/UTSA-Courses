package edu.utsa.tl13;

import java.util.HashMap;

public class SymbolTable {
	private static SymbolTable instance;
	private HashMap<String, String> hm;
	private SymbolTable(){		
		hm = new HashMap<String,String>();
	}
	public static SymbolTable getInstance(){
		if (instance == null)
			instance = new SymbolTable();
		return instance;
	}
	public void addToSymbolTable(String identifier, String value){
		hm.put(identifier,value);
	}
	public String getType(String identifier){
		if (hm.containsKey(identifier)){
			return hm.get(identifier);
		}
		return null;
	}
	
}
