package randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomActualDataType implements Generable<String> {

    private static final List<String> types = Arrays.asList("1", "2", "3");//todo make some types

    @Override
    public String generate(String[] args) {
        Random random = new Random();
        return types.get(random.nextInt(types.size() - 1));
    }
}
