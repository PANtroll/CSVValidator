package randomizer;

import java.util.Random;

public class RandomParagraphType implements Generable{

    @Override
    public Object generate(String... args) {
        Random random = new Random();
        return Type.getTypeByName("type_" + random.nextInt(7) + 1);
    }

    private enum Type{
        type_1, type_2, type_3, type_4, type_5, type_6, type_7;
        public static Type getTypeByName(String name) {
             return valueOf(name);
        }
    }
}
