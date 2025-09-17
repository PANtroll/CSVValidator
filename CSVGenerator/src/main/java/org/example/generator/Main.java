package org.example.generator;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("Timestamp: " + new Date());
        GenerateCSVFile generateCSVFile = new GenerateCSVFile();
        generateCSVFile.generateFile();
        System.out.println("Timestamp: " + new Date());
    }
}
