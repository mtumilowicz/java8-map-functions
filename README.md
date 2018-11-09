# java8-map-functions

_Reference_: https://www.nurkiewicz.com/2014/04/hashmap-performance-improvements-in.html  
_Reference_: https://dzone.com/articles/java-8-hashmaps-keys-and-the-comparable-interface  
_Reference_: https://yermilov.github.io/blog/2017/02/24/tiebreaker-regarding-java-hashmap-treenode-and-tiebreakorder/

# preface
Basically when a bucket becomes too big (currently: `TREEIFY_THRESHOLD = 8`), 
`HashMap` dynamically replaces it with an ad-hoc implementation of tree 
map. This way rather than having pessimistic `O(n)` we get much better 
`O(logn)`. How does it work? Well, previously entries with conflicting 
keys were simply appended to linked list, which later had to be traversed. 
Now `HashMap` promotes list into binary tree, using hash code as a 
branching variable. If two hashes are different but ended up in the 
same bucket, one is considered bigger and goes to the right. If hashes 
are equal (as in our case), `HashMap` hopes that the keys are `Comparable`, 
so that it can establish some order. This is not a requirement of 
`HashMap` keys, but apparently a good practice. If keys are not 
comparable, don't expect any performance improvements in case of heavy 
hash collisions.

The tree implementation inside the `HashMap` is a `Red-Black` tree, which 
means it will always be balanced.

When the `HashMap` implementation tries to find the location of a new 
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
* `default V putIfAbsent(K key, V value)`
* `default boolean remove(Object key, Object value)`
* `default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)`
* `default boolean replace(K key, V oldValue, V newValue)`
* `default V replace(K key, V value)`
* `default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)`
* `default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)`
* `default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)`
* `default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)`

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
        customerMap.forEach((key, value) -> System.out.println(key + ": " + value));
        ```
    * get customer (id = 1) from map or default to EMPTY customer
        ```
        @Value
        class Customer {
            public static final Customer EMPTY = new Customer(0);
            
            Integer id;
        }
        ```
        ```
        customerMap.getOrDefault(1, Customer.EMPTY);
        ```
1. modifying