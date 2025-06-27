package org.example.readers.without_validation;

import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FilesLinesWithoutValidationCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public FilesLinesWithoutValidationCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        Path path = Path.of(fileName);
        final Set<String> masterKeys = new HashSet<>();
        Set<ActualDataUnique> actualDataUniques = new HashSet<>();
        List<CSVLine> csvLines = new ArrayList<>();

        readFile(fileName, path, csvLines);

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

    private void readFile(String fileName, Path path, List<CSVLine> csvLines) {
        try (Stream<String> lines = Files.lines(path)) {
            final int[] lineNumber = {1};
            lines.forEach(line -> {
                if (isLogging && lineNumber[0] % 1_000_000 == 0) {
                    System.out.println(lineNumber[0]);
                }
                lineNumber[0]++;
                if (!line.isBlank() && line.charAt(0) != CSV_COMMENT_CHAR) {
                    csvLines.add(new CSVLine(line, lineNumber[0]));
                }
            });
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);

        }
    }

    @Override
    public String toString() {
        return "FilesLinesWithoutValidation";
    }
}
