[![Build Status](https://travis-ci.com/mtumilowicz/java8-map-functions.svg?branch=master)](https://travis-ci.com/mtumilowicz/java8-map-functions)

# java8-map-functions
Overview of Java 8 additions to Map interface.

_Reference_: https://www.nurkiewicz.com/2014/04/hashmap-performance-improvements-in.html  
_Reference_: https://dzone.com/articles/java-8-hashmaps-keys-and-the-comparable-interface  
_Reference_: https://yermilov.github.io/blog/2017/02/24/tiebreaker-regarding-java-hashmap-treenode-and-tiebreakorder/

# preface
When a bucket exceeds threshold (`TREEIFY_THRESHOLD = 8`), 
`HashMap` dynamically replaces it a tree map. Instead of having 
pessimistic `O(n)` we get `O(logn)`. Previously entries with conflicting 
keys were appended to linked list. Now `HashMap` uses binary tree (hash code as a 
branching variable). If two hashes are different but ended up in the 
same bucket, one is considered bigger and goes to the right. If hashes 
are equal (as in our case), `HashMap` hopes that the keys are `Comparable`, 
so that it can establish some order. This is not a requirement of 
`HashMap` keys, but apparently a good practice.

The tree implementation inside the `HashMap` is a `Red-Black` tree, which 
means it will always be balanced.

When the `HashMap` implementation tries to find the location of a 
entry in the tree, 
first it checks whether the current and the new values are easily 
comparable (`Comparable` interface) or not. In the latter case, it has 
to fall back to a comparison method called `tieBreakOrder(Object a, 
Object b)`. This method tries to compare the two object based on class 
name first, and then using `System.identityHashCode`. However, when 
the key implements Comparable, the process is much simpler. The key 
itself defines how it compares to other keys, so the whole 
insertion/retrieval process speeds up, as there are no extra method 
calls needed for. It's worth mentioning that the same `tieBreakOrder` 
method is used when two Comparable keys turn out to be equal according 
to the compareTo method (the method returns 0).

# summary
## comparing
In `Map.Entry`:
* `public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey()`
* `public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue()`
* `public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp)`
* `public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp)`

## processing
* `default void forEach(BiConsumer<? super K, ? super V> action)`
* `default V getOrDefault(Object key, V defaultValue)`

# modifying
* `default V putIfAbsent(K key, V value)`- If the specified key is 
not already associated with a value (or is mapped to null) associates 
it with the given value and returns null, else returns the current value.

    **returns** the previous value associated with the specified key, or
    null if there was no mapping for the key.

    **Remark**: putIfAbsent(1, null) will add an entry (supposing 1 is absent)
    
* `default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)` - 
If the specified key is not already associated with a value (or is mapped
to null), attempts to compute its value using the given mapping function 
and enters it into this map unless null.

    **returns** the current (existing or computed) value associated with 
    the specified key, or null if the computed value is null
    
    **Remark**: `putIfAbsent(1, null)` will NOT add an entry (supposing 1 is absent)

* `putIfAbsent` vs `computeIfAbsent`: [stackoverflow differences](https://stackoverflow.com/a/48184207)
    * different returns,
    * laziness of `computeIfAbsent` - crucial while constructing expensive
    objects, for example `new ArrayList<>()`,
    * different approach to `null` values (discussed above)

* `default boolean remove(Object key, Object value)` - Removes the entry 
for the specified key only if it is currently mapped to the specified value.

* `default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)` - 
Replaces each entry's value with the result of invoking the given 
function on that entry until all entries have been processed or the
function throws an exception

    **Remark**: Can't change the element type. To do that - use a stream and
    collect to the new structure.

* `default boolean replace(K key, V oldValue, V newValue)` - Replaces the 
entry for the specified key only if currently mapped to the specified value.
* `default V replace(K key, V value)` - Replaces the entry for the 
specified key only if map contains key.
    
* `default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)` - 
If the value for the specified key is present and non-null, attempts to
compute a new mapping given the key and its current mapped value.

    **If the function returns null, the mapping is removed.**
    
    **returns** the new value associated with the specified key, or 
    null if none

* `default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)` -
Attempts to compute a mapping for the specified key and its current
mapped value (or null if there is no current mapping).

    **If the function returns null, the mapping is removed (or remains 
    absent if initially absent).**
    
    **returns** the new value associated with the specified key, or 
    null if none
    
* `default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)` -
If the specified key is not already associated with a value or 
is associated with null, associates it with the given non-null value.
Otherwise, replaces the associated value with the results of the given 
remapping function, or removes if the result is null.

    **If the function returns null the mapping is removed.**
    
    **returns** the new value associated with the specified key, or 
    null if no value is associated with the key

* `compute` vs `merge`
    * arguments of `BiFunction` in `compute`: (key, value)
    * arguments of `BiFunction` in `merge`: (oldValue, value)
# project description
We provide tests for above mentioned methods.

1. comparing - `MapComparingTest`
    * sort entry set stream by keys
        ```
        stream.sorted(Map.Entry.comparingByKey())
        ```
    * sort entry set stream by keys in reverse order
        ```
        stream.sorted(Map.Entry.comparingByKey((x, y) -> Integer.compare(y, x)))
        ```
    * sort entry set stream by values
        ```
        stream.sorted(Map.Entry.comparingByValue(Comparator.comparingInt(p -> p.getHobbies().size())))
        ```
    * sort entry set stream by values in reverse order
        ```
        .sorted(Map.Entry.comparingByValue(Comparator.<Person>comparingInt(p -> p.getHobbies().size()).reversed()))
        ```
1. processing - `MapProcessingTest`
    * print all entries
        ```
        map.forEach((key, value) -> System.out.println(key + ": " + value));
        ```
    * get key (id = 1) from map or default to "NOT-FOUND"
        ```
        map.getOrDefault(1, "NOT-FOUND");
        ```
1. modifying - `MapModifyingTest`
    * putIfAbsent - good way of initializing entries in map
        ```
        Map<String, Integer> counter = new HashMap<>();
        
        counter.putIfAbsent("11-u", 0);
        ```
    * computeIfAbsent - suppose we have `Map<String, List<String>>`
    and we want to add (1, "newValue") such that "newValue" will be
    added to the list of string
        ```
        map.computeIfAbsent(1, key -> new ArrayList<>()).add("newValue1");
        ```
        **Note** that `new ArrayList<>()` is created lazily.
    * we want to remove entry, but only when key is associated with
    specific value
        ```
        map.remove(key, value)
        ```
    * increase all counters
        ```
        Map<String, Integer> counter = new HashMap<>();
        
        counter.replaceAll((k, v) -> ++v)     
        ```
    * replace value for given key only for specific oldValue
        ```
        map.replace(1, "oldValue", "newValue");
        ```
    * replace value for given key (map has to contain that key)
        ```
        map.replace(1, "newValue");
        ```
    * increase a counter
        ```
        map.computeIfPresent(1, (k, v) -> ++v);
        ```
    * multimap: Map<Integer, Set<Integer>> - remove value
    from set and if set is empty - remove whole entry
        ```
        map.computeIfPresent(key , (key, set) -> set.remove(valueToRemove) && set.isEmpty() ? null : set);
        ```
    * increase a counter or init new for new entries
        ```
        map.compute(1, (k, v) -> isNull(v) ? 0 : ++v);
        ```
    * initialize or update in one line - increase 
    a counter or init new for new entries
        ```
        map.merge(1, 0, (oldValue, newValue) -> ++oldValue);
        ```
    