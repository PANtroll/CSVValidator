package org.example.readers.without_validation;

import org.apache.commons.lang3.StringUtils;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ScannerWithoutValidationCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public ScannerWithoutValidationCase(boolean isLogging) {
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
            CSVLine csvLine = csvLines.get(i);
            String line = csvLine.line();
            int lineNumber = csvLine.lineNumber();
            if (isLogging && lineNumber % 1_000_000 == 0) {
                System.out.println(lineNumber);
            }
            String[] tokens = SplitUtil.splitLine(line);
            validate(tokens, masterKeys, actualDataUniques, lineNumber, resultContainer);
            csvLines.set(i, null);//free memory
        }

        return resultContainer;
    }

    private void readFile(String fileName, File file, List<CSVLine> csvLines) {
        try (Scanner scanner = new Scanner(file)) {
            String line;
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNumber++;
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                if (line.charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
                    continue;
                }
                csvLines.add(new CSVLine(line, lineNumber));
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);
        }
    }

    @Override
    public String toString() {
        return "ScannerWithoutValidation";
    }
}
