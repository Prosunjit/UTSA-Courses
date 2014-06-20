package edu.utsa.tl13;

public class IdentNode implements Node{

	public String name;
	public String type;
	public String value;
	public boolean welTyped;
	public String typeExpected;
	
	public void draw(int from){
		
		Drawing d = Drawing.getInstance();
		String fillColor = Constant.NODE_FILL_COLOR;
		int nodeno = d.getNodeno();
		SymbolTable st = SymbolTable.getInstance();
		if (st.getType(name) == null) {
			fillColor = Constant.NODE_FILL_COLOR;
		}
		else if( st.getType(name).equals(Constant.INTEGER)) {
			fillColor = Constant.INT_FILL_COLOR;
			//System.out.println("-->"+name+ ":" + type);
		}
		else if (st.getType(name).equals(Constant.BOOLEAN)) {
			//System.out.println("-->"+name+ ":" + type);
			fillColor = Constant.BOOL_FILL_COLOR;
		}
		
		d.addGVNode(nodeno, name, fillColor, "box");
		d.addGVEdge(from, nodeno);
		
	}
	public void draw(int from, String fillColor, String shape){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno();
		d.addGVNode(nodeno, name, fillColor, shape);
		d.addGVEdge(from, nodeno);
		
	}
	public IdentNode(String name, String type){
		this.typeExpected = null;
		this.name = name;
		this.type = type;		
		this.welTyped = true;
	}
}
