package org.example;

import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.with_validation.*;
import org.example.readers.without_validation.*;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static final String FILE_NAME_100MB = "generated_100mb.csv";
    public static final String FILE_NAME_250MB = "generated_250mb.csv";
    public static final String FILE_NAME_500MB = "generated_500mb.csv";
    public static final String FILE_NAME_1GB = "generated_1gb.csv";
    public static final String FILE_NAME_2GB = "generated_2gb.csv";
    public static final String FILE_NAME_4GB = "generated_4gb.csv";
    //    public static final String FILE_NAME = "generated.csv";
//    public static final String FILE_NAME = "generated_5.csv";
    private static final boolean IS_LOGGING = false;
    public static final String DELIMITER = ";";
    public static final int NUMBER_OF_TESTS = 10;
    private static final int TOTAL_TESTS = 10 * NUMBER_OF_TESTS * 6;
    private static int TEST_NUMBER = 0;

    public static void main(String[] args) {

        try {
            runTests();
//            test manually
//            runWithStopwatch(new BufferReaderCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new ScannerCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new FilesLinesCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new FileReaderCase(IS_LOGGING), null, FILE_NAME_2GB);
//            runWithStopwatch(new CSVReaderCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new BufferReaderWithoutValidationCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new ScannerWithoutValidationCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new FilesLinesWithoutValidationCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new FileReaderWithoutValidationCase(IS_LOGGING), null, FILE_NAME_4GB);
//            runWithStopwatch(new CSVReaderWithoutValidationCase(IS_LOGGING), null, FILE_NAME_2GB);

        } catch (Throwable e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runTests() throws InterruptedException {

        List<String> files = new ArrayList<>();
        files.add(FILE_NAME_100MB);
        files.add(FILE_NAME_250MB);
        files.add(FILE_NAME_500MB);
        files.add(FILE_NAME_1GB);
        files.add(FILE_NAME_2GB);
        files.add(FILE_NAME_4GB);

        try(BufferedWriter br = new BufferedWriter(new FileWriter("results.csv"))) {

            for (int i = 0; i < NUMBER_OF_TESTS; i++) {
                System.gc();
                for (String fileName : files) {
                    int masterDataSize = 0;
                    int actualDataSize = 0;
                    List<CSVImport> tests = new ArrayList<>();
                    tests.add(new BufferReaderCase(IS_LOGGING));
                    tests.add(new ScannerCase(IS_LOGGING));
                    tests.add(new FilesLinesCase(IS_LOGGING));
                    tests.add(new FileReaderCase(IS_LOGGING));
                    tests.add(new CSVReaderCase(IS_LOGGING));
                    tests.add(new BufferReaderWithoutValidationCase(IS_LOGGING));
                    tests.add(new ScannerWithoutValidationCase(IS_LOGGING));
                    tests.add(new FilesLinesWithoutValidationCase(IS_LOGGING));
                    tests.add(new FileReaderWithoutValidationCase(IS_LOGGING));
                    tests.add(new CSVReaderWithoutValidationCase(IS_LOGGING));
                    Collections.shuffle(tests);
                    for (CSVImport importer : tests) {
                        ResultContainer result = runWithStopwatch(importer, br, fileName);
                        if (result == null || !result.errors().isEmpty()) {
                            throw new RuntimeException("Not correct result!");
                        }
                        if (masterDataSize == 0) {
                            masterDataSize = result.masterData().size();
                            actualDataSize = result.actualData().size();
                        } else if (result.masterData().size() != masterDataSize
                                || result.actualData().size() != actualDataSize) {
                            throw new RuntimeException("Not correct result!");
                        }
                        result = null;//clear memory
                        tests.set(tests.indexOf(importer), null);
                        System.gc();
                        Thread.sleep(5_000);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static ResultContainer runWithStopwatch(CSVImport importer, BufferedWriter br, String fileName) throws IOException {
        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage startMemoryUsage = mbean.getHeapMemoryUsage();

        long startTime = System.currentTimeMillis();
        ResultContainer result = importer.readCSVFile(fileName);
        long endTime = System.currentTimeMillis();
        MemoryUsage memoryUsage = mbean.getHeapMemoryUsage();
//        result.masterData().forEach(System.out::println);
//        result.actualData().forEach(System.out::println);
//        System.out.println(importer + " Errors:\r\n");
        result.errors().forEach(System.out::println);
        if (!result.errors().isEmpty()) throw new RuntimeException();
        System.out.println("Memory for " + importer + ":\t\t" + (memoryUsage.getUsed() - startMemoryUsage.getUsed()) / 1000000f + "MB");
        System.out.println("Time for " + importer + ":\t\t" + (endTime - startTime) / 1000f + "s");
        System.out.println(++TEST_NUMBER + "/" + TOTAL_TESTS);
        if(br != null){
            br.write(importer + DELIMITER + fileName.substring(NUMBER_OF_TESTS, fileName.length()-4) + DELIMITER + (endTime - startTime) / 1000f + DELIMITER +
                    (memoryUsage.getUsed() - startMemoryUsage.getUsed()) / 1000000f + "\n");
        }
        return result;
    }
}