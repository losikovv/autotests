package ru.instamart.reforged.core.config;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.common.config.AbstractConfigManager;

public final class ConfigManager extends AbstractConfigManager {

    private static final String CONFIG_DIR = "config";
    private static final String ENV_CONFIG_DIR = "env";

    @Override
    public void loadConfig() {
        ENGINE.loadConfig(BrowserProperties.class, BrowserProperties.NAME, CONFIG_DIR);
        ENGINE.loadConfig(WaitProperties.class, WaitProperties.NAME, CONFIG_DIR);
        ENGINE.loadConfig(UiProperties.class, UiProperties.NAME, getEnvConfigDir());
    }

    private String getEnvConfigDir() {
        return ENV_CONFIG_DIR + "/" + EnvironmentProperties.Env.ENV_NAME.replace("-", "/");
    }

    private ConfigManager() {}

    public static ConfigManager getInstance() {
        return ConfigManager.Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
