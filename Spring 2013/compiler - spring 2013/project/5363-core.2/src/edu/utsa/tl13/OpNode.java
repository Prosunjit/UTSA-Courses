package edu.utsa.tl13;

public class OpNode implements Node {
	
	public String operator;
	public String type;
	
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		d.addGVNode(nodeno, operator, Constant.RIGHT_FILL_COLOR, "box");
		d.addGVEdge(from, nodeno);
	}
	
	public void draw(int from, String fillColor){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		d.addGVNode(nodeno, operator, fillColor, "box");
		d.addGVEdge(from, nodeno);
	}
	public OpNode(String op_value, String t){
		this.operator = op_value;
		this.type = t;
	}
}
