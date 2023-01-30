package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                T object = null;
                if (rs.next()) {
                    object = buildAndFillObject(rs);
                }
                return object;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T object = buildAndFillObject(rs);
                    clientList.add(object);
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));

    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getFieldsValue(client, entityClassMetaData.getFieldsWithoutId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> fieldValues = new ArrayList<>();
            fieldValues.addAll(getFieldsValue(client, entityClassMetaData.getAllFields()));
            fieldValues.addAll(getFieldsValue(client, List.of(entityClassMetaData.getIdField())));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    getFieldsValue(client, entityClassMetaData.getAllFields()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T buildAndFillObject(ResultSet rs) throws InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T object = entityClassMetaData.getConstructor().newInstance();
        for (Field field : entityClassMetaData.getAllFields()) {
            field.setAccessible(true);
            field.set(object, rs.getObject(field.getName().toLowerCase()));
        }

        return object;
    }

    private List<Object> getFieldsValue(T object, List<Field> fields) throws IllegalAccessException {
        List<Object> fieldValues = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldValues.add(field.get(object));
        }

        return fieldValues;
    }
}
