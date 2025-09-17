package org.example.generator.randomizer;

import java.util.Date;
import java.util.Random;

public class RandomDate implements Generable<Date> {

    public static final Random random = new Random();

    /**
     * Generate Date between to arguments
     * @param args 2 arguments: long milliseconds date from and long milliseconds date to
     * @return generate date
     */
    @Override
    public Date generate(String... args) {
        long from = Long.parseLong(args[0]);
        long to = Long.parseLong(args[1]);
        return new Date(from + (Math.abs(random.nextLong()) % (to - from)));
    }

}
