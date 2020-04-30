import java.io.FileReader;

import com.opencsv.CSVReader;

public class CSVReaderTester {

	public static void main(String[] args) {
		readDataLineByLine("C:\\Users\\15088\\Documents\\11th\\CS\\Eclipse Stuff\\ScheduleTest\\src\\test.csv");
	}
	public static void readDataLineByLine(String file) 
	{ 
	  
	    try { 
	  
	        // Create an object of filereader 
	        // class with CSV file as a parameter. 
	        FileReader filereader = new FileReader(file); 
	  
	        // create csvReader object passing 
	        // file reader as a parameter 
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	  
	        // we are going to read data line by line 
	        while ((nextRecord = csvReader.readNext()) != null) { 
	            for (String cell : nextRecord) { 
	                System.out.print(cell); 
	            } 
	            System.out.println(); 
	        } 
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	} 

}
