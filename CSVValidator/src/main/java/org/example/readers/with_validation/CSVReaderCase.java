package org.example.readers.with_validation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.readers.BaseReader;
import org.example.readers.CSVImport;
import org.example.readers.ResultContainer;
import org.example.validation.ActualDataUnique;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CSVReaderCase extends BaseReader implements CSVImport {
    private final boolean isLogging;

    public CSVReaderCase(boolean isLogging) {
        this.isLogging = isLogging;
    }

    @Override
    public ResultContainer readCSVFile(String fileName) {
        ResultContainer resultContainer = new ResultContainer();
        File file = new File(fileName);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_DELIMITER_CHAR).build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(csvParser).build()) {
            int lineNumber = 1;
            Set<String> masterKeys = new HashSet<>();
            Set<ActualDataUnique> actualDataUniques = new HashSet<>();
            String[] tokens = reader.readNext();
            while (tokens != null) {
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (tokens.length == 0 || tokens[0].charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
                    tokens = reader.readNext();
                    continue;
                }
                validate(tokens, masterKeys, actualDataUniques, lineNumber, resultContainer);
                lineNumber++;
                tokens = reader.readNext();
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Problem with file " + fileName + ": " + e);
        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "CSVReaderCase";
    }
}
