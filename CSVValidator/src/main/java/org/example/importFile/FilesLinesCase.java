package org.example.importFile;

import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Stream;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class FilesLinesCase implements CSVImport {
    private boolean isLogging = false;

    public FilesLinesCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        Path path = Path.of(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            final int[] lineNumber = {1};
            final Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            lines.forEach(line -> {
                if (isLogging && lineNumber[0] % 1_000_000 == 0) {
                    System.out.println(lineNumber[0]);
                }
                if (line.startsWith(CSV_COMMENT)) {
                    lineNumber[0]++;
                    return;
                }
                String[] tokens = line.split(CSV_DELIMITER);
                if (tokens[0].equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber[0]);
                    if (validation.isValid(validationContainer, tokens)) {
                        resultContainer.masterData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else if (tokens[0].equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber[0]);
                    if (validation.isValid(validationContainer, tokens)) {
                        resultContainer.actualData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + line);
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else {
                    resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber[0] + " " + line);
                }

                lineNumber[0]++;
            });
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "FilesLinesCase";
    }
}
