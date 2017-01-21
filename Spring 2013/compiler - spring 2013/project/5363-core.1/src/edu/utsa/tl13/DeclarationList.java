package edu.utsa.tl13;

import java.util.ArrayList;
import java.util.Collections;

public class DeclarationList extends GeneralStatement {

	public ArrayList<Declaration> declarations;
	public static DeclarationList instance = null;
	private DeclarationList(){
		this.okay = true;
		declarations = new ArrayList<Declaration>() ;
	}
	public static DeclarationList getInstance(){
		if (instance == null)  
			instance = new DeclarationList();
		return instance; 
	}
	public void AddDeclaration( Declaration declr){	
		declarations.add(declr);
		if (typeChecking()==false)
			this.okay = false;
	}
	public ArrayList<Declaration> getAllDeclaration(){	
		return instance.declarations;
	}
	public void draw(int from){
		// Collections.reverse(declarations);
		for (Node n: declarations){
			n.draw(from);
		}
	}
	public boolean typeChecking(){
		boolean flag = true;
		for (Declaration d: declarations){
			flag = flag && d.okay;
		}
		return flag;
	}
}
