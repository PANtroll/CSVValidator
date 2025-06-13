package org.example.readers;

import org.example.model.CSVData;

import java.util.ArrayList;
import java.util.List;

public record ResultContainer(List<String> csvLine, List<CSVData> masterData, List<CSVData> actualData,
                              List<String> errors) {
    public ResultContainer() {

        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
