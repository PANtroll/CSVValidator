package org.example.model;

import java.util.Date;
import java.util.Objects;

public class MasterData extends CSVData{

    public static final int NUMBER_OF_MASTER_DATA_FIELDS = 20;

    private String city;
    private String street;
    private String postCode;
    private String name;
    private String region;
    private Integer fuel;
    private Date startDate;
    private Date endDate;
    private Date modificationDate;
    private Integer power;
    private PowerTypeEnum powerType;
    private Boolean controllability;
    private VoltageLevelEnum voltageLevel;
    private String measurementLocationId;
    private PlantTypeEnum plantType;
    private Integer soundOptimization;
    private Boolean isBiomassBonus;
    private Boolean isBiomassTechnologyBonus;

    public MasterData() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public PowerTypeEnum getPowerType() {
        return powerType;
    }

    public void setPowerType(PowerTypeEnum powerType) {
        this.powerType = powerType;
    }

    public Boolean getControllability() {
        return controllability;
    }

    public void setControllability(Boolean controllability) {
        this.controllability = controllability;
    }

    public VoltageLevelEnum getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(VoltageLevelEnum voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public String getMeasurementLocationId() {
        return measurementLocationId;
    }

    public void setMeasurementLocationId(String measurementLocationId) {
        this.measurementLocationId = measurementLocationId;
    }

    public PlantTypeEnum getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantTypeEnum plantType) {
        this.plantType = plantType;
    }

    public Integer getSoundOptimization() {
        return soundOptimization;
    }

    public void setSoundOptimization(Integer soundOptimization) {
        this.soundOptimization = soundOptimization;
    }

    public Boolean getBiomassBonus() {
        return isBiomassBonus;
    }

    public void setBiomassBonus(Boolean biomassBonus) {
        isBiomassBonus = biomassBonus;
    }

    public Boolean getBiomassTechnologyBonus() {
        return isBiomassTechnologyBonus;
    }

    public void setBiomassTechnologyBonus(Boolean biomassTechnologyBonus) {
        isBiomassTechnologyBonus = biomassTechnologyBonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MasterData that = (MasterData) o;
        return Objects.equals(masterKey, that.masterKey) && Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(postCode, that.postCode) && Objects.equals(name, that.name) && Objects.equals(region, that.region) && Objects.equals(fuel, that.fuel) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(modificationDate, that.modificationDate) && Objects.equals(power, that.power) && powerType == that.powerType && Objects.equals(controllability, that.controllability) && voltageLevel == that.voltageLevel && Objects.equals(measurementLocationId, that.measurementLocationId) && plantType == that.plantType && Objects.equals(soundOptimization, that.soundOptimization) && Objects.equals(isBiomassBonus, that.isBiomassBonus) && Objects.equals(isBiomassTechnologyBonus, that.isBiomassTechnologyBonus);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(masterKey);
        result = 31 * result + Objects.hashCode(city);
        result = 31 * result + Objects.hashCode(street);
        result = 31 * result + Objects.hashCode(postCode);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(region);
        result = 31 * result + Objects.hashCode(fuel);
        result = 31 * result + Objects.hashCode(startDate);
        result = 31 * result + Objects.hashCode(endDate);
        result = 31 * result + Objects.hashCode(modificationDate);
        result = 31 * result + Objects.hashCode(power);
        result = 31 * result + Objects.hashCode(powerType);
        result = 31 * result + Objects.hashCode(controllability);
        result = 31 * result + Objects.hashCode(voltageLevel);
        result = 31 * result + Objects.hashCode(measurementLocationId);
        result = 31 * result + Objects.hashCode(plantType);
        result = 31 * result + Objects.hashCode(soundOptimization);
        result = 31 * result + Objects.hashCode(isBiomassBonus);
        result = 31 * result + Objects.hashCode(isBiomassTechnologyBonus);
        return result;
    }

    @Override
    public String toString() {
        return "MasterData{" +
                "masterKey='" + masterKey + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", fuel=" + fuel +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", modificationDate=" + modificationDate +
                ", power=" + power +
                ", powerType=" + powerType +
                ", controllability=" + controllability +
                ", voltageLevel=" + voltageLevel +
                ", measurementLocationId='" + measurementLocationId + '\'' +
                ", plantType=" + plantType +
                ", soundOptimization=" + soundOptimization +
                ", isBiomassBonus=" + isBiomassBonus +
                ", isBiomassTechnologyBonus=" + isBiomassTechnologyBonus +
                '}';
    }
}

