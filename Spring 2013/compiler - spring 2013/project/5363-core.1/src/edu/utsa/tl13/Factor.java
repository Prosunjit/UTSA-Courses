package edu.utsa.tl13;

public class Factor implements Node {

	public NumNode numNode;
	public IdentNode identNode;
	public Expression expression;
	public BoolNode boolNode;
	public String type;
	public boolean welTyped;
	
	
	public Factor(Node n){
		numNode = null;
		identNode = null;
		boolNode = null;
		expression = null;
		SymbolTable symbolTable = SymbolTable.getInstance();
		
		if (n.getClass().getName().equals(Constant.EXPRESSION)){
			expression = (Expression)n;
			welTyped = expression.welTyped;
			type = expression.type;
						
		} else if (n.getClass().getName().equals(Constant.NUMNODE)){
			numNode = (NumNode) n;	
			type = numNode.type;
			welTyped = numNode.welTyped;
			
		} 
		else if (n.getClass().getName().equals(Constant.IDENTNODE)){
			identNode = (IdentNode) n;
			type = symbolTable.getType(identNode.name);
			//System.out.println("::"+type);
			welTyped = identNode.welTyped;
		}
		else if (n.getClass().getName().equals(Constant.BOOLNODE)){
			boolNode = (BoolNode) n;
			type = boolNode.type;
			welTyped = boolNode.welTyped;
		}
		else {
			//System.out.println("Factor is not working..." + n.getClass().getName());
			System.out.println("error in factoring");
		}
	}
	
	public void draw( int from){
		int nodeno = Drawing.getInstance().getNodeno();
		Drawing d = Drawing.getInstance();
		//String fillColor = Constant.
		if (numNode != null){
			//d.addGVNode(nodeno, "" + numNode.value, Constant.INT_FILL_COLOR, "box");
			//d.addGVEdge(from, nodeno);
			numNode.draw(from);
		} else if (identNode != null){
			String fillColor;
			fillColor =  type.equals(Constant.INTEGER)? Constant.INT_FILL_COLOR : Constant.BOOL_FILL_COLOR;
			d.addGVNode(nodeno, identNode.name, fillColor, "box");
			d.addGVEdge(from, nodeno);
		} 
		else if (boolNode != null){
			d.addGVNode(nodeno, ""+ boolNode.value, Constant.BOOL_FILL_COLOR, "box");
			d.addGVEdge(from, nodeno);
		}
		else if (expression != null){
			expression.draw(from);
		}	
		
		
	}

}
