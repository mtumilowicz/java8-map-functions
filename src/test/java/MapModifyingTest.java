import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
