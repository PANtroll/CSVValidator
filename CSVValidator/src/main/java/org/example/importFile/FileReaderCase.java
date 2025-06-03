package org.example.importFile;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class FileReaderCase implements CSVImport {

    public static final char NEW_LINE_CHAR = '\n';
    public static final String NEW_LINE = "\n";
    public static final char CARRIAGE_RETURN_CHAR = '\r';
    private final boolean isLogging;

    public FileReaderCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file)) {
            char[] buffer = new char[8_192];
            int readChars = fileReader.read(buffer);
            StringBuilder tmpToken = new StringBuilder();
            int lineNumber = 0;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
//            List<String> tokensList = new LinkedList<>();
//            String[] lines = buffer.split(NEW_LINE);
            boolean isFirstChar = true;
            while (readChars > 0) {
                for (int i = 0; i < readChars; i++) {
                    char c = buffer[i];
                    if (isFirstChar && c == CSV_COMMENT_CHAR) {
                        while (c != NEW_LINE_CHAR) {
                            i++;
                            c = buffer[i];
                        }
                    }
                    if (c == CSV_DELIMITER_CHAR) {
                        tmpToken.append(c);
                        continue;
                    }
                    if (c == CARRIAGE_RETURN_CHAR) {
                        continue;
                    }
                    if (c == NEW_LINE_CHAR) {
                        String[] tokens = tmpToken.toString().split(CSV_DELIMITER);
                        tmpToken = new StringBuilder();
                        String firstToken = tokens[0];
                        lineNumber++;
                        isFirstChar = true;
                        if (isLogging && lineNumber % 1_000_000 == 0) {
                            System.out.println(lineNumber);
                        }
                        if (firstToken.equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                            ValidationManager validation = new ValidationManager();
                            ValidationContainer validationContainer = new ValidationContainer(tokens,
                                    new MasterData(), masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                            if (validation.isValid(validationContainer, firstToken)) {
                                resultContainer.masterData().add(validationContainer.data());
                            } else {
                                resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                                resultContainer.errors().addAll(validationContainer.errors());
                            }
                        } else if (firstToken.equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                            ValidationManager validation = new ValidationManager();
                            ValidationContainer validationContainer = new ValidationContainer(tokens,
                                    new ActualData(), masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                            if (validation.isValid(validationContainer, firstToken)) {
                                resultContainer.actualData().add(validationContainer.data());
                            } else {
                                resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                                resultContainer.errors().addAll(validationContainer.errors());
                            }
                        } else {
                            resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + tmpToken);
                        }
                    } else {
                        tmpToken.append(c);
                        isFirstChar = false;
                    }
                }
                readChars = fileReader.read(buffer);
            }

        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "FileReaderCase";
    }
}
