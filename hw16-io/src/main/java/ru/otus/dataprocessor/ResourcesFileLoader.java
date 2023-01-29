package ru.otus.dataprocessor;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonStructure;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException, ClassNotFoundException {
        //читает файл, парсит и возвращает результат

        List<Measurement> measurements = new ArrayList<>();

        try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            JsonArray jsonArray = jsonReader.read().asJsonArray();
            for (int i=0; i< jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                measurements.add(new Measurement(jsonObject.getString(NAME), Double.parseDouble(jsonObject.getJsonNumber(VALUE).toString())));
            }
        }
        return measurements;
    }
}
