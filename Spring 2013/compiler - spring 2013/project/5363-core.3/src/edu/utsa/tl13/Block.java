package edu.utsa.tl13;

import java.util.ArrayList;

public class Block {
	public String code;
	public ArrayList<String> ILOCCode;
	public ArrayList<Instruction> instructions;
	public ArrayList<Instruction> MIPS_instruction;
	public String name;
	public ArrayList <Block> parents;
	public ArrayList<Block> childBlock ;
	public boolean visited = false;
	public boolean visited4mips = false;
	
	public void addChildBlock(Block b1){
		childBlock.add(b1);
	}
	public void addParent(Block b){
		parents.add(b);
	}
	public Block( ){
		parents = new ArrayList<Block>();
		childBlock = new ArrayList<Block>();
		ILOCCode = new ArrayList<String>();
		instructions = new ArrayList<Instruction>();
		MIPS_instruction = new ArrayList<Instruction>();
		this.name = "B"+(++Constant.current_block);
	}
	public String getName(){
		return this.name;
	}
	public void addInstruction(Instruction i) {
		instructions.add(i);
	}
	public void addILOCCode(String iloc){
		//System.out.println("Block:"+iloc);
		code += "\n" + iloc;
		ILOCCode.add(iloc);
	}
	public ArrayList<String> getAllCode(){
		return ILOCCode;
	}
	public void debug(String s){
		//System.out.println(s);
	}
	public ArrayList<Instruction> getMIPSCode(){
		ArrayList<Instruction> t = new ArrayList<Instruction>();
		//debug(instructions.size()+"");
		
		for (Instruction i:instructions) {
			debug(i.instructionName);
			t = MIPSInstruction.MIPSFromILOC(i);
			//debug("-->"+t.toString());
			i.instructionName = "# " + i.instructionName;
			debug(i.instructionName);
			MIPS_instruction.add(i);
			if (t != null)
				for (Instruction i_m: t){
					MIPS_instruction.add(i_m);
				}
			else {
				debug("nothing comes for " + i.instructionName);
			}
		}
		
		
		return MIPS_instruction;
	}
	
}
