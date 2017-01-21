package edu.utsa.tl13;

public class Instruction {
	String instructionName;
	String source1;
	String source2;
	String destination1;
	String destination2;
	String s1_index ;
	String s2_index ;
	String d1_index ;
	String d2_index ;
	
	public Instruction (String name, String source1, String source2, String dest){		
		this.source1 = this.source2 = this.destination1 = this.destination2 = null;		
		this.instructionName = name;
		this.source1 = source1;
		this.source2 = source2;
		this.destination1 = dest;
		s1_index=s2_index=d1_index=d2_index = null;
	}
	public Instruction (String name,String source1,String s1_index,String source2, String s2_index,String dest1, String d1_index){
		this(name,source1,source2,dest1);
		this.s1_index = s1_index;
		this.s2_index = s2_index;
		this.d1_index = d1_index;
	}
	public Instruction (String name,String source1,String s1_index,String source2, String s2_index,String dest1,String d1_index, String dest2, String d2_index){
		this(name,source1,source2,dest1,dest2);
		this.s1_index = s1_index;
		this.s2_index = s2_index;
		this.d1_index = d1_index;
		this.d2_index = d2_index;
	}
	public Instruction (String name, String source1, String source2, String dest1, String dest2){		
		this(name,source1,source2,dest1);
		this.destination2 = dest2;
		
	}
	
}
