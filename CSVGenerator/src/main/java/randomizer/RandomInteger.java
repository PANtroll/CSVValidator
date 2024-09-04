package randomizer;

import java.util.Random;

public class RandomInteger implements Generable<Integer> {
    /**
     * Generate random positive Integer
     *
     * @param args 1 argument: int max value
     * @return generated Integer
     */
    @Override
    public Integer generate(String[] args) {
        Random random = new Random();
        String maxValueStr = args[0];
        try {
            int maxValue = Integer.parseInt(maxValueStr);
            if (maxValue <= 0) {
                return 0;
            }
            return random.nextInt(maxValue);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
