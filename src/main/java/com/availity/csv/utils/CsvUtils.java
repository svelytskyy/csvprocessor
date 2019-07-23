package com.availity.csv.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.availity.csv.processor.CsvWriter;
import com.availity.csvprocessor.model.UserData;

public class CsvUtils {

	final static Logger log = Logger.getLogger(CsvUtils.class);
	
	public static List<String> getListOfCSVFile(String filePath) {
		String[] files = new File(filePath).list();
		ArrayList<String>list = new ArrayList<String>(); 
		for(String file : files) {
			if(file.endsWith(".csv")) {
				list.add(file);
			}
		}
		return list;
	}
	
	public static UserData toUserData(List<String> parsedLine) {
		return new UserData(parsedLine.get(0),parsedLine.get(1), new Long(Long.parseLong(parsedLine.get(2))), parsedLine.get(3));
	}
	
	public static void putValue(Hashtable<String, List<List<String>>> hashTable,List<String> lines, String company) {
		if(hashTable.get(company) == null) {
			 List<List<String>> lst = new ArrayList();
			 lst.add(lines);
			 hashTable.put(company, lst);
		}else {
			 List<List<String>> lst = hashTable.get(company);
			 lst.add(lines);
			 hashTable.remove(company);
			 hashTable.put(company, lst);
		}
	}
	
	public static void cleanStage(String path) {
		 String folder = path+"/stage/";
		 File index = new File(folder);
		 if (!index.exists()) {
		     index.mkdir();
		 } else {
			 try {
				FileUtils.cleanDirectory(new File(folder));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

	}
	
}
