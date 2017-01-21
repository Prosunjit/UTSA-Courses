package edu.utsa.tl13;

import java.util.HashMap;

public class ILOCInstruction {
	
	public ILOCInstruction(){
		
	}
	
	public static String instructionFromOp(String op){
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("+", "add");
		hm.put("-", "sub");
		hm.put("*", "mul");
		hm.put("div", "div");
		hm.put("mod", "rem");
		hm.put("<","cmp_LT");
		hm.put(">", "cmp_GT");
		hm.put("<=", "cmp_LE");
		hm.put(">=", "cmp_GE");
		hm.put("=", "cmp_EQ");
		hm.put("!=", "cmp_NE");
		
		return hm.get(op);
	}
	
	public static String getArithmeticInstruction ( String operator, String operand1, String operand2, String result) {
		return String.format("%s %s, %s =&gt; %s",instructionFromOp(operator), operand1,operand2,result);
		
			
		
	}
	
	
}
