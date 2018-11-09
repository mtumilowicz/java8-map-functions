import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
}
