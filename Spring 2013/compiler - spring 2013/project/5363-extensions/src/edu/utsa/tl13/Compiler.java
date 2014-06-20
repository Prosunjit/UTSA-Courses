package edu.utsa.tl13;
import java.io.IOException;


public class Compiler {
	public static void main (String[] args) throws IOException {
		//String inputFileName = "testcase.tl13";
		String inputFileName = args[0];
		int baseNameOffset = inputFileName.length() - 5;

		String baseName;
		if (!inputFileName.substring(baseNameOffset).equals(".tl13"))
			throw new RuntimeException("inputFileName does not end in .tl13");
		baseName = inputFileName.substring(0,baseNameOffset);
		String parseOutName = baseName + ".pt.dot";
		
		System.out.println("Input file : " + inputFileName);
		System.out.println("AST file is : " + baseName + ".ast.dot");
		System.out.println("ILOC file is : " + inputFileName + ".iloc.cfg.dot");

		call_parser(baseName);


	}
	public static void call_parser(String tl13FileName){
		Parser parser = new Parser();
		parser.my_parser(tl13FileName);
	}
}

