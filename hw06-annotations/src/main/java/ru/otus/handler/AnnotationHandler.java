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

    public static void main(String[] args) {
        Class clazz = null;
        var beforeList = new ArrayList<Method>();
        var testList = new ArrayList<Method>();
        var afterList = new ArrayList<Method>();

        try {
            clazz = Class.forName(AnnotationHandlerTest.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Class for test not found");
            return;
        }

        fillAnnotationsLists(Arrays.asList(clazz.getMethods()), beforeList, afterList, testList);

        if (testList.isEmpty()) {
            System.out.println("There are no tests in class");
            return;
        }

        int totalPassed = 0;
        int totalFailed = 0;

        for (Method test : testList) {
            try {
                Object object = clazz.getConstructor().newInstance();
                methodListForeach(beforeList, object);
                test.invoke(object);
                methodListForeach(afterList, object);
                totalPassed++;
                System.out.println(test.getName() + " Passed");
            } catch (Exception e) {
                totalFailed++;
                System.out.println(test.getName() + " Failed");
            }
        }

        System.out.println("Total passed: " + totalPassed);
        System.out.println("Total failed: " + totalFailed);
        System.out.println("Number of test: " + testList.size());

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
