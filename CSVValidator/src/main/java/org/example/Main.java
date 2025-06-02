package org.example;

import org.example.importFile.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

//    public static final String FILE_NAME = "generated_100mb.csv";
//    public static final String FILE_NAME = "generated_250mb.csv";
//    public static final String FILE_NAME = "generated_500mb.csv";
//    public static final String FILE_NAME = "generated_1gb.csv";
//    public static final String FILE_NAME = "generated_2gb.csv";
//    public static final String FILE_NAME = "generated_4gb.csv";
    public static final String FILE_NAME = "generated_8gb.csv";
//    public static final String FILE_NAME = "generated_5.csv";
private static final boolean IS_LOGGING = true;

    public static void main(String[] args) {

//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            FileReader fileReader = new FileReader(file);
//            InputStreamReader input = new InputStreamReader(fileInputStream);
//            var inputStream = new DataInputStream(fileInputStream);
//            var bufferedReader = new BufferedReader(input);
//            var scanner = new Scanner(fileReader);
//            var filesLines = Files.lines(path);
//            var objectInputStream = new ObjectInputStream(fileInputStream);
//            var DataInputStream = new DataInputStream(fileInputStream);
//            var reader = new CSVReader(input);
////            reader.readNext();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try{
            runTests();
        }
        catch(Throwable e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runTests() {
        List<CSVImport> tests = new ArrayList<>();
//        tests.add(new BufferReaderCase(IS_LOGGING));
//        tests.add(new ScannerCase(IS_LOGGING));
//        tests.add(new FilesLinesCase(IS_LOGGING));
//        tests.add(new FileReaderCase(IS_LOGGING));
        tests.add(new CSVReaderCase(IS_LOGGING));

        ResultContainer lastResult = null;
        for (CSVImport importer : tests){
            ResultContainer result = runWithStopwatch(importer);
            if(lastResult != null && !result.equals(lastResult)) {
                throw new RuntimeException("Not correct result!");
            }
            lastResult = result;
        }
    }

    private static ResultContainer runWithStopwatch(CSVImport importer) {
        long startTime = System.currentTimeMillis();
        ResultContainer result = importer.readCSVFile(FILE_NAME);
//        result.masterData().forEach(System.out::println);
//        result.actualData().forEach(System.out::println);
        System.out.println(importer + " Errors:\r\n");
        result.errors().forEach(System.out::println);
        if(!result.errors().isEmpty()) throw new RuntimeException();
        System.out.println("Time for " + importer + ": " + (System.currentTimeMillis() - startTime)/1000f + "s");
        return result;
    }
}