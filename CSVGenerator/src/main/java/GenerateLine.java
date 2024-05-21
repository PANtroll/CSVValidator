import randomizer.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateLine {
    private final List<String> keyIds = new ArrayList<>();
    private static final String SEPARATOR = "|";
    private final Generable<Integer> randomInt = new RandomInteger();
    private final Generable<Long> randomLong = new RandomLong();
    private final Generable<BigDecimal> randomBigDecimal = new RandomBigDecimal();
    private final Generable<String> randomType = new RandomActualDataType();

    public String generateLineSectionFirst() {

        return null;
    }

    public String generateLineSectionSecond() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        stringBuilder.append(keyIds.get(random.nextInt(keyIds.size() - 1)));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomType.generate(new String[]{}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomInt.generate(new String[]{"12"}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(1950 + (randomInt.generate(new String[]{"74"})));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate(new String[]{"3"}));
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(randomBigDecimal.generate(new String[]{"2"}));
        return null;
    }

}
