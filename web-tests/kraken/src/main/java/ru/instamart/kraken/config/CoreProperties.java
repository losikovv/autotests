package ru.instamart.kraken.config;

import ru.instamart.utils.config.Config;

public final class CoreProperties {

    public static final String NAME = "core";

    @Config(configName = NAME, fieldName = "defaultRetailer", defaultValue = "metro")
    public static String DEFAULT_RETAILER;
    @Config(configName = NAME, fieldName = "defaultEnvironment", defaultValue = "sbermarket-preprod", args = "env")
    public static String DEFAULT_ENVIRONMENT;
    @Config(configName = NAME, fieldName = "defaultSms", defaultValue = "111111")
    public static String DEFAULT_SMS;

    @Config(configName = NAME, fieldName = "qaseApiToken", defaultValue = "", crypted = true)
    public static String QASE_API_TOKEN;

    @Config(configName = NAME, fieldName = "base64KubeConfig", defaultValue = "", crypted = true)
    public static String BASE64_KUBE_CONFIG;
}
