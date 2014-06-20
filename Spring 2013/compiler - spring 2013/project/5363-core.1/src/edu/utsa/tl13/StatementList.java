package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.Collections;

public class StatementList extends GeneralStatement {

	public ArrayList<GeneralStatement> statementList;
	public boolean weltyped;
	
	
	public StatementList(){
		statementList = new ArrayList<GeneralStatement>();
		this.okay = true;
	}
	
	public void addStatement(GeneralStatement n){
		statementList.add(n);
		this.okay = typeChecking();
	}
	public ArrayList<GeneralStatement> getAllStatement(){
		return this.statementList;
	}
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR: Constant.ERROR_FILL_COLOR;
		new IdentNode("Stmt list","stmnt").draw(from,fillColor,"none");
		
		//System.out.println("inside satementlist"+statementList.size());
		 Collections.reverse(statementList);
		for (GeneralStatement n: statementList){
			n.draw(nodeno);
		}
	}
	public boolean typeChecking(){
		boolean flag=true;
		for (GeneralStatement n: statementList){
			flag = flag && n.okay; 
		}
		return flag;
	}
	
}
