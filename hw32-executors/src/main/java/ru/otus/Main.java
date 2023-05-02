package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {


    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        new Thread(() -> counter1.action(1)).start();
        new Thread(() -> counter2.action(2)).start();
    }
}
