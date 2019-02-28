package ru.instamart.autotests.models;

public class EnvironmentData {
    private String tenant;
    private String server;
    private String host;
    private String auth;
    private boolean secure;

    public EnvironmentData(String tenant, String server, String host, String httpAuth, boolean secure) {
        this.tenant = tenant;
        this.server = server;
        this.host = host;
        this.auth = httpAuth;
        this.secure = secure;
    }

    public String getTenant() {
        return tenant;
    }

    public String getServer() { return server; }

    public String getName() {
        return tenant + "-" + server;
    }

    public String getHost() {
        return host;
    }

    public boolean isSecure() {return secure;}

    public String getBaseURL() {
        if(isSecure()) {return "https://" + host + "/";}
        else {return "http://" + host + "/";}
    }

    public String getFullBaseUrl() {
        if(isSecure()) {return "https://" + auth + host + "/";}
        else {return "http://" + auth + host + "/";}
    }
}
