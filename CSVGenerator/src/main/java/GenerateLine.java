import randomizer.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GenerateLine {
    private static final String SECTION_MASTER_DATA = "M";
    private static final String SECTION_ACTUAL_DATA = "A";
    private static final String SEPARATOR = ";";
    private static final String NEW_LINE = "\r\n";
    private final Generable<Integer> randomInt = new RandomInteger();
    private final Generable<Long> randomLong = new RandomLong();
    private final Generable<Integer> randomIntRange = new RandomIntegerRange();
    private final Generable<String> randomKeyId = new RandomKeyId();
    private final GenerableRandomKey<String> randomKeyIdGetter = (GenerableRandomKey<String>) randomKeyId;
    private final Generable<BigDecimal> randomBigDecimal = new RandomBigDecimal();
    private final Generable<String> randomStringMax = new RandomStringMax();
    private final Generable<String> randomString = new RandomString();
    private final Generable<String> randomType = new RandomActualDataType();
    private final Generable<Date> randomDate = new RandomDate();
    private final Generable<String> randomPlantType = new RandomPlantType();
    private final Generable<RandomParagraphType.Type> randomParagraph = new RandomParagraphType();
    private final ActualDataHelper actualDataHelper = new ActualDataHelper();

    public String generateMetaData() {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

    public String generateLineSectionFirst() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SECTION_MASTER_DATA);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomKeyId.generate());//master key
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomStringMax.generate("20"));//city
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomStringMax.generate("20")).append(" ").append(randomInt.generate("100"));//street
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomIntRange.generate("10000", "99999"));//post code
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomStringMax.generate("20"));//Name
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate("2"));//region
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate("99"));//fuel
        stringBuilder.append(SEPARATOR);
        Date dateNow = new Date();
        Calendar calFrom1950 = new GregorianCalendar(1950, Calendar.JANUARY, 1);
        Calendar calTo2050 = new GregorianCalendar(2050, Calendar.JANUARY, 1);
        Date dateFrom1950 = calFrom1950.getTime();
        Date dateTo2050 = calTo2050.getTime();
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date buildDate = randomDate.generate("" + dateFrom1950.getTime(), "" + dateNow.getTime());
        stringBuilder.append(simpleDateFormat.format(buildDate));//start date
        stringBuilder.append(SEPARATOR);
        Date endDate = randomDate.generate("" + buildDate.getTime(), "" + dateTo2050.getTime());
        stringBuilder.append(simpleDateFormat.format(endDate));//end date
        stringBuilder.append(SEPARATOR);
        Date modifiedDate = randomDate.generate("" + buildDate.getTime(), "" + endDate.getTime());
        stringBuilder.append(simpleDateFormat.format(modifiedDate));//modification date
        stringBuilder.append(SEPARATOR);
        stringBuilder.append((randomInt.generate("499") + 1) * 10);//power
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("TYPE").append(randomInt.generate("5"));//power type
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate("1"));//controllability
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("VLE0").append(randomInt.generate("6"));//voltageLevel
        stringBuilder.append(SEPARATOR);
        Long generateLong = randomLong.generate("1000000000000000", "9999999999999999");//16 digits
        stringBuilder.append(randomString.generate("10")).append(generateLong);//measurement location ID
        stringBuilder.append(SEPARATOR);
        String plantType = randomPlantType.generate();
        stringBuilder.append(plantType);//plant type
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate("3"));//sound optimization
        stringBuilder.append(SEPARATOR);
        int isBiomassBonus = 0;
        if(plantType.equalsIgnoreCase("Biomass")) {
            isBiomassBonus = randomInt.generate("1");
        }
        stringBuilder.append(isBiomassBonus);//biomass bonus
        stringBuilder.append(SEPARATOR);
        int isBiomassTechnologyBonus = 0;
        if(isBiomassBonus > 0){
            isBiomassTechnologyBonus = randomInt.generate("1");
        }
        stringBuilder.append(isBiomassTechnologyBonus);//biomass technology bonus

        stringBuilder.append(NEW_LINE);
        return stringBuilder.toString();
    }

    public String generateLineSectionSecond() {
        StringBuilder stringBuilder = new StringBuilder();
        String existRandomKeyId = randomKeyIdGetter.getExistRandomKeyId();
        String dataReportType = randomType.generate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -25);
        calendar.setTime(randomDate.generate("" + calendar.getTimeInMillis(), "" + new Date().getTime()));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        int tries = 0;
        while (!actualDataHelper.checkUniqueness(existRandomKeyId, dataReportType, year, month, day)){
            tries++;
            if(tries > 500){
                existRandomKeyId = randomKeyIdGetter.getExistRandomKeyId();
                tries = 0;
            }
            calendar.setTime(randomDate.generate("" + calendar.getTimeInMillis(), "" + new Date().getTime()));
            year = String.valueOf(calendar.get(Calendar.YEAR));
            month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }
        stringBuilder.append(SECTION_ACTUAL_DATA);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(existRandomKeyId);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(dataReportType);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(year);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(month);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(day);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate("3", "10000000000.0"));//10mld
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate("2", "100000000.0"));//100mln
        stringBuilder.append(NEW_LINE);
        return stringBuilder.toString();
    }

}
