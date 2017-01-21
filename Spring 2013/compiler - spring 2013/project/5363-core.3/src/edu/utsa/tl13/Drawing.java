package edu.utsa.tl13;

public class Drawing {
	public static int nodeno=0;
	public static Drawing singleton;
	public String gvText;
	private Drawing(){
		gvText = "";
		gvText += getInitials();
	}
	private String getInitials() {
		return " digraph tl12Ast { \n   ordering=out; \n  node [shape = box, style = filled];\n";
	}
	public String getGVFile(){
		return gvText + "\n}";
	}
	public static Drawing getInstance(){
		if (singleton == null)
			singleton = new Drawing();
		return singleton;
	}
	public int getNodeno(){
		return nodeno;
	}
	public int increment(int value){
		return (nodeno += value);
	}
	public void addGVText (String gvText){
		this.gvText += gvText;
	}
	public void addGVEdge(int from, int to){
		addGVText( String.format("n%d -> n%d\n",from,to));
	}
	public int  addGVNode(int nodeno, String label,String fillColor, String shape){ //draw node and increment & return
		addGVText (  			
		String.format("n%d [label=\"%s\",fillcolor=\"%s\",shape=%s]\n", nodeno,label,fillColor,shape)
		);
		return increment(1);
	}
}
