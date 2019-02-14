package ru.instamart.autotests.models;

public class EnvironmentData {
    private String platform;
    private String environment;
    private String host;
    private String auth;
    private boolean secure;

    public EnvironmentData(String platform, String environment, String host, String httpAuth, boolean secure) {
        this.platform = platform;
        this.environment = environment;
        this.host = host;
        this.auth = httpAuth;
        this.secure = secure;
    }

    public String getPlatform() {
        return platform;
    }

    public String getEnvironment () {
        return environment;
    }

    public String getName() {
        return platform + "-" + environment;
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
