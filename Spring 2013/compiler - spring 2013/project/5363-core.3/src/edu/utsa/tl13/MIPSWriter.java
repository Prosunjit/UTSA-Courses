package edu.utsa.tl13;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MIPSWriter {

	public File file;
	public BufferedWriter out;
	
	public MIPSWriter(String file_name) throws IOException{
		debug("file name to create "+file_name);
		createFile(file_name);
		
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
			write_line( new StringBuilder()
					.append(".data\n") 
					.append("newline:	.asciiz \"\\n\" \n")
					.append(".text\n")
					.append(".globl main\n")
					.append("main:\n")
					.append("li $fp, 0x7ffffffc \n").toString());
			
		}catch(Exception e) {}
	} //
	public void write_line (String s) throws IOException{
		out.write(s);
		out.newLine();
	}
	public void debug(String s){
		//System.out.println(s);
	}
	
	public void Process_Block_code(Block b){
	
		ArrayList<Instruction> mipscode = b.getMIPSCode();

		String mipslines = b.getName() + ":\n";
	
		for (Instruction i : mipscode){	
			if (i.instructionName.startsWith("#"))	
				mipslines += "\n";
			mipslines += processEachInstruction(i);
		}
		try{
			write_line(mipslines);
		}catch(Exception e){
			System.out.println("Error writing cfg output file" + e);
		}
	}

	String processEachInstruction(Instruction i){
		debug("-inside writer->" + i.instructionName);
		
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
