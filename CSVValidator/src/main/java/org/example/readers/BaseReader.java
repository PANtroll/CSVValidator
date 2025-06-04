package org.example.readers;

import org.example.model.ActualData;
import org.example.model.MasterData;
import org.example.validation.ActualDataUnique;
import org.example.validation.ValidationContainer;
import org.example.validation.ValidationManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import static org.example.model.ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS;
import static org.example.model.MasterData.NUMBER_OF_MASTER_DATA_FIELDS;
import static org.example.readers.CSVImport.M;
import static org.example.readers.CSVImport.A;
import static org.example.readers.CSVImport.VALIDATION_ERROR;

public class BaseReader {

    protected void validate(String[] tokens, Set<String> masterKeys, Set<ActualDataUnique> actualDataUniques,
                                 int lineNumber, ResultContainer resultContainer) {
        String firstToken = tokens[0];
        if (firstToken.equals(M) && tokens.length == NUMBER_OF_MASTER_DATA_FIELDS) {
            ValidationManager validation = new ValidationManager();
            ValidationContainer validationContainer = new ValidationContainer(tokens, new MasterData(), masterKeys,
                    actualDataUniques, new LinkedList<>(), lineNumber);
            if (validation.isValid(validationContainer, firstToken)) {
                resultContainer.masterData().add(validationContainer.data());
            } else {
                resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                resultContainer.errors().addAll(validationContainer.errors());
            }
        } else if (firstToken.equals(A) && tokens.length == NUMBER_OF_ACTUAL_DATA_FIELDS) {
            ValidationManager validation = new ValidationManager();
            ValidationContainer validationContainer = new ValidationContainer(tokens, new ActualData(), masterKeys,
                    actualDataUniques, new LinkedList<>(), lineNumber);
            if (validation.isValid(validationContainer, firstToken)) {
                resultContainer.actualData().add(validationContainer.data());
            } else {
                resultContainer.errors().add(VALIDATION_ERROR + Arrays.toString(tokens));
                resultContainer.errors().addAll(validationContainer.errors());
            }
        } else {
            resultContainer.errors().add("Not correct numbers of column in row: " + lineNumber + " " + Arrays.toString(tokens));
        }
    }
}
