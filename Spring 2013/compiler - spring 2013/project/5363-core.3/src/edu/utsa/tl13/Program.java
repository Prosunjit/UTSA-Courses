package edu.utsa.tl13;

 class Program extends GeneralStatement {

	 public DeclarationList declarations;
	 public StatementList statements;
	 public Program( DeclarationList dl , StatementList sl){
		 this.declarations = dl;
		 this.statements = sl;
		 this.okay = typeChecking();
	 }
	 
	 public void draw(int from){
		// System.out.println("inside program draw");
		 Drawing d = Drawing.getInstance();
		
		 String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR : Constant.ERROR_FILL_COLOR;
		 int nodeno = d.getNodeno();
		 d.addGVNode(from, "program", fillColor, "box");
		 declarations.draw(nodeno);		
		 statements.draw(nodeno);
		
	 }
	 public boolean typeChecking(){
		 return statements.okay && declarations.okay;
	 }
}
