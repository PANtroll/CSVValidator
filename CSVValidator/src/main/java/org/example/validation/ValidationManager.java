package org.example.validation;

public class ValidationManager {

    public boolean isValid(ValidationContainer validationContainer, String[] tokens) {
        if(tokens == null || tokens.length == 0) {
            return false;
        }
        if("M".equals(tokens[0])){
            MasterDataValidator masterDataValidator = new MasterDataValidator();
            return masterDataValidator.validate(validationContainer);
        }
        if("A".equals(tokens[0])){
            ActualDataValidator actualDataValidator = new ActualDataValidator();
            return actualDataValidator.validate(validationContainer);
        }
        return true;
    }

}
