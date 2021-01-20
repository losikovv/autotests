package instamart.core.settings;

import instamart.core.testdata.Environments;
import instamart.core.testdata.ui.Tenants;
import instamart.core.util.ConfigParser;
import instamart.core.util.FileUtils;
import org.openqa.selenium.remote.BrowserType;

public final class Config {

    private static final String CONFIG_DIR = FileUtils.getResourceDir("config/");
    /** Directories block */
    private static final String CORE_CONFIG_FILE = "core.properties";

    /** Variables block */
    public static boolean IS_KRAKEN_REVEALEN;
    //Core
    public static String DEFAULT_BROWSER;
    public static String DEFAULT_ENVIRONMENT;
    public static String DEFAULT_RETAILER;

    public static int BASIC_TIMEOUT;
    public static int WAITING_TIMEOUT;
    public static boolean DOCKER;
    public static boolean VIDEO;
    public static boolean FULL_SCREEN_MODE;
    public static boolean DO_CLEANUP_AFTER_TEST_RUN;
    public static boolean DO_CLEANUP_BEFORE_TEST_RUN;
    //TODO: Подумать о том что бы избавиться и перейти на валидацию схемы
    public static final boolean REST_IGNORE_PROPERTIES = true;

    public static String QASE_API_TOKEN;

    public static void load() {
        final ConfigParser coreSettings = new ConfigParser(CONFIG_DIR+CORE_CONFIG_FILE);

        // Переменная для обозначения запущен кракен или нет
        IS_KRAKEN_REVEALEN = coreSettings.getBoolean("isKrakenRevealen", false);

        // Если в core.properties нет переменной defaultBrowser, то будет браться значение из параметра запуска -Pbrowser
        // если и там нет, то дефолтное BrowserType.CHROME
        DEFAULT_BROWSER = coreSettings.getString("defaultBrowser", System.getProperty("browser", BrowserType.CHROME));
        // Если в core.properties нет переменной defaultEnvironment, то будет браться значение из параметра запуска -Penv
        // если и там нет, то дефолтное Environments.sbermarket.preprod()
        DEFAULT_ENVIRONMENT = coreSettings.getString("defaultEnvironment", System.getProperty("env", Environments.sbermarket.preprod()));
        DEFAULT_RETAILER = coreSettings.getString("defaultRetailer", Tenants.metro().getAlias());

        BASIC_TIMEOUT = coreSettings.getInt("basicTimeout", 2);
        WAITING_TIMEOUT = coreSettings.getInt("waitingTimeout", 30);

        // Переключатель включает прогон в селенойде
        DOCKER = coreSettings.getBoolean("docker", false);
        // Включает запись видео
        VIDEO = coreSettings.getBoolean("video", false);

        // Запуск тестов на полном экране
        FULL_SCREEN_MODE = coreSettings.getBoolean("fullScreenMode", false);
        // Удаление всех сущностей после теста
        DO_CLEANUP_AFTER_TEST_RUN = coreSettings.getBoolean("doCleanupAfterTestRun", true);
        // Все существующие инстансы браузера связанные с selenium будут удалены, рабочий браузер не убивается
        DO_CLEANUP_BEFORE_TEST_RUN = coreSettings.getBoolean("doCleanupBeforeTestRun", true);

        QASE_API_TOKEN = coreSettings.getString("qaseApiToken", "");
    }
}
