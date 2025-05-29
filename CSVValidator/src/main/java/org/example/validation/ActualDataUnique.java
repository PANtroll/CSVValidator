package org.example.validation;

import org.example.model.DataReportTypeEnum;

public record ActualDataUnique(String masterKey, DataReportTypeEnum dataReportType, int year, int month, int day) {
}
