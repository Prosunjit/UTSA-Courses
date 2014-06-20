package edu.utsa.tl13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ILOC {
	public static CFGGV gv = null ;
	public static MIPSWriter mw = null ;
	static String  fileName = "program.iloc.cfg.dot";
	static String  mips_fileName = "program.s";
	public ILOC(){
		try {
			gv = new CFGGV(fileName);
			mw = MIPSWriter.getInstance(mips_fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void SetCFGFileName(String name){
		fileName = name;
	}
	public static void setMIPSFileName(String name){
		mips_fileName = name;
	}
	
	public static Block ILOC4DeclarationList(Block inBlock, DeclarationList dl){
		
		
		for (Declaration d: dl.declarations){
			String t1 = expressionILOC(inBlock, d.ident);
			inBlock.addILOCCode(String.format("loadI 0 =&gt; %s",t1));
			ArrayList<String> arr_elm = array_elements(t1);
			String name_t1 = arr_elm.get(0);
			Instruction ins = new Instruction("loadI","0",null,name_t1);
			if (arr_elm.size()>1){
				ins.d1_index = arr_elm.get(1);
			}
			inBlock.addInstruction(ins);
		}
		return inBlock;
	}
	
	public static void PrintILOCCode(Block block) throws IOException{
		if (gv == null) gv = new CFGGV(fileName);
		gv.Process_Block_code(block);
		for (Block b: block.parents){
			debug("parents edge" + block.getName() + ":" + b.getName());
			gv.addEdge(block.getName(), b.getName());
		
		}
		for (Block b: block.childBlock){
			if (b.visited == false) {
				gv.addEdge(block.getName(), b.getName());
				PrintILOCCode(b);
			}
			else {
				gv.addEdge(block.getName(), b.getName());
			}
		}
		debug("iloc print start2");
	}

	public static void PrintMIPSCode(Block block) throws IOException{
		if (mw == null) mw = MIPSWriter.getInstance(mips_fileName);
		
		mw.Process_Block_code(block);
		block.visited4mips = true;
		for (Block b: block.childBlock){
			if (b.visited4mips == false ) {
				PrintMIPSCode(b);
			}
		}
	}

	public  static void completeWriting(Block start, Block end) throws IOException{
		gv.write_line("}\n");
		gv.write_line(String.format("entry -> %s",start.getName()));
		gv.write_line(String.format("%s -> %s",end.getName(),"exit"));
		gv.write_line(String.format("}"));
		gv.completeWriting();
	}
	public  static void completeMIPSWriting() throws IOException{
		
		mw.completeWriting();
	}
	
	public static Block ILOC4While(Block inBlock, GeneralStatement gs){
		
		Block b = inBlock;
		Block expBlock = new Block();
		Block stmtBlock = new Block();
		Block exitBlock = new Block();
		
		String name_t1=null,index_t1=null;
		
		

		WhileStatement ws = (WhileStatement)gs;
		
		inBlock.addILOCCode(String.format("jumpl -&gt; %s", expBlock.getName()));
		inBlock.addInstruction(new Instruction("jumpl",null,null,expBlock.getName()));
		inBlock.addChildBlock(expBlock);
		
		
		String t1 = expressionILOC(expBlock, ws.exp);
		ArrayList<String> arr_elm = array_elements(t1);
		name_t1 = arr_elm.get(0);
		Instruction ins = new Instruction("cbr",name_t1,null,stmtBlock.getName(),exitBlock.getName());
		if (arr_elm.size()>1){
			index_t1 = arr_elm.get(1);
			ins.s1_index = index_t1;
		}
		expBlock.addILOCCode(String.format("cbr %s -&gt; %s , %s",t1,stmtBlock.getName(),exitBlock.getName()));
		expBlock.addInstruction(ins);
		Block stmt_exit_block = ILOC4StatementList(stmtBlock, ws.sl);
		stmt_exit_block.addILOCCode(String.format("jumpl -&gt; %s",expBlock.getName()));
		stmt_exit_block.addInstruction(new Instruction("jumpl",null,null,expBlock.getName()));
		stmt_exit_block.addParent(expBlock);
		
		expBlock.addChildBlock(stmtBlock);
		expBlock.addChildBlock(exitBlock);
		
		//stmt_exit_block.addChildBlock(expBlock);
		return exitBlock;
		
	}
	
	public static Block ILOC4IF(Block inBlock, GeneralStatement gs){
		
		IfStatement is = (IfStatement) gs;
		
		Block if_start_blk = inBlock; // it will append incremental number
		Block if_blk = new Block();
		Block else_blk = new Block();
		Block exit_blk = new Block();
		String t1,t2,t3;
		String name_t1=null,index_t1=null;
		
		t1 = expressionILOC(if_start_blk, is.exp);
		ArrayList<String> arr_elm = array_elements(t1);
		name_t1 = arr_elm.get(0);
		if (arr_elm.size()>1){
			index_t1 = arr_elm.get(1);
		}
		if (is.elseStatements.statementList.size()> 0){
			if_start_blk.addILOCCode(String.format("cbr %s =&gt; %s, %s",t1,if_blk.getName(),else_blk.getName()));
			Instruction ins = new Instruction("cbr",t1,null,if_blk.getName(),else_blk.getName());
			if (index_t1 != null)
				ins.s1_index = index_t1;
			inBlock.addInstruction(ins);
			
		}else {
			if_start_blk.addILOCCode(String.format("cbr %s =&gt; %s, %s",t1,if_blk.getName(),exit_blk.getName()));
			Instruction ins = new Instruction("cbr",t1,null,if_blk.getName(),exit_blk.getName());
			if (index_t1 != null)
				ins.s1_index = index_t1;
			inBlock.addInstruction(ins);
		}
		
		if_start_blk.addChildBlock(if_blk);
		
		Block if_stmt_end_block = ILOC4StatementList(if_blk, is.ifStatements);
	
		if_stmt_end_block.addChildBlock(exit_blk);
		
	
		if (is.elseStatements.statementList.size()> 0){
			if_start_blk.addChildBlock(else_blk);
			Block else_stmt_end_block = ILOC4StatementList(else_blk, is.elseStatements);
			else_stmt_end_block.addChildBlock(exit_blk);
		} else {
			if_start_blk.addChildBlock(exit_blk);
		}
		
		return exit_blk;
		
	}
	public static void debug (String s){
		//System.out.println(s);
	}
	public static  Block ILOC4StatementList (Block inBlock, StatementList sl){
		
		Block b = inBlock;
		for (GeneralStatement gs: sl.statementList){
			//debug("IlocStmt"+gs.getClass().getName());
			if (gs.getClass().getName().equals(Constant.ASSIGNMENT)){
				b = ILOC4Assignment(b, gs);
			}
			else if (gs.getClass().getName().equals(Constant.WRITEINT)){
				b = ILOC4WriteInt (b, gs);
			}
			else if (gs.getClass().getName().equals(Constant.IFSTATEMENT)){
				b = ILOC4IF(b, gs);
			}
			else if (gs.getClass().getName().equals(Constant.WHILESTATEMENT)){
				b = ILOC4While(b, gs);
			}
			else if (gs.getClass().getName().equals(Constant.EXITSTATEMENT)){
				b = ILOC4Exit(b, gs);
			}
			
		}
		//debug("stmnt ends");
		return b;
	}
	
	public static Block ILOC4Exit (Block inBlock, GeneralStatement gs){
		inBlock.addILOCCode(String.format("exit"));
		inBlock.addInstruction(new Instruction("exit",null,null,null,null,null,null)	);
		return inBlock;
	}
	
	public static Block ILOC4ReadInt (Block inBlock, GeneralStatement gs){
	
		return null;
	}
	public static ArrayList<String> array_elements(String ar){
		ArrayList<String> nameNIndex= new ArrayList<String>();
		String name="", index="";
		if (ar.contains("[")){
			name= ar.substring(0, ar.indexOf('[') );
			index = ar.substring(ar.indexOf('[')+1,ar.indexOf(']'));
			nameNIndex.add(name);
			nameNIndex.add(index);			
			return nameNIndex;
		}
		
		nameNIndex.add(ar);
		return nameNIndex;
	}
	public static Block ILOC4WriteInt(Block inBlock, GeneralStatement gs){
		String t1,t2; // register
		WriteInt wi = (WriteInt)gs;
		String name="", index="";
		t1 = expressionILOC(inBlock,wi.exp );
		ArrayList<String> arr_elm = array_elements(t1);
		
		if (arr_elm.size()>1) { // Array 
			name = arr_elm.get(0);
			index = arr_elm.get(1);
			debug("writeint block"+name+index);
			inBlock.addILOCCode(String.format("writeInt %s", name+"["+index+"]"));
			inBlock.addInstruction(new Instruction("writeInt",name,index,null,null,null,null)	);
		}
		else{
			inBlock.addILOCCode(String.format("writeInt %s", arr_elm.get(0)));
			inBlock.addInstruction(new Instruction("writeInt",t1,null,null)	);
		}
		
		return inBlock;
	}
	
	public static Block ILOC4Assignment(Block inBlock, GeneralStatement gs){
		
		Assignment assignment = (Assignment)gs;
		String t1,t2; // registers.
		String name_t1,name_t2,index_t1,index_t2;
		name_t1=name_t2=index_t1=index_t2=null;
		t1 = expressionILOC(inBlock, assignment.ident);
		if (assignment.exp != null){			
			t2 = expressionILOC(inBlock, assignment.exp);
			inBlock.addILOCCode(String.format("i2i %s =&gt; %s", t2,t1));
			
			ArrayList<String> arr_elm_t1 = array_elements(t1);
			ArrayList<String> arr_elm_t2 = array_elements(t2);
			
			name_t1 = arr_elm_t1.get(0);
			name_t2 = arr_elm_t2.get(0);
			debug("inside assgn" + name_t1 + name_t2);
			Instruction ins = new Instruction("i2i",name_t2,null,name_t1);			
			if (arr_elm_t1.size()>1) { // Array 				
				index_t1 = arr_elm_t1.get(1);		
				ins.d1_index=index_t1;
			}
			if (arr_elm_t2.size()>1) { // Array 			
				index_t2 = arr_elm_t2.get(1);
				ins.s1_index = index_t2;
			}
			inBlock.addInstruction(ins);
			// need to write Store operation as well
		}
		else if (assignment.readInt != null) {
			inBlock.addILOCCode(String.format("readInt =&gt; %s", t1));
			
			ArrayList<String> arr_elm_t1 = array_elements(t1);
			name_t1 = arr_elm_t1.get(0);
			debug("i2i instruction with " + name_t1);
			Instruction ins = new Instruction("readInt",null,null,name_t1);
			if (arr_elm_t1.size()>1) { // Array 				
				index_t1 = arr_elm_t1.get(1);		
				ins.d1_index=index_t1;
				debug("i2i instruction with " + index_t1);
			}
			inBlock.addInstruction(ins);
		}
		return inBlock;
	}
	
	public static String expressionILOC(Block inBlock, Node  node){
		String result=null, t1,t2; //registers
		
		HashMap<String,Integer> hm = new HashMap<String, Integer>();
		hm.put(Constant.SIMPLEEXPRESSION, 1);
		hm.put(Constant.TERM, 2);
		hm.put(Constant.EXPRESSION, 3);
		hm.put( Constant.FACTOR, 4);
		hm.put(Constant.NUMNODE, 5);
		hm.put(Constant.BOOLNODE, 6);
		hm.put(Constant.IDENTNODE, 7);
		
		switch (hm.get(node.getClass().getName())){
		case 1:{
			String name_t1="",name_t2="";
			SimpleExpression se = (SimpleExpression)node;
			t1 = expressionILOC(inBlock,se.l_term);
			if (se.op != null){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,se.r_term);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(se.op.operator, t1, t2, result));
				
				ArrayList<String> arr_elm_t1 = array_elements(t1);
				ArrayList<String> arr_elm_t2 = array_elements(t2);
				
				name_t1 = arr_elm_t1.get(0);
				name_t2 = arr_elm_t2.get(0);
				
				Instruction ins = new Instruction(ILOCInstruction.instructionFromOp(se.op.operator),name_t1,name_t2,result);
				if (arr_elm_t1.size()>1){
					ins.s1_index = arr_elm_t1.get(1);
				}
				if (arr_elm_t2.size()>1){
					ins.s2_index = arr_elm_t2.get(1);
				}
				inBlock.addInstruction(ins);
				return result;
			}
			return t1;
		}
		case 2:{
			Term term = (Term)node;
			String name_t1="",name_t2="";
			t1 = expressionILOC(inBlock,term.l_factor);
			if (term.opnode != null	){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,term.r_factor);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(term.opnode.operator, t1, t2, result));
				
				ArrayList<String> arr_elm_t1 = array_elements(t1);
				ArrayList<String> arr_elm_t2 = array_elements(t2);
				
				name_t1 = arr_elm_t1.get(0);
				name_t2 = arr_elm_t2.get(0);
				
				Instruction ins = new Instruction(ILOCInstruction.instructionFromOp(term.opnode.operator) ,name_t1,name_t2,result);
				if (arr_elm_t1.size()>1){
					ins.s1_index = arr_elm_t1.get(1);
				}
				if (arr_elm_t2.size()>1){
					ins.s2_index = arr_elm_t2.get(1);
				}
				inBlock.addInstruction(ins);
				return result;
			}
			return t1;
		}
		case  3: {
			Expression exp = (Expression) node;
			String name_t1="",name_t2="";
			t1 = expressionILOC(inBlock,exp.l_s_exp);
			if (exp.op != null){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,exp.r_s_exp);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(exp.op.operator, t1, t2, result));
				
				ArrayList<String> arr_elm_t1 = array_elements(t1);
				ArrayList<String> arr_elm_t2 = array_elements(t2);
				
				name_t1 = arr_elm_t1.get(0);
				name_t2 = arr_elm_t2.get(0);
				
				Instruction ins = new Instruction(ILOCInstruction.instructionFromOp(exp.op.operator) ,name_t1,name_t2,result);
				
				if (arr_elm_t1.size()>1){
					ins.s1_index = arr_elm_t1.get(1);
				}
				if (arr_elm_t2.size()>1){
					ins.s2_index = arr_elm_t2.get(1);
				}
				
				inBlock.addInstruction(ins);
				return result;
			}
			return t1;
		}
		case 4: {
			//expressionILOC(inBlock,node);
			Factor f = (Factor)node;
			if (f.expression != null) return expressionILOC(inBlock,f.expression);
			else if (f.identNode != null) return expressionILOC(inBlock,f.identNode);
			else if (f.numNode != null) return expressionILOC(inBlock,f.numNode);
			else if (f.boolNode != null) return expressionILOC(inBlock,f.boolNode);
			break;
		}
		case 5:{
			NumNode n = (NumNode) node;
			//load the number in register
			
			result = Register.nextRegister();
			inBlock.addILOCCode(String.format("loadI %d =&gt; %s",n.value,result));
			inBlock.addInstruction(new Instruction( "loadI" ,""+n.value,null,result));
			debug("exp:numnode2");
			return result;
		}
		case 6:{
			BoolNode n = (BoolNode) node;
			//load the number in register
			result = Register.nextRegister();
			int boolvalue = n.value ? 1: 0;   // casting boolean true to integer 1, boolean false to integer 0
			
			inBlock.addILOCCode(String.format("loadI %d =&gt; %s",boolvalue,result));
			inBlock.addInstruction(new Instruction( "loadI" ,""+boolvalue ,null,result));
			return result;
		}
		
		case 7: {
			IdentNode ident = (IdentNode)node;
			if (ident.exp != null){ // the ident is an array element
				 t1 = expressionILOC(inBlock, ident.exp) ;
				 return ident.name+"["+t1+"]";
			} else
			return "r_"+ident.name;
			
		}	
		}	
		return null;
		
	}
}
