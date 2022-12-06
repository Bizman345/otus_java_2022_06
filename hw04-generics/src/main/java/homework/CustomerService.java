package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private TreeMap<Customer, String> customerMap = new TreeMap<>((o1, o2) -> Long.compare(o1.getScores(), o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customerMap.firstEntry();
        if (entry != null) {
            entry = Map.entry((Customer)entry.getKey().clone(), entry.getValue());
        }
        return entry;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customerMap.higherEntry(customer);
        if (entry != null) {
            entry = Map.entry((Customer)entry.getKey().clone(), entry.getValue());
        }
        return entry;
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
