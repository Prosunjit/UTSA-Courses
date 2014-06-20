import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class NC2 {
		private static String DELIM="(.)*";
	   public static void main (String[] args) {
		   NC2 nc2 = new NC2();
		   ArrayList<String> pattern = nc2.readFile(args[0]); 
		   nc2.generate(pattern);
		   
		
	    }
	   public void generate(ArrayList<String> input) {
		  		   
		   int size = input.size();
		   for(int i=0; i<size; i++){
			   for(int j=i+1; j<size; j++){
				   System.out.println(input.get(i) + DELIM + input.get(j));
				   System.out.println(input.get(j) + DELIM + input.get(i));
			   }
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
