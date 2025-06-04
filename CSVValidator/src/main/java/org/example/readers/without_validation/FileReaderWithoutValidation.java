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
import java.util.*;

public class FileReaderWithoutValidation extends BaseReader implements CSVImport {

    public static final char NEW_LINE_CHAR = '\n';
    public static final String NEW_LINE = "\n";
    public static final char CARRIAGE_RETURN_CHAR = '\r';
    private final boolean isLogging;

    public FileReaderWithoutValidation(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        Set<String> masterKeys = new HashSet<>();
        Set<ActualDataUnique> actualDataUniques = new HashSet<>();
        List<CSVLine> csvLines = new ArrayList<>();

        readFile(fileName, file, csvLines);

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

    private void readFile(String fileName, File file, List<CSVLine> csvLines) {
        try (FileReader fileReader = new FileReader(file)) {
            char[] buffer = new char[8_192];
            int readChars = fileReader.read(buffer);
            StringBuilder tmpToken = new StringBuilder();
            int lineNumber = 0;
            List<String> tokensList = new LinkedList<>();
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
                        String line = Arrays.stream(tokens).reduce(StringUtils.EMPTY, (str1, str2) -> str1.concat(CSV_DELIMITER).concat(str2));
                        csvLines.add(new CSVLine(line.substring(1), lineNumber));
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
    }

    @Override
    public String toString() {
        return "FileReaderWithoutValidation";
    }
}
