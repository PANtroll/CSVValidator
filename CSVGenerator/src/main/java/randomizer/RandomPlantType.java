package randomizer;

import java.util.Random;

public class RandomPlantType implements Generable<String> {

    @Override
    public String generate(String... args) {
        Random random = new Random();
        switch (random.nextInt(8)) {
            case 0:
                return Type.SOLAR.name();
            case 1:
                return Type.BIOMASS.name();
            case 2:
                return Type.COAL.name();
            case 3:
                return Type.WIND.name();
            case 4:
                return Type.ATOM.name();
            case 5:
                return Type.WATER.name();
            case 6:
                return Type.GAS.name();
            case 7:
                return Type.GEOTHERMAL.name();
        }
        return Type.SOLAR.name();
    }

    private enum Type {
        SOLAR, BIOMASS, COAL, WIND, ATOM, WATER, GAS, GEOTHERMAL;

        public static RandomPlantType.Type getTypeByName(String name) {
            return valueOf(name);
        }
    }
}
