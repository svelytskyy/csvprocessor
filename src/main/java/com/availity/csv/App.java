package com.availity.csv;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.availity.csv.processor.CsvLoader;
import com.availity.csv.processor.CsvReader;
import com.availity.csv.utils.CsvUtils;



/**
 * @author Sergiiy Velytskyy
 * 
 *  Business case :
 *    Availity receives enrollment 8les from various bene8ts management and enrollment solutions (I.e. HR platforms, payroll platforms).  
 *    Most of these 8les are typically in EDI format.  However, there are some 8les in CSV format.  
 *    For the 8les in CSVformat, write a program that will read the content of the 8le and separate enrollees by insurance company in its own 8le. 
 *    Additionally, sort the contents of each file by last and 8rst name (ascending).  Lastly, if there are duplicate User Ids for the same Insurance Company, 
 *    then only the record with the highest version should be included. The following data points are included in the file: 
 * 				
 * 					User Id (string)
 * 					First and Last Name (string)
 * 					Version (integer) 
 * 					Insurance Company (string)
 * 
 * 
 * 		1. To read csv files there was implemented CSV parser. Assumption was that files are very large and can't be loaded
 * 			into RAM fully, the access to the CSV file is sequential 
 */
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
