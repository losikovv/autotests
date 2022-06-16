package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

public final class WaitProperties {

    public static final String NAME = "wait";

    @Config(configName = NAME, fieldName = "basicTimeout", defaultValue = "10")
    public static int BASIC_TIMEOUT;
    @Config(configName = NAME, fieldName = "waitingTimeout", defaultValue = "15")
    public static int WAITING_TIMEOUT;

    @Config(configName = NAME, fieldName = "pollingInterval", defaultValue = "250")
    public static int POLLING_INTERVAL;

    @Config(configName = NAME, fieldName = "maxPageLoadTimeout", defaultValue = "20")
    public static int MAX_PAGE_LOAD_TIMEOUT;
    @Config(configName = NAME, fieldName = "maxScriptLoadTimeout", defaultValue = "250")
    public static int MAX_SCRIPT_LOAD_TIMEOUT;
}
