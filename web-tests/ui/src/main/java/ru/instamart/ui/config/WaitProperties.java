package ru.instamart.ui.config;

import ru.instamart.utils.config.Config;

public final class WaitProperties {

    public static final String NAME = "wait";

    @Config(configName = NAME, fieldName = "basicTimeout", defaultValue = "10")
    public static int BASIC_TIMEOUT;
    @Config(configName = NAME, fieldName = "waitingTimeout", defaultValue = "15")
    public static int WAITING_TIMEOUT;
}
