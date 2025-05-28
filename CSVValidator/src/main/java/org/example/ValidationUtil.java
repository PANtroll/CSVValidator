package org.example;

import java.util.Calendar;
import java.util.Date;

public class ValidationUtil {

    public static Date isValidDate(String date){

        if(date==null || date.isBlank()){
            return null;
        }
        String[] dateParts = date.split("-");
        Calendar cal = Calendar.getInstance();
        if(dateParts.length!=3){
            return null;
        }
        try{
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            if(year < 1900 && month > 12 && day > 31){
                return null;
            }
            cal.set(year, month - 1, day);
        }
        catch(Exception e){
            return null;
        }
        return new Date(cal.getTimeInMillis());
    }

}
