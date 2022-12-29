package ru.instamart.kraken.config;

import ru.instamart.kraken.common.config.Config;

public final class CoreProperties {

    public static final String NAME = "core";

    @Config(configName = NAME, fieldName = "defaultSms", defaultValue = "111111")
    public static String DEFAULT_SMS;
    @Config(configName = NAME, fieldName = "defaultUiSms", defaultValue = "199011")
    public static String DEFAULT_UI_SMS;

    @Config(configName = NAME, fieldName = "base64KubeConfig", defaultValue = "", encrypted = true)
    public static String BASE64_KUBE_CONFIG;

    @Config(configName = NAME, fieldName = "grpcPort", defaultValue = "443")
    public static int GRPC_PORT;

    @Config(configName = NAME, fieldName = "kafkaServer", defaultValue = "51.250.33.222:9094")
    public static String KAFKA_SERVER;

    @Config(configName = NAME, fieldName = "mySqlPoolSize", defaultValue = "1")
    public static int DEFAULT_MYSQL_POOL_SIZE;
    @Config(configName = NAME, fieldName = "webhookSiteUrl", defaultValue = "https://webhook.site")
    public static String DEFAULT_WEBHOOK_SITE_URL;

    @Config(configName = NAME, fieldName = "abServiceUrl", defaultValue = "https://admin-gw.k-stage.sbermarket.tech")
    public static String AB_SERVICE_URL;

    @Config(configName = NAME, fieldName = "basicAuthUsername", defaultValue = "", encrypted = true)
    public static String BASIC_AUTH_USERNAME;
    @Config(configName = NAME, fieldName = "basicAuthPassword", defaultValue = "", encrypted = true)
    public static String BASIC_AUTH_PASSWORD;
}
