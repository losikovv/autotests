package ru.instamart.kraken.config;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.enums.Server;
import ru.sbermarket.common.config.Config;
import ru.sbermarket.common.config.Env;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Env
@Slf4j
public final class EnvironmentProperties {

    public static final String NAME = "env";

    @Config(configName = NAME, fieldName = "tenant", defaultValue = "")
    public static String TENANT;
    @Config(configName = NAME, fieldName = "server", defaultValue = "")
    public static String SERVER;
    @Config(configName = NAME, fieldName = "service", defaultValue = "")
    public static String SERVICE;
    @Config(configName = NAME, fieldName = "servicePgPort", defaultValue = "")
    public static int SERVICE_PG_PORT;
    @Config(configName = NAME, fieldName = "stage", defaultValue = "")
    public static String STAGE;
    @Config(configName = NAME, fieldName = "httpAuth", defaultValue = "", crypted = true)
    public static String HTTP_AUTH;
    @Config(configName = NAME, fieldName = "defaultSid", defaultValue = "81")
    public static int DEFAULT_SID;
    @Config(configName = NAME, fieldName = "defaultMetroMoscowSid", defaultValue = "1")
    public static int DEFAULT_METRO_MOSCOW_SID;
    @Config(configName = NAME, fieldName = "defaultMetroMoscowUuid", defaultValue = "")
    public static String DEFAULT_METRO_MOSCOW_UUID;
    @Config(configName = NAME, fieldName = "defaultAuchanSid", defaultValue = "72")
    public static int DEFAULT_AUCHAN_SID;
    @Config(configName = NAME, fieldName = "defaultMetroCrossZonesSid", defaultValue = "8")
    public static int DEFAULT_METRO_CROSSZONES_SID;
    @Config(configName = NAME, fieldName = "defaultOnDemandSid", defaultValue = "94")
    public static int DEFAULT_ON_DEMAND_SID;
    @Config(configName = NAME, fieldName = "defaultPrereplacementSid", defaultValue = "6")
    public static int DEFAULT_PREREPLACEMENT_SID;
    @Config(configName = NAME, fieldName = "defaultTid", defaultValue = "")
    public static int DEFAULT_TID;
    @Config(configName = NAME, fieldName = "defaultBrandId", defaultValue = "4")
    public static int DEFAULT_BRAND_ID;
    @Config(configName = NAME, fieldName = "defaultProductCountryId", defaultValue = "1")
    public static int DEFAULT_PRODUCT_COUNTRY_ID;
    @Config(configName = NAME, fieldName = "defaultIdZone", defaultValue = "")
    public static int DEFAULT_ID_ZONE;
    @Config(configName = NAME, fieldName = "defaultShiftsZoneId", defaultValue = "")
    public static int DEFAULT_SHIFTS_ZONE_ID;
    @Config(configName = NAME, fieldName = "defaultShopperSid", defaultValue = "")
    public static int DEFAULT_SHOPPER_SID;
    @Config(configName = NAME, fieldName = "dbUrl", defaultValue = "")
    public static String DB_URL;
    @Config(configName = NAME, fieldName = "dbPort", defaultValue = "")
    public static int DB_PORT;
    @Config(configName = NAME, fieldName = "dbPgUrl", defaultValue = "")
    public static String DB_PGSQL_URL;
    @Config(configName = NAME, fieldName = "dbPgPort", defaultValue = "")
    public static int DB_PG_PORT;
    @Config(configName = NAME, fieldName = "dbUsername", defaultValue = "", crypted = true)
    public static String DB_MYSQL_USERNAME;
    @Config(configName = NAME, fieldName = "dbPassword", defaultValue = "", crypted = true)
    public static String DB_MYSQL_PASSWORD;
    @Config(configName = NAME, fieldName = "dbPgUsername", defaultValue = "", crypted = true)
    public static String DB_PGSQL_USERNAME;
    @Config(configName = NAME, fieldName = "dbPgPassword", defaultValue = "", crypted = true)
    public static String DB_PGSQL_PASSWORD;
    @Config(configName = NAME, fieldName = "cloudPaymentsUrl", defaultValue = "")
    public static String CLOUD_PAYMENTS_URL;
    @Config(configName = NAME, fieldName = "sberIdUrl", defaultValue = "")
    public static String SBER_ID_URL;
    @Config(configName = NAME, fieldName = "rbsuatPaymentsUrl", defaultValue = "")
    public static String RBSUAT_PAYMENTS_URL;
    @Config(configName = NAME, fieldName = "jobLandingUrl", defaultValue = "")
    public static String JOB_LANDING_URL;
    @Config(configName = NAME, fieldName = "publicCryptoKey", defaultValue = "")
    public static String PUBLIC_CRYPTO_KEY;

    @Config(configName = NAME, fieldName = "k8sNameStfSpace", defaultValue = "")
    public static String K8S_NAME_STF_SPACE;
    @Config(configName = NAME, fieldName = "k8sLabelStfSelector", defaultValue = "")
    public static String K8S_LABEL_STF_SELECTOR;
    @Config(configName = NAME, fieldName = "k8sNameShpSpace", defaultValue = "")
    public static String K8S_NAME_SHP_SPACE;
    @Config(configName = NAME, fieldName = "k8sLabelShpSelector", defaultValue = "")
    public static String K8S_LABEL_SHP_SELECTOR;
    @Config(configName = NAME, fieldName = "basicUrl", defaultValue = "")
    private static String BASIC_URL;
    @Config(configName = NAME, fieldName = "shopperUrl", defaultValue = "")
    private static String SHOPPER_URL;
    //Для кейсов бизнес-витрины в связке с stf
    @Config(configName = NAME, fieldName = "b2cUrl", defaultValue = "")
    private static String B2C_URL;
    @Config(configName = NAME, fieldName = "protocol", defaultValue = "https", args = "protocol")
    private static String PROTOCOL;
    @Config(configName = NAME, fieldName = "qaUrl", defaultValue = "")
    private static String QA_URL;
    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "")
    private static String ADMIN_URL;
    @Config(configName = NAME, fieldName = "shopperGwUrl", defaultValue = "")
    private static String SHOPPER_GW_URL;
    @Config(configName = NAME, fieldName = "ssoAuthUrl", defaultValue = "")
    private static String SSO_AUTH_URL;
    @Config(configName = NAME, fieldName = "pgSqlPoolSize", defaultValue = "10")
    public static int DEFAULT_PGSQL_POOL_SIZE;
    @Config(configName = NAME, fieldName = "adminPassword", defaultValue = "", crypted = true)
    public static String ADMIN_PASSWORD;
    @Config(configName = NAME, fieldName = "headerForwardTo", defaultValue = "s-sb-stfkraken")
    public static String PROXY_HEADER_FORWARD_TO;


    public static class Env {

        //TODO: Немножк костылей. Насколько мне известно эти урлы можно без костылей достать из переменных которые в основной пайпе от разработки
        static {
            var customBasicUrl = System.getProperty("url_stf");
            var customShopperUrl = System.getProperty("url_shp");

            if (nonNull(customBasicUrl) && !customBasicUrl.isBlank()) {
                customBasicUrl = getDomainName(customBasicUrl);
                SERVER = customBasicUrl.contains("kraken")
                        ? Server.PREPROD.name().toLowerCase() : customBasicUrl.contains("k-stage")
                        ? Server.STAGING.name().toLowerCase() : Server.PRODUCTION.name().toLowerCase();
                BASIC_URL = customBasicUrl;
                STAGE = BASIC_URL.replace("stf-", "").replace(".k-stage.sbermarket.tech", "");

                if (!BASIC_URL.contains("stf-")) {
                    DB_URL = DB_URL.replace("_kraken", "");
                    DB_PGSQL_URL = DB_PGSQL_URL.replace("_kraken", "");
                } else {
                    DB_URL = DB_URL.replace("kraken", STAGE);
                    DB_PGSQL_URL = DB_PGSQL_URL.replace("kraken", STAGE);
                }


                if (BASIC_URL.contains("stf-")) {
                    K8S_NAME_STF_SPACE = K8S_NAME_STF_SPACE.replace("kraken", STAGE);
                    K8S_NAME_SHP_SPACE = K8S_NAME_SHP_SPACE.replace("kraken", STAGE);
                    QA_URL = QA_URL.replace("kraken", STAGE);
                } else {
                    K8S_NAME_STF_SPACE = K8S_NAME_STF_SPACE.replace("stfkraken", STAGE);
                    K8S_NAME_SHP_SPACE = K8S_NAME_SHP_SPACE.replace("shpkraken", STAGE);
                    QA_URL = QA_URL.replace("stf-kraken", STAGE);
                }
                log.debug("Кастомные данные при ручном запуске на стейджах");
                log.debug("BASIC_URL: {}", BASIC_URL);
                log.debug("Server: {}", SERVER);
                log.debug("Stage: {}", STAGE);
                log.debug("DB_URL: {}", DB_URL);
                log.debug("DB_PGSQL_URL: {}", DB_PGSQL_URL);
                log.debug("QA_URL: {}", QA_URL);
                log.debug("K8S_NAME_STF_SPACE: {}", K8S_NAME_STF_SPACE);
                log.debug("K8S_NAME_SHP_SPACE: {}", K8S_NAME_SHP_SPACE);

                if (nonNull(customShopperUrl) && !customShopperUrl.isBlank()) {
                    SHOPPER_URL = getDomainName(customShopperUrl);
                } else if (customBasicUrl.startsWith("stf-")) {
                    SHOPPER_URL = "shp" + customBasicUrl.substring(3);
                } else SHOPPER_URL = "shp-0.k-stage.sbermarket.tech";
                log.debug("SHOPPER_URL: " + SHOPPER_URL);
            }
        }

        public static String ENV_NAME = TENANT + "-" + SERVER;
        public static String BASIC_URL_WITH_AUTH = Optional.ofNullable(HTTP_AUTH).orElse("") + BASIC_URL + "/";

        public static String FULL_SITE_URL = PROTOCOL + "://" + BASIC_URL + "/";
        public static String FULL_SITE_URL_WITH_BASIC_AUTH = PROTOCOL + "://" + Optional.ofNullable(HTTP_AUTH).orElse("") + BASIC_URL + "/";
        public static String FULL_B2C_URL_WITH_BASIC_AUTH = PROTOCOL + "://" + Optional.ofNullable(HTTP_AUTH).orElse("") + B2C_URL + "/";
        public static String FULL_ADMIN_URL = FULL_SITE_URL + "admin/spa/";
        public static String FULL_ADMIN_URL_WITH_BASIC_AUTH = FULL_SITE_URL_WITH_BASIC_AUTH + "admin/spa/";
        public static String FULL_ADMIN_URL_OLD = FULL_SITE_URL + "admin/";
        public static String FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD = FULL_SITE_URL_WITH_BASIC_AUTH + "admin/";
        public static String FULL_SHOPPER_URL = PROTOCOL + "://" + SHOPPER_URL + "/";
        public static String PROD_FULL_SITE_URL = PROTOCOL + "://" + "api." + BASIC_URL + "/";
        public static String QA_FULL_URL = PROTOCOL + "://" + QA_URL + "/";
        public static String ADMIN_FULL_URL = PROTOCOL + "://" + Optional.ofNullable(ADMIN_URL).orElse(BASIC_URL_WITH_AUTH) + "/";
        public static String ONE_SESSION = System.getProperty("one_session");
        public static String FULL_SHOPPER_GW_URL = PROTOCOL + "://" + SHOPPER_GW_URL + "/";
        public static String FULL_SSO_AUTH_URL = PROTOCOL + "://" + SSO_AUTH_URL + "/";
        public static String DEMO_CLOUD_PAYMENTS_URL = PROTOCOL + "://" + CLOUD_PAYMENTS_URL + "/";
        public static String DEMO_RBSUAT_PAYMENTS_URL = PROTOCOL + "://" + RBSUAT_PAYMENTS_URL + "/";
        public static String FULL_JOB_LANDING_URL = PROTOCOL + "://" + JOB_LANDING_URL + "/";
        public static String FULL_SBER_ID_URL = PROTOCOL + "://" + SBER_ID_URL + "/";

        public static boolean isPreprod() {
            return Server.PREPROD.name().equalsIgnoreCase(EnvironmentProperties.SERVER);
        }

        public static boolean isProduction() {
            return Server.PRODUCTION.name().equalsIgnoreCase(EnvironmentProperties.SERVER);
        }

        private static String getDomainName(String url) {
            try {
                URI uri = new URI(url);
                return uri.toURL().getHost();
            } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
                log.debug("Domain parse error: {}", e.getMessage());
            }
            return url;
        }
    }
}
