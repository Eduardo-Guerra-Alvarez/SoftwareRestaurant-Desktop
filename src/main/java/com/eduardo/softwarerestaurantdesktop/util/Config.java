package com.eduardo.softwarerestaurantdesktop.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try(FileInputStream fis = new FileInputStream("config/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("File not loaded");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
