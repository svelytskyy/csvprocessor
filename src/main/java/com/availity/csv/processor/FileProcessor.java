package com.availity.csv.processor;

import java.util.List;
import org.apache.log4j.Logger;
import com.availity.csvprocessor.model.UserData;

public class FileProcessor{
	
	final static Logger log = Logger.getLogger(FileProcessor.class);
	
	public void processFile(String filePath, String basePath) {

		CsvReader reader = new CsvReader();
		List<UserData>lines = reader.readFile(filePath, basePath);
		CsvWriter writer = new CsvWriter();
		writer.writeUserDataIntoFile(filePath, lines, false);
	}
	
}
