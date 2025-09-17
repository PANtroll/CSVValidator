package org.example.readers;

public interface CSVImport {

    String CSV_DELIMITER = ";";
    String M = "M";
    String A = "A";
    char CSV_DELIMITER_CHAR = ';';
    char CSV_COMMENT_CHAR = '#';
    String VALIDATION_ERROR = "Validation error! ";

    ResultContainer readCSVFile(String fileName);
}
