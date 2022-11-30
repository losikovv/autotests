package ru.instamart.kraken.config;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.common.config.Config;
import ru.instamart.kraken.common.config.Env;
import ru.instamart.kraken.enums.CiModule;
import ru.instamart.kraken.enums.Server;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Env
@Slf4j
public final class EnvironmentProperties {

    public static final String NAME = "env";

    @Config(configName = NAME, fieldName = "tenant", defaultValue = "")
    public static String TENANT;
    @Config(configName = NAME, fieldName = "server", defaultValue = "")
    public static String SERVER;
    @Config(configName = NAME, fieldName = "service", defaultValue = "", args = "service")
    public static String SERVICE;
    @Config(configName = NAME, fieldName = "stage", defaultValue = "")
    public static String STAGE;
    @Config(configName = NAME, fieldName = "defaultSid", defaultValue = "81", env = "DEFAULT_SID")
    public static int DEFAULT_SID;
    @Config(configName = NAME, fieldName = "defaultMetroMoscowSid", defaultValue = "1")
    public static int DEFAULT_METRO_MOSCOW_SID;
    @Config(configName = NAME, fieldName = "defaultCheckoutSid", defaultValue = "14")
    public static int DEFAULT_CHECKOUT_SID;
    @Config(configName = NAME, fieldName = "defaultOnDemandSid", defaultValue = "94")
    public static int DEFAULT_ON_DEMAND_SID;
    @Config(configName = NAME, fieldName = "defaultTid", defaultValue = "")
    public static int DEFAULT_TID;
    @Config(configName = NAME, fieldName = "defaultFiltersTid", defaultValue = "")
    public static int DEFAULT_FILTERS_TID;
    @Config(configName = NAME, fieldName = "defaultBrandId", defaultValue = "4")
    public static int DEFAULT_BRAND_ID;
    @Config(configName = NAME, fieldName = "defaultProductCountryId", defaultValue = "1")
    public static int DEFAULT_PRODUCT_COUNTRY_ID;
    @Config(configName = NAME, fieldName = "defaultIdZone", defaultValue = "")
    public static int DEFAULT_ID_ZONE;
    @Config(configName = NAME, fieldName = "defaultShiftsZoneId", defaultValue = "3767")
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
    @Config(configName = NAME, fieldName = "dbUsername", defaultValue = "", encrypted = true)
    public static String DB_MYSQL_USERNAME;
    @Config(configName = NAME, fieldName = "dbPassword", defaultValue = "", encrypted = true)
    public static String DB_MYSQL_PASSWORD;
    @Config(configName = NAME, fieldName = "dbPgUsername", defaultValue = "", encrypted = true)
    public static String DB_PGSQL_USERNAME;
    @Config(configName = NAME, fieldName = "dbPgPassword", defaultValue = "", encrypted = true)
    public static String DB_PGSQL_PASSWORD;
    @Config(configName = NAME, fieldName = "publicCryptoKey", defaultValue = "")
    public static String PUBLIC_CRYPTO_KEY;

    @Config(configName = NAME, fieldName = "k8sNameStfSpace", defaultValue = "")
    public static String K8S_NAME_STF_SPACE;
    @Config(configName = NAME, fieldName = "k8sLabelStfSelector", defaultValue = "")
    public static String K8S_LABEL_STF_SELECTOR;
    @Config(configName = NAME, fieldName = "k8sNameShpSpace", defaultValue = "", env = "SHP_NAMESPACE")
    public static String K8S_NAME_SHP_SPACE;
    @Config(configName = NAME, fieldName = "k8sLabelShpSelector", defaultValue = "", env = "SHP_LABEL")
    public static String K8S_LABEL_SHP_SELECTOR;
    @Config(configName = NAME, fieldName = "basicUrl", defaultValue = "stf-kraken.k-stage.sbermarket.tech", env = "URL_STF_BACKEND")
    public static String BASIC_URL;
    @Config(configName = NAME, fieldName = "shopperUrl", defaultValue = "", env = "URL_SHP")
    private static String SHOPPER_URL;

    @Config(configName = NAME, fieldName = "adminUrl", defaultValue = "", env = "ADMIN_URL")
    private static String ADMIN_URL;
    @Config(configName = NAME, fieldName = "shopperGwUrl", defaultValue = "")
    public static String SHOPPER_GW_URL;
    @Config(configName = NAME, fieldName = "ssoAuthUrl", defaultValue = "")
    public static String SSO_AUTH_URL;
    @Config(configName = NAME, fieldName = "pgSqlPoolSize", defaultValue = "10")
    public static int DEFAULT_PGSQL_POOL_SIZE;
    @Config(configName = NAME, fieldName = "adminPassword", defaultValue = "", encrypted = true)
    public static String ADMIN_PASSWORD;
    @Config(configName = NAME, fieldName = "metroToken", defaultValue = "", encrypted = true)
    public static String METRO_TOKEN;

    public static class Env {

        //TODO: Немножк костылей. Насколько мне известно эти урлы можно без костылей достать из переменных которые в основной пайпе от разработки
        static {
            var customBasicUrl = System.getenv("URL_STF_BACKEND");
            var customShopperUrl = System.getenv("URL_SHP");

            if (nonNull(customBasicUrl) && !customBasicUrl.isBlank()) {
                customBasicUrl = getDomainName(customBasicUrl);
                SERVER = customBasicUrl.contains("kraken")
                        ? Server.PREPROD.name().toLowerCase() : customBasicUrl.contains("stage")
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
                } else {
                    K8S_NAME_STF_SPACE = K8S_NAME_STF_SPACE.replace("stfkraken", STAGE);
                    K8S_NAME_SHP_SPACE = K8S_NAME_SHP_SPACE.replace("shpkraken", STAGE);
                }

                shopperUrl(customBasicUrl, customShopperUrl);
                printProperty();
            }

            if (CiModule.isUi()) {
                final var stfForwardTo = CiModule.isStf() ?
                        System.getenv("STF_FORWARD") : System.getenv("B2B_FORWARD");
                forward(stfForwardTo);
                shopperUrl(customBasicUrl, customShopperUrl);
                printProperty();
            }

            if (CiModule.isAdmin()) {
                forward(System.getenv("ADMIN_FORWARD"));
                shopperUrl(customBasicUrl, customShopperUrl);
                printProperty();
            }

            if (CiModule.isHrOpsPartners()) {
                BASIC_URL = getDomainName(System.getenv("HR_OPS_PARTNERS_URL")).replaceAll("sbermarket.ru", "do_not_run_on.prod");
                printProperty();
            }
        }

        public static String ENV_NAME = TENANT + "-" + SERVER;

        public static String FULL_SITE_URL = "https://" + (isProduction() ? "api." : "") + BASIC_URL + "/";
        public static String FULL_SHOPPER_URL = "https://" + SHOPPER_URL + "/";
        public static String ADMIN_FULL_URL = isProduction() ? ADMIN_URL : "https://" + BASIC_URL + "/";
        public static String ONE_SESSION = System.getProperty("one_session");
        public static String ETA_NAMESPACE = getEtaNamespace();
        public static String SHIPPINGCALC_NAMESPACE = getShippingcalcNamespace();
        public static String SURGELEVEL_NAMESPACE = "paas-content-operations-surgelevel" + getSurgelevelHashOrBranch();
        public static String SURGELEVEL_HASH_OR_BRANCH = getSurgelevelHashOrBranch();

        private static String getEtaNamespace() {
            var etaNamespace = System.getProperty("url_paas_eta", "paas-content-operations-eta");
            if (isNull(etaNamespace) || etaNamespace.isEmpty() || etaNamespace.isBlank()) {
                etaNamespace = "paas-content-operations-eta";
            }
            return etaNamespace;
        }

        private static String getShippingcalcNamespace() {
            String shippingcalcNamespace = System.getProperty("url_paas_shippingcalc", "paas-content-operations-shippingcalc");
            if (isNull(shippingcalcNamespace) || shippingcalcNamespace.isEmpty() || shippingcalcNamespace.isBlank()) {
                shippingcalcNamespace = "paas-content-operations-shippingcalc";
            }
            return shippingcalcNamespace;
        }

        private static String getSurgelevelHashOrBranch() {
            String surgeLevelHashOrBranch = System.getProperty("surge_hash_commit_or_branch", "");
            if (isNull(surgeLevelHashOrBranch) || surgeLevelHashOrBranch.isEmpty() || surgeLevelHashOrBranch.isBlank()) {
                return "";
            }
            return "-" + surgeLevelHashOrBranch;
        }

        public static boolean isPreprod() {
            return Server.PREPROD.name().equalsIgnoreCase(EnvironmentProperties.SERVER);
        }

        public static boolean isProduction() {
            return Server.PRODUCTION.name().equalsIgnoreCase(EnvironmentProperties.SERVER);
        }

        private static String getDomainName(final String url) {
            try {
                final var uri = new URI(url);
                return uri.toURL().getHost();
            } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
                log.debug("Domain parse error: {}", e.getMessage());
            }
            return url;
        }

        private static void forward(final String stfForwardTo) {
            if (isNull(stfForwardTo)) {
                return;
            }
            STAGE = stfForwardTo.replaceAll("s-sb-stf|s-sb-|-\\w+$", "");
            TENANT = stfForwardTo.replaceAll("^.+-", "").replaceAll("^sm", "");
            BASIC_URL = stfForwardTo.contains("s-sb-stf")
                    ? stfForwardTo.replaceAll("s-sb-stf", "stf-").replaceAll("-sbermarket", "") + ".k-stage.sbermarket.tech"
                    : stfForwardTo.replaceAll("s-sb-|-sbermarket", "") + ".k-stage.sbermarket.tech";
            SERVER = Server.CUSTOM.name().toLowerCase();

            if (stfForwardTo.contains("s-sb-stf")) {
                DB_URL = DB_URL.replace("kraken", STAGE);
                DB_PGSQL_URL = DB_PGSQL_URL.replace("kraken", STAGE);
            } else {
                DB_URL = DB_URL.replace("_kraken", "");
                DB_PGSQL_URL = DB_PGSQL_URL.replace("_kraken", "");
            }

            if (stfForwardTo.contains("s-sb-stf")) {
                K8S_NAME_STF_SPACE = K8S_NAME_STF_SPACE.replace("kraken", STAGE);
                K8S_NAME_SHP_SPACE = K8S_NAME_SHP_SPACE.replace("kraken", STAGE);
            } else {
                K8S_NAME_STF_SPACE = K8S_NAME_STF_SPACE.replace("stfkraken", STAGE);
                K8S_NAME_SHP_SPACE = K8S_NAME_SHP_SPACE.replace("shpkraken", STAGE);
            }
        }

        private static void shopperUrl(final String customBasicUrl, final String customShopperUrl) {
            if (nonNull(customShopperUrl) && !customShopperUrl.isBlank()) {
                SHOPPER_URL = getDomainName(customShopperUrl);
            } else if (nonNull(customBasicUrl) && customBasicUrl.startsWith("stf-")) {
                SHOPPER_URL = "shp" + customBasicUrl.substring(3);
            } else SHOPPER_URL = "shp-0.k-stage.sbermarket.tech";
        }

        private static void printProperty() {
            log.debug("Кастомные данные при ручном запуске на стейджах");
            log.debug("BASIC_URL: {}", BASIC_URL);
            log.debug("Server: {}", SERVER);
            log.debug("Stage: {}", STAGE);
            log.debug("DB_URL: {}", DB_URL);
            log.debug("DB_PGSQL_URL: {}", DB_PGSQL_URL);
            log.debug("K8S_NAME_STF_SPACE: {}", K8S_NAME_STF_SPACE);
            log.debug("K8S_NAME_SHP_SPACE: {}", K8S_NAME_SHP_SPACE);
            log.debug("SHOPPER_URL: " + SHOPPER_URL);
        }
    }

    public static class BFF {
        public static boolean TRUE = System.getProperty("bff") != null && System.getProperty("bff").equals("true");
        public static String FORWARD = System.getProperty("bff_forward") == null ? "default" : System.getProperty("bff_forward");
    }
}
