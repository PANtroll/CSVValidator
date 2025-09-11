package randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomActualDataType implements Generable<String> {

    private static final List<String> types = Arrays.asList("TYPE_1", "TYPE_2", "TYPE_3", "TYPE_4", "TYPE_5", "TYPE_6");
    public static final Random RANDOM = new Random();

    @Override
    public String generate(String... args) {
        return types.get(RANDOM.nextInt(types.size()));
    }
}
