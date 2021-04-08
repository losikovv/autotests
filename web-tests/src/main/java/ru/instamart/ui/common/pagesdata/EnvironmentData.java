package ru.instamart.ui.common.pagesdata;

import ru.instamart.core.util.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static ru.instamart.core.settings.Config.DEFAULT_ENVIRONMENT;

public enum EnvironmentData {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentData.class);

    private static final String ENV_DIR = Objects.requireNonNull(EnvironmentData.class.getClassLoader().getResource("environment_configs/")).getPath();

    private String tenant;
    private String server;
    private String basicUrl;
    private String adminUrl;
    private String httpAuth;
    private String shopperUrl;
    private String defaultSid;
    private String defaultShopperSid;
    private String protocol;

    public void load() {
        final String file = String.format(ENV_DIR+"%s.properties", DEFAULT_ENVIRONMENT);
        final Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));

            this.tenant = properties.getProperty("tenant");
            this.server = properties.getProperty("server");
            this.basicUrl = properties.getProperty("basicUrl");
            this.adminUrl = properties.getProperty("adminUrl");
            this.httpAuth = Crypt.INSTANCE.decrypt(properties.getProperty("httpAuth"));
            this.shopperUrl = properties.getProperty("shopperUrl");
            this.defaultSid = properties.getProperty("defaultSid");
            this.defaultShopperSid = properties.getProperty("defaultShopperSid");
            this.protocol = System.getProperty("protocol", "https");
        } catch (FileNotFoundException e) {
            logger.error("File {} not found", file);
        } catch (IOException e) {
            logger.error("Cant' load or parse file", e);
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
        return protocol + "://" + adminUrl + "/";
    }

    public String getAdminUrlWithHttpAuth() {
        return protocol + "://" + httpAuth + adminUrl + "/";
    }

    public String getShopperUrl() {return protocol + "://" + shopperUrl + "/";}

    public int getDefaultSid() {
        return Integer.parseInt(defaultSid);
    }

    public int getDefaultShopperSid() {
        return Integer.parseInt(defaultShopperSid);
    }
}
