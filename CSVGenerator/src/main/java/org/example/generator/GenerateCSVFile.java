package org.example.generator;

import java.io.*;

public class GenerateCSVFile {

    public static final int NUMBER_OF_GENERATED_FIRST_SECTION_LINES = 4*1_200_000;//1gb
    public static final int NUMBER_OF_GENERATED_SECOND_SECTION_LINES = 4*10_800_000;//1gb
//    public static final int NUMBER_OF_GENERATED_FIRST_SECTION_LINES = 250 * 1_200;//1mb
//    public static final int NUMBER_OF_GENERATED_SECOND_SECTION_LINES = 250 * 10_800;//1mb

    public File generateFile(){
        GenerateLine generateLine = new GenerateLine();
        File newFile = new File("generated.csv");
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(newFile))){

            for (int i = 0; i < NUMBER_OF_GENERATED_FIRST_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionFirst());
                if(i%1_000 == 0){
                    System.out.println("Progress 1: " + (double) i/NUMBER_OF_GENERATED_FIRST_SECTION_LINES);
                }
            }
            for (int i = 0; i < NUMBER_OF_GENERATED_SECOND_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionSecond());
                if(i%1_000 == 0){
                    System.out.println("Progress 2: " + (double) i/NUMBER_OF_GENERATED_SECOND_SECTION_LINES);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return newFile;
    }
}
