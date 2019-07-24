package com.availity.csv;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.availity.csv.processor.CsvLoader;
import com.availity.csv.processor.CsvReader;
import com.availity.csv.utils.CsvUtils;


public class App 
{
	final static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
        CsvLoader loader = new CsvLoader();
        Properties prop = CsvUtils.loadProperty();
        String path = prop.getProperty("insurance.folder.root");
        List<String>csvFiles = CsvUtils.getListOfCsvFiles(path);
        for(String csvFile : csvFiles) {
        	loader.loadcsv(csvFile, path, 1000);
        }
        log.debug(csvFiles);
        List<String>csvStageFiles = CsvUtils.getListOfCsvFiles(path+"\\stage\\");
        log.debug(csvStageFiles);
        for(String csvFile : csvStageFiles) {
        	CsvReader reader = new CsvReader();
        	reader.sortAndRemoveDuplicates(path+"stage\\"+csvFile, path);
        }
    }
    
    
}
