package org.example.importFile;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class BufferReaderCase implements CSVImport {
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
                    lineNumber++;
                    continue;
                }
                String[] tokens = line.split(CSV_DELIMITER);
                String firstToken = tokens[0];
                if (firstToken.equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                    if (validation.isValid(validationContainer, firstToken)) {
                        resultContainer.masterData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else if (firstToken.equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                    if (validation.isValid(validationContainer, firstToken)) {
                        resultContainer.actualData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else {
                    resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + line);
                }

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
