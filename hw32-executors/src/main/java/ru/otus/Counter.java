package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private static Object monitor = new Object();
    private static int lastThread = 2;
    private int count = 1;
    private boolean up = true;

    public void action(int threadNumber) {
        synchronized (monitor) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    while (lastThread == threadNumber) {
                        monitor.wait();
                    }

                    logger.info("Поток номер {}. Значение - {}", threadNumber, this.getCount());

                    lastThread = threadNumber;

                    Thread.sleep(300);
                    monitor.notifyAll();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private int getCount() {
        if (count == 10) {
            up = false;
        }

        if (count == 1) {
            up = true;
        }

        return up ? count++ : count--;
    }
}
