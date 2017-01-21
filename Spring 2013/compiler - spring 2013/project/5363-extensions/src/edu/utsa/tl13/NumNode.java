package edu.utsa.tl13;

public class NumNode implements Node{
	public String  type;
	public long value;
	public boolean welTyped;
	
	public NumNode(long value){
		type = Constant.CONSTANT;
		this.value = value;
		typeChecking();
		
	}

	public void draw(int from){
			Drawing d = Drawing.getInstance();
			int nodeno = d.getNodeno();
			String fillColor = typeChecking()? Constant.INT_FILL_COLOR: Constant.ERROR_FILL_COLOR;
			d.addGVNode(nodeno, ""+value, fillColor, "box");
			d.addGVEdge(from, nodeno);
					
	}
	public boolean typeChecking(){
		if (this.value >=0 && this.value <= java.lang.Math.pow(2,31)-1 ) {			
			return welTyped = true;
		}
		else return welTyped = false;
	}
}
