package edu.utsa.tl13;

public class Beta4 implements Node {

	Factor factor;
	OpNode op2;
	public Beta4(){
		this.factor = null; 
		this.op2 = null;
	}
	public Beta4( Factor f, OpNode o) {
		this.factor = f;
		this.op2 = o;
	}
	public void draw(int from){}
}
