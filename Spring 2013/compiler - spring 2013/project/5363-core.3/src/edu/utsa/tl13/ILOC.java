package edu.utsa.tl13;

import java.io.IOException;
import java.util.HashMap;

public class ILOC {
	public static CFGGV gv = null ;
	public static MIPSWriter mw = null ;
	static String  fileName = "program.iloc.cfg.dot";
	static String  mips_fileName = "program.s";
	public ILOC(){
		try {
			gv = new CFGGV(fileName);
			mw = new MIPSWriter(mips_fileName);
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
			inBlock.addInstruction(new Instruction("loadI","0",null,t1));
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
		if (mw == null) mw = new MIPSWriter(mips_fileName);
		
		mw.Process_Block_code(block);
		block.visited4mips = true;
		for (Block b: block.childBlock){
			if (b.visited4mips == false) {
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
		debug("MIPS WRitting Complete##");
		mw.completeWriting();
	}
	
	public static Block ILOC4While(Block inBlock, GeneralStatement gs){
		
		Block b = inBlock;
		Block expBlock = new Block();
		Block stmtBlock = new Block();
		Block exitBlock = new Block();
		
		WhileStatement ws = (WhileStatement)gs;
		
		inBlock.addILOCCode(String.format("jumpl -&gt; %s", expBlock.getName()));
		inBlock.addInstruction(new Instruction("jumpl",null,null,expBlock.getName()));
		inBlock.addChildBlock(expBlock);
		String t1 = expressionILOC(expBlock, ws.exp);
		
		expBlock.addILOCCode(String.format("cbr %s -&gt; %s , %s",t1,stmtBlock.getName(),exitBlock.getName()));
		expBlock.addInstruction(new Instruction("cbr",t1,null,stmtBlock.getName(),exitBlock.getName()));
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
		
		//inBlock.addILOCCode(String.format("jumpl =&gt; %s", if_start_blk.getName()));
		//inBlock.addChildBlock(if_start_blk);
		//if_start_blk.addParent(inBlock);
		String t1,t2,t3;
		t1 = expressionILOC(if_start_blk, is.exp);
		if (is.elseStatements.statementList.size()> 0){
			if_start_blk.addILOCCode(String.format("cbr %s =&gt; %s, %s",t1,if_blk.getName(),else_blk.getName()));
			inBlock.addInstruction(new Instruction("cbr",t1,null,if_blk.getName(),else_blk.getName()));
		}else {
			if_start_blk.addILOCCode(String.format("cbr %s =&gt; %s, %s",t1,if_blk.getName(),exit_blk.getName()));
			inBlock.addInstruction(new Instruction("cbr",t1,null,if_blk.getName(),exit_blk.getName()));
		}
		
		if_start_blk.addChildBlock(if_blk);
		//if_start_blk.addChildBlock(else_blk);
		
		Block if_stmt_end_block = ILOC4StatementList(if_blk, is.ifStatements);
		if_stmt_end_block.addChildBlock(exit_blk);
		
		//Block else_stmt_end_block = ILOC4StatementList(else_blk, is.elseStatements);
		//else_stmt_end_block.addChildBlock(exit_blk);
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
			
		}
		//debug("stmnt ends");
		return b;
	}
	
	public static Block ILOC4ReadInt (Block inBlock, GeneralStatement gs){
	
		return null;
	}
	public static Block ILOC4WriteInt(Block inBlock, GeneralStatement gs){
		String t1,t2; // register
		WriteInt wi = (WriteInt)gs;
		
		t1 = expressionILOC(inBlock,wi.exp );
		inBlock.addILOCCode(String.format("writeInt %s", t1));
		inBlock.addInstruction(new Instruction("writeInt",t1,null,null)	);
		return inBlock;
	}
	
	public static Block ILOC4Assignment(Block inBlock, GeneralStatement gs){
		
		Assignment assignment = (Assignment)gs;
		String t1,t2; // registers.
		
		t1 = expressionILOC(inBlock, assignment.ident);
		if (assignment.exp != null){			
			t2 = expressionILOC(inBlock, assignment.exp);
			inBlock.addILOCCode(String.format("i2i %s =&gt; %s", t2,t1));
			inBlock.addInstruction(new Instruction("i2i",t2,null,t1)	);
			// need to write Store operation as well
		}
		else if (assignment.readInt != null) {
			inBlock.addILOCCode(String.format("readInt =&gt; %s", t1));
			inBlock.addInstruction(new Instruction("readInt",null,null,t1)	);
		}
		return inBlock;
	}
	
	public static String expressionILOC(Block inBlock, Node  node){
		String result=null, t1,t2; //registers
		debug("in Exp"+node.getClass().getName());
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
			SimpleExpression se = (SimpleExpression)node;
			t1 = expressionILOC(inBlock,se.l_term);
			if (se.op != null){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,se.r_term);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(se.op.operator, t1, t2, result));
				inBlock.addInstruction(new Instruction(ILOCInstruction.instructionFromOp(se.op.operator),t1,t2,result));
				return result;
			}
			return t1;
		}
		case 2:{
			Term term = (Term)node;
			t1 = expressionILOC(inBlock,term.l_factor);
			if (term.opnode != null	){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,term.r_factor);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(term.opnode.operator, t1, t2, result));
				inBlock.addInstruction(new Instruction(ILOCInstruction.instructionFromOp(term.opnode.operator) ,t1,t2,result));
				return result;
			}
			return t1;
		}
		case  3: {
			Expression exp = (Expression) node;
			t1 = expressionILOC(inBlock,exp.l_s_exp);
			if (exp.op != null){
				result = Register.nextRegister();
				t2 = expressionILOC(inBlock,exp.r_s_exp);
				inBlock.addILOCCode(ILOCInstruction.getArithmeticInstruction(exp.op.operator, t1, t2, result));
				inBlock.addInstruction(new Instruction(ILOCInstruction.instructionFromOp(exp.op.operator) ,t1,t2,result));
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
			debug("exp:numnode");
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
			return "r_"+ident.name;
			
		}
		
		default:{
			IdentNode identnode = (IdentNode) node;
			String identifier = identnode.name;
			String r1,r2;
			String ident_offset;
			String ident_baseaddr;
			SymbolTable st = SymbolTable.getInstance();
			ident_offset = st.getOffset(identifier);
			ident_baseaddr = st.getBaseAddress(identifier);
			if (ident_offset == null){ // variable is not loaded in register. Emit code to load identifier in register. and get the offset
				 r1 = Register.getNamedRegister(identifier);
				 ident_baseaddr = "r_arp";
				 String offset = "@" + r1; //  may need to change this code later 
				 st.setBaseNOffset(identifier, ident_baseaddr, offset); //setting the offset
				 
				 ident_offset = Register.nextRegister();
				 inBlock.addILOCCode( String.format("loadI %s =&gt; %s",offset, ident_offset)); // code loading the offset
			
			}
			//emiting code to load the offset value of the ident in t1;
			 result = Register.nextRegister();
			 inBlock.addILOCCode(String.format("loadA0 %s, %s =&gt; %s",ident_baseaddr,ident_offset,result));
			 //inBlock.addInstruction(new Instruction( "loadI" ,""+boolvalue ,null,result));
			 return result;
		}
	
		}	
		return null;
		
	}
}
