package instamart.ui.common.pagesdata;

public class EnvironmentData {
    private String tenant;
    private String server;
    private String basicUrl;
    private String adminUrl;
    private String httpAuth;

    public EnvironmentData(String tenant, String server, String basicUrl, String adminUrl, String httpAuth) {
        this.tenant = tenant;
        this.server = server;
        this.basicUrl = basicUrl;
        this.adminUrl = adminUrl;
        this.httpAuth = httpAuth;
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
}
