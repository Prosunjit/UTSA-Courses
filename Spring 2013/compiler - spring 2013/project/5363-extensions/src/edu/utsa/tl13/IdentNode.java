package edu.utsa.tl13;

public class IdentNode implements Node{

	public String name;
	public String type;
	public String value;
	public boolean welTyped;
	public String typeExpected;
	public boolean isArray;
	public int arraySize;
	public Expression exp;
	
	public void draw(int from){
		
		Drawing d = Drawing.getInstance();
		String fillColor = Constant.NODE_FILL_COLOR;
		SymbolTable st = SymbolTable.getInstance();
		if (st.getType(name) == null) {
			fillColor = Constant.NODE_FILL_COLOR;
		}
		else if( st.getType(name).equals(Constant.INTEGER)) {
			fillColor = Constant.INT_FILL_COLOR;
		}
		else if (st.getType(name).equals(Constant.BOOLEAN)) {
			fillColor = Constant.BOOL_FILL_COLOR;
		}
		draw(from,fillColor,"box");
	}
	public void draw(int from, String fillColor, String shape){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		d.addGVNode(nodeno, name, fillColor, shape);
		d.addGVEdge(from, nodeno);
		//nodeno = d.getNodeno();
		if ( exp != null) { // array type.
			int t_nodeno = d.getNodeno();
			d.addGVNode(t_nodeno, "[", fillColor, shape);			
			d.addGVEdge(nodeno, t_nodeno);
			exp.draw(nodeno);
			t_nodeno = d.getNodeno();
			d.addGVNode(t_nodeno, "]", fillColor, shape);			
			d.addGVEdge(nodeno, t_nodeno);
		}
		
	}
	public IdentNode(String name, String type){
		this.typeExpected = null;
		this.exp = null;
		this.name = name;
		this.type = type;		
		this.welTyped = true;
	}
	public IdentNode(String name, String type, Expression exp){
		this(name,type);
		this.exp = exp;
	}
}
