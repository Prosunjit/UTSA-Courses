package edu.utsa.tl13;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CFGGV {

	public File file;
	public BufferedWriter out;
	
	public CFGGV(String file_name) throws IOException{
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
					.append("digraph cfgviz {\n") 
					.append("node [shape = none];\n")
					.append("edge [tailport = s];\n")
					.append("entry\n")
					.append("subgraph cluster {\n")
					.append("color=\"/x11/white\"\n").toString());
			
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
		//debug("inside CFGGV");
		ArrayList<String> iloc = b.ILOCCode;
		debug("inside CFGGV2");
		String blockCode = b.getName() + " [label=<<table border=\"0\">" + processHeaderLine(b.getName());
		for (String s: iloc){
			blockCode += processEachLine(s);
		}
		try{
		write_line (blockCode + "</table>>,fillcolor=\"/x11/white\",shape=box]");
		b.visited = true;
		}catch(Exception e){
			System.out.println("Error writing cfg output file" + e);
		}
	}
	public void addEdge(String from, String to){
		try{
			write_line(String.format("%s -> %s",from,to));
		}catch(Exception e){
			
		}
	}
	String processEachLine(String s){
		return String.format("<tr><td border=\"0\" colspan=\"1\">%s</td></tr>",s);
	}
	String processHeaderLine(String s){
		return String.format("<tr><td border=\"1\" colspan=\"1\">%s</td></tr>",s);
	}
	void completeWriting() throws IOException{
		//write_line()
		out.close();
	}

	
}
