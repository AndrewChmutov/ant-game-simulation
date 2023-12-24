package com.game;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingsLoader {
    public Settings loadSettings(String name) {
        String path = "src/main/config/" + name + ".json";

        Settings settings = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            settings = objectMapper.readValue(new File(path), Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings.recalculate();

        return settings;
    }

    public void dumpSettings(String name, Settings settings) {
        String path = "src/main/config/" + name + ".json";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
