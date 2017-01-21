import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;
class KMer 
{
	private static String SEQUENCE_FILE="input_sequence.txt";
	private static String PATTERN_FILE="input_pattern.txt";
	private static String OUTPUT_FILE="k-mer-frequency.txt";
	private static String DELIM=" ";
	private static int MAX_MER_LENGTH=5;	
	private static int MISMATCH_START_FROM_LENGTH=5; //when 5 mismatch = 2
	private static int MISMATCH_START_WITH=1;	
   public static void main(String args[])
	{
     
	   KMer kmer_generator = new KMer();
	   if ( args.length >= 6){ //mismatch number
		   MISMATCH_START_WITH = Integer.parseInt(args[5]);
	   }
	   if (args.length >= 5){
		   MISMATCH_START_FROM_LENGTH = Integer.parseInt(args[4]);
	   }
	   if (args.length >= 4) {
		   System.out.println(args[3]);
		   MAX_MER_LENGTH= Integer.parseInt(args[3]);
	   }
	   if ( args.length >=3){
			OUTPUT_FILE = args[2];
		}
	   if ( args.length >=2 ){
			SEQUENCE_FILE = args[0];
			PATTERN_FILE = args[1];
		}
		else{
			System.out.println("Your default sequence file is input_sequence.txt");
			//return ;
		}
		ArrayList<String> sequence = kmer_generator.readFile(SEQUENCE_FILE,9999999); //read whole file.
		ArrayList<String> pattern = kmer_generator.readFile(PATTERN_FILE,MAX_MER_LENGTH); // read file upto a length.
		//System.out.println(sequence);
		//System.out.println(pattern);
		kmer_generator.calculateFrequency(sequence,pattern);
		
		
	}
   public void calculateFrequency(ArrayList<String> sequence, ArrayList<String> pattern){
	   ListIterator<String> seqI = sequence.listIterator();
	   ListIterator<String> patI = pattern.listIterator();
	   String freLine="";
	   String searchInSeq;
	   String searchPattern;
	   String output="";
	   int patLenth=0;
	   int mismatch = 0;
	   
	   /* Print the header */
	   output = "sequence";
	   while(patI.hasNext()){
		   searchPattern = patI.next();
		   output += DELIM + searchPattern;		   
	   }
	   output += "\n";
	   
	   while (seqI.hasNext()) {
		   searchInSeq = seqI.next();
		   patI = pattern.listIterator();
		   freLine = searchInSeq.trim().replace(" ", ":");
		   while( patI.hasNext()){
			   searchPattern = patI.next();
			   patLenth = searchPattern.length();
			   if (patLenth >= MISMATCH_START_FROM_LENGTH){
				   mismatch = MISMATCH_START_WITH;
				   if (mismatch <= 0)
					   freLine += DELIM + countMatches(searchInSeq, searchPattern);
				   else
					   freLine += DELIM + matchWithMismatch(searchInSeq, searchPattern,mismatch);
			   }
			   else freLine += DELIM + countMatches(searchInSeq, searchPattern);
		   }
		   //System.out.println(freLine);
		   freLine += "\n";
		   output += freLine;
	   }
	   //System.out.println(output);
	  write2File(OUTPUT_FILE,output);
	   
   }
   
   public int matchWithMismatch(String matchIn, String matchFor, int mismatch){
	   int count = 0;
	   int matchForLength = matchFor.length();
	   for(int i=0; i<matchIn.length()-matchForLength -1 ; i++){
		   
		   String tmp = matchIn.substring(i,i+matchForLength);
		   if (countMismatch(tmp,matchFor)<=mismatch){
			   //System.out.println("mistach is "+ tmp + "::"+matchFor);
			   count ++;
		   }
	   }
	   return count;
   }
   public int countMismatch(String matchIn, String matchFor){
	   int count = 0;
	   	for(int i=0; i<matchIn.length();i++){
	   		
	   		if (matchIn.charAt(i) != matchFor.charAt(i))
	   			count ++;
	   	}
	   return count;
   }
   public int countMatches(String str, String sub) {
	      if (isEmpty(str) || isEmpty(sub)) {
	          return 0;
	      }
	      int count = 0;
	      int idx = 0;
	      while ((idx = str.indexOf(sub, idx)) != -1) {
	          count++;
	          idx += sub.length();
	      }
	      return count;
	  }
   public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
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

   public ArrayList<String> readFile(String file, long lineLength){
	   
	   ArrayList<String> content = new ArrayList<String>();
	   try{
	    FileInputStream fstream = new FileInputStream(file);		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;		
		while ((strLine = br.readLine()) != null) 	{
			if (strLine.trim().length()>1)
			{
				if( strLine.trim().length() > lineLength )
					break;
				content.add(strLine);
			}
			
		}
		
		in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	   return content;
	   
   }
   
}

