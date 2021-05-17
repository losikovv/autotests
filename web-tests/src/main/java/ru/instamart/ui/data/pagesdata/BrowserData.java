package ru.instamart.ui.data.pagesdata;

public class BrowserData {
    private final String browser;
    private final String os;
    private final String version;

    public BrowserData(String browser, String os, String version) {
        this.browser = browser;
        this.os = os;
        this.version = version;
    }

    public String getBrowserDriver() {
        return browser;
    }

    public String getOs() {
        return os;
    }

    public String getVersion() {
        return version;
    }

}
