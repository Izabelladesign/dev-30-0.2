package s25.cs151.application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  CSVFileManager class to manage the Csv file operations such as the writing and reading of data to a file
 *  in which is store in an OfficeHoursData Directory
 */
public class CSVFileManager {
    /**
     * Use Polymorphism to read, write and save to the CSV file
     */
    public interface CSVFileOperations {
        String[] toCSV();
    }

    private final File csvFile;
    private final File dataDir;

    public CSVFileManager(String nameOfFile) {

        dataDir = new File("OfficeHoursData");
        //checks if our directory OfficeHoursData doesn't exist
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        // creates a file object for our CSV file
        csvFile = new File(dataDir, nameOfFile + ".csv");

        try {
            //creates a file if it does not already exist
            if (csvFile.createNewFile()) {
                System.out.println("File created: " + csvFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong when creating the file.");
        }
    }

    /**
     * Adds rows of data to our CSV file
     * in which each value is seperated by commas
     */
    public void fileWrite(ArrayList<String> data) {
        // Converts the given data into a string seperated by commas
        String csvLine = String.join(",", data);
        // Join the list of values into one comma-separated line to write and save to our CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Something went wrong writing to " + csvFile.getName());

        }
    }

    /**
     * Reads all the data from our CSV files and returns it as a list of rows
     */
    public ArrayList<ArrayList<String>> fileRead() {
        ArrayList<ArrayList<String>> myDataList = new ArrayList<>();

        try {

            FileReader fr = new FileReader(csvFile);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {
                //reads each line until the end of the file
                String theLine = br.readLine();
                String[] fields = theLine.split(",");
                ArrayList<String> lineList = new ArrayList<>(Arrays.asList(fields));
                myDataList.add(lineList);
            }
            // Closes the file readers
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Something went wrong when reading.");
        }

        return myDataList;
    }

    /**
     * Save the data permanently after the information is added
     *
     * @param rows
     */
    public void overwriteFile(ArrayList<ArrayList<String>> rows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) { // false = overwrite mode
            for (ArrayList<String> row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    public void saveObjectsToFile(List<CSVFileOperations> objects) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
            for (CSVFileOperations obj : objects) {
                bw.write(String.join(",", obj.toCSV()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}