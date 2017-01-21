package edu.utsa.tl13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class FileOperation {

	public static String[] readFile(String fileName){
		String content="";
		try{
			String line;
			int pos;        
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				if ( (pos = line.indexOf("%")) != -1)
					line = line.substring(0,pos);
				content += " " + line;
			}
			br.close();
		}catch( Exception e) {System.out.println("Error_Class Reading Input file "+e);}
		return getwords(content);
	}
	public static String[] getwords ( String fileContent ) {
		//System.out.println(fileContent);
		//String fileContent = " program var X as int ;    \n  begin  X := readInt ;  writeInt 2 * X ; end ";
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