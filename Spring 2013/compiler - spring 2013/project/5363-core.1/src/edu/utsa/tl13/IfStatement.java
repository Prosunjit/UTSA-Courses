package edu.utsa.tl13;

public class IfStatement extends GeneralStatement {

	public Expression exp;
	public StatementList ifStatements, elseStatements;
	boolean welTyped=true;
	
	public IfStatement(Expression exp, StatementList ifs, StatementList elses){
		this.exp = exp;
		this.ifStatements= ifs;
		this.elseStatements = elses;
		typeChecking();
		this.okay = welTyped;
	} 
	public void draw(int from){
		Drawing d = Drawing.getInstance();
		int nodeno = d.getNodeno(); // if node as root
		String fillColor = typeChecking()? Constant.RIGHT_FILL_COLOR: Constant.ERROR_FILL_COLOR;
		new IdentNode("IF","if").draw(from);
		if (exp != null){
			exp.draw(nodeno);
		}
		if (ifStatements != null){
			ifStatements.draw(nodeno);
		}
		if (elseStatements != null){
			elseStatements.draw(nodeno);
		}
	}
	public boolean typeChecking(){
		if (elseStatements != null) {
			if (exp.welTyped && ifStatements.okay && elseStatements.okay){
				welTyped = true;
				return true;
			}
			else {
				welTyped = false;
				return false;
			}
		}
		else
			return 
					welTyped = exp.welTyped && ifStatements.okay;
		
	}
}
