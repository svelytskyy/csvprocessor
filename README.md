-------------------------------------------------------------
							TASK #1
-------------------------------------------------------------	

-------------------------------------------------------------
							TASK #4
-------------------------------------------------------------										

Business case :
Coding exercise: You are tasked to write a checker that validates the parentheses of a LISP code.  
Write a program (in Java or JavaScript) which takes in a string as an input and returns true if all the parentheses 
in the string are properly closed and nested.


Implementation :

Implementation is in class com.availity.lisp.Lisp.java

-------------------------------------------------------------
							TASK #5
-------------------------------------------------------------

Business Case :

Availity receives enrollment files from various bene8ts management and enrollment 
solutions (I.e. HR platforms, payroll platforms).  Most of these files are typically in EDI format.  
However, there are some files in CSV format.  For the files in CSVformat, 
write a program that will read the content of the 8le and separate enrollees by insurance company in its own file. 
Additionally, sort the contents of each file by last and first name (ascending).  
Lastly, if there are duplicate User Ids for the same Insurance Company, then only the record with the highest version 
should be included. The following data points are included in the file:
User Id (string)
First and Last Name (string)
Version (integer)
Insurance Company (string)


Implementation :

App.java - main class

To run the project in eclipse next steps are required :

1. Import the project as maven project
2. Run as Maven install
3. Make sure Eclipse Java Build path and Java Compile are using Java 1.8 or higher
4. Change the property : point root path to your csv files in fileprocessor.properties


1. 	In order to read very large CSV files, CSV parser was implemented.
	POC was done on Apache commons-csv parser, and it was found that it loads the whole document into RAM
	which is not our case. Thats why own CSV parser was implemented which reads CSV records sequentially.
	The parser was based on CsvLexicalAnalyzer class which makes analysis of CSV record based on RFC 4180 standard.

			https://en.wikipedia.org/wiki/Comma-separated_values
	
	All unit test cases were created according above RFC 4180 standard.
	Assumtions on CSV file :
	Any size, no headers, column order is matter and in below format :
	
	User Id (string), First and Last Name (string), Version (integer), Insurance Company (string)
	
	Unit Test cases for CSV parser :
	
	1. 1997,Ford,E350
	2. "1997","Ford","E350"
	3. 1997,Ford,E350,"Super, luxurious truck"
	4. 1997,Ford,E350,"Super, ""luxurious"" truck"
	5. 1997, "Ford" ,E350
	
	Below cases are not supported :
	
	1) Fields with embedded line breaks must be quoted (however, many CSV implementations do not support embedded line breaks).
		1997,Ford,E350,"Go get one now
		they are going fast"
	2) In some CSV implementations[which?], leading and trailing spaces and tabs are trimmed (ignored). Such trimming is forbidden by RFC 4180, which states "Spaces are considered part of a field and should not be ignored."
		1997, Ford, E350
		not same as
		1997,Ford,E350
			
2.	Unfortunately there wasn't enough time to implement sorting for large files. 
	But in this case to sort large csv files algorithm Merge-Sort would be applied with Multithreading :
	One group of Threads would read, sort and merge splitted csv files and the other thread would write the result into the final CSV file.
