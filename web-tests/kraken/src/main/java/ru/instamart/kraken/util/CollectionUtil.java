package ru.instamart.kraken.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * Определяем, есть ли в хэшэ указанная пара из инта и стринга
     */
    public static boolean hasPairInMap(final int key, final String value, final Map<Integer, String> map) {
        final String mapValue = map.get(key);

        return nonNull(mapValue) && mapValue.equals(value);
    }

    /**
     * Собирает два списка в один сохраняя порядок
     */
    public static <K, V> Map<K, V> mergeIntoMap(final List<K> keys, final List<V> values) {
        final Iterator<K> keyIter = keys.iterator();
        final Iterator<V> valIter = values.iterator();
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors
                        .toMap(
                                _i -> keyIter.next(),
                                _i -> valIter.next(),
                                (u, v) -> {
                                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                                }, LinkedHashMap::new
                        ));
    }

    /**
     * Убирает уникальные значения по <V>
     */
    public static <K, V> Map<K, V> removeUniqueValues(final Map<K, V> map) {
        final Map<V, Long> counts = map.values().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return map.entrySet().stream()
                .filter(e -> counts.get(e.getValue()) > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        }, LinkedHashMap::new
                ));
    }

    /**
     * Сортирует мапу по <V> в обратном порядке
     */
    public static Map<String, Integer> reverseSortMapByValue(final Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    // Сортировка списка в прямом порядке
    public static <K> List<K> sortList(List<K> list) {
        return list.stream().sorted().collect(Collectors.toList());
    }

    // Сортировка списка в обратном порядке
    public static <K> List<K> reverseListOrder(List<K> list) {
        return list.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }
}
