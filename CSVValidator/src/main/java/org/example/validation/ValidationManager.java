package org.example.validation;

public class ValidationManager {
    public static final String M = "M";
    public static final String A = "A";
    private final MasterDataValidator masterDataValidator = new MasterDataValidator();
    private final ActualDataValidator actualDataValidator = new ActualDataValidator();

    public boolean isValid(ValidationContainer validationContainer, String[] tokens) {
        if(tokens == null || tokens.length == 0) {
            return false;
        }
        if(M.equals(tokens[0])){
            return masterDataValidator.validate(validationContainer);
        }
        if(A.equals(tokens[0])){
            return actualDataValidator.validate(validationContainer);
        }
        return true;
    }

}
