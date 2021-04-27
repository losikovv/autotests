package ru.instamart.ui.common.pagesdata;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.core.util.Crypt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static ru.instamart.core.settings.Config.DEFAULT_ENVIRONMENT;

@Slf4j
public enum EnvironmentData {

    INSTANCE;

    private static final String ENV_DIR = Objects.requireNonNull(EnvironmentData.class.getClassLoader().getResource("environment_configs/")).getPath();

    private String tenant;
    private String server;
    private String basicUrl;
    private String httpAuth;
    private String shopperUrl;
    private String defaultSid;
    private String defaultShopperSid;
    private String protocol;

    public void load() {
        final String file = String.format(ENV_DIR + "%s.properties", DEFAULT_ENVIRONMENT);
        final Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));

            this.tenant = properties.getProperty("tenant");
            this.server = System.getProperty("url_stf", properties.getProperty("server"));
            this.basicUrl = System.getProperty("url_stf", properties.getProperty("basicUrl"));
            this.httpAuth = Crypt.INSTANCE.decrypt(properties.getProperty("httpAuth"));
            this.defaultSid = properties.getProperty("defaultSid");
            this.defaultShopperSid = properties.getProperty("defaultShopperSid");
            this.protocol = System.getProperty("protocol", "https");

            String customBasicUrl = System.getProperty("url_stf");
            if (customBasicUrl != null && customBasicUrl.startsWith("stf")) {
                this.shopperUrl = System.getProperty("url_shp", "shp" + customBasicUrl.substring(3));
            } else {
                this.shopperUrl = System.getProperty("url_shp", properties.getProperty("shopperUrl"));
            }

            log.info("Basic URL: " + this.basicUrl);
            log.info("Shopper URL: " + this.shopperUrl);
        } catch (FileNotFoundException e) {
            log.error("File {} not found", file);
        } catch (IOException e) {
            log.error("Cant' load or parse file", e);
        }
    }

    public String getTenant() {
        return tenant;
    }

    public String getServer() {
        return server;
    }

    public String getName() {
        return tenant + "-" + server;
    }

    public String getBasicUrl() {
        return protocol + "://" + basicUrl + "/";
    }

    public String getBasicUrlWithHttpAuth() {
        return protocol + "://" + httpAuth + basicUrl + "/";
    }

    public String getAdminUrl() {
        return protocol + "://" + basicUrl + "/admin/";
    }

    public String getAdminUrlWithHttpAuth() {
        return protocol + "://" + httpAuth + basicUrl + "/admin/";
    }

    public String getShopperUrl() {
        return protocol + "://" + shopperUrl + "/";
    }

    public int getDefaultSid() {
        return Integer.parseInt(defaultSid);
    }

    public int getDefaultShopperSid() {
        return Integer.parseInt(defaultShopperSid);
    }
}
