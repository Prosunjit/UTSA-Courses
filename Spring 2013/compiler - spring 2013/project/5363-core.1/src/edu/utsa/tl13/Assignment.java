package edu.utsa.tl13;

public class Assignment extends GeneralStatement {

	public IdentNode ident;
	public OpNode assgn;
	public Expression exp;
	public IdentNode readInt;
	
	public boolean welTyped;
	
	
	public Assignment(IdentNode ident, OpNode assgn, Node exp) {
		
		this.ident = ident;
		this.assgn = assgn; 
		if (exp.getClass().getName().equals(Constant.EXPRESSION)){
			this.exp = (Expression)exp;
		} else if (exp.getClass().getName().equals(Constant.IDENTNODE)){
			this.readInt = (IdentNode)exp;
		}
		typeChecking();
		this.okay = welTyped;
	}
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR: Constant.ERROR_FILL_COLOR; 
		if (readInt != null) {
			if (assgn != null )	{
				d.addGVNode(nodeno, assgn.operator, fillColor, "box");
				d.addGVEdge(from, nodeno);				
				ident.draw(nodeno);				
				readInt.draw(nodeno);
			}
		}
		else if (exp != null){
			if (assgn != null )	{
				d.addGVNode(nodeno, assgn.operator, fillColor, "box");
				d.addGVEdge(from, nodeno);
				ident.draw(nodeno);	
				exp.draw(nodeno);
			}
		}
		
	}
	public boolean typeChecking(){
		if (readInt != null){
			if (readInt.type.equals(ident.type)){
				//System.out.println("read int true");
				welTyped = true;
				okay = true;
				return true;
			}
			else {
				//System.out.println("read int false");
				welTyped = false;
				okay = false;
				return false;
			}
		}
		else if (exp != null){
			//System.out.println("::"+exp.type+ident.type);
			if (exp.welTyped && exp.type.equals(ident.type)){
				welTyped = true;
				okay = true;
				return true;
			}
			else {
				//System.out.println("extype"+exp.type + "" + ident.type);
				welTyped = false; okay = false;
				return false;
			}
		}
		return false;
	}
}
