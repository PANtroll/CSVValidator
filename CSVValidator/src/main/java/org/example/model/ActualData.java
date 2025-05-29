package org.example.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ActualData extends CSVData{

    private DataReportTypeEnum dataReportType;
    private Integer year;
    private Integer month;
    private Integer day;
    private BigDecimal quantity;
    private BigDecimal amount;

    public static final int NUMBER_OF_ACTUAL_DATA_FIELDS = 8;

    public DataReportTypeEnum getDataReportType() {
        return dataReportType;
    }

    public void setDataReportType(DataReportTypeEnum dataReportType) {
        this.dataReportType = dataReportType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActualData that = (ActualData) o;
        return Objects.equals(masterKey, that.masterKey) && dataReportType == that.dataReportType && Objects.equals(year, that.year) && Objects.equals(month, that.month) && Objects.equals(day, that.day) && Objects.equals(quantity, that.quantity) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(masterKey);
        result = 31 * result + Objects.hashCode(dataReportType);
        result = 31 * result + Objects.hashCode(year);
        result = 31 * result + Objects.hashCode(month);
        result = 31 * result + Objects.hashCode(day);
        result = 31 * result + Objects.hashCode(quantity);
        result = 31 * result + Objects.hashCode(amount);
        return result;
    }

    @Override
    public String toString() {
        return "ActualData{" +
                "masterKey='" + masterKey + '\'' +
                ", dataReportType=" + dataReportType +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
