package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

import java.util.List;

public final class UiProperties {

    public static final String NAME = "ui";

    @Config(configName = NAME, fieldName = "basicAuthUsername", defaultValue = "", crypted = true)
    public static String BASIC_AUTH_USERNAME;
    @Config(configName = NAME, fieldName = "basicAuthPassword", defaultValue = "", crypted = true)
    public static String BASIC_AUTH_PASSWORD;

    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "", crypted = true)
    public static String ADMIN_URL;

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


    @Config(configName = NAME, fieldName = "sberIdUrl", defaultValue = "https://online.sberbank.ru/")
    public static String SBER_ID_URL;
    @Config(configName = NAME, fieldName = "jobLandingUrl", defaultValue = "https://job.sbermarket.ru/")
    public static String JOB_LANDING_URL;
    @Config(configName = NAME, fieldName = "rbsuatPaymentsUrl", defaultValue = "https://web.rbsuat.com/")
    public static String RBSUAT_PAYMENTS_URL;
    @Config(configName = NAME, fieldName = "demoCloudPaymentsUrl", defaultValue = "https://demo.cloudpayments.ru/")
    public static String DEMO_CLOUD_PAYMENTS_URL;

    @Config(configName = NAME, fieldName = "defaultCookies", defaultValue = "")
    public static List DEFAULT_COOKIES;
}
