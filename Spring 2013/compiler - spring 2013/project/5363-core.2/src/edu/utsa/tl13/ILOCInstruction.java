package edu.utsa.tl13;

import java.util.HashMap;

public class ILOCInstruction {
	
	public ILOCInstruction(){
		
	}
	public static String getArithmeticInstruction ( String operator, String operand1, String operand2, String result) {
		String instruction = null;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("+", 1);
		hm.put("-", 2);
		hm.put("*", 3);
		hm.put("div", 4);
		hm.put("mod", 5);
		hm.put("<",6);
		hm.put(">", 7);
		hm.put("<=", 8);
		hm.put(">=", 9);
		hm.put("=", 10);
		hm.put("!=", 11);
		
		switch(hm.get(operator)){
		case 1: {
			return String.format("add %s, %s =&gt; %s",operand1,operand2,result);
		}case 2: {
			return String.format("sub %s, %s =&gt; %s",operand1,operand2,result);
		}case 3: {
			return String.format("mul %s, %s =&gt; %s",operand1,operand2,result);
		}case 4: {
			return String.format("div %s, %s =&gt; %s",operand1,operand2,result);
		}case 5: {
			return String.format("mod %s, %s =&gt; %s",operand1,operand2,result);
		}case 6: {
			return String.format("cmp_LT %s, %s =&gt; %s",operand1,operand2,result);
		}case 7: {
			return String.format("cmp_GT %s, %s =&gt; %s",operand1,operand2,result);
		}case 8: {
			return String.format("cmp_LE %s, %s =&gt; %s",operand1,operand2,result);
		}case 9: {
			return String.format("cmp_GE %s, %s =&gt; %s",operand1,operand2,result);
		}case 10: {
			return String.format("cmp_EQ %s, %s =&gt; %s",operand1,operand2,result);
		}case 11: {
			return String.format("cmp_NE %s, %s =&gt; %s",operand1,operand2,result);
		}
		
		}
			
		return instruction;
	}
	
	
}
