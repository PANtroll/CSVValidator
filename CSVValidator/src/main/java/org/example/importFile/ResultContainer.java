package org.example.importFile;

import org.example.model.CSVData;

import java.util.ArrayList;
import java.util.List;

public record ResultContainer(List<String> csvLine, List<CSVData> masterData, List<CSVData> actualData, List<String> errors) {
    public ResultContainer() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public ResultContainer(List<String> csvLine, List<CSVData> masterData, List<CSVData> actualData, List<String> errors) {
        this.csvLine = csvLine;
        this.masterData = masterData;
        this.actualData = actualData;
        this.errors = errors;
    }
}
