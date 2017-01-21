package edu.utsa.tl13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

class FileOperation {
	
	public static String fileContent (String fileName){
		String content = "";
		try{
			String line;
			int pos;        
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if (  line.indexOf("%-") == -1 && line.indexOf("-%") == -1 && (pos = line.indexOf("%"))!= -1) // remove  
					line = line.substring(0,pos);
				content += " " + line;
			}
			br.close();
		}catch( Exception e) {System.out.println("Error_Class Reading Input file "+e);}
		return content + " " ;
		// space is intentionally added otherwise DFSlexar is giving wrong result.
	}

	public static String[] readFile(String fileName){
		String content= fileContent (fileName)	 ;		
		return getwords(content);
	}
	public static String[] getwords ( String fileContent ) {
		String []words = fileContent.split("\\s+");
		return words;  
		
	
	}
	public  static void write2File(String fileName, String content){
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter(fileName);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(content);
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}

}