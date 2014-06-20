package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public  class MIPSInstruction {
	static Instruction ILOC_instruction;
	static ArrayList<Instruction>MIPSIns ;
	static int next_t_register=0;
	public static ArrayList<Instruction> getMIPSInstruction (Instruction ILOC_ins ){
		return MIPSIns = MIPSFromILOC(ILOC_ins);
		
	}
	static void debug(String s) {
		//System.out.println(s);
	}
	public static ArrayList<Instruction> MIPSFromILOC(Instruction iloc){
		ArrayList<Instruction> mips = new ArrayList<Instruction>();
		String t1,t2;
		debug(iloc.instructionName);
		if (iloc.instructionName.startsWith("#")){ // commented instruction
			mips.add(iloc);
		}
		else if (iloc.instructionName == "loadI"){
			String offset = SymbolTable.getInstance().getOffset(iloc.destination1);
			debug(offset);
			//String destMemory = String.format("%d(/$fp)",offset);
			String destMemory = offset+"($fp)";
			mips.add(new Instruction("li",iloc.source1,null,t1=next_t_Register()));
			mips.add(new Instruction("sw",destMemory,null,t1));
			reset_t_register();
			return mips;
		}
		else if (iloc.instructionName == "readInt") {			
			String offset = SymbolTable.getInstance().getOffset(iloc.destination1);
			//String destMemory = String.format("%d($fp)",offset);
			String destMemory = offset+"($fp)";
			mips.add(new Instruction("li","5",null,"$v0"));
			mips.add(new Instruction("syscall",null,null,null));
			mips.add(new Instruction("add","$v0","$zero",t1=next_t_Register()));
			mips.add(new Instruction("sw",destMemory,null,t1));
			reset_t_register();
			return mips;
		}
		else if (iloc2MipsCommand(iloc.instructionName) != null){ // add,sub, mul, div, mod
			String offset1 = SymbolTable.getInstance().getOffset(iloc.source1);			
			String sourceMemory1 = offset1+"($fp)";
			String offset2 = SymbolTable.getInstance().getOffset(iloc.source2);
			String sourceMemory2 = offset2+"($fp)";			
			String offset = SymbolTable.getInstance().getOffset(iloc.destination1);			
			String destMemory = offset+"($fp)";
			
			mips.add(new Instruction("lw",sourceMemory1,null,t1=next_t_Register()));
			mips.add(new Instruction("lw",sourceMemory2,null,t2=next_t_Register()));
			mips.add(new Instruction(iloc2MipsCommand(iloc.instructionName),t1,t2,t1));
			mips.add(new Instruction("sw",destMemory,null,t1));	
			reset_t_register();
			return mips;
		}
		else if (iloc.instructionName == "writeInt"){
			String offset = SymbolTable.getInstance().getOffset(iloc.source1);
			String destMemory = offset+"($fp)";
			mips.add(new Instruction("li","1",null,"$v0"));
			
			mips.add(new Instruction("lw",destMemory,null,t1=next_t_Register()));
			mips.add(new Instruction("add",t1,"$zero","$a0"));
			mips.add(new Instruction("syscall",null	,null,null));
			mips.add(new Instruction("li","4",null,"$v0"));
			mips.add(new Instruction("la","newline",null,"$a0"));
			mips.add(new Instruction("syscall",null	,null,null));
			reset_t_register();
			return mips;
		}
		else if (iloc.instructionName == "i2i"){
			String offset1 = SymbolTable.getInstance().getOffset(iloc.source1);
			String source1Memory = offset1+"($fp)";
			String offset2 = SymbolTable.getInstance().getOffset(iloc.destination1);
			String dest1Memory = offset2+"($fp)";
			mips.add(new Instruction("lw",source1Memory,null,t1=next_t_Register()));
			mips.add(new Instruction("add",t1,"$zero",t1));
			mips.add(new Instruction("sw",dest1Memory,null,t1));
			reset_t_register();
			return mips;
		}
		else if (iloc.instructionName == "jumpl"){
			mips.add(new Instruction ("j",null,null,iloc.destination1));
			return mips;
		}
		else if (iloc2mips4logicalOp(iloc.instructionName) != null) { //logical (less than / greater than)
			String mips_instruction = iloc2mips4logicalOp(iloc.instructionName);
			String offset1 = SymbolTable.getInstance().getOffset(iloc.source1);			
			String sourceMemory1 = offset1+"($fp)";
			String offset2 = SymbolTable.getInstance().getOffset(iloc.source2);
			String sourceMemory2 = offset2+"($fp)";			
			String offset = SymbolTable.getInstance().getOffset(iloc.destination1);			
			String destMemory = offset+"($fp)";
			mips.add(new Instruction("lw",sourceMemory1,null,t1=next_t_Register()));
			mips.add(new Instruction("lw",sourceMemory2,null,t2=next_t_Register()));
			mips.add(new Instruction(mips_instruction,t1,t2,t1 ));
			mips.add(new Instruction("sw",destMemory,null,t1));	
			reset_t_register();
			return mips;
		}
		else if (iloc.instructionName == "cbr") {
			debug("inside cbr:mipsinstruction");
			String offset1 = SymbolTable.getInstance().getOffset(iloc.source1);			
			String sourceMemory1 = offset1+"($fp)";
			mips.add(new Instruction("lw",sourceMemory1,null,t1=next_t_Register()));
			mips.add(new Instruction("bne","$zero",iloc.destination1,t1));
			mips.add(new Instruction("sw",sourceMemory1,null,t1));
			mips.add(new Instruction("j",null,null,iloc.destination2));
			reset_t_register();
			return mips;
		}
		return null;
		
	}
	public static String iloc2mips4logicalOp(String ilocOp){
		HashMap<String, String> hm = new HashMap<String,String>();
		hm.put("cmp_LT", "slt");
		hm.put("cmp_GT", "sgt");
		hm.put("cmp_LE", "sle");
		hm.put("cmp_GE", "sge");
		hm.put("cmp_EQ", "seq");
		hm.put("cmp_NE", "sne");
		if (hm.containsKey(ilocOp))return hm.get(ilocOp);
		return null;
	}
	public static String iloc2MipsCommand(String iloc){
		HashMap<String, String> hm = new HashMap<String,String>();
		hm.put("mul", "mul");
		hm.put("div", "div");
		hm.put("add", "addu");
		hm.put("sub", "subu");
		hm.put("rem", "rem");
		
		if (hm.containsKey(iloc))return hm.get(iloc);
		return null;
	}
	public static String next_t_Register(){
		return "$t"+next_t_register++;
	}
	public static void reset_t_register(){
		next_t_register = 0;
	}
}
