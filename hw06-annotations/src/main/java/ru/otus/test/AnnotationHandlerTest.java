package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class AnnotationHandlerTest {
    private Integer a = Integer.valueOf(0);
    private Integer b= Integer.valueOf(0);

    @Before
    public void before() {

    }

    @Test
    public void equalTest() {
        a.equals(b);
    }

    @Test
    public void systemOutTest() {
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testWithException() throws Exception {
        throw new Exception("Some exception");
    }

    @After
    public void after() {
        a = b;
    }
}
