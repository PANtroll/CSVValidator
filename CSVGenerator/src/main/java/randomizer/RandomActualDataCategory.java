package randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomActualDataCategory implements Generable<String> {

    private static final List<String> categorise = Arrays.asList("CAT1", "CAT2", "CAT3", "CAT4", "CAT4", "CAT5");//todo make some categorise

    @Override
    public String generate(String[] args) {
        Random random = new Random();
        return categorise.get(random.nextInt(categorise.size() - 1));
    }
}
