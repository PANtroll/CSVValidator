package org.example.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MasterData {

    private String masterKey;
    private String city;
    private String street;
    private String postCode;
    private String name;
    private Integer type;
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

}
