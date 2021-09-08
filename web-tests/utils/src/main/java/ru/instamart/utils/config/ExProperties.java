package ru.instamart.utils.config;

import java.util.Properties;

import static java.util.Objects.isNull;

/**
 * Расширяет базовый класс {@link Properties}
 * Получаем с помощью {@link Properties#getProperty(String)}
 * значение из конфигурационного файла и дополнительными методами парсим значение
 * из вернувшейся строки.
 */
public final class ExProperties extends Properties {

    public int getIntProperty(final String name, final String defaultValue) {
        return getIntProperty(name, Integer.parseInt(defaultValue));
    }

    public int getIntProperty(final String name, final int defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Integer.parseInt(val.trim());
    }

    public long getLongProperty(final String name, final String defaultValue) {
        return getLongProperty(name, Long.parseLong(defaultValue));
    }


    public long getLongProperty(final String name, final long defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Long.parseLong(val.trim());
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

    public boolean getBooleanProperty(final String name, final String defaultValue) {
        return getBooleanProperty(name, Boolean.parseBoolean(defaultValue));
    }

    public boolean getBooleanProperty(final String name, final boolean defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Boolean.parseBoolean(val.trim());
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

    public double getDoubleProperty(final String name, final String defaultValue) {
        return getDoubleProperty(name, Double.parseDouble(defaultValue));
    }

    public double getDoubleProperty(final String name, final double defaultValue) {
        final String val = getProperty(name);
        return (isNull(val)) ? defaultValue : Double.parseDouble(val.trim());
    }
}
