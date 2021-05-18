package ru.instamart.kraken.util;

import java.util.Map;

import static java.util.Objects.nonNull;

public final class MapUtil {

    /**
     * Определяем, есть ли в хэшэ указанная пара из инта и стринга
     */
    public static boolean hasPairInMap(final int key, final String value, final Map<Integer, String> map) {
        final String mapValue = map.get(key);

        return nonNull(mapValue) && mapValue.equals(value);
    }
}
