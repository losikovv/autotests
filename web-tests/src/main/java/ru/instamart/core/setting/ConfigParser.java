package ru.instamart.core.setting;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

@Slf4j
public final class ConfigParser {

    private final Properties properties = new Properties();
    private final File file;

    public ConfigParser(String name) {
        this(new File(name));
    }

    public ConfigParser(final File file) {
        this.file = file;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.defaultCharset())) {
                this.properties.load(inputStreamReader);
            }
        } catch (Exception e) {
            log.warn("[" + this.file.getName() + "] Can't load config with reason: " + e.getMessage());
        }
    }

    public String getString(final String key, final String defaultValue) {
        final String value = getValue(key);
        if (value == null) {
            log.warn("[" + this.file.getName() + "] missing property for key: " + key + " using default value: " + defaultValue);
            return defaultValue;
        }
        return value;
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        final String value = getValue(key);
        if (value == null) {
            log.warn("[" + this.file.getName() + "] missing property for key: " + key + " using default value: " + defaultValue);
            return defaultValue;
        }

        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            log.warn("[" + this.file.getName() + "] invalid value specified for key: " + key + " specified value: " + value + " should be \"boolean\" using default value: " + defaultValue);
            return defaultValue;
        }
    }

    public int getInt(final String key, final int defaultValue) {
        final String value = getValue(key);
        if (value == null) {
            log.warn("[" + this.file.getName() + "] missing property for key: " + key + " using default value: " + defaultValue);
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("[" + this.file.getName() + "] Invalid value specified for key: " + key + " specified value: " + value + " should be \"int\" using default value: " + defaultValue);
            return defaultValue;
        }
    }

    private String getValue(final String key) {
        final String value = this.properties.getProperty(key);
        return value != null ? value.trim() : null;
    }
}

