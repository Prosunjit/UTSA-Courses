/*
 * This file select feature ( attributes ) that occour more than a predefined number of sequences.
 * How to run : java FeatureSelector TF_1_fre_positive feature 50
 * 
 */
import java.io.*;
import java.util.ArrayList;

import javax.annotation.processing.Filer;
 
class FeatureSelector
{
		private static int NUMROW;
		private static int NUMCOL;
		private static int THRESHOLD_VALUE=0;
		private static int THRESHOLD_SEQ_NUM=20;
		private static String OUTPUT_DELIM = ",";
		private static String INPUT_DELIM = " ";
		private static String OUTPUT="ALL";
       public static void main(String args[])
       {
    	   	  
              String fileName = "fre-table";//input file
              ArrayList deleteCandidate = new ArrayList();
              FeatureSelector filter = new FeatureSelector(); 
              //System.out.println(args[0]);
              if (args.length>=3){
            	  THRESHOLD_SEQ_NUM = Integer.parseInt(args[2]);
              }
              if (args.length>=2 ) {
            	  
            	  if (args[1].equals("feature")){            		
            		  OUTPUT=args[1];
            		  fileName = args[0];
            		  filter.clean(fileName);
            	  }
            	  else if ( args[1].equals("fasta")) {
            		  filter.convert2Fasta(fileName);
            		  return ;
            	  }            		  
              }else if (args.length>=1){
            	  fileName = args[0];   
              }
             
                   
       }
       
       public void clean(String fileName){
    	   NUMROW = 0;
           /*try*/
           {
         	             	  
         	  String [][] frequencyTable  = TwoDArrayFromFile(fileName);

         	  //System.out.println("lenght of :"+frequencyTable[1][0]);
         	  ArrayList<String> delcan = selectDeleteCandidate(frequencyTable);        	  

         	  generateFilteredOutput(frequencyTable,delcan);

               
           }
          //catch (Exception e)// incase of any errors
           {
                 // System.err.println("There was a error1 : " + e.toString());
           }
    	   
       }
       
       public void convert2Fasta(String fileName) {
    	   try{
	    	   FileInputStream fStream = new FileInputStream(fileName);
	       
	           DataInputStream dInput = new DataInputStream(fStream);
	         
	           // whilst there is data available in the input stream
	           while (dInput.available() !=0)
	           {
	        	   	  
	                  String in = dInput.readLine();// read a line from the input file
	                  in = in.trim();
	                  if (in.length()>1) { // not blank line
                            System.out.println(">"+in);
                            System.out.println(in);
	                  }
	                  
	           }
	          dInput.close(); 
	   		} catch (Exception e)     {
	   				System.err.println("There was a error2 : " + e.toString());
	   		}
    	   
       }
       
       public ArrayList selectDeleteCandidate( String [][] table){
    	   int count = 0;
    	   ArrayList<String> candidate = new ArrayList<String>();    	   
    	   for(int col=1; col<NUMCOL; col++){
    		   count = 0;
    		   for(int row=1; row<NUMROW; row++) {
    			   if ( Integer.parseInt(table[row][col]) > THRESHOLD_VALUE )
    				   count ++;    			   
    		   }
    		   if ( count < THRESHOLD_SEQ_NUM )
    			   candidate.add(col+"");    		   
    	   }
    	   return candidate;
    	   
       }
       public void generateFilteredOutput(String [][] table, ArrayList<String> removeList )
       {
    	   if ( OUTPUT.equals("feature")){
    		   
    		   for(int j=1; j<NUMCOL; j++){
    			   if ( removeList.contains(j+"") == false){ 
    				   System.out.print(table[0][j]+"\n");
    			   }
    		   }
    	   }else { 
	    	   
		    	   for(int i=0; i<NUMROW; i++){
		    		   for(int j=0; j<NUMCOL; j++){
		    			   if ( removeList.contains(j+"") == false){ 
		    				   System.out.print(table[i][j]+OUTPUT_DELIM);
		    			   }
		    		   }
		    		   System.out.println();
		    	   }
	    	}
       }
       
       
       public String [][] TwoDArrayFromFile(String FileName){
    	   
    	   String [][] frequencyTable = new String[4010][400];
    	   int row=0,col;
    	   
    	   try{
		    	   FileInputStream fStream = new FileInputStream(FileName);
		       
		           DataInputStream dInput = new DataInputStream(fStream);
		         
		           // whilst there is data available in the input stream
		           while (dInput.available() !=0)
		           {
		        	   	  
		                  String in = dInput.readLine();// read a line from the input file
		                  in.trim();
		                  if (in.length()>1) {
			                  frequencyTable[row]= in.split(INPUT_DELIM );
			                  row++;           
		                  }
		                  
		           }
		           NUMROW = row;	// setting number of instances.
		           NUMCOL = frequencyTable[0].length;
		           //close the two files.
		           dInput.close(); 
    	   		} catch (Exception e)     {
    	   				System.err.println("There was a error3 : " + e.toString());
    	   		}
    	   return frequencyTable;
    	   
       }
}