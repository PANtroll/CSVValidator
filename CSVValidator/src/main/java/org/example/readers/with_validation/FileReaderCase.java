package org.example.readers.with_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class FileReaderCase extends BaseReader implements CSVImport {

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
            List<String> tokensList = new LinkedList<>();
//            String[] lines = buffer.split(NEW_LINE);
            while (readChars > 0) {
                for (int i = 0; i < readChars; i++) {
                    char c = buffer[i];
                    if (c == CSV_COMMENT_CHAR) {
                        while (c != NEW_LINE_CHAR) {
                            i++;
                            c = buffer[i];
                        }
                    }
                    if (c == CSV_DELIMITER_CHAR) {
                        tokensList.add(tmpToken.toString());
                        tmpToken = new StringBuilder();
                        continue;
                    }
                    if (c == CARRIAGE_RETURN_CHAR) {
                        continue;
                    }
                    if (c == NEW_LINE_CHAR) {
                        tokensList.add(tmpToken.toString());
                        tmpToken = new StringBuilder();
                        String[] tokens = tokensList.toArray(new String[0]);
                        lineNumber++;
                        if (isLogging && lineNumber % 1_000_000 == 0) {
                            System.out.println(lineNumber);
                        }
                        if (StringUtils.isBlank(tokens[0])) {
                            tokensList.clear();
                            continue;
                        }
                        validate(tokens, masterKeys, actualDataUniques, lineNumber, resultContainer);
                        tokensList.clear();
                    } else {
                        tmpToken.append(c);
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
