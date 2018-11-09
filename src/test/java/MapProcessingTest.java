import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mtumilowicz on 2018-11-09.
 */
public class MapProcessingTest {
    
    @Test
    public void forEach() {
        Customer c1 = new Customer(1);
        Customer c2 = new Customer(2);
        Customer c3 = new Customer(3);

        Map<Integer, Customer> customerMap = new HashMap<>();

        customerMap.put(c1.getId(), c1);
        customerMap.put(c2.getId(), c2);
        customerMap.put(c3.getId(), c3);

        customerMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
