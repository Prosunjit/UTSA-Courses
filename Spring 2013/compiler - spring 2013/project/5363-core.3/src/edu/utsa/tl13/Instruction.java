package edu.utsa.tl13;

public class Instruction {
	String instructionName;
	String source1;
	String source2;
	String destination1;
	String destination2;
	
	public Instruction (String name, String source1, String source2, String dest){		
		this.source1 = this.source2 = this.destination1 = this.destination2 = null;		
		this.instructionName = name;
		this.source1 = source1;
		this.source2 = source2;
		this.destination1 = dest;
	}
	public Instruction (String name, String source1, String source2, String dest1, String dest2){		
		this(name,source1,source2,dest1);
		this.destination2 = dest2;
		
	}
	
}
