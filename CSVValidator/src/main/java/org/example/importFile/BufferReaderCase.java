package org.example.importFile;

import java.io.*;

public class BufferReaderCase implements CSVImport{


    public static final String CSV_DELIMITER = ",";
    private static final String CSV_COMMENT = "#";

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            int lineNumber = 1;
            while (!line.isBlank()) {
                if(line.startsWith(CSV_COMMENT)) {
                    lineNumber++;
                    continue;
                }
                String[] tokens = line.split(CSV_DELIMITER);
                if(tokens[0].equals("M") && tokens.length != 20
                || tokens[0].equals("A") && tokens.length != 9) {
                    resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + line);
                }
                //new MasterData(line);

            }
        }
        catch (IOException e){
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return null;
    }
}
