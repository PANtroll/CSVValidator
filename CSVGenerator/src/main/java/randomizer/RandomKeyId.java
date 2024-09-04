package randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomKeyId implements Generable<String>, Geterable<String> {
    public static final String KEY_PREFIX = "EC1048EN00P";
    public static final String MIN_VALUE = "100000000000000000";
    public static final String MAX_VALUE = "999999999999999999";
    private final List<String> keyIds = new ArrayList<>();
    private final Generable<Long> randomLong = new RandomLong();

    /**
     * generate 29 character unique key with 18 digits generated long
     *
     * @param args empty table
     * @return unique key
     */
    @Override
    public String generate(String[] args) {
        String keyId = null;
        while (keyId == null || keyIds.contains(keyId)) {
            keyId = KEY_PREFIX + randomLong.generate(new String[]{MIN_VALUE, MAX_VALUE}).toString();
        }
        keyIds.add(keyId);
        return keyId;
    }

    /**
     * randomly pick keyId if exist on list
     *
     * @return keyId
     */
    @Override
    public String getExistRandomKeyId() {
        if (keyIds.isEmpty()) {
            return null;
        }
        Random r = new Random();
        return keyIds.get(r.nextInt(keyIds.size() - 1));
    }
}
