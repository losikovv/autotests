package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

public final class BrowserProperties {

    public static final String NAME = "browser";

    @Config(configName = NAME, fieldName = "defaultBrowser", defaultValue = "chrome_remote", args = "browser")
    public static String BROWSER;
    @Config(configName = NAME, fieldName = "browserVersion", defaultValue = "latest", args = "version")
    public static String BROWSER_VERSION;
    @Config(configName = NAME, fieldName = "browserLocalVersion", defaultValue = "latest")
    public static String BROWSER_LOCAL_VERSION;

    @Config(configName = NAME, fieldName = "remoteUrl", defaultValue = "remoteUrl")
    public static String REMOTE_URL;
    @Config(configName = NAME, fieldName = "video", defaultValue = "false")
    public static boolean VIDEO;
    @Config(configName = NAME, fieldName = "vnc", defaultValue = "false")
    public static boolean VNC;
    @Config(configName = NAME, fieldName = "fullScreenMode", defaultValue = "true")
    public static boolean FULL_SCREEN_MODE;

    @Config(configName = NAME, fieldName = "enableProxy", defaultValue = "false")
    public static boolean ENABLE_PROXY;
    @Config(configName = NAME, fieldName = "ignoreSsl", defaultValue = "true")
    public static boolean IGNORE_SSL;

    @Config(configName = NAME, fieldName = "enableProfile", defaultValue = "false")
    public static boolean ENABLE_PROFILE;
    @Config(configName = NAME, fieldName = "profilePath", defaultValue = "")
    public static String PROFILE_PATH;
    @Config(configName = NAME, fieldName = "profileName", defaultValue = "Default")
    public static String PROFILE_NAME;
}
