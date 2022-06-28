package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "stfUrl", defaultValue = "", crypted = true)
    public static String STF_URL;
    @Config(configName = NAME, fieldName = "b2bUrl", defaultValue = "", crypted = true)
    public static String B2B_URL;

    @Config(configName = NAME, fieldName = "selgrosUrl", defaultValue = "", crypted = true)
    public static String SELGROS_URL;
}
