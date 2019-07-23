package com.availity.csv.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.availity.csv.lexical.CsvFormat;
import com.availity.csv.lexical.CsvLexicalAnalyzer;
import com.availity.csv.parser.CsvRecordParser;
import com.availity.csvprocessor.model.UserData;

public class SortFileProcessor{
	
	final static Logger log = Logger.getLogger(SortFileProcessor.class);
	
	public void sort(String filePath, String basePath) {
	    Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filePath));
			long recordNum = 1;
			CsvRecordParser parser = new CsvRecordParser();
			List<UserData>lst = new ArrayList<UserData>();
			while (scanner.hasNext()) {
	           String line = scanner.nextLine();
	           try {
	        	   List<String>parsedline = parser.parseRecord(line, new CsvLexicalAnalyzer(CsvFormat.DEFAULT));
	        	   UserData data = new UserData();
	        	   try {
	        		   data.createUserData(parsedline);
	        	   } catch(Exception e) {
	        		   log.error(e);
	        		   CsvWriter writer = new CsvWriter();
	        		   writer.writeFile(basePath, line);
	        	   }
	        	   lst.add(data);
	        	   log.debug(parsedline);
			} catch (Exception e) {
				log.error(e);
			}
	           //List<String> parsedLine = parser.parseRecord(line, ',', '"');
	           recordNum++;
			}
			Collections.sort(lst);
			log.debug(lst);
			
		} catch (FileNotFoundException e) {
			log.error(e);
		} finally {
			if(scanner != null)	scanner.close();
		}

	}
	
}
