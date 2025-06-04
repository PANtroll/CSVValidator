package org.example.readers.with_validation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
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
                if (tokens[0].charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
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
