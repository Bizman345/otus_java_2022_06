package ru.otus.appcontainer;

import org.apache.commons.lang3.StringUtils;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exceptions.CreateContextException;

import javax.naming.ContextNotEmptyException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        try {
            checkConfigClass(configClass);
            var methods = getMethodsByOrder(configClass);
            var constructor = configClass.getDeclaredConstructor();
            var clazz = constructor.newInstance();
            var methodResult = new Object();

            for (Method method : methods) {
                var annotation = method.getAnnotation(AppComponent.class);
                checkComponentByName(annotation.name());

                if (method.getParameters().length == 0) {
                    methodResult = method.invoke(clazz);
                } else {
                    methodResult = method.invoke(clazz, Stream.of(method.getParameterTypes()).map(this::getAppComponent).toArray());
                }
                appComponents.add(methodResult);
                appComponentsByName.put(annotation.name(), methodResult);

            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(comp -> componentClass.isAssignableFrom(comp.getClass()))
                .reduce((a,b) -> {
                    throw new CreateContextException("There is a duplicate component");
                })
                .orElseThrow(() -> new CreateContextException("Component not found"));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new CreateContextException("Component not found");
        }
        return component;
    }

    private List<Method> getMethodsByOrder(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods()).sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order())).collect(Collectors.toList());
    }

    private void checkComponentByName(String componentName) {
        if (StringUtils.isEmpty(componentName)) {
            throw new CreateContextException("Component name is empty");
        }
        if (appComponentsByName.get(componentName) != null) {
            throw new CreateContextException("Component already exist");
        }
    }
}
