package com.availity.csv.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.availity.csv.lexical.CsvFormat;
import com.availity.csv.lexical.CsvLexicalAnalyzer;
import com.availity.csv.parser.CsvRecordParser;
import com.availity.csvprocessor.model.UserData;

public class CsvReader {
	
	final static Logger log = Logger.getLogger(CsvReader.class);
	
	private void processDuplicateUsers(Map<String,UserData>map, UserData data, List<Long>lstPosToRemove){
		UserData mapedUser = map.get(data.getUserid());
		if((mapedUser != null && mapedUser.getVersion() < data.getVersion())){
			map.put(data.getUserid(), data);
			lstPosToRemove.add(mapedUser.getPos());
		}else if( mapedUser == null){
			map.put(data.getUserid(), data);
		}
	}
	
	//remove duplicates and sort the file
	public List<UserData>sortAndRemoveDuplicates(String filePath, String basePath){
		Scanner scanner = null;
		List<UserData>lst = new ArrayList<UserData>();
		try {
			scanner = new Scanner(new File(filePath));
			long recordNum = 1;
			CsvRecordParser parser = new CsvRecordParser();
			List<Long>lstPosToRemove = new ArrayList<Long>();
			Map<String,UserData>map = new HashMap<String,UserData>();
			while (scanner.hasNext()) {
	           String line = scanner.nextLine();
	           try {
	        	   List<String>parsedline = parser.parseRecord(line, new CsvLexicalAnalyzer(CsvFormat.DEFAULT));
	        	   UserData data = new UserData();
	        	   try {
	        		   data.createUserData(parsedline, recordNum-1);
	        		   processDuplicateUsers(map, data, lstPosToRemove);
	        	   } catch(Exception e) {
	        		   log.error(e);
	        		   CsvWriter writer = new CsvWriter();
	        		   writer.writeLineIntoFile(basePath, line);
	        	   }
	        	   lst.add(data);
	        	   log.debug(parsedline);
	           } catch (Exception e) {
				log.error(e);
	           }
	           //List<String> parsedLine = parser.parseRecord(line, ',', '"');
	           recordNum++;
			}
			
			//remove duplicate users and sort it
			for(Long pos : lstPosToRemove) {
				lst.remove(pos);
			}
			Collections.sort(lst);
			log.debug(lst);
			log.debug(lstPosToRemove);
			log.debug(map);
			CsvWriter writer = new CsvWriter();
			writer.writeUserDataIntoFile(filePath, lst, false);
		} catch (FileNotFoundException e) {
			log.error(e);
		} finally {
			if(scanner != null)	scanner.close();
		}
		return lst;
	}
	
	public List<UserData>readFile(String filePath, String basePath){
		Scanner scanner = null;
		List<UserData>lst = new ArrayList<UserData>();
		try {
			scanner = new Scanner(new File(filePath));
			long recordNum = 0;
			CsvRecordParser parser = new CsvRecordParser();
			while (scanner.hasNext()) {
	           String line = scanner.nextLine();
	           try {
	        	   List<String>parsedline = parser.parseRecord(line, new CsvLexicalAnalyzer(CsvFormat.DEFAULT));
	        	   UserData data = new UserData();
	        	   try {
	        		   data.createUserData(parsedline, recordNum);
	        	   } catch(Exception e) {
	        		   log.error(e);
	        		   CsvWriter writer = new CsvWriter();
	        		   writer.writeLineIntoFile(basePath, line);
	        	   }
	        	   lst.add(data);
	        	   log.debug(parsedline);
			} catch (Exception e) {
				log.error(e);
			}
	           //List<String> parsedLine = parser.parseRecord(line, ',', '"');
	           recordNum++;
			}
			//Collections.sort(lst);
			log.debug(lst);
			CsvWriter writer = new CsvWriter();
			writer.writeUserDataIntoFile(filePath, lst, false);
		} catch (FileNotFoundException e) {
			log.error(e);
		} finally {
			if(scanner != null)	scanner.close();
		}
		return lst;
	}
	
}
