package org.example.generator.randomizer;

import java.util.Random;

public class RandomInteger implements Generable<Integer> {

    public static final Random random = new Random();

    /**
     * Generate random positive Integer
     *
     * @param args 1 argument: int max value
     * @return generated Integer
     */
    @Override
    public Integer generate(String... args) {
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
