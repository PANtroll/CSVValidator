import randomizer.*;

import java.math.BigDecimal;
import java.util.Random;

public class GenerateLine {
    private static final String SECTION_MASTER_DATA = "M";
    private static final String SECTION_ACTUAL_DATA = "A";
    private static final String SEPARATOR = "|";
    private static final String NEW_LINE = "\r\n";
    private final Generable<Integer> randomInt = new RandomInteger();
    private final Generable<String> randomKeyId = new RandomKeyId();
    private final Geterable<String> randomKeyIdGetter = (Geterable<String>) randomKeyId;
    private final Generable<BigDecimal> randomBigDecimal = new RandomBigDecimal();
    private final Generable<String> randomString = new RandomString();
    private final Generable<String> randomType = new RandomActualDataType();

    public String generateMetaData(){
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }

    public String generateLineSectionFirst() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SECTION_MASTER_DATA);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomKeyId.generate(new String[]{}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomString.generate(new String[]{"20"}));
//        stringBuilder.append(SEPARATOR);

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
