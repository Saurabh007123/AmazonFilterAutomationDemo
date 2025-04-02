package csvFile;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVFileWriter 
{
	public static String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    public static String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\datafiles\\DataStore_" + timestamp + ".csv";
    
	public static void WriteCSVFileWriter(String Product_title, String getReviewCount, String get_ProductRating, String get_productPrice) {

		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) { // Append mode
            // Check if the file is empty to write the header
            if (new File(filePath).length() == 0) {
                String[] header = {"Prod_Title", "Review_cnt","ProductRating","ProductPrice"};
                writer.writeNext(header); // Write header only if the file is empty
            }
            String[] data = {Product_title, String.valueOf(getReviewCount), get_ProductRating, get_productPrice};
            writer.writeNext(data); // Write new data
            writer.flush(); // Ensure data is written
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
      }
   }
}