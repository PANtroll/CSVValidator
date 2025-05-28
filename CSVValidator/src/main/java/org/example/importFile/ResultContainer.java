package org.example.importFile;

import java.util.ArrayList;
import java.util.List;

public record ResultContainer(List<String> csvLine, List<Object> masterData, List<Object> actualData, List<String> errors) {
    public ResultContainer() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public ResultContainer(List<String> csvLine, List<Object> masterData, List<Object> actualData, List<String> errors) {
        this.csvLine = csvLine;
        this.masterData = masterData;
        this.actualData = actualData;
        this.errors = errors;
    }
}
