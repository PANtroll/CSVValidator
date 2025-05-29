package org.example.validation;

public interface CSVValidator {

    String INVALID_X_IN_LINE_X = "Invalid %s in line %d!";
    String INVALID_X_FORMAT_IN_LINE_X = "Invalid %s format in line %d!";
    String EMPTY_X_IN_LINE_X = "Empty %s in line %d!";
    String INVALID_X_LENGTH_IN_LINE_X = "Invalid %s length in line %d!";
    String X_ALREADY_EXIST_IN_LINE_X = "%s already exist in line %d!";
    String X_LENGTH_IS_TOO_LONG_IN_LINE_X = "%s length is too long in line %d!";
    String INVALID_X_VALUE_IN_LINE_X = "Invalid %s value in line %d!";
    String X_CAN_NOT_BE_NEGATIVE_IN_LINE_X = "%s can not be negative in line %d!";
    String X_CAN_NOT_BE_MORE_THAN_99_IN_LINE_X = "%s can not be more than 99 in line %d!";
    String NOT_CORRECT_NUMBER_OF_COLUMNS_IN_LINE_X = "Not correct number of columns in line %d!";
    String X_CAN_NOT_BE_AFTER_X_IN_LINE_X = "%s can not be after %s in line %d!";
    String MASTER_KEY_NOT_EXIST_IN_FIRST_SECTION_IN_LINE_X = "Master Key not exist in First Section in line %d!";
    String X_CAN_NOT_HAS_MORE_THAN_X_DIGIT_PRECISION_IN_LINE_X = "%s can not has more than %d digit precision in line %d!";
    String DUPLICATED_UNIQUE_VALUES_IN_LINE_X = "Duplicated unique values in line %d!";

    boolean validate(ValidationContainer container);
}
