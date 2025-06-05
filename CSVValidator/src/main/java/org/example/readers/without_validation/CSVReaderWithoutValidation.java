package org.example.readers.without_validation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
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

public class CSVReaderWithoutValidation extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public CSVReaderWithoutValidation(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        Set<String> masterKeys = new HashSet<>();
        List<CSVLine> csvLines = new ArrayList<>();
        Set<ActualDataUnique> actualDataUniques = new HashSet<>();

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
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_DELIMITER_CHAR).build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(csvParser).build()) {
            int lineNumber = 1;
            String[] tokens = reader.readNext();
            while (tokens != null) {
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (tokens[0].charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
                    continue;
                }

                String line = String.join(CSV_DELIMITER, tokens);
                csvLines.add(new CSVLine(line, lineNumber));
                lineNumber++;
                tokens = reader.readNext();
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Problem with file " + fileName + ": " + e);
        }
    }

    @Override
    public String toString() {
        return "CSVReaderWithoutValidation";
    }
}
