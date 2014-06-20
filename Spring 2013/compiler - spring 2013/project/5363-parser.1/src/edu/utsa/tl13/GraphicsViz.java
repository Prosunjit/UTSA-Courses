package edu.utsa.tl13;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class GraphicsViz{
	   public File file;
	   public BufferedWriter out;
	   public int gv_index;
	   public GraphicsViz(String file_name) throws IOException{
	     createFile(file_name);
	     gv_index = 1;
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
	       write_line("digraph parseTree {");
	       write_line("\t ordering=out;");
	       write_line(" node [shape = box, style = filled];");
	    
	     }catch(Exception e) {}
	   } //
	   public void write_line (String s) throws IOException{
	     out.write(s);
	     out.newLine();
	   }
	   public void annotate_first_line(String label) throws Exception{
	     out.write("n"+gv_index + " [label=\""+label+"\",fillcolor=\"/x11/white\",shape=box]");
	     gv_index++;
	     out.newLine();
	   }
	    public int  annotate_line(String label,int from_index) {
	      try{
	        out.write("n"+gv_index + " [label=\""+label+"\",fillcolor=\"/x11/white\",shape=box]");
	        out.newLine();
	        String connection = String.format("n%s -> n%s",from_index,gv_index);
	        write_line(connection);
	        gv_index++;
	        return gv_index -1;
	      }
	      catch(Exception e) { 
	       return gv_index -1;
	      }
	   }
	   
	  } 