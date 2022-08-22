package ru.instamart.kraken.common.config;

public abstract class AbstractConfigManager {

    protected static final ConfigEngine ENGINE = new ConfigEngine();

    public abstract void loadConfig();
}
