package edu.utsa.tl13;

public class WriteInt extends GeneralStatement{
	public Expression exp;
	boolean welTyped;
	
	
	public WriteInt(Expression exp){
		this.exp = exp;		
		this.okay = typeChecking();
	}
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		if (exp!=null){
			String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR : Constant.ERROR_FILL_COLOR;
			new IdentNode("WriteInt","writeInt").draw(from,fillColor,"box");			
			exp.draw(nodeno);
		}
	}
	public boolean typeChecking(){
		//System.out.println(exp.type);
		return exp.type.equals(Constant.INTEGER);
	}
}
