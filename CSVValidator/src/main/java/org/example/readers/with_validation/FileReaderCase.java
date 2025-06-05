package org.example.readers.with_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.validation.ActualDataUnique;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
            int lineNumber = 0;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            List<String> tokensList = new LinkedList<>();
            boolean isNewBuffered = false;
            int lastIndex = 0;
            String restOfLine = "";
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
                        addNewToken(isNewBuffered, restOfLine, buffer, lastIndex, i, tokensList);
                        lastIndex = i + 1;
                        isNewBuffered = false;
                        continue;
                    }
                    if (c == CARRIAGE_RETURN_CHAR) {
                        continue;
                    }
                    if (c == NEW_LINE_CHAR) {
                        addNewToken(isNewBuffered, restOfLine, buffer, lastIndex, i - 1, tokensList);
                        lastIndex = i + 1;
                        isNewBuffered = false;
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
                    }
                }
                if (lastIndex < readChars) {
                    if (buffer[readChars - 1] == CARRIAGE_RETURN_CHAR) {
                        restOfLine = new String(buffer, lastIndex, readChars - lastIndex - 1);
                    } else {
                        restOfLine = new String(buffer, lastIndex, readChars - lastIndex);
                    }
                    isNewBuffered = true;
                }
                lastIndex = 0;
                readChars = fileReader.read(buffer);
            }

        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }

        return resultContainer;
    }

    private void addNewToken(boolean isNewBuffered, String restOfLine, char[] buffer, int lastIndex, int i,
                             List<String> tokensList) {
        String tmpToken;
        if (isNewBuffered) {
            tmpToken = restOfLine + new String(buffer, lastIndex, Math.max(0, i - lastIndex));
        } else {
            tmpToken = new String(buffer, lastIndex, i - lastIndex);
        }
        tokensList.add(tmpToken);
    }

    @Override
    public String toString() {
        return "FileReaderCase";
    }
}
