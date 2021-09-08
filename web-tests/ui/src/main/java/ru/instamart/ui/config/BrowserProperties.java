package ru.instamart.ui.config;

import ru.instamart.utils.config.Config;

public final class BrowserProperties {

    public static final String NAME = "browser";

    @Config(configName = NAME, fieldName = "defaultBrowser", defaultValue = "chrome_remote", args = "browser")
    public static String BROWSER;
    @Config(configName = NAME, fieldName = "browserVersion", defaultValue = "latest", args = "version")
    public static String BROWSER_VERSION;

    @Config(configName = NAME, fieldName = "remoteUrl", defaultValue = "remoteUrl")
    public static String REMOTE_URL;
    @Config(configName = NAME, fieldName = "video", defaultValue = "false")
    public static boolean VIDEO;
    @Config(configName = NAME, fieldName = "vnc", defaultValue = "false")
    public static boolean VNC;
    @Config(configName = NAME, fieldName = "fullScreenMode", defaultValue = "true")
    public static boolean FULL_SCREEN_MODE;
}
