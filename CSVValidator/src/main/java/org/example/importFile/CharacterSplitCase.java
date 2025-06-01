package org.example.importFile;

import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.*;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class CharacterSplitCase implements CSVImport {

    public static final char NEW_LINE_CHAR = '\n';

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file)) {
            char[] buffer = new char[2048];
            fileReader.read(buffer);
            StringBuffer line = new StringBuffer();
            int lineNumber = 1;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            List<String> tokensList = new LinkedList<>();
            char c = buffer[0];
            while (buffer.length > 0) {
                if (c == CSV_COMMENT_CHAR) {
                    while (c != NEW_LINE_CHAR) {
                        fileReader.read(buffer);
                    }
                }
                line.append(c);
                if (c == CSV_DELIMITER_CHAR) {
                    String token = line.toString();
                    tokensList.add(token);
                }
                if (c == NEW_LINE_CHAR) {
                    String[] tokens = tokensList.toArray(new String[0]);
                    lineNumber++;
                    if (lineNumber % 1_000_000 == 0) {
                        System.out.println(lineNumber);
                    }
                    if (tokens[0].equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                        ValidationManager validation = new ValidationManager();
                        ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(), masterKeys, actualDataUniques, new ArrayList<>(), lineNumber);
                        if (validation.isValid(validationContainer, tokens)) {
                            resultContainer.masterData().add(validationContainer.data());
                        } else {
                            resultContainer.errors().add(VALIDATION_ERROR + line);
                            resultContainer.errors().addAll(validationContainer.errors());
                        }
                    } else if (tokens[0].equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                        ValidationManager validation = new ValidationManager();
                        ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(), masterKeys, actualDataUniques, new ArrayList<>(), lineNumber);
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
                fileReader.read(buffer);
            }

        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }
}
