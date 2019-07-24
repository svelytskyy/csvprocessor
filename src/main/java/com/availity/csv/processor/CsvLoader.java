package com.availity.csv.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import com.availity.csv.lexical.CsvFormat;
import com.availity.csv.lexical.CsvLexicalAnalyzer;
import com.availity.csv.parser.CsvRecordParser;
import com.availity.csv.utils.CsvUtils;
import com.availity.csvprocessor.model.UserData;



/**
 * @author Sergiy Velytskyy
 * 
 * reads CSV file sequentially, writes data into buffered, filtered by insurance company.
 * Than buffer with buffer size passed from App is flushed to the file.
 *
 */
public class CsvLoader {
	
	final static Logger log = Logger.getLogger(CsvLoader.class);
	
	private Hashtable<String, List<List<String>>> companyBufferData = new Hashtable<String, List<List<String>>>();
	
	public ArrayList<UserData> loadcsv(String csvFile, String path, long bufferSize) {
		
		String file = path + csvFile;
	    Scanner scanner = null;
		try {
			CsvUtils.cleanStage(path);
			scanner = new Scanner(new File(file));
			long recordNum = 1;
			CsvRecordParser parser = new CsvRecordParser();
			List<UserData>buffer = new ArrayList<UserData>();
			while (scanner.hasNext()) {
	           String line = scanner.nextLine();
	           try {
	        	   List<String>parsedline = null;
	        	   try {
	        		   parsedline = parser.parseRecord(line, new CsvLexicalAnalyzer(CsvFormat.DEFAULT));
	   			   } catch (Exception e) {
				     log.error(e);
	        		   CsvWriter writer = new CsvWriter();
	        		   writer.writeLineIntoFile(path, line);				     
	   			   }        	   
	        	   log.debug(parsedline);
	        	   CsvUtils.putValue(companyBufferData, parsedline, parsedline.get(3));
	        	   if((recordNum % bufferSize) == 0 || !scanner.hasNext()) {
	        		   CsvWriter writer = new CsvWriter();
	        		   writer.writeToFile(companyBufferData, path);
	        		   log.debug(companyBufferData);
	        		   companyBufferData =  new Hashtable<String, List<List<String>>>();
	        	   }
			} catch (Exception e) {
				log.error(e);
			}
	           //List<String> parsedLine = parser.parseRecord(line, ',', '"');
	           recordNum++;
			}
			
		} catch (FileNotFoundException e) {
			log.error(e);
		} finally {
			if(scanner != null)	scanner.close();
		}
		return null;
	}
	
	public ArrayList<UserData> apacheloadcsv(String csvFile, String path){
		Reader reader = null;
		try {     
			reader = Files.newBufferedReader(Paths.get(path+csvFile));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("User id", "First and Last Name", "Version", "Insurance Company")
                    .withIgnoreHeaderCase()
                    .withTrim());
            int rowNum = 0;
            for (CSVRecord csvRecord : csvParser) {
            	System.out.println(csvRecord);
                // Accessing Values by Column Index
            	if(rowNum != 0) {
            		rowNum++;
	            	UserData data = new UserData();
	                data.setUserid(csvRecord.get(0));
	                data.setFullname(csvRecord.get(1));
	                data.setVersion(Long.parseLong(csvRecord.get(2)));
	                data.setCompany(csvRecord.get(3));
	                log.debug(data);
            	}
            	else {
            		rowNum++;
            	}
            }
	    }catch(Exception e) {
	        	log.error(e);
	    }finally {
	    	if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e);;
				}
	    }
		return null;
	}
	
}
