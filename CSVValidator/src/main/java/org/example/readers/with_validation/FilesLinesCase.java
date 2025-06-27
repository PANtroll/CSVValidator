package org.example.readers.with_validation;

import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FilesLinesCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

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
                lineNumber[0]++;
                if (!line.isBlank() && line.charAt(0) != CSV_COMMENT_CHAR) {
                    String[] tokens = SplitUtil.splitLine(line);
                    validate(tokens, masterKeys, actualDataUniques, lineNumber[0], resultContainer);
                }
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
