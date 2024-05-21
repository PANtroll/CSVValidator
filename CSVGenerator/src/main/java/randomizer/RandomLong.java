package randomizer;

import java.util.Random;

public class RandomLong implements Generable<Long> {
    /**
     * Generate random positive Integer
     *
     * @param args 1 argument: int max value
     * @return generated Integer
     */
    @Override
    public Long generate(String[] args) {
        Random random = new Random();
        long result = random.nextLong();
        return result < 0 ? -result : result;
    }
}
