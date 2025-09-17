package org.example.validation;

import org.example.model.CSVData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record ValidationContainer(String[] tokens, CSVData data, Set<String> masterKeys,
                                  Set<ActualDataUnique> actualDataUniques, List<String> errors, int lineNumber) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ValidationContainer that = (ValidationContainer) o;
        return lineNumber == that.lineNumber && Objects.equals(data, that.data) && Arrays.equals(tokens, that.tokens) && Objects.equals(errors, that.errors) && Objects.equals(masterKeys, that.masterKeys) && Objects.equals(actualDataUniques, that.actualDataUniques);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(tokens);
        result = 31 * result + Objects.hashCode(data);
        result = 31 * result + Objects.hashCode(masterKeys);
        result = 31 * result + Objects.hashCode(actualDataUniques);
        result = 31 * result + Objects.hashCode(errors);
        result = 31 * result + lineNumber;
        return result;
    }

    @Override
    public String toString() {
        return "ValidationContainer{" +
                "tokens=" + Arrays.toString(tokens) +
                ", data=" + data +
                ", masterKeys=" + masterKeys +
                ", actualDataUniques=" + actualDataUniques +
                ", errors=" + errors +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
