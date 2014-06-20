package edu.utsa.tl13;

public class Record {
	public String name;
	public String value;
	public String baseAddress;
	public String offset;
	public boolean inRegister;
	public String type;
	public boolean array;
	
	public Record(String identifier_name, String type){
		inRegister = false;
		baseAddress = null;	
		offset = null;
		array = false;
		this.type = type;
		this.name = identifier_name;		
	}
	public void setOffset(String offset){
		this.offset = offset;
	}
	public String getOffset (){
		return this.offset;
	}
	public void  set_baseAddress(String baseAddress){
		 this.baseAddress = baseAddress;
		 this.inRegister = true;
	}
	
}
