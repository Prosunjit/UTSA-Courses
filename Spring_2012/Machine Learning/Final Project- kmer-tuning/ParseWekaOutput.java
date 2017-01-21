/*
 * This program would take weka output and format it in our desired format like "SEQUENCE LABEL PROBABILITY"
 * how to run : java ParseWekaOutput weka.output 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class ParseWekaOutput {

    	private static String FILE2PARSE="weka.output";
    	private static String OUTPUT_FILE="parsedOutput.txt";
	    public static void main (String[] args) {
	    	
	    	String output= "";
	    
		    if ( args[0].length()>=2 ){
		    	OUTPUT_FILE = args[1];
		    }
		    if (args[0].length()>=1)
		    {
		    	FILE2PARSE = args[0];
		    }
					
			ParseWekaOutput wekaOut = new ParseWekaOutput();		
			
			ArrayList<String> outputLines = wekaOut.readFile(FILE2PARSE);
			
			ListIterator<String> lineIterator = outputLines.listIterator();
			//escaping first few lines.
			while( lineIterator.hasNext()) {
				String line = lineIterator.next();
				line = line.trim();
				if ( line.contains("inst#")) break; 
				
			}
			while( lineIterator.hasNext()) {
				String line = lineIterator.next();
				line = line.replace("+", ""	);
				line = line.trim().replaceAll("( )+", " ");
				String [] parts = line.split(" ");
				//System.out.println(parts[4].replace("(", "").replace(")", "") + "\t" + parts[2].split(":")[1] + "\t" + parts[3].split(",")[0].replace("*", ""));
				output += (parts[4].replace("(", "").replace(")", "") + "\t" + parts[2].split(":")[1] + "\t" + parts[3].split(",")[0].replace("*", ""));
				output += "\n";
			}
			
			wekaOut.write2File(OUTPUT_FILE, output);
		
	    }
	    
	    public void write2File(String fileName, String content){
	 	   
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
    
	   public ArrayList<String> readFile(String file){		   
		   ArrayList<String> content = new ArrayList<String>();
		   try{
		    FileInputStream fstream = new FileInputStream(file);		
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;		
			while ((strLine = br.readLine()) != null) 	{
				if (strLine.trim().length()>1)
				content.add(strLine);
				
			}
			
			in.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		   return content;
		   
	   }
}

