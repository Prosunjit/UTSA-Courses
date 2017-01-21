package edu.utsa.tl13;

public class Declaration extends GeneralStatement {
	public IdentNode ident;
	public TypeNode typenode;
	public boolean duplicate=false;
	
	public Declaration (IdentNode i, TypeNode t){
		this.okay = true;
		this.ident = i;
		this.typenode = t;
		addToSymbolTable(i.name,t.type);
		if(duplicate)	this.okay = false;
	}
	void debug(String s){
		//System.out.println(s);
	}
	public void addToSymbolTable(String identifier, String type){
		//identifier = "r_"+identifier;
		SymbolTable st = SymbolTable.getInstance();
		debug("declaration: adding to symbol table"+identifier);
		if (st.getType(identifier) == null){
			SymbolTable.getInstance().addToSymbolTable(identifier, type);
			
		} else{
			debug("declaration: adding to symbol table duplicate"+identifier);
			duplicate = true;
		}
		
	}
	public void draw(int from){
	
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor = Constant.NODE_FILL_COLOR;
		if (typenode.type.equals(Constant.INTEGER)) fillColor = Constant.INT_FILL_COLOR;
		else if (typenode.type.equals(Constant.BOOLEAN)) fillColor = Constant.BOOL_FILL_COLOR;
		fillColor = this.duplicate ? Constant.ERROR_FILL_COLOR: fillColor;  
		new IdentNode("decl:'"+ident.name+"'", "decl").draw(from,fillColor,"box");
		new IdentNode(typenode.type,"type").draw(nodeno);
	
	
	}
}
