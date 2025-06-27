package org.example.readers.without_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileReaderWithoutValidationCase extends BaseReader implements CSVImport {

    public static final char NEW_LINE_CHAR = '\n';
    public static final String NEW_LINE = "\n";
    public static final char CARRIAGE_RETURN_CHAR = '\r';
    private final boolean isLogging;

    public FileReaderWithoutValidationCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        Set<String> masterKeys = new HashSet<>();
        Set<ActualDataUnique> actualDataUniques = new HashSet<>();
        List<CSVLine> csvLines = new ArrayList<>();

        readFile(fileName, csvLines);

        for (int i = 0; i < csvLines.size(); i++) {
            CSVLine csvTokens = csvLines.get(i);
            int lineNumber = csvTokens.lineNumber();
            if (isLogging && lineNumber % 1_000_000 == 0) {
                System.out.println(lineNumber);
            }
            String[] tokens = SplitUtil.splitLine(csvTokens.line());
            validate(tokens, masterKeys, actualDataUniques, lineNumber, resultContainer);
            csvLines.set(i, null);
        }

        return resultContainer;
    }

    private void readFile(String fileName, List<CSVLine> csvLines) {
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file)) {
            char[] buffer = new char[256];
            int readChars = fileReader.read(buffer);
            int lineNumber = 0;
            boolean isLoading = false;
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
                    if (c == NEW_LINE_CHAR) {
                        String line;
                        if (isLoading) {
                            line = restOfLine + new String(buffer, lastIndex, Math.max(0, i - lastIndex - 1));
                            isLoading = false;
                        } else {
                            line = new String(buffer, lastIndex, i - lastIndex - 1);
                        }
                        lastIndex = i + 1;
                        lineNumber++;
                        if (isLogging && lineNumber % 1_000_000 == 0) {
                            System.out.println(lineNumber);
                        }
                        if (StringUtils.isBlank(line)) {
                            continue;
                        }
                        csvLines.add(new CSVLine(line, lineNumber));
                    }
                }
                if (lastIndex < readChars) {
                    if (buffer[readChars - 1] == CARRIAGE_RETURN_CHAR) {
                        restOfLine = new String(buffer, lastIndex, readChars - lastIndex - 1);
                    } else {
                        restOfLine = new String(buffer, lastIndex, readChars - lastIndex);
                    }
                    isLoading = true;
                }
                lastIndex = 0;
                readChars = fileReader.read(buffer);
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }
    }

    @Override
    public String toString() {
        return "FileReaderWithoutValidation";
    }
}
