package ru.instamart.kraken.common.config;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * Расширяет базовый класс {@link Properties}
 * Получаем с помощью {@link Properties#getProperty(String)}
 * значение из конфигурационного файла и дополнительными методами парсим значение
 * из вернувшейся строки.
 */
public final class ExProperties extends Properties {

    public String getStringProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getProperty(name, defaultValue) : systemProperty;
    }

    public int getIntProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getIntProperty(name, defaultValue) : Integer.parseInt(systemProperty);
    }

    public int getIntProperty(final String name, final String defaultValue) {
        return getIntProperty(name, Integer.parseInt(defaultValue));
    }

    public int getIntProperty(final String name, final int defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Integer.parseInt(val.trim());
    }

    public long getLongProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getLongProperty(name, defaultValue) : Long.parseLong(systemProperty);
    }

    public long getLongProperty(final String name, final String defaultValue) {
        return getLongProperty(name, Long.parseLong(defaultValue));
    }

    public long getLongProperty(final String name, final long defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Long.parseLong(val.trim());
    }

    public byte getByteProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getByteProperty(name, defaultValue) : Byte.parseByte(systemProperty);
    }

    public byte getByteProperty(final String name, final String defaultValue) {
        return getByteProperty(name, Byte.parseByte(defaultValue));
    }

    public byte getByteProperty(final String name, final byte defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Byte.parseByte(val.trim());
    }

    public byte getByteProperty(final String name, final int defaultValue) {
        return getByteProperty(name, (byte) defaultValue);
    }

    public boolean getBooleanProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getBooleanProperty(name, defaultValue) : Boolean.parseBoolean(systemProperty);
    }

    public boolean getBooleanProperty(final String name, final String defaultValue) {
        return getBooleanProperty(name, Boolean.parseBoolean(defaultValue));
    }

    public boolean getBooleanProperty(final String name, final boolean defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Boolean.parseBoolean(val.trim());
    }

    public float getFloatProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getFloatProperty(name, defaultValue) : Float.parseFloat(systemProperty);
    }

    public float getFloatProperty(final String name, final String defaultValue) {
        return getFloatProperty(name, Float.parseFloat(defaultValue));
    }

    public float getFloatProperty(final String name, final float defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Float.parseFloat(val.trim());
    }

    public float getFloatProperty(final String name, final double defaultValue) {
        return getFloatProperty(name, (float) defaultValue);
    }

    public double getDoubleProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ? getDoubleProperty(name, defaultValue) : Double.parseDouble(systemProperty);
    }

    public double getDoubleProperty(final String name, final String defaultValue) {
        return getDoubleProperty(name, Double.parseDouble(defaultValue));
    }

    public double getDoubleProperty(final String name, final double defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Double.parseDouble(val.trim());
    }

    public List<?> getListProperty(final String name, final String defaultValue, final String systemProperty) {
        return (isNull(systemProperty) || systemProperty.isEmpty()) ?
                getListProperty(name, defaultValue) : Stream.of(systemProperty.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public List<?> getListProperty(final String name, final String defaultValue) {
        return getListProperty(name, Stream.of(defaultValue.split(",")).map(String::trim).collect(Collectors.toList()));
    }

    public List<?> getListProperty(final String name, final List<?> defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Stream.of(val.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
