package ru.instamart.autotests.models;

public class EnvironmentData {
    private String name;
    private String host;
    private String httpAuth;
    private String baseURL;
    private String fullBaseUrl;

    public EnvironmentData(String name, String host, String httpAuth) {
        this.name = name;
        this.host = host;
        this.httpAuth = httpAuth;
        this.baseURL = "https://" + host + "/";
        this.fullBaseUrl = "https://" + httpAuth + host + "/";
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getHttpAuth() {
        return httpAuth;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getFullBaseUrl() {
        return fullBaseUrl;
    }
}
