Name: Prosunjit Biswas (@01232785)
Current State: Submitting Extension part of the project.

Extention I have attempted:

i) permit operators to occur adjacent to identifiers/numbers/keywords without intervening spaces [LANGUAGE EXTENSION]
ii) Support for arrays of integers [LANGUAGE EXTENSION]
iii) multi-line comments whose beginning is marked by "%- and ending is marked by "-%" [LANGUAGE EXTENSION]

iv) unreachable code elimination [OPTIMIZATION]

Output file: output file is named as Inputfilename followed by ".ast.dot", ".iloc.cfg.dot", ".s"


Testing:  
i) Remove intervening space:
	Test Program: removing_space.tl13
	Status: Works fine so far have been tested.

ii) Support for arrays:
	Declaration: var Array[100] as int; 
		     Array indentifier can similarly be used as ordinary identifier.
	Test program:
	a) Array_Expression.tl13
	 Purpose: to check how various Expression works
	b) read_into_int_array.tl13
	 Purposes: Check how readInt, Writeint works; how storing values into array works.
	c) find_max_from_array.tl13
	 Purpose: shows Traversing the array works.
	d) multiple_array.tl13
	 Purpose: To show that multiple array works just fine.
	Status: Works fine so far tested. It does not support initializing the array at the time of declaration.

iii) multi-line comments
	How to Comment: multiline comment starts with '%-' and ends with '-%'
	Test Program:
	a) multiline_comment_test_case.tl13
	 purpose: To show it works with nested multiline comments
	d) Multiline_comment_2.tl13
	 Purpose: show additional scenarios
	Status: Works just fine.

iv) unreachable code Elimination:
	How it Works: when it finds 'exit' keyword, the program halts and quit generating further spim code.
	Limitation: Sometime it generates spim code that does not run successfuly, and some time give error.

	Test Program:
	a) unreachable_code_elimination.tl13
	 Purpose: to show that 'exit' works.
	Status: Not complete work. Sometest cases fail.

Instruction for running the code: 

1. Compile all the .java files (javac -d build -sourcepath src src/edu/utsa/tl13/Compiler.java)
2. Run Compile.java program with inputfile name given as first parameter. (java -cp build edu.utsa.tl13.Compiler test-inputs/testcase.tl13)



"This submission is my own work. All assistance other than that received from the instructor, from the TA, or on the class piazza forum, has been described in full above.",
Date: May 1st, 2013.
