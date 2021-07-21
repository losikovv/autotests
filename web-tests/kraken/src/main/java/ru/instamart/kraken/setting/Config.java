package ru.instamart.kraken.setting;

import ru.instamart.kraken.testdata.Environments;
import ru.instamart.kraken.testdata.Tenants;

public final class Config {

    private static final String CONFIG_DIR = "../data/config/";
    /** Directories block */
    private static final String CORE_CONFIG_FILE = "core.properties";

    //Core
    public static String DEFAULT_BROWSER;
    public static String BROWSER_VERSION;
    public static String DEFAULT_ENVIRONMENT;
    public static String DEFAULT_RETAILER;
    public static String DEFAULT_SMS;

    public static int BASIC_TIMEOUT;
    public static int WAITING_TIMEOUT;
    public static long POLLING_INTERVAL;
    public static String REMOTE_URL;
    public static boolean VIDEO;
    public static boolean VNC;
    public static boolean FULL_SCREEN_MODE;
    public static final boolean REST_IGNORE_PROPERTIES = true;

    public static boolean USE_JS_CLICK;
    public static boolean USE_JS_FILL;

    public static String QASE_API_TOKEN;

    public static void load() {
        final ConfigParser coreSettings = new ConfigParser(CONFIG_DIR+CORE_CONFIG_FILE);

        // Если в core.properties нет переменной defaultBrowser, то будет браться значение из параметра запуска -Pbrowser
        // если и там нет, то дефолтное BrowserType.CHROME
        DEFAULT_BROWSER = coreSettings.getString("defaultBrowser", System.getProperty("browser", "chrome"));
        //DEFAULT_BROWSER = coreSettings.getString("defaultBrowser", System.getProperty("browser", "chrome_remote"));
        //это как напоминалка, нужно просто сделать переключатель, ибо сейчас очень сложно
        //BROWSER_VERSION = coreSettings.getString("browserVersion", System.getProperty("version", "84"));
        BROWSER_VERSION = coreSettings.getString("browserVersion", System.getProperty("version", "latest"));
        // Если в core.properties нет переменной defaultEnvironment, то будет браться значение из параметра запуска -Penv
        // если и там нет, то дефолтное Environments.sbermarket.preprod()
        DEFAULT_ENVIRONMENT = coreSettings.getString("defaultEnvironment", System.getProperty("env", Environments.sbermarket.preprod()));
        DEFAULT_RETAILER = coreSettings.getString("defaultRetailer", Tenants.METRO.getAlias());

        BASIC_TIMEOUT = coreSettings.getInt("basicTimeout", 2);
        WAITING_TIMEOUT = coreSettings.getInt("waitingTimeout", 30);
        POLLING_INTERVAL = coreSettings.getLong("pollingInterval", 250);
        DEFAULT_SMS = coreSettings.getString("defaultSms","111111");

        REMOTE_URL = coreSettings.getString("remoteUrl", "http://localhost:4444/wd/hub");
        // Включает запись видео
        VIDEO = coreSettings.getBoolean("video", false);
        VNC = coreSettings.getBoolean("vnc", false);

        // Запуск тестов на полном экране
        FULL_SCREEN_MODE = coreSettings.getBoolean("fullScreenMode", false);

        USE_JS_CLICK = coreSettings.getBoolean("useJsClick", false);
        USE_JS_FILL = coreSettings.getBoolean("useJsFill", false);

        QASE_API_TOKEN = coreSettings.getString("qaseApiToken", "");
    }
}
