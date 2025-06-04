package org.example.importFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;

public class CSVReaderCase implements CSVImport {
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
                String firstToken = tokens[0];
                if (isLogging && lineNumber % 1_000_000 == 0) {
                    System.out.println(lineNumber);
                }
                if (firstToken.charAt(0) == CSV_COMMENT_CHAR) {
                    lineNumber++;
                    continue;
                }
                if (firstToken.equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                    if (validation.isValid(validationContainer, firstToken)) {
                        resultContainer.masterData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else if (firstToken.equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
                    ValidationManager validation = new ValidationManager();
                    ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(),
                            masterKeys, actualDataUniques, new LinkedList<>(), lineNumber);
                    if (validation.isValid(validationContainer, firstToken)) {
                        resultContainer.actualData().add(validationContainer.data());
                    } else {
                        resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                        resultContainer.errors().addAll(validationContainer.errors());
                    }
                } else {
                    resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + Arrays.toString(tokens));
                }
                lineNumber++;
                tokens = reader.readNext();
            }
        } catch (IOException e) {
            System.out.println("Problem with file " + fileName + ": " + e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return resultContainer;
    }

    @Override
    public String toString() {
        return "CSVReaderCase";
    }
}
