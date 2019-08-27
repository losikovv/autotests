package ru.instamart.autotests.appmanager.models;

public class EnvironmentData {
    private TenantData tenant;
    private String server;
    private String host;
    private String auth;

    public EnvironmentData(TenantData tenant, String server, String host, String httpAuth) {
        this.tenant = tenant;
        this.server = server;
        this.host = host;
        this.auth = httpAuth;
    }

    public TenantData getTenant() {
        return tenant;
    }

    public String getServer() { return server; }

    public String getName() {
        return tenant.getAlias() + "-" + server;
    }

    public String getHost() {
        return host;
    }

    public String getBaseURL(boolean httpAuth) {
        if(httpAuth) return "https://" + auth + host + "/";
        else return "https://" + host + "/";
    }

    public String getAdminURL() {
        if(server.equalsIgnoreCase("production")) return "https://instamart.ru/admin/";
        else return "https://staging.instamart.ru/admin/";
    }
}
