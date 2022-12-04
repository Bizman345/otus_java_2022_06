package ru.otus.handler;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.test.AnnotationHandlerTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationHandler {

    private static final String CLASS_NOT_FOUND = "Class for test not found";
    private static final String NO_TEST_IN_CLASS = "There are no tests in class";
    private static final String PASSED = "%s Passed %n";
    private static final String FAILED = "%s Failed %n";
    private static final String TOTAL_PASSED = "Total passed: %s %n";
    private static final String TOTAL_FAILED = "Total failed: %s %n";
    private static final String TOTAL_TESTS = "Number of test: %s %n";

    public static void main(String[] args) {
        Class clazz;
        var beforeList = new ArrayList<Method>();
        var testList = new ArrayList<Method>();
        var afterList = new ArrayList<Method>();

        try {
            clazz = Class.forName(AnnotationHandlerTest.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println(CLASS_NOT_FOUND);
            return;
        }

        fillAnnotationsLists(Arrays.asList(clazz.getMethods()), beforeList, afterList, testList);

        if (testList.isEmpty()) {
            System.out.println(NO_TEST_IN_CLASS);
            return;
        }

        executeAndPrintStatistic(beforeList, testList, afterList, clazz);

    }

    private static void executeAndPrintStatistic(ArrayList<Method> beforeList, ArrayList<Method> testList,ArrayList<Method> afterList, Class clazz) {
        int totalPassed = 0;
        int totalFailed = 0;

        for (Method test : testList) {
            try {
                Object object = clazz.getConstructor().newInstance();
                methodListForeach(beforeList, object);
                test.invoke(object);
                methodListForeach(afterList, object);
                totalPassed++;
                System.out.format(PASSED, test.getName());
            } catch (Exception e) {
                totalFailed++;
                System.out.format(FAILED, test.getName());
            }
        }

        System.out.format(TOTAL_PASSED, totalPassed);
        System.out.format(TOTAL_FAILED, totalFailed);
        System.out.format(TOTAL_TESTS, testList.size());
    }


    private static void fillAnnotationsLists(List<Method> methods, ArrayList<Method> beforeList, ArrayList<Method> afterList, ArrayList<Method> testList) {
        if (!methods.isEmpty()) {
            methods.forEach(method -> {
                if (method.isAnnotationPresent(Before.class)) {
                    beforeList.add(method);
                }
                if (method.isAnnotationPresent(After.class)) {
                    afterList.add(method);
                }
                if (method.isAnnotationPresent(Test.class)) {
                    testList.add(method);
                }
            });
        }
    }

    private static void methodListForeach(List<Method> methods, Object object) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }
}
