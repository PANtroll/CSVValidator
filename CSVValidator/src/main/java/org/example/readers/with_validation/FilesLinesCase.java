package org.example.readers.with_validation;

import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.readers.SplitUtil;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

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
                if (line.charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber[0]++;
                    return;
                }
//                String[] tokens = line.split(CSV_DELIMITER);
                String[] tokens = SplitUtil.splitLine(line);
                validate(tokens, masterKeys, actualDataUniques, lineNumber[0], resultContainer);
                lineNumber[0]++;
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
