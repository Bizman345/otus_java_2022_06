package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class AnnotationHandlerTest {
    private static final String A_EQUAL_B = "A equal B: %b %n";
    private static final String RUN_BEFORE = "Run before";
    private static final String RUN_AFTER = "Run after";
    private static final String SOME_EXCEPTION = "Some exception";
    private Integer a = 0;
    private Integer b= 0;

    @Before
    public void before() {
        System.out.println(RUN_BEFORE);
    }

    @Test
    public void equalTest() {
        System.out.format(A_EQUAL_B, a.equals(b));
    }

    @Test
    public void systemOutTest() {
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testWithException() throws Exception {
        throw new Exception(SOME_EXCEPTION);
    }

    @After
    public void after() {
        System.out.println(RUN_AFTER);
        a = b;
    }
}
