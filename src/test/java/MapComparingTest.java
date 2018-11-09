import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by mtumilowicz on 2018-11-09.
 */
public class MapComparingTest {
    
    @Test
    public void sortByKeys() {
        Person p1 = new Person(1, ImmutableSet.of("1"));
        Person p2 = new Person(2, ImmutableSet.of("2"));
        Person p3 = new Person(3, ImmutableSet.of("3"));

        Map<Integer, Person> personMap = new HashMap<>();
        
        personMap.put(p1.getId(), p1);
        personMap.put(p2.getId(), p2);
        personMap.put(p3.getId(), p3);

        List<Integer> ids = personMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        assertThat(ids, is(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void sortByKeys_reverse() {
        Person p1 = new Person(1, ImmutableSet.of("1"));
        Person p2 = new Person(2, ImmutableSet.of("2"));
        Person p3 = new Person(3, ImmutableSet.of("3"));

        Map<Integer, Person> personMap = new HashMap<>();

        personMap.put(p1.getId(), p1);
        personMap.put(p2.getId(), p2);
        personMap.put(p3.getId(), p3);

        List<Integer> ids = personMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((x, y) -> Integer.compare(y, x)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(ids, is(Arrays.asList(3, 2, 1)));
    }

    @Test
    public void sortByValues_compareBySetSize() {
        Person p1 = new Person(1, ImmutableSet.of("1", "2", "3"));
        Person p2 = new Person(2, ImmutableSet.of("1", "2", "3", "4"));
        Person p3 = new Person(3, ImmutableSet.of("3"));

        Map<Integer, Person> personMap = new HashMap<>();

        personMap.put(p1.getId(), p1);
        personMap.put(p2.getId(), p2);
        personMap.put(p3.getId(), p3);

        List<Integer> ids = personMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(p -> p.getHobbies().size())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(ids, is(Arrays.asList(3, 1, 2)));
    }

    @Test
    public void sortByValues_compareBySetSize_reverse() {
        Person p1 = new Person(1, ImmutableSet.of("1", "2", "3"));
        Person p2 = new Person(2, ImmutableSet.of("1", "2", "3", "4"));
        Person p3 = new Person(3, ImmutableSet.of("3"));

        Map<Integer, Person> personMap = new HashMap<>();

        personMap.put(p1.getId(), p1);
        personMap.put(p2.getId(), p2);
        personMap.put(p3.getId(), p3);

        List<Integer> ids = personMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.<Person>comparingInt(p -> p.getHobbies().size()).reversed()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(ids, is(Arrays.asList(2, 1, 3)));
    }
}
