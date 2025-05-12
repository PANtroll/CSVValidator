package org.example;

import com.opencsv.CSVReader;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        File file = new File("generated.csv");
        try {
            InputStreamReader sb = new InputStreamReader(new FileInputStream(file));
            var inputStream = new DataInputStream(new FileInputStream(file));
            inputStream.read
            CSVReader reader = new CSVReader(sb);
            reader.readNext();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Hello world!");
    }
}