import com.google.common.collect.ImmutableSet;
import lombok.Value;

import java.util.Set;

/**
 * Created by mtumilowicz on 2018-11-09.
 */
@Value
class Person {
    Integer id;
    ImmutableSet<String> hobbies;

    Person(Integer id, Set<String> hobbies) {
        this.id = id;
        this.hobbies = ImmutableSet.copyOf(hobbies);
    }
}
