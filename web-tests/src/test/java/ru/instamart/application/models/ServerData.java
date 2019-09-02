package ru.instamart.application.models;

public class ServerData {
    private TenantData tenant;
    private String environment;
    private String host;
    private String auth;

    public ServerData(TenantData tenant, String environment, String host, String httpAuth) {
        this.tenant = tenant;
        this.environment = environment;
        this.host = host;
        this.auth = httpAuth;
    }

    public TenantData getTenant() {
        return tenant;
    }

    public String getEnvironment() { return environment; }

    public String getName() {
        return tenant.getAlias() + "-" + environment;
    }

    public String getHost() {
        return host;
    }

    public String getBaseURL(boolean httpAuth) {
        if(httpAuth && !auth.equals("")) return "https://" + auth + "@" + host + "/";
        else return "https://" + host + "/";
    }

    public String getAdminURL() {
        return getBaseURL(true) + "admin/";
    }
}
