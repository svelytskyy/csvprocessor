package com.availity.csv;

import com.availity.csv.processor.CsvLoader;
import com.availity.csv.processor.SortFileProcessor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CsvLoader loader = new CsvLoader();
        String csvFile = "file1.csv";
        String path = "D:\\Projects\\eclipse\\TestAvaility\\files\\";
        loader.loadcsv(csvFile, path, 5);
        SortFileProcessor sortProcessor = new SortFileProcessor();
        sortProcessor.sort(path+"stage"+"\\BCBSFL.csv", path);
        //loader.apacheloadcsv(csvFile, path);
    }
}
