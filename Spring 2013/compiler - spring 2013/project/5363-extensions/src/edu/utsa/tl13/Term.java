package edu.utsa.tl13;

public class Term implements Node{

	public OpNode opnode;
	public Factor l_factor;
	
	public Factor r_factor;
	public String type;
	public boolean welTyped;
	public String typeExpected;
	
	public Term( Factor l, OpNode op, Factor r){
		
		//debug("Term: r term is  "+ r.identNode);
		this.typeExpected = null;
		this.l_factor = l;
		if (op != null && r !=null){
			this.r_factor = r;
			this.opnode = op;			
		}
		typeChecking();
		debug("Term: type is "+ type);
	}
	public void debug(String s) {
		//System.out.println("Term->debug "+s);
	}
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		String fillColor;
		if (typeChecking() == true ){
			
			fillColor = Constant.RIGHT_FILL_COLOR;
		}
		else{
			
			fillColor = Constant.ERROR_FILL_COLOR;
		}
		if (opnode != null ){
			d.addGVNode(nodeno, opnode.operator, fillColor, "box");
			d.addGVEdge(from, nodeno);
			if (l_factor != null ) {
				l_factor.draw(nodeno);
			}
			if (r_factor != null){
				r_factor.draw(nodeno);
			}
		
		} else {
			if (l_factor != null ) {
				l_factor.draw(from);
			}
		}
		
	}
	public boolean typeChecking(){
		if ((l_factor != null && r_factor != null) && l_factor.type.equals(r_factor.type) && l_factor.type.equals(Constant.INTEGER)){
			welTyped = true;
			type = l_factor.type;			
			return welTyped;
		}if ((l_factor != null && r_factor != null) && l_factor.type.equals(r_factor.type) && l_factor.type.equals(Constant.BOOLEAN)){
			welTyped = false;
			type = Constant.ERRORTYPE;			
			return welTyped;
		} else if ((l_factor != null && r_factor != null) && ! l_factor.type.equals(r_factor.type)){
			welTyped = false;
			type = Constant.ERRORTYPE;			
			return welTyped;
		}else if(l_factor != null && r_factor == null){
			welTyped = l_factor.welTyped;			
			type = l_factor.type;
			return welTyped;
		} else {
			type = Constant.ERRORTYPE;
			welTyped = false;
			return welTyped;
		}
		
	}
	
}
