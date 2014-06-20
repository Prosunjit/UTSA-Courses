package edu.utsa.tl13;

import java.util.HashMap;

public class SymbolTable {
	private static SymbolTable instance;
	private HashMap<String, Record> hm;
	private SymbolTable(){		
		hm = new HashMap<String,Record>();
	}
	public static SymbolTable getInstance(){
		if (instance == null)
			instance = new SymbolTable();
		return instance;
	}
	public void addToSymbolTable(String identifier, String type){
		Record r = new Record(identifier,type);		
		r.setOffset( "@"+identifier);  
		hm.put(identifier,r);
	}
	public String getType(String identifier){
		if (hm.containsKey(identifier)){
			return hm.get(identifier).type;
		}
		return null;
	}
	public String getOffset(String identifier){
		if (hm.containsKey(identifier)){
			 return hm.get(identifier).offset;
		}
		return null;
	}
	public String getBaseAddress(String identifier){
		if (hm.containsKey(identifier)){
			 return hm.get(identifier).baseAddress;
		}
		return null;
	}
	public void setBaseNOffset(String identifier, String baseaddr, String offset){
		if (hm.containsKey(identifier)){
			 Record r = hm.get(identifier);
			 r.set_baseAddress(baseaddr);
			 r.setOffset(offset);
			 
		}
	}
	
	
}
