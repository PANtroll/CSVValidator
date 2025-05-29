package org.example.validation;

import org.example.model.CSVData;

import java.util.List;
import java.util.Set;

public record ValidationContainer(String[] tokens, CSVData data, Set<String> masterKeys, Set<ActualDataUnique> actualDataUniques, List<String> errors, int lineNumber) {
}
