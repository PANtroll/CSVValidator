package org.example.validation;

import org.apache.commons.lang3.StringUtils;
import org.example.model.ActualData;
import org.example.model.DataReportTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ActualDataValidator implements CSVValidator {


    public static final String DATE = "Date";
    public static final String AMOUNT = "Amount";
    public static final String QUANTITY = "Quantity";
    public static final String DAY = "Day";
    public static final String MONTH = "Month";
    public static final String YEAR = "Year";
    public static final String DATA_REPORT_TYPE = "Data Report Type";
    public static final String MASTER_KEY = "Master Key";

    @Override
    public boolean validate(ValidationContainer validationContainer) {

        String[] tokens = validationContainer.tokens();
        validateAndParseMasterKey(tokens[1], validationContainer);
        validateAndParseDataReportType(tokens[2], validationContainer);
        validateAndParseYear(tokens[3], validationContainer);
        validateAndParseMonth(tokens[4], validationContainer);
        validateAndParseDay(tokens[5], validationContainer);
        validateAndParseQuantity(tokens[6], validationContainer);
        validateAndParseAmount(tokens[7], validationContainer);

        if (validationContainer.errors().isEmpty()) {
            validateDate(validationContainer);
            validateUniqueness(validationContainer);
        }

        return validationContainer.errors().isEmpty();
    }


    private void validateAndParseMasterKey(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, MASTER_KEY, validationContainer.lineNumber()));
            return;
        }
        if (value.length() != 29) {
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, MASTER_KEY, validationContainer.lineNumber()));
            return;
        }
        if (!validationContainer.masterKeys().contains(value)) {
            validationContainer.errors().add(String.format(MASTER_KEY_NOT_EXIST_IN_FIRST_SECTION_IN_LINE_X, validationContainer.lineNumber()));
            return;
        }
        validationContainer.data().setMasterKey(value);
    }

    private void validateAndParseDataReportType(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, DATA_REPORT_TYPE, validationContainer.lineNumber()));
            return;
        }
        if (value.length() != 6) {
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, DATA_REPORT_TYPE, validationContainer.lineNumber()));
            return;
        }
        try {
            DataReportTypeEnum dataReportType = DataReportTypeEnum.valueOf(value);
            ((ActualData) validationContainer.data()).setDataReportType(dataReportType);
        } catch (IllegalArgumentException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, DATA_REPORT_TYPE, validationContainer.lineNumber()));
        }
    }

    private void validateAndParseYear(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, YEAR, validationContainer.lineNumber()));
            return;
        }
        if (value.length() != 4) {
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, YEAR, validationContainer.lineNumber()));
            return;
        }
        try {
            int year = Integer.parseInt(value);
            if (year < 1900 || year > 2100) {
                validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, YEAR, validationContainer.lineNumber()));
                return;
            }
            ((ActualData) validationContainer.data()).setYear(year);
        } catch (NumberFormatException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, YEAR, validationContainer.lineNumber()));
        }
    }

    private void validateAndParseMonth(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, MONTH, validationContainer.lineNumber()));
            return;
        }
        if (value.length() > 2) {
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, MONTH, validationContainer.lineNumber()));
            return;
        }
        try {
            int month = Integer.parseInt(value);
            if (month < 0 || month > 12) {
                validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, MONTH, validationContainer.lineNumber()));
                return;
            }
            ((ActualData) validationContainer.data()).setMonth(month);
        } catch (NumberFormatException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, MONTH, validationContainer.lineNumber()));
        }
    }

    private void validateAndParseDay(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, DAY, validationContainer.lineNumber()));
            return;
        }
        if (value.length() > 2) {
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, DAY, validationContainer.lineNumber()));
            return;
        }
        try {
            int day = Integer.parseInt(value);
            if (day < 0 || day > 31) {
                validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, DAY, validationContainer.lineNumber()));
                return;
            }
            ((ActualData) validationContainer.data()).setDay(day);
        } catch (NumberFormatException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, DAY, validationContainer.lineNumber()));
        }
    }

    private void validateAndParseQuantity(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, QUANTITY, validationContainer.lineNumber()));
            return;
        }
        try {
            BigDecimal quantity = new BigDecimal(value);
            if (quantity.scale() > 3) {
                validationContainer.errors().add(String.format(X_CAN_NOT_HAS_MORE_THAN_X_DIGIT_PRECISION_IN_LINE_X, QUANTITY, 3, validationContainer.lineNumber()));
                return;
            }
            quantity = quantity.setScale(3, RoundingMode.HALF_DOWN);
            if (quantity.compareTo(BigDecimal.ZERO) < 0) {
                validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, QUANTITY, validationContainer.lineNumber()));
                return;
            }
            ((ActualData) validationContainer.data()).setQuantity(quantity);
        } catch (NumberFormatException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, QUANTITY, validationContainer.lineNumber()));
        }
    }

    private void validateAndParseAmount(String value, ValidationContainer validationContainer) {
        if (StringUtils.isBlank(value)) {
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, AMOUNT, validationContainer.lineNumber()));
            return;
        }
        try {
            BigDecimal amount = new BigDecimal(value);
            if (amount.scale() > 2) {
                validationContainer.errors().add(String.format(X_CAN_NOT_HAS_MORE_THAN_X_DIGIT_PRECISION_IN_LINE_X, AMOUNT, 2, validationContainer.lineNumber()));
                return;
            }
            amount = amount.setScale(3, RoundingMode.HALF_DOWN);
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, AMOUNT, validationContainer.lineNumber()));
                return;
            }
            ((ActualData) validationContainer.data()).setAmount(amount);
        } catch (NumberFormatException e) {
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, AMOUNT, validationContainer.lineNumber()));
        }
    }

    private void validateDate(ValidationContainer validationContainer) {

        ActualData actualData = (ActualData) validationContainer.data();
        Integer day = actualData.getDay();
        Integer month = actualData.getMonth();
        Integer year = actualData.getYear();
        if (year == null || month == null || day == null) {
            return;
        }
        ValidationUtil.checkDate(validationContainer, DATE, day, month, year);
    }

    private void validateUniqueness(ValidationContainer validationContainer) {
        ActualData actualData = (ActualData) validationContainer.data();
        ActualDataUnique actualDataUnique = new ActualDataUnique(actualData.getMasterKey(), actualData.getDataReportType(),
                actualData.getYear(), actualData.getMonth(), actualData.getDay());
        if (validationContainer.actualDataUniques().contains(actualDataUnique)) {
            validationContainer.errors().add(String.format(DUPLICATED_UNIQUE_VALUES_IN_LINE_X, validationContainer.lineNumber()));
            return;
        }
        validationContainer.actualDataUniques().add(actualDataUnique);
    }

}
