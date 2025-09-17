package org.example.generator.randomizer;

import java.util.Random;

public class RandomString implements Generable<String> {

    public static final Random random = new Random();

    /**
     * Generate a random alphabetic String
     * @param args 1 argument: word size -1
     * @return generated String
     */
    @Override
    public String generate(String... args) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = Integer.parseInt(args[0]);

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
