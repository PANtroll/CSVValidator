package randomizer;

import java.util.Random;

public class RandomLong implements Generable<Long> {
    /**
     * Generate random positive Integer
     *
     * @param args 2 arguments: long min value and long max value
     * @return generated Integer
     */
    @Override
    public Long generate(String[] args) {
        Random random = new Random();
        long min = Long.parseLong(args[0]);
        long max = Long.parseLong(args[1]);
        return random.nextLong(min, max);
    }
}
