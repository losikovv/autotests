package ru.instamart.kraken.config;

import ru.instamart.utils.config.Config;
import ru.instamart.utils.config.Env;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Env
public final class EnvironmentProperties {
    
    public static final String NAME = "env";

    @Config(configName = NAME, fieldName = "tenant", defaultValue = "")
    public static String TENANT;
    @Config(configName = NAME, fieldName = "server", defaultValue = "")
    public static String SERVER;

    @Config(configName = NAME, fieldName = "basicUrl", defaultValue = "")
    private static String BASIC_URL;
    @Config(configName = NAME, fieldName = "httpAuth", defaultValue = "", crypted = true)
    private static String HTTP_AUTH;

    @Config(configName = NAME, fieldName = "shopperUrl", defaultValue = "")
    private static String SHOPPER_URL;
    @Config(configName = NAME, fieldName = "defaultSid", defaultValue = "")
    public static int DEFAULT_SID;
    @Config(configName = NAME, fieldName = "defaultShopperSid", defaultValue = "")
    public static int DEFAULT_SHOPPER_SID;

    @Config(configName = NAME, fieldName = "protocol", defaultValue = "https", args = "protocol")
    private static String PROTOCOL;

    @Config(configName = NAME, fieldName = "dbUrl", defaultValue = "")
    public static String DB_URL;
    @Config(configName = NAME, fieldName = "dbUsername", defaultValue = "", crypted = true)
    public static String DB_USERNAME;
    @Config(configName = NAME, fieldName = "dbPassword", defaultValue = "", crypted = true)
    public static String DB_PASSWORD;
    @Config(configName = NAME, fieldName = "k8sNameSpace", defaultValue = "")
    public static String K8S_NAME_SPACE;
    @Config(configName = NAME, fieldName = "k8sLabelSelector", defaultValue = "")
    public static String K8S_LABEL_SELECTOR;

    public static class Env {

    //TODO: Немножк костылей. Насколько мне известно эти урлы можно без костылей достать из переменных которые в основной пайпе от разработки
        static {
            var customBasicUrl = System.getProperty("url_stf");
            var customShopperUrl = System.getProperty("url_shp");

            if (nonNull(customBasicUrl) && !customBasicUrl.isBlank()) {
                SERVER = customBasicUrl.split("\\.")[0];
                BASIC_URL = customBasicUrl;

                if (nonNull(customShopperUrl) && !customShopperUrl.isBlank()) {
                    SHOPPER_URL = customShopperUrl;
                } else if (customBasicUrl.startsWith("stf-")) {
                    SHOPPER_URL = "shp" + customBasicUrl.substring(3);
                } else SHOPPER_URL = "shp-0.k-stage.sbermarket.tech";
            }
        }

        public static String ENV_NAME = TENANT + "-" + SERVER;
        public static String FULL_SITE_URL = PROTOCOL + "://" + BASIC_URL + "/";
        public static String FULL_SITE_URL_WITH_BASIC_AUTH = PROTOCOL + "://" + HTTP_AUTH + BASIC_URL + "/";
        public static String FULL_ADMIN_URL = FULL_SITE_URL + "admin/";
        public static String FULL_ADMIN_URL_WITH_BASIC_AUTH = FULL_SITE_URL_WITH_BASIC_AUTH + "admin/";
        public static String FULL_SHOPPER_URL = PROTOCOL + "://" + SHOPPER_URL + "/";
    }
}
