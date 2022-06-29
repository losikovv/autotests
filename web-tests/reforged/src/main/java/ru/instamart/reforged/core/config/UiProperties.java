package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "stfUrl", defaultValue = "", crypted = true)
    public static String STF_URL;
    @Config(configName = NAME, fieldName = "headerStfForwardTo", defaultValue = "s-sb-stfkraken-sbermarket")
    public static String HEADER_STF_FORWARD_TO;

    @Config(configName = NAME, fieldName = "b2bUrl", defaultValue = "", crypted = true)
    public static String B2B_URL;
    @Config(configName = NAME, fieldName = "headerB2bForwardTo", defaultValue = "s-sb-stfkraken-smbusiness")
    public static String HEADER_B2B_FORWARD_TO;

    @Config(configName = NAME, fieldName = "selgrosUrl", defaultValue = "", crypted = true)
    public static String SELGROS_URL;
    @Config(configName = NAME, fieldName = "headerSelgrosForwardTo", defaultValue = "s-sb-stfkraken-selgros")
    public static String HEADER_SELGROS_FORWARD_TO;
}
