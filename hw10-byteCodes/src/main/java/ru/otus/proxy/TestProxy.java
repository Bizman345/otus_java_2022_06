package ru.otus.proxy;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

public class TestProxy {

    public TestProxy() {
    }

    public static TestLoggingInterface createTestLogging(){
        InvocationHandler handler = new TestLoggingHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(TestProxy.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class TestLoggingHandler implements InvocationHandler {
        private TestLoggingInterface originalObject;

        public TestLoggingHandler(TestLoggingInterface originalObject) {
            this.originalObject = originalObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method originalMethod;
            Class originalClass = originalObject.getClass();
            Class[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length == 0) {
                originalMethod = originalClass.getMethod(method.getName());
            } else {
                originalMethod = originalClass.getMethod(method.getName(), parameterTypes);
            }
            if (originalMethod.isAnnotationPresent(Log.class)) {
                StringBuilder builder = new StringBuilder("executed method: " + method.getName());

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    builder.append(", " + parameters[i].getName() + ": " + args[i]);
                }
                System.out.println(builder);
            }
            return originalMethod.invoke(originalObject, args);
        }
    }
}
