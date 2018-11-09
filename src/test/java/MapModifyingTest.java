import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-11-09.
 */
public class MapModifyingTest {
    
    @Test
    public void putIfAbsent_exists() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        map.putIfAbsent(1, "2");

        assertThat(map.get(1), is("1"));
    }

    @Test
    public void putIfAbsent_notExists() {
        Map<Integer, String> map = new HashMap<>();

        map.putIfAbsent(1, "1");

        assertThat(map.get(1), is("1"));
    }

    @Test
    public void putIfAbsent_mappedToNull() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, null);

        map.putIfAbsent(1, "1");

        assertThat(map.get(1), is("1"));
    }
    
    @Test
    public void remove_sameKey_differentValue() {
        Map<Integer, String> map = new HashMap<>();
        
        map.put(1, "1");
        
        map.remove(1, "2");
        
        assertThat(map.get(1), is("1"));
    }

    @Test
    public void remove_sameKey_sameValue() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        map.remove(1, "1");

        assertTrue(map.isEmpty());
    }
    
    @Test
    public void replaceAll() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");

        map.replaceAll((k, v) -> v + "-updated");

        assertThat(map.get(1), is("1-updated"));
        assertThat(map.get(2), is("2-updated"));
        assertThat(map.get(3), is("3-updated"));
    }
    
    @Test
    public void replace_key_value_newValue_found() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "old");
        
        map.replace(1, "old", "replaced");

        assertThat(map.get(1), is("replaced"));
    }

    @Test
    public void replace_key_value_newValue_notFound() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        map.replace(1, "2", "replaced");

        assertThat(map.get(1), is("1"));
    }

    @Test
    public void replace_key_value_newValue_oldNull() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, null);

        map.replace(1, null, "replaced");

        assertThat(map.get(1), is("replaced"));
    }

    @Test
    public void replace_key_value_newValue_newNull() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        map.replace(1, "1", null);

        assertThat(map.size(), is(1));
        assertNull(map.get(1));
    }
    
    @Test
    public void replace_key_value_found() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        map.replace(1, "replaced");

        assertThat(map.get(1), is("replaced"));
    }

    @Test
    public void replace_key_value_notFound() {
        Map<Integer, String> map = new HashMap<>();

        map.replace(1, "replaced");

        assertTrue(map.isEmpty());
    }
    
    @Test
    public void replace_key_value_mappedToNull() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, null);

        map.replace(1, "replaced");

        assertThat(map.get(1), is("replaced"));
    }

    @Test
    public void computeIfAbsent() {
        Map<Integer, List<String>> map = new HashMap<>();

        map.computeIfAbsent(1, key -> new ArrayList<>()).add("newValue1");
        map.computeIfAbsent(1, key -> new ArrayList<>()).add("newValue2");

        assertThat(map.get(1), is(Arrays.asList("newValue1", "newValue2")));
    }

    @Test
    public void computeIfAbsent_computedNull() {
        Map<Integer, List<String>> map = new HashMap<>();

        map.computeIfAbsent(1, key -> null);

        assertTrue(map.isEmpty());
    }

    @Test
    public void computeIfPresent_present_nonNullValue() {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(1, 1);
        map.computeIfPresent(1, (k, v) -> ++v);

        assertThat(map.get(1), is(2));
    }

    @Test
    public void computeIfPresent_present_nullValue() {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(1, null);
        map.computeIfPresent(1, (k, v) -> ++v);

        assertThat(map.get(1), is(nullValue()));
    }

    @Test
    public void computeIfPresent_present_computeNul() {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(1, 1);
        map.computeIfPresent(1, (k, v) -> null);

        assertTrue(map.isEmpty());
    }

    @Test
    public void computeIfPresent_absent() {
        Map<Integer, Integer> map = new HashMap<>();
        
        map.computeIfPresent(1, (k, v) -> ++v);

        assertTrue(map.isEmpty());
    }
}
