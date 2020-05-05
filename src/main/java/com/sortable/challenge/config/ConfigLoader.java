package com.sortable.challenge.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConfigLoader {
    private static String CONFIG_PATH = "/auction/config.json";

    public String loadConfig() {
        File file = new File(CONFIG_PATH);
        System.out.println("Loading Config");
        String buffer = "";

        try {
            buffer = new String(Files.readAllBytes(Paths.get(CONFIG_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;

    }
}
