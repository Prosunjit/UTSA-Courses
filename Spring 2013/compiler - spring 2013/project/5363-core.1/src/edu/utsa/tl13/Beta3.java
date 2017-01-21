package edu.utsa.tl13;

public class Beta3 implements Node {

	Term term;
	OpNode op3;
	public Beta3(){
		this.term = null; 
		this.op3 = null;
	}
	public Beta3( Term t, OpNode o) {
		this.term = t;
		this.op3 = o;
	}
	public void draw(int from){}
}
