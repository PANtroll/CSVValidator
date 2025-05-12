package randomizer;

import java.util.Random;

public class RandomPlantType implements Generable<String> {

    @Override
    public String generate(String... args) {
        Random random = new Random();
        switch (random.nextInt(8)) {
            case 0:
                return Type.solar.name();
            case 1:
                return Type.biomass.name();
            case 2:
                return Type.coal.name();
            case 3:
                return Type.wind.name();
            case 4:
                return Type.atom.name();
            case 5:
                return Type.water.name();
            case 6:
                return Type.gas.name();
            case 7:
                return Type.geothermal.name();
        }
        return Type.solar.name();
    }

    private enum Type {
        solar, biomass, coal, wind, atom, water, gas, geothermal;

        public static RandomPlantType.Type getTypeByName(String name) {
            return valueOf(name);
        }
    }
}
