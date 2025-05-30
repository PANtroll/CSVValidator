package randomizer;

import java.util.*;

public class RandomKeyId implements Generable<String>, Geterable<String> {
    public static final String KEY_PREFIX = "EC1048EN00P";
    public static final String MIN_VALUE = "100000000000000000";
    public static final String MAX_VALUE = "999999999999999999";
    private final Set<String> keyIdsSet = new HashSet<>();
    private final List<String> keyIdsList = new ArrayList<>();
    private final RandomLong randomLong = new RandomLong();

    /**
     * generate 29 character unique key with 18 digits generated long
     *
     * @param args empty table
     * @return unique key
     */
    @Override
    public String generate(String... args) {
        String keyId = null;
        while (keyId == null || keyIdsSet.contains(keyId)) {
            keyId = KEY_PREFIX + randomLong.generate(MIN_VALUE, MAX_VALUE);
        }
        keyIdsSet.add(keyId);
        return keyId;
    }

    /**
     * randomly pick keyId if exist on list
     *
     * @return keyId
     */
    @Override
    public String getExistRandomKeyId() {
        if (keyIdsList.isEmpty()) {
            if(keyIdsSet.isEmpty()) {
                return null;
            }
            keyIdsList.addAll(keyIdsSet);
        }
        Random r = new Random();
        return keyIdsList.get(r.nextInt(keyIdsList.size() - 1));
    }
}
