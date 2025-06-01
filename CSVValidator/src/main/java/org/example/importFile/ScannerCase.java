package org.example.importFile;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class ScannerCase implements CSVImport {
    private boolean isLogging = false;

    public ScannerCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            String line = "";
            int lineNumber = 1;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNumber++;
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (line.startsWith(CSV_COMMENT)) {
                    lineNumber++;
                    continue;
                }
                String[] tokens = line.split(CSV_DELIMITER);
                if (tokens[0].equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(),
                            masterKeys, actualDataUniques, new ArrayList<>(), lineNumber);
                    if (validation.isValid(validationContainer, tokens)) {
                        resultContainer.masterData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else if (tokens[0].equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(),
                            masterKeys, actualDataUniques, new ArrayList<>(), lineNumber);
                    if (validation.isValid(validationContainer, tokens)) {
                        resultContainer.actualData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else {
                    resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "ScannerCase";
    }
}
