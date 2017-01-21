package edu.utsa.tl13;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MIPSWriter {

	public File file;
	public BufferedWriter out;
	private static MIPSWriter instance=null;
	private MIPSWriter(String file_name) throws IOException{
		createFile(file_name);
	}
	
	public static MIPSWriter getInstance(String fileName) {
		if (instance == null)
			try {
				return instance = new MIPSWriter(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}
	
	public void createFile(String file_name) {
		try{
			file = new File(file_name);
			boolean exist = file.createNewFile();
			if (!exist)
			{
				//System.out.println("File already exists.");
				file.delete();
			}
			FileWriter fstream = new FileWriter(file_name);
			out = new BufferedWriter(fstream);
			write_heading();
		}catch(Exception e) {}
	} //
	
	public void write_heading() throws IOException{
		write_line( new StringBuilder()
		.append(".data\n") 
		.append("newline:	.asciiz \"\\n\" \n").toString());
	}
	public void complete_heading() throws IOException{
		write_line( new StringBuilder()
		.append(".text\n")
		.append(".globl main\n")
		.append("main:\n")
		.append("li $fp, 0x7ffffffc \n").toString());
	}
	public void addArrayVariable (String name, int size) {
		debug("adding array " + name);
		try {
			write_line( new StringBuilder()
			.append(name+":\n")
			.append(".space " + size*4).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write_line (String s) throws IOException{
		out.write(s);
		out.newLine();
	}
	public void debug(String s){
		//System.out.println(s);
	}

	boolean exitFound = false;
	public boolean Process_Block_code(Block b){
		debug("block code ");
		if (exitFound == true) {
			debug("exit found in block "+b.getName());
			return true;
		}
		ArrayList<Instruction> mipscode = b.getMIPSCode();
		if (b.exitFlg == true) 
			exitFound = true;
		String mipslines = b.getName() + ":\n";
		
	
		for (Instruction i : mipscode){	

			 if (i.instructionName.startsWith("#"))	
				mipslines += "\n";
			else if (i.instructionName.startsWith("exit")){
				debug("processing exit flg true;");
			
			}
			mipslines += processEachInstruction(i);
		}
		try{
			write_line(mipslines);
		}catch(Exception e){
			System.out.println("Error writing cfg output file" + e);
		}
		return exitFound;
	}

	String processEachInstruction(Instruction i){
		//debug("-inside writer->" + i.instructionName);
		
		if (i.destination1 != null && i.destination2 != null){
			if (i.instructionName.contains("cbr")) {
				return String.format("%s %s, %s, %s  \n",i.instructionName,i.source1,i.destination1,i.destination2).toString();
			}
			else if (i.source1 != null && i.source2 != null){
				System.out.println("case not handled yet");
			}
			else if (i.source1 != null){
				//return String.format("%s %s , %s , %s \n",i.instructionName,i.destination1,i.source1,i.source2).toString();
				System.out.println("case not handled yet");
			}
			else if (i.source1 == null && i.source2 == null){
				System.out.println("case not handled yet");
			}
				
		}else if (i.destination1 != null) {
			if ( i.source1 != null && i.source2 != null) {
				return String.format("%s %s , %s , %s \n",i.instructionName,i.destination1,i.source1,i.source2).toString();
			}else if (i.source1 != null) {
				return String.format("%s %s , %s  \n",i.instructionName,i.destination1,i.source1).toString();
			}
			else if (i.source1 == null  && i.source2 == null){
				return String.format("%s %s  \n",i.instructionName,i.destination1).toString();
			}
		}
		else if (i.destination1 == null && i.destination2 == null) {
			if ( i.source1 != null && i.source2 != null) {
				return String.format("%s %s , %s  \n",i.instructionName,i.source1,i.source2).toString();
			}else if (i.source1 != null) {
				return String.format("%s %s   \n",i.instructionName,i.source1).toString();
			}
			return i.instructionName + "\n"	;
		}

		return null;
			
	}

	void completeWriting() throws IOException{
		//write_line()
		write_line( new StringBuilder()
		.append("# exit \n") 
		.append("li $v0, 10 \n")
		.append("syscall \n").toString());
		
		out.close();
	}

	
}
