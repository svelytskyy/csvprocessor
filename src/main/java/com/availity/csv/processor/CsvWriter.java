package com.availity.csv.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class CsvWriter {
	
	final static Logger log = Logger.getLogger(CsvWriter.class);
	
	public void writeToFile(Hashtable<String, List<List<String>>> companyBufferData, String path) throws IOException{
		BufferedWriter writer = null;
		try {
			 Set<String> keys = companyBufferData.keySet();
			 String folder = path+"/stage/";
			 for(String key : keys) {
				String file = folder+key+".csv" ;
				 writer = new BufferedWriter(
		                 new FileWriter(file, true)  //Set true for append mode
		         );
				 List<List<String>> lines = companyBufferData.get(key);
				 for(List<String> line : lines) {
					 writer.write(line.get(0) + "," + line.get(1) + "," + line.get(2) + "," + line.get(3));
					 writer.newLine();
				 }
				 writer.close();
			 }
		}catch(Exception e) {
			log.error(e);
		}finally {
			if(writer != null)writer.close();
		}
	}
	
	public void writeFile(String file, String line) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(
			         new FileWriter(file, true)
			);
			writer.write(line);
		} catch (IOException e) {
			log.error(e);
		}finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
