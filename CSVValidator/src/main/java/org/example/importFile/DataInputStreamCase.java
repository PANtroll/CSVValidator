package org.example.importFile;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class DataInputStreamCase implements CSVImport {

    public static final char NEW_LINE_CHAR = '\n';
    public static final String NEW_LINE = "\n";

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
            String s = inputStream.readUTF();
            char[] buffer = s.toCharArray();
            StringBuffer line = new StringBuffer();
            int lineNumber = 1;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            List<String> tokensList = new LinkedList<>();
//            String[] lines = buffer.split(NEW_LINE);
            while (buffer.length > 0) {
                for (int i = 0; i < buffer.length; i++) {
                    char c = buffer[i];
                    if (c == CSV_COMMENT_CHAR) {
                        while (c != NEW_LINE_CHAR) {
                            i++;
                            c = buffer[i];
                        }
                    }

                    line.append(c);
                    if (c == CSV_DELIMITER_CHAR) {
                        String token = line.toString();
                        tokensList.add(token);
                    }
                    if (c == '\r'){
                        continue;
                    }
                    if (c == NEW_LINE_CHAR) {
                        String[] tokens = tokensList.toArray(new String[0]);
                        lineNumber++;
                        if (lineNumber % 100_000 == 0) {
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
                }
                buffer = inputStream.readUTF().toCharArray();
            }

        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }
}
