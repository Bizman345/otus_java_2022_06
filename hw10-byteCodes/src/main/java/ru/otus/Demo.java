package ru.otus;

import ru.otus.annotations.Log;
import ru.otus.proxy.TestLogging;
import ru.otus.proxy.TestProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

class Demo {

    public static void main(String... args) {
        action();
    }

    public static void action() {
        TestProxy.createTestLogging().calculation();
        TestProxy.createTestLogging().calculation(6);
        TestProxy.createTestLogging().calculation(6, 5);
        TestProxy.createTestLogging().calculation(6, 5, "Some string");
    }

}
