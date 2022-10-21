package ru.instamart.reforged.core.config;

import ru.instamart.kraken.common.config.Config;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.CiModule;

import java.util.List;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "", args = "admin_url")
    public static String ADMIN_URL;

    @Config(configName = NAME, fieldName = "stfUrl", defaultValue = "", env = "URL_STF_FRONT")
    public static String STF_URL;
    @Config(configName = NAME, fieldName = "headerStfForwardTo", defaultValue = "s-sb-stfkraken-sbermarket", env = "STF_FORWARD")
    public static String HEADER_STF_FORWARD_TO;

    @Config(configName = NAME, fieldName = "selgrosUrl", defaultValue = "")
    public static String SELGROS_URL;
    @Config(configName = NAME, fieldName = "headerSelgrosForwardTo", defaultValue = "s-sb-stfkraken-selgros")
    public static String HEADER_SELGROS_FORWARD_TO;

    @Config(configName = NAME, fieldName = "defaultCookies", defaultValue = "")
    public static List<String> DEFAULT_COOKIES;

    @Config(configName = NAME, fieldName = "defaultSid", defaultValue = "81")
    public static int DEFAULT_SID;

    @Config(configName = NAME, fieldName = "defaultMetroMoscowSid", defaultValue = "1")
    public static int DEFAULT_METRO_MOSCOW_SID;
    @Config(configName = NAME, fieldName = "defaultMetroMoscowUuid", defaultValue = "")
    public static String DEFAULT_METRO_MOSCOW_UUID;
    @Config(configName = NAME, fieldName = "defaultMetroCrossZonesSid", defaultValue = "8")
    public static int DEFAULT_METRO_CROSSZONES_SID;

    @Config(configName = NAME, fieldName = "defaultAuchanSid", defaultValue = "72")
    public static int DEFAULT_AUCHAN_SID;

    @Config(configName = NAME, fieldName = "defaultPrereplacementSid", defaultValue = "6")
    public static int DEFAULT_PREREPLACEMENT_SID;

    public static class Env {

        static {
            if (CiModule.isUi()) {
                ADMIN_URL = EnvironmentProperties.BASIC_URL + "admin/";
            }
        }

        public static String ADMIN_FRONT_URL = ADMIN_URL;
    }
}
