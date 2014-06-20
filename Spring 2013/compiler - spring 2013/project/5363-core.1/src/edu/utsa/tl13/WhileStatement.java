package edu.utsa.tl13;

public class WhileStatement extends GeneralStatement {
	public Expression exp;
	public StatementList sl;
	boolean welTyped=true;
	public WhileStatement( Expression exp, StatementList sl){
		
		this.exp = exp;
		this.sl = sl;
		typeChecking();
		this.okay = typeChecking();
	}
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR: Constant.ERROR_FILL_COLOR;
		if (exp != null){
			d.addGVNode(nodeno, "While", fillColor, "box");
			d.addGVEdge(from, nodeno);
			exp.draw(nodeno);
			sl.draw(nodeno);
		}
	}
	public boolean typeChecking(){
		if (exp != null)
			return exp.welTyped && sl.okay;
		else return false;
	}
}
