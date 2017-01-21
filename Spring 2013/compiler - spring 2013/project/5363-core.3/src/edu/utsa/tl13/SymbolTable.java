package edu.utsa.tl13;

import java.util.HashMap;

public class SymbolTable {
	private static SymbolTable instance;
	private HashMap<String, Record> hm;
	private static int offset = 0;
	
	private SymbolTable(){		
		hm = new HashMap<String,Record>();
	}
	public static SymbolTable getInstance(){
		if (instance == null)
			instance = new SymbolTable();
		return instance;
	}
	public void debug(String s) {
		//System.out.println(s);
	}
	public int getNsetNextOffset( int size){
		offset = offset - size;
		return offset + size;
	}
	public void addToSymbolTable(String identifier, String type){
		Record r = new Record(identifier,type);		
		
		r.setOffset( ""+offset);;  
		debug("Adding to ST "+identifier  +":"+offset);
		offset -= 4;
		hm.put(identifier,r);
	}
	public String getType(String identifier){
		if (hm.containsKey(identifier)){
			return hm.get(identifier).type;
		}
		return null;
	}
	public String getOffset(String identifier){
		debug("Accessing symboltable " + identifier);
		if (register_mapped_variable(identifier) != null )
			identifier = register_mapped_variable(identifier);
		if (hm.containsKey(identifier)){
			debug("return offset for  " + identifier + hm.get(identifier).offset);
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
	public String register_mapped_variable(String registerName){ //
		if (registerName.contains("_")) return registerName.substring(1+registerName.indexOf("_")) ;
		return null;
	}
	
	
}
