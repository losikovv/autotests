package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

import java.util.List;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "", args = "admin_url")
    public static String ADMIN_URL;

    @Config(configName = NAME, fieldName = "stfUrl", defaultValue = "", args = "url_stf_front")
    public static String STF_URL;
    @Config(configName = NAME, fieldName = "headerStfForwardTo", defaultValue = "s-sb-stfkraken-sbermarket", args = "stf_forward")
    public static String HEADER_STF_FORWARD_TO;

    @Config(configName = NAME, fieldName = "b2bUrl", defaultValue = "", args = "url_b2b_front")
    public static String B2B_URL;
    @Config(configName = NAME, fieldName = "headerB2bForwardTo", defaultValue = "s-sb-stfkraken-smbusiness", args = "b2b_forward")
    public static String HEADER_B2B_FORWARD_TO;

    @Config(configName = NAME, fieldName = "selgrosUrl", defaultValue = "")
    public static String SELGROS_URL;
    @Config(configName = NAME, fieldName = "headerSelgrosForwardTo", defaultValue = "s-sb-stfkraken-selgros")
    public static String HEADER_SELGROS_FORWARD_TO;

    @Config(configName = NAME, fieldName = "sberIdUrl", defaultValue = "https://online.sberbank.ru/")
    public static String SBER_ID_URL;
    @Config(configName = NAME, fieldName = "jobLandingUrl", defaultValue = "https://job.sbermarket.ru/")
    public static String JOB_LANDING_URL;
    @Config(configName = NAME, fieldName = "rbsuatPaymentsUrl", defaultValue = "https://web.rbsuat.com/")
    public static String RBSUAT_PAYMENTS_URL;
    @Config(configName = NAME, fieldName = "demoCloudPaymentsUrl", defaultValue = "https://demo.cloudpayments.ru/")
    public static String DEMO_CLOUD_PAYMENTS_URL;

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
}
