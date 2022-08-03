package ru.instamart.kraken.config;

import ru.sbermarket.common.config.Config;

public final class CoreProperties {

    public static final String NAME = "core";

    @Config(configName = NAME, fieldName = "defaultRetailer", defaultValue = "metro")
    public static String DEFAULT_RETAILER;
    @Config(configName = NAME, fieldName = "defaultEnvironment", defaultValue = "sbermarket-preprod", args = "env")
    public static String DEFAULT_ENVIRONMENT;
    @Config(configName = NAME, fieldName = "defaultSms", defaultValue = "111111")
    public static String DEFAULT_SMS;
    @Config(configName = NAME, fieldName = "defaultUiSms", defaultValue = "199011")
    public static String DEFAULT_UI_SMS;

    @Config(configName = NAME, fieldName = "qaseApiToken", defaultValue = "", crypted = true)
    public static String QASE_API_TOKEN;

    @Config(configName = NAME, fieldName = "base64KubeConfig", defaultValue = "", crypted = true)
    public static String BASE64_KUBE_CONFIG;

    @Config(configName = NAME, fieldName = "grpcPort", defaultValue = "443")
    public static int GRPC_PORT;

    @Config(configName = NAME, fieldName = "kafkaServer", defaultValue = "51.250.33.222:9094")
    public static String KAFKA_SERVER;

    @Config(configName = NAME, fieldName = "defaultQaServiceTimeout", defaultValue = "15")
    public static int DEFAULT_QA_SERVICE_TIMEOUT;

    @Config(configName = NAME, fieldName = "mySqlPoolSize", defaultValue = "1")
    public static int DEFAULT_MYSQL_POOL_SIZE;
    @Config(configName = NAME, fieldName = "webhookSiteUrl", defaultValue = "https://webhook.site")
    public static String DEFAULT_WEBHOOK_SITE_URL;

    @Config(configName = NAME, fieldName = "abServiceUrl", defaultValue = "https://paas-content-growth-ab-platform-back.k-stage.sbmt.io")
    public static String AB_SERVICE_URL;
}
