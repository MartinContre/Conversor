package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.KeynotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for reading JSON files and retrieving values.
 */
public class JsonReader {
    private static final Logger logger = LogManager.getLogger(JsonReader.class);

    private JsonObject jsonObject;

    /**
     * Creates a new instance of the JsonReader class.
     *
     * @param filePath The path to the JSON file.
     */
    public JsonReader(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                throw new FileNotFoundException("El archivo JSON no existe: " + filePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = JsonParser.parseReader(reader);
            jsonObject = (JsonObject) obj;
        } catch (IOException e) {
            logger.error(String.format("An exception occurred while reading the JSON file. Exception: %s", e));
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an array of values associated with the given key from the JSON object.
     *
     * @param key The key for retrieving the values.
     * @return An array of values.
     * @throws IllegalStateException if the JSON file has not been loaded.
     */
    public String[] getValues(String key) {
        try {
            if (jsonObject == null) {
                throw new IllegalStateException("El archivo JSON no ha sido cargado");
            }

            JsonElement element = jsonObject.get(key);

            if (element == null) {
                throw new KeynotFoundException("La clave no se encuentra en el archivo JSON: " + key);
            }

            if (element.isJsonArray()) {
                JsonArray jsonArray = element.getAsJsonArray();
                String[] values = new String[jsonArray.size()];

                for (int i = 0; i < jsonArray.size(); i++) {
                    values[i] = jsonArray.get(i).getAsString();
                }

                return values;
            }

            throw new KeynotFoundException("El valor de la clave no es un arreglo en el archivo JSON: " + key);
        } catch (IllegalStateException | KeynotFoundException e) {
            logger.error(String.format("An exception occurred while reading the JSON file. Exception: %s", e));
            return new String[0];
        }
    }

    /**
     * Retrieves the value associated with the given key from the JSON object.
     *
     * @param key The key for retrieving the value.
     * @return The value as a string.
     */
    public String getValue(String key) {
        JsonElement element = jsonObject.get(key);
        if (element == null) {
            try {
                throw new KeynotFoundException("La clave no se encuentra en el archivo JSON: " + key);
            } catch (KeynotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return element.getAsString();
    }
}
