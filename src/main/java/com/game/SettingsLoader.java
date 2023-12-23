package com.game;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingsLoader {
    public Settings loadSettings(String name) {
        String path = "src/main/config/" + name + ".json";
        System.out.println(path);

        Settings settings = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            settings = objectMapper.readValue(new File(path), Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return settings;
    }

    public void dumpSettings(String name, Settings settings) {
        String path = "src/main/config/" + name + ".json";
        System.out.println(path);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
