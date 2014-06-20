package edu.utsa.tl13;

public class Beta2 implements Node {

	SimpleExpression se;
	OpNode op4;
	public Beta2(){
		this.se = null; 
		this.op4 = null;
	}
	public Beta2( SimpleExpression s, OpNode o) {
		this.se = s;
		this.op4 = o;
	}
	public void draw(int from){}
}
