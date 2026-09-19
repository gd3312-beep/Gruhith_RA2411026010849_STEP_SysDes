package com.gdb.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {
    private Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String configPath) {
        loadProperties(configPath);
    }

    private void loadProperties(String configPath) {
        InputStream is = null;
        try {
            // 1. Try loading from classpath first
            is = getClass().getClassLoader().getResourceAsStream(configPath);

            // 2. If not found on classpath, try loading from file system
            if (is == null) {
                File file = new File(configPath);
                if (file.exists()) {
                    is = new FileInputStream(file);
                }
            }

            // 3. Load key-value pairs if stream was found
            if (is != null) {
                properties.load(is);
            }
        } catch (Exception e) {
            System.err.println("Warning: Failed to load properties from " + configPath + ": " + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
