package homework;


import java.util.Stack;

public class CustomerReverseOrder {

    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private Stack<Customer> customerStack;

    public CustomerReverseOrder() {
        customerStack = new Stack<>();
    }

    public void add(Customer customer) {
        customerStack.add(customer);
    }

    public Customer take() {
        return customerStack.pop();
    }
}
