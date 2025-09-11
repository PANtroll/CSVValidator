package randomizer;

import java.util.Random;

public class RandomStringMax implements Generable<String> {

    public static final Random RANDOM = new Random();

    /**
     * Generate a random alphabetic String
     * @param args 1 argument: max word size -1
     * @return generated String
     */
    @Override
    public String generate(String... args) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int maxStringLength = RANDOM.nextInt(Integer.parseInt(args[0]))+1;

        return RANDOM.ints(leftLimit, rightLimit + 1)
                .limit(maxStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
