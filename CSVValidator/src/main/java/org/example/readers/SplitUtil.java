package org.example.readers;

import java.util.LinkedList;
import java.util.List;

import static org.example.readers.CSVImport.CSV_DELIMITER_CHAR;

public class SplitUtil {

    private SplitUtil() {
    }

    public static String[] splitLine(String line) {
        List<String> listOfStrings = new LinkedList<>();
        int index;
        int lastIndex = 0;
        while ((index = line.indexOf(CSV_DELIMITER_CHAR, lastIndex)) != -1) {
            listOfStrings.add(line.substring(lastIndex, index));
            lastIndex = index + 1;
        }
        listOfStrings.add(line.substring(lastIndex));
        return listOfStrings.toArray(new String[0]);
    }
}
