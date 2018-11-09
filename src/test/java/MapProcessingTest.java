import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2018-11-09.
 */
public class MapProcessingTest {
    
    @Test
    public void forEach() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");

        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }
    
    @Test
    public void getOrDefault_found() {
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "1");

        String str = map.getOrDefault(1, "NOT-FOUND");
        
        assertThat(str, is("1"));
    }

    @Test
    public void getOrDefault_notFound() {
        Map<Integer, String> map = new HashMap<>();
        
        String str = map.getOrDefault(1, "NOT-FOUND");
        
        assertThat(str, is("NOT-FOUND"));
    }
    
}
