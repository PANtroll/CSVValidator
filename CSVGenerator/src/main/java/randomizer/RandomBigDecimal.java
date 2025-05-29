package randomizer;

import java.math.BigDecimal;

public class RandomBigDecimal implements Generable<BigDecimal> {
    /**
     * Generate BigDecimal with given scale
     * @param args 1 argument int BigDecimal scale
     * @return generated BigDecimal
     */
    @Override
    public BigDecimal generate(String... args) {
        int scale = Integer.parseInt(args[0]);
        String range = args[1];
        BigDecimal max = new BigDecimal(range);
        BigDecimal randFromDouble = BigDecimal.valueOf(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        return actualRandomDec.setScale(scale, BigDecimal.ROUND_FLOOR);
    }


}
