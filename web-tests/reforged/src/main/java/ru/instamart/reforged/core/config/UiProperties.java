package ru.instamart.reforged.core.config;

import ru.instamart.kraken.common.config.Config;

import java.util.List;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "", args = "admin_url", env = "ADMIN_URL")
    public static String ADMIN_URL;

    @Config(configName = NAME, fieldName = "stfUrl", defaultValue = "", env = "URL_STF_FRONT")
    public static String STF_URL;
    @Config(configName = NAME, fieldName = "headerStfForwardTo", defaultValue = "s-sb-stfkraken-sbermarket", env = "STF_FORWARD")
    public static String HEADER_STF_FORWARD_TO;

    @Config(configName = NAME, fieldName = "b2bUrl", defaultValue = "", args = "url_b2b_front", env = "URL_B2B_FRONT")
    public static String B2B_URL;
    @Config(configName = NAME, fieldName = "headerB2bForwardTo", defaultValue = "s-sb-stfkraken-smbusiness", env = "B2B_FORWARD")
    public static String HEADER_B2B_FORWARD_TO;

    @Config(configName = NAME, fieldName = "selgrosUrl", defaultValue = "")
    public static String SELGROS_URL;
    @Config(configName = NAME, fieldName = "headerSelgrosForwardTo", defaultValue = "s-sb-stfkraken-selgros")
    public static String HEADER_SELGROS_FORWARD_TO;

    @Config(configName = NAME, fieldName = "hrPartnersUrl", defaultValue = "", env = "HR_OPS_PARTNERS_URL")
    public static String HR_PARTNERS_URL;

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

    @Config(configName = NAME, fieldName = "defaultLentaSid", defaultValue = "58")
    public static int DEFAULT_LENTA_SID;

    @Config(configName = NAME, fieldName = "defaultPrereplacementSid", defaultValue = "6")
    public static int DEFAULT_PREREPLACEMENT_SID;

    @Config(configName = NAME, fieldName = "alcoholCategoryPermalink", defaultValue = "")
    public static String ALCOHOL_CATEGORY_LINK;
    @Config(configName = NAME, fieldName = "freeDeliveryPromotionId", defaultValue = "")
    public static int FREE_DELIVERY_PROMO_ID;
}
