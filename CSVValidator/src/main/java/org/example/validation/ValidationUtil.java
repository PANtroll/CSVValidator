package org.example.validation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;

import static org.example.validation.MasterDataValidator.INVALID_X_FORMAT_IN_LINE_X;
import static org.example.validation.MasterDataValidator.INVALID_X_IN_LINE_X;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static final String MINUS = "-";

    public static Date validateDate(String value, ValidationContainer validationContainer, String columnName) {
        if (value.length() != 10) {
            validationContainer.errors().add(String.format(INVALID_X_IN_LINE_X, columnName, validationContainer.lineNumber()));
            return null;
        }
        String[] dataParts = value.split(MINUS);
        if (dataParts.length != 3) {
            validationContainer.errors().add(INVALID_X_FORMAT_IN_LINE_X);
            return null;
        }
        try{
            int day = Integer.parseInt(dataParts[0]);
            int month = Integer.parseInt(dataParts[1]);
            int year = Integer.parseInt(dataParts[2]);
            return checkDate(validationContainer, columnName, day, month, year);
        }
        catch (NumberFormatException _){
            validationContainer.errors().add(INVALID_X_FORMAT_IN_LINE_X);
        }
        return null;
    }

    public static Date checkDate(ValidationContainer validationContainer, String columnName, int day, int month, int year) {
        try {
            LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0 ,0);
            int dayLocal = localDateTime.getDayOfMonth();
            int monthLocal = localDateTime.getMonthValue();
            int yearLocal = localDateTime.getYear();
            if(day != dayLocal || month != monthLocal || year != yearLocal) {
                validationContainer.errors().add(String.format(INVALID_X_IN_LINE_X, columnName, validationContainer.lineNumber()));
            }
            return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        } catch (Exception _) {
            validationContainer.errors().add(String.format(INVALID_X_FORMAT_IN_LINE_X, columnName, validationContainer.lineNumber()));
        }
        return null;
    }
}
