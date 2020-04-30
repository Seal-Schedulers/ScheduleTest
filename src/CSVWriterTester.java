import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This code is an example from geeks for geeks

public class CSVWriterTester {

	private static final String SAMPLE_CSV_FILE_PATH = "C:\\Users\\15088\\Documents\\11th\\CS\\Eclipse Stuff\\ScheduleTest\\src\\test.csv";

    public static void main(String[] args) throws IOException {
    	writeDataLineByLine(SAMPLE_CSV_FILE_PATH);
    }
    
    public static void writeDataLineByLine(String filePath) 
    { 
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
      
            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile); 
      
            // adding header to csv 
            String[] header = { "Name", "Class", "Grades", "Favorite Subject"}; 
            writer.writeNext(header); 
      
            // add data to csv 
            String[] data1 = { "Noe", "11", "100", "Math" }; 
            writer.writeNext(data1); 
            String[] data2 = { "Advika", "11", "100", "Chemistry" }; 
            writer.writeNext(data2); 
      
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
    
    public static void writeDataForCustomSeperatorCSV(String filePath) { 
      
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(filePath); 
      
        try { 
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file); 
      
            // create CSVWriter with '|' as separator 
            CSVWriter writer = new CSVWriter(outputfile); 
      
            // create a List which contains String array 
            List<String[]> data = new ArrayList<String[]>(); 
            data.add(new String[] { "Name", "Class", "Marks" }); 
            data.add(new String[] { "Aman", "10", "620" }); 
            data.add(new String[] { "Suraj", "10", "630" }); 
            writer.writeAll(data); 
      
            // closing writer connection 
            writer.close(); 
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
    } 
}
