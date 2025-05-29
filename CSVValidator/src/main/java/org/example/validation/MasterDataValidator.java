package org.example.validation;

import org.apache.commons.lang3.StringUtils;
import org.example.model.MasterData;
import org.example.model.PlantTypeEnum;
import org.example.model.PowerTypeEnum;
import org.example.model.VoltageLevelEnum;

import java.util.Date;

public class MasterDataValidator implements CSVValidator {

    @Override
    public boolean validate(ValidationContainer validationContainer){

        String[] tokens = validationContainer.tokens();
        if(tokens.length != MasterData.NUMBER_OF_MASTER_DATA_FIELDS){
            validationContainer.errors().add(String.format(NOT_CORRECT_NUMBER_OF_COLUMNS_IN_LINE_X, validationContainer.lineNumber()));
            return false;
        }
        validateAndParseMasterKey(tokens[1], validationContainer);
        validateAndParseCity(tokens[2], validationContainer);
        validateAndParseStreet(tokens[3], validationContainer);
        validateAndParsePostCode(tokens[4], validationContainer);
        validateAndParseName(tokens[5], validationContainer);
        validateAndParseRegion(tokens[6], validationContainer);
        validateAndParseFuel(tokens[7], validationContainer);
        validateAndParseStartDate(tokens[8], validationContainer);
        validateAndParseEndDate(tokens[9], validationContainer);
        validateAndParseModificationDate(tokens[10], validationContainer);
        validateAndParsePower(tokens[11], validationContainer);
        validateAndParsePowerType(tokens[12], validationContainer);
        validateAndParseControllability(tokens[13], validationContainer);
        validateAndParseVoltageLevel(tokens[14], validationContainer);
        validateAndParseMeasurementLocationId(tokens[15], validationContainer);
        validateAndParsePlantType(tokens[16], validationContainer);
        validateAndParseSoundOptimization(tokens[17], validationContainer);
        validateAndParseIsBiomassBonus(tokens[18], validationContainer);
        validateAndParseIsBiomassTechnologyBonus(tokens[19], validationContainer);

        if(validationContainer.errors().isEmpty()){
            validateDateRelations(validationContainer);
        }

        return validationContainer.errors().isEmpty();
    }

    private void validateAndParseMasterKey(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Master Key", validationContainer.lineNumber()));
            return;
        }
        if(value.length() != 29){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Master Key", validationContainer.lineNumber()));
            return;
        }
        if(validationContainer.masterKeys().contains(value)){
            validationContainer.errors().add(String.format(X_ALREADY_EXIST_IN_LINE_X, "Master Key", validationContainer.lineNumber()));
            return;
        }
        validationContainer.masterKeys().add(value);
        validationContainer.data().setMasterKey(value);
    }
    private void validateAndParseCity(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "City" , validationContainer.lineNumber()));
            return;
        }
        if(value.length() > 256){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "City", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setCity(value);
    }
    private void validateAndParseStreet(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Street", validationContainer.lineNumber()));
            return;
        }
        if(value.length() > 256){
            validationContainer.errors().add(String.format(X_LENGTH_IS_TOO_LONG_IN_LINE_X, "Street", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setStreet(value);
    }
    private void validateAndParsePostCode(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Post Code", validationContainer.lineNumber()));
            return;
        }
        if(value.length() != 5){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Post Code", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setPostCode(value);
    }
    private void validateAndParseName(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Name", validationContainer.lineNumber()));
            return;
        }
        if(value.length() > 256){
            validationContainer.errors().add(String.format(X_LENGTH_IS_TOO_LONG_IN_LINE_X, "Name", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setName(value);
    }
    private void validateAndParseRegion(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Region", validationContainer.lineNumber()));
            return;
        }
        if(value.length() != 2){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Region", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setRegion(value);
    }
    private void validateAndParseFuel(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Fuel", validationContainer.lineNumber()));
            return;
        }
        if(value.length() > 2){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Fuel", validationContainer.lineNumber()));
            return;
        }
        try{
            int fuel = Integer.parseInt(value);
            if(fuel < 0){
                validationContainer.errors().add(String.format(X_CAN_NOT_BE_NEGATIVE_IN_LINE_X, "Fuel", validationContainer.lineNumber()));
                return;
            }
            if(fuel >= 100){
                validationContainer.errors().add(String.format(X_CAN_NOT_BE_MORE_THAN_99_IN_LINE_X, "Fuel", validationContainer.lineNumber()));
                return;
            }
            ((MasterData)validationContainer.data()).setFuel(fuel);
        }
        catch(NumberFormatException e){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Fuel", validationContainer.lineNumber()));
        }
    }
    private void validateAndParseStartDate(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Start Date", validationContainer.lineNumber()));
            return;
        }
        Date startDate = ValidationUtil.validateDate(value, validationContainer, "Start Date");
        if(startDate != null){
            ((MasterData) validationContainer.data()).setStartDate(startDate);
        }
    }
    private void validateAndParseEndDate(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            return;
        }
        Date endDate = ValidationUtil.validateDate(value, validationContainer, "End Date");
        if(endDate != null){
            ((MasterData) validationContainer.data()).setEndDate(endDate);
        }
    }
    private void validateAndParseModificationDate(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            return;
        }
        Date modificationDate = ValidationUtil.validateDate(value, validationContainer, "Modification Date");
        if(modificationDate != null){
            ((MasterData) validationContainer.data()).setEndDate(modificationDate);
        }
    }
    private void validateAndParsePower(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Power", validationContainer.lineNumber()));
            return;
        }
        try{
            int power = Integer.parseInt(value);
            if(power < 0){
                validationContainer.errors().add(String.format(X_CAN_NOT_BE_NEGATIVE_IN_LINE_X, "Power", validationContainer.lineNumber()));
                return;
            }
            ((MasterData)validationContainer.data()).setPower(power);
        }
        catch(NumberFormatException e){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Power", validationContainer.lineNumber()));
        }
    }
    private void validateAndParsePowerType(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Power Type", validationContainer.lineNumber()));
            return;
        }
        if(value.length() != 5){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Power Type", validationContainer.lineNumber()));
            return;
        }
        try{
            PowerTypeEnum plantType = PowerTypeEnum.valueOf(value);
            ((MasterData) validationContainer.data()).setPowerType(plantType);
        }
        catch(IllegalArgumentException e){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Power Type", validationContainer.lineNumber()));
        }
    }
    private void validateAndParseControllability(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Controllability", validationContainer.lineNumber()));
            return;
        }
        if("0".equals(value)){
            ((MasterData)validationContainer.data()).setControllability(Boolean.FALSE);
            return;
        }
        if("1".equals(value)){
            ((MasterData)validationContainer.data()).setControllability(Boolean.TRUE);
            return;
        }
        validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Controllability", validationContainer.lineNumber()));
    }
    private void validateAndParseVoltageLevel(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Voltage Level", validationContainer.lineNumber()));
            return;
        }
        if(value.length() != 5){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Voltage Level", validationContainer.lineNumber()));
            return;
        }
        try{
            VoltageLevelEnum voltageLevel = VoltageLevelEnum.valueOf(value);
            ((MasterData)validationContainer.data()).setVoltageLevel(voltageLevel);
        }
        catch(IllegalArgumentException e){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Voltage Level", validationContainer.lineNumber()));
        }
    }
    private void validateAndParseMeasurementLocationId(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Measurement Location ID", validationContainer.lineNumber()));
            return;
        }
        if (value.length() != 26){
            validationContainer.errors().add(String.format(INVALID_X_LENGTH_IN_LINE_X, "Measurement Location ID", validationContainer.lineNumber()));
            return;
        }
        ((MasterData)validationContainer.data()).setMeasurementLocationId(value);
    }
    private void validateAndParsePlantType(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Plant Type", validationContainer.lineNumber()));
            return;
        }
        try{
            PlantTypeEnum plantType = PlantTypeEnum.valueOf(value);
            ((MasterData) validationContainer.data()).setPlantType(plantType);
        }
        catch(IllegalArgumentException e){
            validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Plant Type", validationContainer.lineNumber()));
            return;
        }
    }
    private void validateAndParseSoundOptimization(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Sound Optimization", validationContainer.lineNumber()));
            return;
        }
        if("0".equals(value)){
            ((MasterData)validationContainer.data()).setSoundOptimization(0);
            return;
        }
        if("1".equals(value)){
            ((MasterData)validationContainer.data()).setSoundOptimization(1);
            return;
        }
        if("2".equals(value)){
            ((MasterData)validationContainer.data()).setSoundOptimization(2);
            return;
        }
        validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Sound Optimization", validationContainer.lineNumber()));
    }
    private void validateAndParseIsBiomassBonus(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Biomass Bonus", validationContainer.lineNumber()));
            return;
        }
        if("0".equals(value)){
            ((MasterData)validationContainer.data()).setBiomassBonus(Boolean.FALSE);
            return;
        }
        if("1".equals(value)){
            ((MasterData)validationContainer.data()).setBiomassBonus(Boolean.TRUE);
            return;
        }
        validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Biomass Bonus", validationContainer.lineNumber()));
    }
    private void validateAndParseIsBiomassTechnologyBonus(String value, ValidationContainer validationContainer){
        if(StringUtils.isBlank(value)){
            validationContainer.errors().add(String.format(EMPTY_X_IN_LINE_X, "Biomass Technology Bonus", validationContainer.lineNumber()));
            return;
        }
        if("0".equals(value)){
            ((MasterData)validationContainer.data()).setBiomassTechnologyBonus(Boolean.FALSE);
            return;
        }
        if("1".equals(value)){
            ((MasterData)validationContainer.data()).setBiomassTechnologyBonus(Boolean.TRUE);
            return;
        }
        validationContainer.errors().add(String.format(INVALID_X_VALUE_IN_LINE_X, "Biomass Technology Bonus", validationContainer.lineNumber()));
    }
    private void validateDateRelations(ValidationContainer validationContainer){
        MasterData masterData = (MasterData) validationContainer.data();
        Date startDate = masterData.getStartDate();
        Date endDate = masterData.getEndDate();
        Date modificationDate = masterData.getModificationDate();
        if(endDate != null && startDate.after(endDate)){
            validationContainer.errors().add(String.format(X_CAN_NOT_BE_AFTER_X_IN_LINE_X, startDate, endDate, validationContainer.lineNumber()));
        }
        if(modificationDate != null && modificationDate.after(endDate)){
            validationContainer.errors().add(String.format(X_CAN_NOT_BE_AFTER_X_IN_LINE_X, modificationDate, endDate, validationContainer.lineNumber()));
        }
        if(modificationDate != null && startDate.after(modificationDate)){
            validationContainer.errors().add(String.format(X_CAN_NOT_BE_AFTER_X_IN_LINE_X, startDate, modificationDate, validationContainer.lineNumber()));
        }
    }
}
