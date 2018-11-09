import lombok.Value;

/**
 * Created by mtumilowicz on 2018-11-09.
 */
@Value
class Customer {
    public static final Customer EMPTY = new Customer(0);
    
    Integer id;
}
