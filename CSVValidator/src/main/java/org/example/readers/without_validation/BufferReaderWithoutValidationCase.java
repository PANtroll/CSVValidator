package org.example.readers.without_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.validation.ActualDataUnique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BufferReaderWithoutValidationCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public BufferReaderWithoutValidationCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        int lineNumber = 1;
        List<CSVLine> lines = new ArrayList<>();//check Linked
        readFile(fileName, file, lineNumber, lines);
        Set<String> masterKeys = new HashSet<>();
        Set<ActualDataUnique> actualDataUniques = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            CSVLine csvLine = lines.get(i);
            String line = csvLine.line();
            lineNumber = csvLine.lineNumber();
            if (isLogging && lineNumber % 1_000_000 == 0) {
                System.out.println(lineNumber);
            }
            validate(line, masterKeys, actualDataUniques, lineNumber, resultContainer);
            lines.set(i, null);//free memory
        }
        return resultContainer;
    }

    private void readFile(String fileName, File file, int lineNumber, List<CSVLine> lines) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while (!StringUtils.isBlank(line = bufferedReader.readLine())) {
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (line.charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
                    continue;
                }
                lines.add(new CSVLine(line, lineNumber));
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);
        }
    }


    @Override
    public String toString() {
        return "BufferReaderCase";
    }
}
