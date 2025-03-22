package s25.cs151.application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVFileManager {
    private final File theFile;
    private final File theDir;

    public CSVFileManager(String nameOfFile) {

        theDir = new File("OfficeHoursData");
        if (!theDir.exists()) {
            theDir.mkdir();
        }
        theFile = new File(theDir, nameOfFile + ".csv");

        try {
            if (theFile.createNewFile()) {
                System.out.println("File created: " + theFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong when creating the file.");
        }
    }

    public void fileWrite(ArrayList<String> data) {
        String csvLine = String.join(",", data);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(theFile, true))) {
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Something went wrong writing to " + theFile.getName());

        }
    }

    public ArrayList<ArrayList<String>> fileRead() {
        ArrayList<ArrayList<String>> myDataList = new ArrayList<>();

        try {

            FileReader fr = new FileReader(theFile);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {

                String theLine = br.readLine();
                String[] fields = theLine.split(",");
                ArrayList<String> lineList = new ArrayList<>(Arrays.asList(fields));
                myDataList.add(lineList);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Something went wrong when reading.");
        }

        return myDataList;
    }
}