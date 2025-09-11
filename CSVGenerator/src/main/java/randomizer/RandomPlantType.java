package randomizer;

import java.util.Random;

public class RandomPlantType implements Generable<String> {

    public static final Random RANDOM = new Random();

    @Override
    public String generate(String... args) {
        return switch (RANDOM.nextInt(8)) {
            case 0 -> Type.SOLAR.name();
            case 1 -> Type.BIOMASS.name();
            case 2 -> Type.COAL.name();
            case 3 -> Type.WIND.name();
            case 4 -> Type.ATOM.name();
            case 5 -> Type.WATER.name();
            case 6 -> Type.GAS.name();
            case 7 -> Type.GEOTHERMAL.name();
            default -> Type.SOLAR.name();
        };
    }

    private enum Type {
        SOLAR, BIOMASS, COAL, WIND, ATOM, WATER, GAS, GEOTHERMAL;

        public static RandomPlantType.Type getTypeByName(String name) {
            return valueOf(name);
        }
    }
}
