package org.example;

import com.opencsv.CSVReader;
import org.example.importFile.BufferReaderCase;
import org.example.importFile.CSVImport;
import org.example.importFile.ResultContainer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static final String FILE_NAME = "generated_100mb.csv";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        File file = new File(FILE_NAME);
        Path path = file.toPath();
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

        CSVImport importer = new BufferReaderCase();
        ResultContainer result = importer.readCSVFile(FILE_NAME);
        result.masterData().forEach(System.out::println);
        result.actualData().forEach(System.out::println);
        System.out.println("Errors:\r\n");
        result.errors().forEach(System.out::println);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime)/1000f + "s");
    }
}