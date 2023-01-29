package ru.otus.dataprocessor;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл

        try (JsonWriter jsonWriter = Json.createWriter(new FileWriter(this.fileName))) {

            var jsonObjectBuilder = Json.createObjectBuilder();

            data.forEach((key, value) -> {
                jsonObjectBuilder.add(key, value);
            });

            var jsonObject = jsonObjectBuilder.build();

            jsonWriter.writeObject(jsonObject);
        }
    }
}
