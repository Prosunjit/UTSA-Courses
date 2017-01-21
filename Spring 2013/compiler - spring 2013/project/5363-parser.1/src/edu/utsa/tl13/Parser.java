package edu.utsa.tl13;


import java.util.*;
public class Parser {

    
  public  int current_symbol_index;
  
  public int gv_index;
  public  ArrayList<Token> tokens;
  GraphicsViz gv;
  static String  InputFileName;
  
  public String seekNext(){
    
    return "";
  }
  
    public void my_parser (String t113_file) {
      InputFileName  = t113_file;
      Parser parser = new Parser();
      parser.parse();       
    };
    
    public static void debug( String s) {
    
     //System.out.println(s);
    }
    
    public void parse(){
      try{
       gv = new GraphicsViz(InputFileName+".pt.dot"); //basename here
      }catch(Exception e) {}
       current_symbol_index=0;
       
       Tokenizer tokenizer = new Tokenizer(InputFileName);
       tokens= tokenizer.getTokens();
       
       for (Token t: tokens){
        //System.out.println(t.Token_type);
      }
       if ( program())
    	   System.out.println("Successfully Created outputfile");
       
       try{
         gv.write_line("}");
         gv.out.close();
       }catch(Exception e) {}
    }

    
    public boolean fetch_word(String word,int from_index){
      
      Token ob = tokens.get(current_symbol_index);  
      //System.out.println(current_symbol_index + " " + ob.Token_type);
      if (ob.Token_type.equals(word) == true) {
        current_symbol_index ++;
        try{
              gv.annotate_line(ob.Display_string,from_index);
                        
        }catch(Exception e) {}
        
        return true;
      }
     Error_Class.showMessage("Syntax Error : " + "found "+ ob.Token_type + " instead of " + word);
     return false;
    }
    public String seek_next_word( ){
      Token ob = tokens.get(current_symbol_index);
      
      if (!ob.Token_value.equals("")) {
        //debug("next symbol is " + ob.Token_type);
        return ob.Token_type;
      }
      return "";
      }
     
    
    
    public boolean program(){
      int gv_first_track = gv.gv_index;
      try{
      gv.annotate_first_line("program");
      }catch(Exception e){}
      if ( 
          fetch_word("PROGRAM",gv_first_track) &&
          declarations(gv_first_track) &&       
          fetch_word("BEGIN",gv_first_track) &&
          statement_sequence(gv_first_track) &&
          fetch_word("END",gv_first_track)
         )
        return true;
      Error_Class.showMessage("Syntax Error");
      return false;
      
    }
      public boolean declarations(int from_index){
    
      int gv_first_track = gv.gv_index;
      
      if (seek_next_word().equals("BEGIN")){ //matching ifsillon rule
        int x = gv.annotate_line("declaration",from_index);
        gv.annotate_line("IFSILON",x);
        return true;
      }
       try{
         gv.annotate_line("declaration",from_index);
       }catch(Exception e){}
      if (
          fetch_word("VAR",gv_first_track) &&
          fetch_word("ident",gv_first_track) &&
          fetch_word("AS",gv_first_track) &&
          type(gv_first_track) &&
          fetch_word("SC",gv_first_track) &&
          declarations(gv_first_track)
        )    return true;
     else return false;
    }
    
    
    public boolean statement_sequence(int from_index){
      int gv_first_track = gv.gv_index;
      if (!Arrays.asList("END","ELSE").contains(seek_next_word()))
        gv.annotate_line("statement sequence",from_index); // drawing gv_node
      if (Arrays.asList("END","ELSE").contains(seek_next_word())) {
        
        int x = gv.annotate_line("statement sequence",from_index);
        gv.annotate_line("IFSILON",x);
        return true;
      }
      else if (
        statement(gv_first_track) &&
        fetch_word("SC",gv_first_track) &&
        statement_sequence(gv_first_track) 
       ) {
             /*try{
               gv.annotate_line("statement sequence",current_symbol_index);
             }catch(Exception e){}*/
            return true;
        }
      Error_Class.showMessage("Syntax Error in statement sequences");
      return false;
    }
    
    public boolean statement(int from_index) {
      int gv_first_track = gv.gv_index;
      gv.annotate_line("statement",from_index); // drawing gv_node
      if (seek_next_word().equals("ident")) return assignment(gv_first_track);
      else if (seek_next_word().equals("IF")) return if_statement(gv_first_track);
      else if (seek_next_word().equals("WHILE")) return while_statement(gv_first_track);
      else if (seek_next_word().equals("WRITEINT")) return writeInt(gv_first_track);
      else Error_Class.showMessage("Syntax Error  in statement");
      return false;
    }
    
    public boolean assignment(int from_index){
      int gv_first_track = gv.gv_index;
      gv.annotate_line("assignment",from_index); // drawing gv_node
      if ( 
          fetch_word("ident",gv_first_track) &&
          fetch_word("ASSGN",gv_first_track) &&
          beta1(gv_first_track)
         ) return true;
      else {
        Error_Class.showMessage("Syntax Error  in Assignment");
        return false;
      }
    }
    public boolean beta1(int from_index){
      int gv_first_track = from_index;
      if ( Arrays.asList("ident","num","boollit","LP").contains(seek_next_word())) return expression(gv_first_track); 
      else  if (seek_next_word().equals("READINT") &&  fetch_word("READINT",gv_first_track)) return true;
      else Error_Class.showMessage("Syntax Error  beta1");
      return false;
    }
    
    public boolean if_statement(int from_index){
       int gv_first_track = gv.gv_index;
       gv.annotate_line("if statement",from_index); // drawing gv_node
      if (
          fetch_word("IF",gv_first_track) &&
          expression(gv_first_track) &&
          fetch_word("THEN",gv_first_track) &&
          statement_sequence(gv_first_track) &&
          else_clause(gv_first_track) &&
          fetch_word("END",gv_first_track)
         ) return true;
      else {
        Error_Class.showMessage("Syntax Error inside if statement");
        return false;
      }
    }
    public boolean else_clause(int from_index){
       int gv_first_track = gv.gv_index;
       gv.annotate_line("else clause",from_index); // drawing gv_node
       if (seek_next_word().equals("END")) {
          int x = gv.annotate_line("else clause",from_index);
          gv.annotate_line("IFSILON",x);
        return true;
       }
      else if (
               fetch_word("ELSE",gv_first_track) &&
               statement_sequence(gv_first_track)
              ) return true;
      else {
        Error_Class.showMessage("Syntax Error  inside else clause");
        return false;
      }
        
    }
    
    public boolean while_statement(int from_index){
       int gv_first_track = gv.gv_index;
       gv.annotate_line("while",from_index); // drawing gv_node
      if (
          fetch_word("WHILE",gv_first_track) &&
          expression(gv_first_track) &&
          fetch_word("DO",gv_first_track) && 
          statement_sequence(gv_first_track) &&
          fetch_word("END",gv_first_track)
         ) return true;
      Error_Class.showMessage("Syntax Error  inside while clause");
      return false;
    }
    public boolean writeInt(int from_index){
      int gv_first_track = gv.gv_index;
      gv.annotate_line("writeInt",from_index); // drawing gv_node
      if (
          fetch_word("WRITEINT",gv_first_track) &&
          expression(gv_first_track)
         ) return true;
      Error_Class.showMessage("Syntax Error  in writeInt");
      return false;
    }
    
    public boolean expression(int from_index){
      int gv_first_track = gv.gv_index;
      gv.annotate_line("expression",from_index); // drawing gv_node
      if (
          simple_expression(gv_first_track) &&
          beta2(from_index)
         ) return true;
      Error_Class.showMessage("Syntax Error  in expression");
      return false;
    }
    public boolean beta2(int from_index){
       int gv_first_track = from_index;
      if (
          seek_next_word().equals("THEN") ||
          seek_next_word().equals("DO") ||
          seek_next_word().equals("SC") ||
          seek_next_word().equals("RP")
         ) return true;
      else if ( 
               fetch_word("OP4",gv_first_track) &&
               simple_expression(gv_first_track)
              ) return true;
      Error_Class.showMessage("Syntax Error  in beta2");
      return false;     
      
    }
    public boolean simple_expression(int from_index){
      int gv_first_track = gv.gv_index;
      gv.annotate_line("simple expression",from_index); // drawing gv_node
      if ( 
          term(gv_first_track) &&
          beta3(from_index)
         ) return true;
      Error_Class.showMessage("Syntax Error  in simple expression");
      return false; 
    }
    public boolean beta3(int from_index){
       int gv_first_track = from_index;
      if (
          seek_next_word().equals("THEN") ||
          seek_next_word().equals("DO") ||
          seek_next_word().equals("SC") ||
          seek_next_word().equals("RP") ||
          seek_next_word().equals("OP4")
         ) return true;
      else if (
               fetch_word("OP3",gv_first_track) &&
               term(gv_first_track)
              ) return true;
      Error_Class.showMessage("Syntax Error  in beta3");
      return false;
    }
    
    public boolean term(int from_index){
       int gv_first_track = gv.gv_index;
      gv.annotate_line("term",from_index); // drawing gv_node
      if (
          factor(gv_first_track) &&
          beta4(from_index)
         ) return true;
      Error_Class.showMessage("Syntax Error  in term");
      return false;
     }
    
    public boolean beta4(int from_index){
      int gv_first_track = from_index;
      if (
          Arrays.asList("OP3","OP4","SC","DO","THEN","RP").contains(seek_next_word())
         ) return true;
      else if (
               fetch_word("OP2",gv_first_track) &&
               factor(gv_first_track)
              ) return true;
      Error_Class.showMessage("Syntax Error  in beta4");
      return false;
    }
    public boolean factor(int from_index){
      int gv_first_track = gv.gv_index;
      gv.annotate_line("factor",from_index); // drawing gv_node
      if ( seek_next_word().equals("LP") ) {
        if (
            fetch_word("LP",gv_first_track) &&
            expression(gv_first_track) &&
            fetch_word("RP",gv_first_track)
           ) return true;
      }
      else if ( seek_next_word().equals("ident") && fetch_word("ident",gv_first_track) ) return true;
      else if ( seek_next_word().equals("num") && fetch_word("num",gv_first_track) ) return true;
      else if ( seek_next_word().equals("boollit") && fetch_word("boollit",gv_first_track) ) return true;
      Error_Class.showMessage("Syntax Error  in factor");
      return false;
    }
 
    public boolean type(int from_index){
       int gv_first_track = gv.gv_index;
      gv.annotate_line("type",from_index); // drawing gv_node
      
      if ( seek_next_word().equals("INT") && fetch_word("INT",gv_first_track)) return true;
      if ( seek_next_word().equals("BOOL") && fetch_word("BOOL",gv_first_track) ) return true;
      Error_Class.showMessage("Syntax Error ");
      return false;
    }
    
   

  


}
