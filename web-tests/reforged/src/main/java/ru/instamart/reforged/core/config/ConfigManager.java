package ru.instamart.reforged.core.config;

import ru.instamart.utils.config.AbstractConfigManager;

public final class ConfigManager extends AbstractConfigManager {

    private static final String CONFIG_DIR = "config";

    @Override
    public void loadConfig() {
        ENGINE.loadConfig(BrowserProperties.class, BrowserProperties.NAME, CONFIG_DIR);
        ENGINE.loadConfig(WaitProperties.class, WaitProperties.NAME, CONFIG_DIR);
    }

    private ConfigManager() {}

    public static ConfigManager getInstance() {
        return ConfigManager.Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
