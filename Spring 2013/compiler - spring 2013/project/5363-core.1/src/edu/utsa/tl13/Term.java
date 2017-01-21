package edu.utsa.tl13;

public class Term implements Node{

	public OpNode opnode;
	public Factor l_factor;
	
	public Factor r_factor;
	public String type;
	public boolean welTyped;
	public Term( Factor l, OpNode op, Factor r){
		this.l_factor = l;
		if (op != null && r !=null){
			this.r_factor = r;
			this.opnode = op;			
		}
		typeChecking();
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
			
			return true;
		}else if(l_factor != null){
			welTyped = l_factor.welTyped;
			
			type = l_factor.type;
			//System.out.println(l_factor.type);
			return true;
		}
		welTyped = false;
		return false;
		
	}
	
}
