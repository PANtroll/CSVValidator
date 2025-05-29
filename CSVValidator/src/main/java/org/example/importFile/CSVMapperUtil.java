package org.example.importFile;

import org.example.model.*;
import org.example.validation.ValidationUtil;

import java.math.BigDecimal;
import java.util.Arrays;

public class CSVMapperUtil {

    public static MasterData fillMasterDataByCSV(String[] csvLine){
        MasterData masterData = new MasterData();
        if(csvLine == null || csvLine.length != MasterData.NUMBER_OF_MASTER_DATA_FIELDS || !csvLine[0].equals("M") ){
            return null;
        }
        try {
            masterData.setMasterKey(csvLine[1]);
            masterData.setCity(csvLine[2]);
            masterData.setStreet(csvLine[3]);
            masterData.setPostCode(csvLine[4]);
            masterData.setName(csvLine[5]);
            masterData.setRegion(csvLine[6]);
            masterData.setFuel(Integer.parseInt(csvLine[7]));
            masterData.setStartDate(ValidationUtil.isValidDate(csvLine[8]));
            masterData.setEndDate(ValidationUtil.isValidDate(csvLine[9]));
            masterData.setModificationDate(ValidationUtil.isValidDate(csvLine[10]));
            masterData.setPower(Integer.parseInt(csvLine[11]));
            masterData.setPowerType(PowerTypeEnum.valueOf(csvLine[12]));
            masterData.setControllability(Boolean.getBoolean(csvLine[13]));
            masterData.setVoltageLevel(VoltageLevelEnum.valueOf(csvLine[14]));
            masterData.setMeasurementLocationId(csvLine[15]);
            masterData.setPlantType(PlantTypeEnum.valueOf(csvLine[16]));
            masterData.setSoundOptimization(Integer.parseInt(csvLine[17]));
            masterData.setBiomassBonus(Boolean.getBoolean(csvLine[18]));
            masterData.setBiomassTechnologyBonus(Boolean.getBoolean(csvLine[19]));
        }
        catch (Exception e){
            System.out.println("Type error in " + Arrays.toString(csvLine));
        }
        return masterData;
    }

    public static ActualData fillActualDataByCSV(String[] csvLine){
        ActualData actualData = new ActualData();
        if(csvLine == null || csvLine.length != ActualData.NUMBER_OF_ACTUAL_DATA_FIELDS || !csvLine[0].equals("A") ){
            return null;
        }
        try {
            actualData.setMasterKey(csvLine[1]);
            actualData.setDataReportType(DataReportTypeEnum.valueOf(csvLine[2]));
            actualData.setYear(Integer.parseInt(csvLine[3]));
            actualData.setMonth(Integer.parseInt(csvLine[4]));
            actualData.setDay(Integer.parseInt(csvLine[5]));
            actualData.setQuantity(new BigDecimal(csvLine[6]));
            actualData.setAmount(new BigDecimal(csvLine[7]));
        }
        catch (Exception e){
                System.out.println("Type error in " + Arrays.toString(csvLine));
            }
        return actualData;
    }

}
