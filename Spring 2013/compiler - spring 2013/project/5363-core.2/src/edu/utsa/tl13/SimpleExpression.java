package edu.utsa.tl13;

public class SimpleExpression implements Node{

	public Term l_term;
	public Term r_term;
	public OpNode op;
	public String type;
	public boolean welTyped;
	public String typeExpected;
	
	
	public SimpleExpression( Term l, OpNode o, Term r){
		this.typeExpected = null;
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
			op.draw(from, fillColor);
			//d.addGVNode(nodeno, op.operator, fillColor, "box");
			//d.addGVEdge(from, nodeno);
			if (l_term != null){
				//if (!this.typeExpected.equals(l_term.type))
					//l_term.typeExpected = this.typeExpected;
				l_term.draw(nodeno);
			}
			if (r_term != null) {
				//if ( !this.typeExpected.equals(l_term.type))
					//r_term.typeExpected = this.typeExpected;
				r_term.draw(nodeno);
			}
		}
		else {
			if (l_term != null){
				//if (!this.typeExpected.equals(l_term.type))
				//	l_term.typeExpected = this.typeExpected;
				l_term.draw(from);
			}
		}
	}
	
	public boolean typeChecking(){
		if (op != null && l_term != null & r_term != null){
			
			if (l_term.welTyped && r_term.welTyped && (l_term.type.equals(r_term.type)) && l_term.type.equals(Constant.INTEGER) )	{
				welTyped = true;
				type = l_term.type;
				return true;
			} else if (  !(l_term.type.equals(r_term.type))   )	{
				welTyped = false;
				type = Constant.ERRORTYPE;
				return welTyped;
			}
		} else if (l_term != null){
			if (l_term.welTyped){
				//System.out.print("SE->" + l_term.type);
				welTyped = true;
				type = l_term.type;
				return welTyped;
			}
			else {
				//System.out.print("" + l_term.type + "---->");
				type = Constant.ERRORTYPE;
				welTyped = false;	
				return welTyped;
			}
			
		}  
		welTyped = false;
		type = Constant.ERRORTYPE;
		return l_term.welTyped; // if only one

			
		 
	}

}
