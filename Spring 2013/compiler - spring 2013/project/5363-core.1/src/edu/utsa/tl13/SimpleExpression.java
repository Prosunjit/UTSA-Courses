package edu.utsa.tl13;

public class SimpleExpression implements Node{

	public Term l_term;
	public Term r_term;
	public OpNode op;
	public String type;
	public boolean welTyped;
	
	
	public SimpleExpression( Term l, OpNode o, Term r){
		this.l_term = l;
		if (o != null) {
			this.r_term = r;
			this.op = o;
			
		}		
		typeChecking();
	}
	public void draw( int from ){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor;
		
		if (typeChecking() == true ) fillColor = Constant.RIGHT_FILL_COLOR;
		else fillColor = Constant.ERROR_FILL_COLOR;
		if (op !=  null ){
			d.addGVNode(nodeno, op.operator, fillColor, "box");
			d.addGVEdge(from, nodeno);
			if (l_term != null){
				l_term.draw(nodeno);
			}
			if (r_term != null)
				r_term.draw(nodeno);
		}
		else {
			if (l_term != null)
				l_term.draw(from);
		}
	}
	
	public boolean typeChecking(){
		if (op != null && l_term != null & r_term != null){
			
			if (l_term.welTyped && r_term.welTyped && (l_term.type.equals(r_term.type)) && l_term.type.equals(Constant.INTEGER) )	{
				//System.out.print("" + l_term.type + "---->"+ r_term.type);
				welTyped = true;
				type = l_term.type;
				return true;
			}
		} else if (l_term != null){
			if (l_term.welTyped){
				//System.out.print("SE" + l_term.type);
				welTyped = true;
				type = l_term.type;
				return true;
			}
			else {
				//System.out.print("" + l_term.type + "---->");
				welTyped = false;	
				return false;
			}
			
		}
		return l_term.welTyped; // if only one 
	}

}
