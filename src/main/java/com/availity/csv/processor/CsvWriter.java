package com.availity.csv.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class CsvWriter {
	
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
			
		}finally {
			if(writer != null)writer.close();
		}
	}

}
