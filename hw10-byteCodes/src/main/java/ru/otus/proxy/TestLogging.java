package ru.otus.proxy;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation() {};

    @Log
    @Override
    public void calculation(int param) {};

    @Log
    @Override
    public void calculation(int param1, int param2) {

    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {

    }

    @Override
    public void anotherMethod(int param1) {

    }
}

