package ru.otus.jdbc.mapper;

import ru.otus.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    private String name;
    private Constructor<T> constructor;
    private Field id;
    private List<Field> fields;
    private List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        name = clazz.getSimpleName().toLowerCase();
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        List<Field> fieldsWithGetAndSet = findFieldWithGetAndSet(Arrays.asList(clazz.getDeclaredFields()), Arrays.asList(clazz.getMethods()));

        id = findIdAnnotation((fieldsWithGetAndSet));
        fields = fieldsWithGetAndSet;
        fieldsWithoutId = findFieldsWithoutIdAnnotation(fieldsWithGetAndSet);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return id;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private List<Field> findFieldWithGetAndSet(List<Field> fields, List<Method> methods) {
        return fields.stream().filter(field -> {
            Boolean getter = false;
            Boolean setter = false;
            for (Method method : methods) {
                if (isMethodName(method, concatinate("get", field))) {
                    getter = true;
                    if (setter) {
                        break;
                    }
                }
                if (isMethodName(method, concatinate("set", field))) {
                    setter = true;
                    if (getter) {
                        break;
                    }
                }
            }
            return getter && setter;
        }).collect(Collectors.toList());
    }

    private Field findIdAnnotation(List<Field> fields) {
        return fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new RuntimeException("No @id annotatied field"));
    }

    private List<Field> findFieldsWithoutIdAnnotation(List<Field> fields) {
        return fields.stream().filter(field -> !field.isAnnotationPresent(Id.class)).collect(Collectors.toList());
    }

    private Boolean isMethodName(Method method, String name) {
        return method.getName().equalsIgnoreCase(name);
    }

    private String concatinate(String str, Field field) {
        return str + field.getName();
    }
}
