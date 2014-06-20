package edu.utsa.tl13;



import java.util.*;
public class Parser {

	public  int current_symbol_index;
	public  ArrayList<Token> tokens;
	
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
		
		current_symbol_index=0;
		Program p;
		Drawing d = Drawing.getInstance();
		DeclarationList dl;
		Tokenizer tokenizer = new Tokenizer(InputFileName + ".tl13");
		tokens= tokenizer.getTokens();
		try {
			//debug("debug point");
		if ( (p = program())!=null) {
			p.draw(0);
			FileOperation.write2File(InputFileName+".ast.dot", d.getGVFile());
			
			if (p.okay){
				System.out.println("No Type Error :)");
				
			}else{
				System.out.println("! Type Error.The program exits without generating mips code !");
				
				return ;
			}
			Block b = new Block();
			ILOC.SetCFGFileName(InputFileName+".iloc.cfg.dot");
			ILOC.setMIPSFileName(InputFileName+".s");
			
			System.out.println("Executable MIPS file  is " + InputFileName + ".s");
			
			b = ILOC.ILOC4DeclarationList(b, p.declarations);
			Block end = ILOC.ILOC4StatementList(b, p.statements);			
			ILOC.PrintILOCCode(b);
			ILOC.PrintMIPSCode(b);
			
			ILOC.completeWriting(b,end);
			ILOC.completeMIPSWriting();
		} else {
			
		}
		
		}catch(Exception e){
			System.out.println("Program terminated without creating files." + e );
			e.printStackTrace();
		}
		
		
	}


	public boolean fetch_word(String word){

		debug("parser->fetch_word"+word);
		Token ob = tokens.get(current_symbol_index);  
		if (ob.Token_type.equals(word) == true) {
			current_symbol_index ++;
			return true;
		}
		Error_Class.showMessage("Syntax Error : " + "found "+ ob.Token_type + " instead of " + word);
		return false;
	}
	public String fetch(String word){
		Token ob = tokens.get(current_symbol_index);  
		if (ob.Token_type.equals(word) == true) {
			current_symbol_index ++;
			return ob.Token_value;
		}
		Error_Class.showMessage("Syntax Error : " + "found "+ ob.Token_type + " instead of " + word);
		return "";
	}
	public String seek_next_word( ){
		Token ob = tokens.get(current_symbol_index);      
		if (!ob.Token_value.equals("")) {
			return ob.Token_type;
		}
		return "";
	}



	public Program program(){
		DeclarationList dl;
		StatementList sl;
		
			if ( 
				fetch_word("PROGRAM") &&
				( dl=declarations() ) != null &&       
				fetch_word("BEGIN") &&
				(sl=statement_sequence())!=null &&
				fetch_word("END")
				) {
					return new Program(dl,sl);
					
			}
			
		Error_Class.showMessage("Syntax Error");
		return null;

	}
	public DeclarationList declarations( ){
		String identName;
		TypeNode tn;
	 	if (seek_next_word().equals("BEGIN")){ //matching ifsillon rule
			return DeclarationList.getInstance();
		}
		if (
				fetch_word("VAR") &&
				! (identName = fetch("ident")).equals("") &&
				fetch_word("AS") &&
				(tn = type())!= null &&
				fetch_word("SC") &&
				declarations() != null
			) 
		{ 
			DeclarationList.getInstance().AddDeclaration(new Declaration(new IdentNode(identName,tn.type), tn));
			return DeclarationList.getInstance();
		}
		else return null;
	}


	public StatementList statement_sequence(){

		StatementList sl;
		GeneralStatement stmnt;

		debug("parser: statement_sequence");
		if (Arrays.asList("END","ELSE").contains(seek_next_word())) {
			debug("parser: statement_sequence2");
			return new StatementList();
		}
		else {
			debug("parser: statement_sequence3");
			if (		
				((stmnt=statement())!= null) &&
				fetch_word("SC") &&
				(sl=statement_sequence())!=null 
				) {
			debug("parser:Statement_sequence4");
			sl.addStatement(stmnt);
			return sl;
			}
		}

		
		Error_Class.showMessage("Syntax Error in statement sequences");
		return null;
	}

	public GeneralStatement statement( ) {
		if (seek_next_word().equals("ident")){
			return assignment();
		}
		else if (seek_next_word().equals("IF")){
			return if_statement();
		}
		else if (seek_next_word().equals("WHILE")){
			return while_statement();
		}
		else if (seek_next_word().equals("WRITEINT")){
			debug("parser:statement");
			return writeInt();
		}
		else Error_Class.showMessage("Syntax Error  in statement");
		return null;
	}

	public GeneralStatement assignment( ){
		Node b1;
		String assgn,identName;
		
		if ( 
				!(identName = fetch("ident")).equals("") &&
				!(assgn = fetch("ASSGN")).equals("") &&
				(b1=beta1())!=null
			){
				return new Assignment(new IdentNode(identName,SymbolTable.getInstance().getType(identName)), new OpNode(assgn,Constant.ASSGN), b1);
		}
		else {
			Error_Class.showMessage("Syntax Error  in Assignment");
			return null;
		}
	}
	public Node beta1(){
		
		if ( Arrays.asList("ident","num","boollit","LP").contains(seek_next_word())){
			return expression(); 
		}
		else  if (seek_next_word().equals("READINT") &&  fetch_word("READINT")){
			
			return new IdentNode(Constant.READINT,Constant.INTEGER);
		}
		else Error_Class.showMessage("Syntax Error  beta1");
		return null;
	}

	public IfStatement if_statement( ){		
		Expression exp;
		StatementList ifs, elses;
		if (
				fetch_word("IF") &&
				(exp= expression())!=null &&
				fetch_word("THEN") &&
				(ifs = statement_sequence())!=null &&
				(elses = else_clause())!=null &&
				fetch_word("END")
				) return new IfStatement(exp, ifs, elses);
		else {
			Error_Class.showMessage("Syntax Error inside if statement");
			return null;
		}
	}
	public StatementList else_clause(){
		StatementList sl;
		if (seek_next_word().equals("END")) {
			return new StatementList(); // change it to null ... Correction
			
		}
		else if (
				fetch_word("ELSE") &&
				(sl=statement_sequence())!=null
				) {
			return sl;
		}
		else {
			Error_Class.showMessage("Syntax Error  inside else clause");
			return null;
		}

	}

	public WhileStatement while_statement(){
		Expression exp;
		StatementList sl;
		if (
				fetch_word("WHILE") &&
				(exp = expression())!=null &&
				fetch_word("DO") && 
				(sl = statement_sequence())!=null &&
				fetch_word("END")
			){
			return new WhileStatement(exp,sl);
		}
		Error_Class.showMessage("Syntax Error  inside while clause");
		return null;
	}
	public WriteInt writeInt(){
		Expression exp;
		if (
				fetch_word("WRITEINT") &&
				(exp = expression()) != null
				)
		{
			debug("parser: writeint - exp is not null");
			return new WriteInt(exp);
		}
		else { debug("Parser:writeint: exp is null");
		}
		Error_Class.showMessage("Syntax Error  in writeInt");
		return null;
	}

	public Expression expression(){
		SimpleExpression sexp;
		Beta2 b2;
		if (
				(sexp = simple_expression())!=null &&
				(b2 = beta2())!= null 
				) {
			return new Expression(sexp,b2.op4, b2.se);
		}
		Error_Class.showMessage("Syntax Error  in expression");
		return null;
	}
	public Beta2 beta2(){
		
		
		String op4;
		SimpleExpression se;
		if (
				seek_next_word().equals("THEN") ||
				seek_next_word().equals("DO") ||
				seek_next_word().equals("SC") ||
				seek_next_word().equals("RP")
				) return new Beta2();
		else if ( 
				!(op4 = fetch("OP4")).equals("") &&
				(se = simple_expression()) != null
				){
			return new Beta2(se, new OpNode(op4,Constant.OP4));
		}
		Error_Class.showMessage("Syntax Error  in beta2");
		return null;     

	}
	public SimpleExpression simple_expression(){
		Beta3 bt3;
		Term t;
		if ( 
				(t = term())!=null &&
				(bt3 = beta3())!=null
				){
			return new SimpleExpression(t,bt3.op3,bt3.term);
		}
		Error_Class.showMessage("Syntax Error  in simple expression");
		return null; 
	}
	public Beta3 beta3(){
		String op3;
		Term t;
		if (
				seek_next_word().equals("THEN") ||
				seek_next_word().equals("DO") ||
				seek_next_word().equals("SC") ||
				seek_next_word().equals("RP") ||
				seek_next_word().equals("OP4")
				) return new Beta3();
		else if (
				! (op3=fetch("OP3")).equals("") &&
				(t=term())!=null
				) return new Beta3(t,new OpNode(op3,Constant.OP3));
		Error_Class.showMessage("Syntax Error  in beta3");
		return null;
	}

	public Term term(){
		Factor f;
		Beta4 b4;
		if (
				(f = factor()) != null &&
				(b4 = beta4()) != null
			){
			return new Term(f,b4.op2,b4.factor);
		}
		Error_Class.showMessage("Syntax Error  in term");
		return null;
	}

	public Beta4 beta4(){
		String op2;
		Factor f;
		if (
			Arrays.asList("OP3","OP4","SC","DO","THEN","RP").contains(seek_next_word())
			) return new Beta4();
		else if (
				!(op2 = fetch("OP2")).equals("") &&
				(f=(Factor) factor())!=null
				) 
		{
			return new Beta4(f, new OpNode(op2,Constant.OP2));
		}
		Error_Class.showMessage("Syntax Error  in beta4");
		return null;
	}
	public Factor factor(){
		Expression exp;
		String identName;
		if ( seek_next_word().equals("LP") ) {
			if (
					fetch_word("LP") &&
					(exp=(Expression) expression())!=null &&
					fetch_word("RP")
					){
				//exp.nodeType="Expression"; 
				return new Factor(exp);
			}
		}
		else if ( seek_next_word().equals("ident") && !(identName=fetch("ident")).equals(""))  {
			String type = SymbolTable.getInstance().getType(identName);
			if (type == null) 
				type = Constant.UNKNOWNTYPE;
			return new Factor(new IdentNode(identName,type));
		}
		else if ( seek_next_word().equals("num") && !(identName=fetch("num")).equals("") ){ //identName = num value.
			return new Factor(new  NumNode(Long.parseLong(identName)));
		}
		else if ( seek_next_word().equals("boollit") && !(identName=fetch("boollit")).equals("") ){
			return new Factor( new BoolNode(Boolean.parseBoolean(identName)));
		}
		Error_Class.showMessage("Syntax Error  in factor");
		return null;
	}

	public TypeNode type(){
		
		if ( seek_next_word().equals("INT") && fetch_word("INT")){
			return new TypeNode(Constant.INTEGER);
		}
		if ( seek_next_word().equals("BOOL") && fetch_word("BOOL") ) {
			return new TypeNode(Constant.BOOLEAN);  
		};
		
		Error_Class.showMessage("Syntax Error ");
		return null;
	}


}
