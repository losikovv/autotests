package instamart.ui.common.pagesdata;

public class EnvironmentData {
    private final String tenant;
    private final String server;
    private final String basicUrl;
    private final String adminUrl;
    private final String httpAuth;
    private final String shopperUrl;

    public EnvironmentData(String tenant, String server, String basicUrl,
                           String adminUrl, String httpAuth, String shopperUrl) {
        this.tenant = tenant;
        this.server = server;
        this.basicUrl = basicUrl;
        this.adminUrl = adminUrl;
        this.httpAuth = httpAuth;
        this.shopperUrl = shopperUrl;
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
        return "https://" + basicUrl + "/";
    }

    public String getBasicUrlWithHttpAuth() {
        return "https://" + httpAuth + basicUrl + "/";
    }

    public String getAdminUrl() {
        return "https://" + adminUrl + "/";
    }

    public String getAdminUrlWithHttpAuth() {
        return "https://" + httpAuth + adminUrl + "/";
    }

    public String getShopperUrl() {return "https://" + shopperUrl + "/";}
}
