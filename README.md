-------------------------------------------------------------
							TASK #1
-------------------------------------------------------------	

Question : Tell me about your proudest professional achievement.  It can be a personal or school project. 

Answer : My all projects in all companies where I worked before are deserved to be considered
		as my professional achievement. It's very hard to determine which one is the proudest since I try to be 
		a creative person in each of them.
		For example, there was implemented own CSV parser with sequential access to 
		files with very large volume in Availity assignment of the last task . Some ideas of Lexical analyzer were 
		taken in Apache CSV parser and some improvement was made in open source code : https://github.com/apache/commons-csv.
		But unfortunately, not all ideas like : multithreading merge-sort of large files were implemented since it's a big and time consuming task.
		In Florida Blue - my current company latest project achievements are  Message Center, Eligibility & benefits Service Engine,
		Cloud PAAS migration, Member Emulation. One of Florida BLue Certificate of Appreciation is attached.
  



-------------------------------------------------------------
							TASK #2
-------------------------------------------------------------	

Question : Tell me a about a book, blog, article or GitHub repo you read or liked recently, and why you like it and why you should recommend I do the same. 

Answer : The latest tech book I have almost completed is Next Generation Databases: NoSQL, NewSQL, and Big Data.
 		This book is about helping you choose the correct database technology at a time. 
 		In today's world when technologies are blooming daily it becomes very important to understand the concept but not details especially databases.  
	

-------------------------------------------------------------
							TASK #3
-------------------------------------------------------------

Question : If you were to describe to a 7-year old what Availity does, what would you say? 

Answer : I would say in many cases it's even difficult to explain to adult person :). 

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
solutions (I.e. HR platforms, payroll platforms).  Most of these 8les are typically in EDI format.  
However, there are some 8les in CSV format.  For the 8les in CSVformat, 
write a program that will read the content of the 8le and separate enrollees by insurance company in its own 8le. 
Additionally, sort the contents of each 8le by last and 8rst name (ascending).  
Lastly, if there are duplicate User Ids for the same Insurance Company, then only the record with the highest version 
should be included. The following data points are included in the 8le:
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
			
2.	Unfortunately there wasn't enough time to implement sorting for large files. 
	But in this case to sort large csv files algorithm Merge-Sort would be applied with Multithreading :
	One group of Threads would read, sort and merge splitted csv files and the other thread would write the result into the final CSV file.