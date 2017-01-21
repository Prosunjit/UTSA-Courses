package edu.utsa.tl13;

public class BoolNode implements Node{
	public String  type;
	public Boolean value;
	public boolean welTyped;
	
	public BoolNode( boolean value){
		type = Constant.BOOLEAN;
		this.value = value;
		welTyped = true;
	}

	public void draw(int from){
			
	}
}
