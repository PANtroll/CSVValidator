package org.example.readers;

public interface CSVImport {

    String CSV_DELIMITER = ";";
    String CSV_COMMENT = "#";
    String M = "M";
    String A = "A";
    char M_CHAR = 'M';
    char A_CHAR = 'A';
    char CSV_DELIMITER_CHAR = ';';
    char CSV_COMMENT_CHAR = '#';
    String VALIDATION_ERROR = "Validation error! ";

    ResultContainer readCSVFile(String fileName);
}
