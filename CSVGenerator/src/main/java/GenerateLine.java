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
    private static final String SEPARATOR = "|";
    private static final String NEW_LINE = "\r\n";
    private final Generable<Integer> randomInt = new RandomInteger();
    private final Generable<Integer> randomIntRange = new RandomIntegerRange();
    private final Generable<String> randomKeyId = new RandomKeyId();
    private final Geterable<String> randomKeyIdGetter = (Geterable<String>) randomKeyId;
    private final Generable<BigDecimal> randomBigDecimal = new RandomBigDecimal();
    private final Generable<String> randomString = new RandomString();
    private final Generable<String> randomType = new RandomActualDataType();
    private final Generable<Date> randomDate = new RandomDate();

    public String generateMetaData() {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

    public String generateLineSectionFirst() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SECTION_MASTER_DATA);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomKeyId.generate(new String[]{}));//master key
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate(new String[]{"20"}) + " " + randomInt.generate(new String[]{"100"}));//street
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomIntRange.generate(new String[]{"10000", "99999"}));//post code
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate(new String[]{"20"}));//Name
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate(new String[]{"1"}));//type
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate(new String[]{"99"}));//fuel
        stringBuilder.append(SEPARATOR);
        Date dateNow = new Date();
        Calendar calFrom1950 = new GregorianCalendar(1950, Calendar.JANUARY, 1);
        Calendar calTo2050 = new GregorianCalendar(2050, Calendar.JANUARY, 1);
        Date dateFrom1950 = calFrom1950.getTime();
        Date dateTo2050 = calTo2050.getTime();
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date buildDate = randomDate.generate(new String[]{"" + dateFrom1950.getTime(), "" + dateNow.getTime()});
        stringBuilder.append(simpleDateFormat.format(buildDate));//start date
        stringBuilder.append(SEPARATOR);
        Date endDate = randomDate.generate(new String[]{"" + buildDate.getTime(), "" + dateTo2050.getTime()});
        stringBuilder.append(simpleDateFormat.format(endDate));//end date
        stringBuilder.append(SEPARATOR);
        Date modifiedDate = randomDate.generate(new String[]{"" + buildDate.getTime(), "" + endDate});
        stringBuilder.append(simpleDateFormat.format(modifiedDate));
        stringBuilder.append(SEPARATOR);
        Date startDate = randomDate.generate(new String[]{"" + modifiedDate.getTime(), "" + endDate});
        stringBuilder.append(simpleDateFormat.format(startDate));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append((randomInt.generate(new String[]{"499"}) + 1) * 10);//power
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate(new String[]{"20"}));
        stringBuilder.append(SEPARATOR);

        stringBuilder.append(NEW_LINE);
        return stringBuilder.toString();
    }

    public String generateLineSectionSecond() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SECTION_ACTUAL_DATA);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomKeyIdGetter.getExistRandomKeyId());
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomType.generate(new String[]{}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate(new String[]{"12"}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(1900 + (randomInt.generate(new String[]{"124"})));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate(new String[]{"3"}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate(new String[]{"2"}));
        stringBuilder.append(NEW_LINE);
        return stringBuilder.toString();
    }

}
