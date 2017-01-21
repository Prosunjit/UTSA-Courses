package edu.utsa.tl13;

public class Expression implements Node{

	public SimpleExpression l_s_exp, r_s_exp;
	public OpNode op ;
	public boolean welTyped;
	public String type;
	public String typeExpected;
	//public boolean typeError=false;
	
	public Expression( SimpleExpression l, OpNode o, SimpleExpression r){
		this.typeExpected = null;
		this.l_s_exp = l;
		if(o != null){
			this.op = o;
			this.r_s_exp = r;
			
		}
		
		typeChecking();
	}
	public void setExpectedType(String type){
		typeExpected = type;
	}
	public void draw( int from ){
		int nodeno = Drawing.getInstance().getNodeno();
		int newroot;
		Drawing d = Drawing.getInstance();
		String fillColor = typeChecking() ? Constant.RIGHT_FILL_COLOR: Constant.ERROR_FILL_COLOR;
				
		if (op != null) {
				
			newroot = d.getNodeno();
				op.draw(from,fillColor);
				
				if (l_s_exp != null & r_s_exp!= null) {
				
					l_s_exp.draw(newroot);
					r_s_exp.draw(newroot);
				}
		}
		else if (l_s_exp!=null  ){
				
			l_s_exp.draw(from); 
			
		}
	}
	public boolean typeChecking(){
		if (l_s_exp != null && r_s_exp != null){
			if ( l_s_exp.welTyped && r_s_exp.welTyped && (l_s_exp.type.equals(r_s_exp.type))&& l_s_exp.type.equals(Constant.INTEGER )){
				welTyped = true;
				type = Constant.BOOLEAN;
				return welTyped;
			}
			else {
				welTyped = false;
				type = Constant.ERRORTYPE;
				return welTyped;
			}

		}else if (l_s_exp != null){
			welTyped = l_s_exp.welTyped;
			type = l_s_exp.type;
			return welTyped;
		}
		else {
			type = Constant.ERRORTYPE;
			return welTyped = false;
		}
		
	}

}
