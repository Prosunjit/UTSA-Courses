import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Set;

/* This java file will read two input files process them and create an output arff file.	
*/
public class ArffWriter {			
	static HashMap bruteForceMap;
	static HashMap memeMap;
	static BufferedReader bruteForceReader;
	static BufferedReader memeReader;
	static BufferedWriter arffWriter = null;
	static BufferedWriter logWriter = null;
	static String bruteForceHeader = null;
	static String memeHeader = null;
	static String fileOne = null;
	static String delimOne = null;
	static String fileTwo = null;
	static String delimTwo = null;
	static String outputFile = null;
	static boolean bruteForceEmpty = false;
	static boolean memeEmpty = false;
	static String seqdelim = ":";
	static String defaultClass = " ?";
	static String logFile = "arffWriter.log";

	private static void closeStreams() {
		try {
			if(bruteForceReader != null) bruteForceReader.close();
			if(memeReader != null) memeReader.close();
			if(arffWriter != null) arffWriter.close();
		}
		catch(IOException e) {
			System.out.println("Exception while closing files \n" + e.getMessage());
		}
	}
	
	private static void createArffHeader() {
		try {
			arffWriter.write("@relation bio\n\n");			
			if(bruteForceEmpty == false) {
				StringTokenizer tokensBruteForce = new StringTokenizer(bruteForceHeader);
				arffWriter.write("@attribute " + tokensBruteForce.nextToken() + " string \n");
				while(tokensBruteForce.hasMoreTokens()) {
					arffWriter.write("@attribute " + tokensBruteForce.nextToken() + " numeric \n");
				}				
			}
						

			if(memeEmpty == false) {
				StringTokenizer tokensMeme = new StringTokenizer(memeHeader);
				
				/* Ignore the first attribute only when brute Force is not empty */
				if(bruteForceEmpty == false) 				
					tokensMeme.nextToken();
					
				else
					arffWriter.write("@attribute " + tokensMeme.nextToken() + " string \n");
					
				while(tokensMeme.hasMoreTokens()) {
					arffWriter.write("@attribute " + tokensMeme.nextToken() + " numeric \n");
				}
			}

			arffWriter.write("@attribute class {p, n}\n");
			arffWriter.write("\n");
		}
		catch(IOException ioe) {
			System.out.println("Exception while creating header \n" + ioe.getMessage());
		}
	}
	
	private static void createArffData() {		
		String bruteForceLine;
		String memeLine;
		String key;
		try {
			arffWriter.write("@data\n");
			if(bruteForceEmpty == true && memeEmpty == false) {
				Set keySet = memeMap.keySet();
				Iterator it = keySet.iterator();
				while(it.hasNext()) {
					key = (String)it.next();
					memeLine = (String)memeMap.get(key);
					memeLine = memeLine.substring(key.length()).trim();
					System.out.println(key + "\t" + memeLine);
					arffWriter.write(key + "\t" + memeLine + "\n");
				}
			}
			else if(bruteForceEmpty == false && memeEmpty == true) {
				Set keySet = bruteForceMap.keySet();
				Iterator it = keySet.iterator();
				while(it.hasNext()) {
					key = (String)it.next();
					bruteForceLine = (String)bruteForceMap.get(key);
					bruteForceLine = bruteForceLine.substring(key.length()).trim();
					System.out.println(key + "\t" + bruteForceLine);
					arffWriter.write(key + "\t" + bruteForceLine + "\n");
				}
			}
			else if(bruteForceEmpty == false && memeEmpty == false) {
				Set keySet = bruteForceMap.keySet();
				Iterator it = keySet.iterator();
				while(it.hasNext()) {
					key = (String)it.next();
					bruteForceLine = (String)bruteForceMap.get(key);
					memeLine = (String)memeMap.get(key);
					bruteForceLine = bruteForceLine.substring(key.length()).trim();
					memeLine = memeLine.substring(key.length()).trim();
					System.out.println(key + "\t" + bruteForceLine + "\t" + memeLine);
					arffWriter.write(key + "\t" + bruteForceLine + "\t" + memeLine + "\n");
				}
			}			
		}
		catch(IOException ioe) {
			System.out.println("Exception while creating data \n" + ioe.getMessage());
		}
	}
	
	
	public static void main(String args[]) {
		FileInputStream bfFin = null;
		FileInputStream memeFin = null;
		bruteForceMap = new HashMap();
		memeMap = new HashMap();		
		String defaultDelim = "\t";
		HashMap parametersMap;		
		
		if(args.length != 5) {
			System.out.println("Error: 5 parameters needed");
			System.exit(0);			
		}
		
		System.out.println("Parameters are");		
		
		fileOne = args[0];		
		delimOne = args[1];
		fileTwo = args[2];
		delimTwo = args[3];
		outputFile = args[4];
		
		System.out.println("Brute Force Input \"" + fileOne + "\"");
		System.out.println("Brute Force Delim \"" + delimOne + "\"");
		System.out.println("Meme Input \"" + fileTwo + "\"");
		System.out.println("Meme Delim \"" + delimTwo + "\"");
		System.out.println("Arff File \"" + outputFile + "\"");
		
		/* Hard Code for now */
		if(" ".equals(delimOne)) {
			System.out.println("Delim One is space");
			delimOne = " ";
		}
		else if("\t".equals(delimOne)) {
			System.out.println("Delim One is tab");
			delimOne = "\\t";
		}
		
		if(" ".equals(delimTwo)) {
			System.out.println("Delim two is space");
			delimTwo = " ";
		}
		else if("\t".equals(delimTwo)) {
			System.out.println("Delim two is tab");
			delimTwo = "\\t";
		}
		
		try {
			boolean bfPresent = (new File(fileOne)).exists();
			boolean memePresent = (new File(fileTwo)).exists();
			
			/* If Both files are not present then we wont proceed */
			if(bfPresent == false && memePresent == false) {
				System.out.println("Error: Both Input files are not present");
				System.exit(0);
			}
			
			bfFin = new FileInputStream(fileOne);
			memeFin = new FileInputStream(fileTwo);
			bruteForceReader = new BufferedReader(new InputStreamReader(bfFin));
			memeReader = new BufferedReader(new InputStreamReader(memeFin));
			arffWriter = new BufferedWriter(new FileWriter(outputFile));
			//logWriter = new BufferedWriter(new FileWriter(logFile));
			
			StringTokenizer tokenizer = null;
			String line = null;
			String seq = null;
			int i=1;
			
			/* If one of the files is empty then we will write the result from the one present */
			if(bfFin.available() != 0) {
				bruteForceEmpty = false;
				while((line = bruteForceReader.readLine()) != null) {
					if(line.length() == 0) continue;
					if(i == 1) {						
						bruteForceHeader = line;
						i++;
					}
					else {
						tokenizer = new StringTokenizer(line);
						if(tokenizer.hasMoreTokens()) {					
							seq = tokenizer.nextToken();
							if(seq.indexOf(seqdelim) >= 0) {
								String seqlabel[] = seq.split(seqdelim);
								String label = seqlabel[1];								
								line = seqlabel[0] + line.substring(seq.length()).trim() + " " + seqlabel[1];
								seq = seqlabel[0];
							}
							else {
								line = line + defaultClass;
							}
							System.out.println("Adding " + line + " for seq " + seq);
							bruteForceMap.put(seq, line);
						}
					}
				}
			}
			else {
				System.out.println("Brute Force Empty");
				bruteForceEmpty = true;				
			}			

			i=1;
			if(memeFin.available() != 0) {
				memeEmpty = false;
				while((line = memeReader.readLine()) != null) {				
					if(line.length() == 0) continue;
					if(i == 1) {						
						memeHeader = line;
						i++;
					}
					else {
						tokenizer = new StringTokenizer(line);
						if(tokenizer.hasMoreTokens()) {
							seq = tokenizer.nextToken();
							System.out.println("Adding " + line + " for seq " + seq);
							memeMap.put(seq, line);
						}
					}				
				}
			}
			else {
				System.out.println("Meme Empty");
				memeEmpty = true;
			}			
			
			
			System.out.print("Number of lines in Brute Force File:" + bruteForceMap.size() + "\n");
			System.out.print("Number of lines in Meme File:" + memeMap.size() + "\n");
			if(memeEmpty == false || bruteForceEmpty == false) {
				createArffHeader();
				createArffData();
			}
			
			/* Make sure to close the streams */
			closeStreams();
		}
		catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}