package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private EntityClassMetaData<?> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + classMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder builder = new StringBuilder("select * ");
        builder.append("from ")
                .append(classMetaData.getName())
                .append(" ")
                .append("where ")
                .append(classMetaData.getIdField().getName())
                .append("= ?");
        return builder.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder buildValues = new StringBuilder(" values(");
        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(classMetaData.getName() + "(");

        List<Field> allFields = classMetaData.getFieldsWithoutId();

        for (int i = 0; i < allFields.size(); i ++ ) {
            buildValues.append("?");
            builder.append(allFields.get(i).getName());

            if (i == allFields.size() - 1) {
                buildValues.append(")");
                builder.append(")");
            } else {
                buildValues.append(", ");
                builder.append(", ");
            }

        }
        builder.append(buildValues);

        return builder.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder builder = new StringBuilder("update ");
        builder.append(classMetaData.getName());

        for (Field field : classMetaData.getFieldsWithoutId()) {
            builder.append(field.getName())
                    .append("= ? ");
        }

        builder.append("where ")
                .append(classMetaData.getIdField().getName())
                .append("= ?");

        return "update client set name = ? where id = ?";
    }
}
