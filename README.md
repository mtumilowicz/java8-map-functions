# java8-map-functions

* `public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey()`
* `public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue()`
* `public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp)`
* `public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp)`
* `default V getOrDefault(Object key, V defaultValue)`
* `default void forEach(BiConsumer<? super K, ? super V> action)`
* `default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)`
* `default V putIfAbsent(K key, V value)`
* `default boolean remove(Object key, Object value)`
* `default boolean replace(K key, V oldValue, V newValue)`
* `default V replace(K key, V value)`
* `default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)`
* `default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)`
* `default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)`
* `default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)`