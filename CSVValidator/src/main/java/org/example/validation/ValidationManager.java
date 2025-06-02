package org.example.validation;

public class ValidationManager {
    public static final String M = "M";
    public static final String A = "A";
    private final MasterDataValidator masterDataValidator = new MasterDataValidator();
    private final ActualDataValidator actualDataValidator = new ActualDataValidator();

    public boolean isValid(ValidationContainer validationContainer, String firstToken) {
        if (validationContainer.tokens() == null) {
            return false;
        }
        if (M.equals(firstToken)) {
            return masterDataValidator.validate(validationContainer);
        }
        if (A.equals(firstToken)) {
            return actualDataValidator.validate(validationContainer);
        }
        return true;
    }

}
