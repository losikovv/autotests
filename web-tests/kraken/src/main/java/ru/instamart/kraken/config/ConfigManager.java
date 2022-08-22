package ru.instamart.kraken.config;

import ru.instamart.kraken.common.config.AbstractConfigManager;

public final class ConfigManager extends AbstractConfigManager {

    private static final String CONFIG_DIR = "config";
    private static final String ENV_CONFIG_DIR = "environment_configs";

    @Override
    public void loadConfig() {
        ENGINE.loadConfig(CoreProperties.class, CoreProperties.NAME, CONFIG_DIR);
        ENGINE.loadConfig(EnvironmentProperties.class, CoreProperties.DEFAULT_ENVIRONMENT, ENV_CONFIG_DIR);
    }

    private ConfigManager() {}

    public static ConfigManager getInstance() {
        return ConfigManager.Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
