package org.example.readers.with_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BufferReaderCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public BufferReaderCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            int lineNumber = 1;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            while (!StringUtils.isBlank(line)) {
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (line.charAt(0) == CSV_COMMENT_CHAR) {
                    line = bufferedReader.readLine();
                    lineNumber++;
                    continue;
                }
                String[] tokens = SplitUtil.splitLine(line);
                validate(tokens, masterKeys, actualDataUniques, lineNumber, resultContainer);
                line = bufferedReader.readLine();
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "BufferReaderCase";
    }
}
