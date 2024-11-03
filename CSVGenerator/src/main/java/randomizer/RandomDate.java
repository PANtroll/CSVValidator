package randomizer;

import java.util.Date;
import java.util.Random;

public class RandomDate implements Generable<Date> {
    /**
     * Generate Date between to arguments
     * @param args 2 arguments: long milliseconds date from and long milliseconds date to
     * @return generate date
     */
    @Override
    public Date generate(String[] args) {
        long from = Long.parseLong(args[0]);
        long to = Long.parseLong(args[1]);
        Random random = new Random();
        return new Date(Math.abs(from + (from + random.nextLong()) % (to - from)));
    }

    @Override
    public String toString() {
        return "10.12.12";
    }
}
