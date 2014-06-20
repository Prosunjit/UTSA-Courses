package edu.utsa.tl13;

import java.util.ArrayList;

public class Block {
	public String code;
	public ArrayList<String> ILOCCode;
	public String name;
	public ArrayList <Block> parents;
	public ArrayList<Block> childBlock ;
	public boolean visited = false;
	
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
		this.name = "B"+(++Constant.current_block);
	}
	public String getName(){
		return this.name;
	}
	public void addILOCCode(String iloc){
		//System.out.println("Block:"+iloc);
		code += "\n" + iloc;
		ILOCCode.add(iloc);
	}
	public ArrayList<String> getAllCode(){
		return ILOCCode;
	}
	
}
