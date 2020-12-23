package instamart.core.common;

import instamart.api.helpers.InstamartApiHelper;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.BrowserData;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.helpers.*;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static instamart.core.settings.Config.*;

public class AppManager {

    private static final Logger log = LoggerFactory.getLogger(AppManager.class);

    public final static String testrunId = Generate.testRunId();

    public WebDriver driver;
    static public BrowserData browserData;

    private BrowseHelper browseHelper;
    private PerformHelper performHelper;
    private ReachHelper reachHelper;
    private DetectionHelper detectionHelper;
    private GrabHelper grabHelper;
    private Shop shopHelper;
    private User userHelper;
    private Checkout checkoutHelper;
    private Administration administrationHelper;
    private WaitingHelper waitingHelper;
    private InstamartApiHelper instamartApiHelper;

    public void rise() {
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        getBasicUrl();
    }

    public void getBasicUrl() {
        driver.get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    private void initDriver() {
        if (DOCKER) {
            final DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(DEFAULT_BROWSER); // Chrome, FF, Opera
            caps.setCapability("enableVNC", true);
            caps.setCapability("enableVideo", VIDEO);
            caps.setCapability("timeZone", "Europe/Moscow");
            try {
                //TODO: Move URL to Config
                driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), caps);
                getCapabilities(driver);
            } catch (MalformedURLException e) {
                log.error("[initDriver] failed to create remote web driver");
                throw new RuntimeException(e);
            }
        } else {
            switch (DEFAULT_BROWSER) {
                case BrowserType.FIREFOX:
                    if (DO_CLEANUP_BEFORE_TEST_RUN) {
                        cleanProcessByName(BrowserType.FIREFOX);
                    }
                    //System.setProperty("webdriver.gecko.driver", "/Users/tinwelen/Documents/GitHub/automag/web-tests/geckodriver");
                    driver = new FirefoxDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.CHROME:
                    if (DO_CLEANUP_BEFORE_TEST_RUN) {
                        cleanProcessByName(BrowserType.CHROME);
                    }
                    //ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
                    //int port = chromeDriverService.getUrl().getPort();
                    final ChromeOptions options = new ChromeOptions();
                    if ((System.getProperty("os.name")).contains("Mac")) {
                        System.setProperty("webdriver.chrome.driver", "WebDriverMac/chromedriver");
                    } else {
                        options.addArguments("--headless");
                        options.addArguments("--disable-extensions");
                        options.addArguments("--no-sandbox");
                        options.addArguments("--disable-dev-shm-usage");
                        System.setProperty("webdriver.chrome.driver", "WebDriverLinux/chromedriver");
                    }
                    driver = new ChromeDriver(options);
                    getCapabilities(driver);
                    //System.out.println(String.format("\nChromedriver запущен на порту: %1$s",port));
                    break;
                case BrowserType.SAFARI:
                    if (DO_CLEANUP_BEFORE_TEST_RUN) {
                        cleanProcessByName(BrowserType.SAFARI);
                    }
                    driver = new SafariDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.IE:
                    if (DO_CLEANUP_BEFORE_TEST_RUN) {
                        cleanProcessByName(BrowserType.IE);
                    }
                    driver = new InternetExplorerDriver(); // there is no IE driver for mac yet :(
                    getCapabilities(driver);
                    break;
            }
        }
    }

    /**функция получает данные о браузере и тестовой машине */
    private void getCapabilities(final WebDriver driver){
        final Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        browserData = new BrowserData(cap.getBrowserName(),
                cap.getPlatform().toString(),cap.getVersion());
    }

    private void initHelpers() {
        this.browseHelper = new BrowseHelper(driver,  this);
        this.performHelper = new PerformHelper(driver, this);
        this.reachHelper = new ReachHelper(driver, this);
        this.detectionHelper = new DetectionHelper(driver, this);
        this.grabHelper = new GrabHelper(driver,this);
        this.shopHelper = new Shop(driver, this);
        this.userHelper = new User(driver, this);
        this.checkoutHelper = new Checkout(driver, this);
        this.administrationHelper = new Administration(driver, this);
        this.waitingHelper = new WaitingHelper(driver, this);
        this.instamartApiHelper = new InstamartApiHelper();
    }

    private void applyOptions() {
        if (FULL_SCREEN_MODE) {driver.manage().window().fullscreen(); }
        if (BASIC_TIMEOUT > 0) {driver.manage().timeouts().implicitlyWait(BASIC_TIMEOUT, TimeUnit.SECONDS);}
    }

    private void revealKraken() {
        IS_KRAKEN_REVEALEN = true; // это вот как раз то самое место где она в true ставиться
        // на данный момент решил вопрос через группы при старте тест сьютов
        log.info("ENVIRONMENT: {} ({})", EnvironmentData.INSTANCE.getName(), EnvironmentData.INSTANCE.getBasicUrl());
        log.info("TEST RUN ID: {}", testrunId);
        log.info("ADMIN: {} / {}", UserManager.getDefaultAdmin().getLogin(), UserManager.getDefaultAdmin().getPassword());
        log.info("USER: {} / {}", UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Этот метод чистииит окружение после тестов и перед ними
     * удаляет процессы с запущенными браузерами, если используется докер, то скипается
     * Удаляет только инстансы созданные селениумом, нормальный браузер не убивается
     * Метод отлажен на MACOS и 100% не будет работать на линухе, или винде
     * @param name имя браузера
     */
     public void cleanProcessByName(final String name){
         if (name.equals("chrome")) {
             final String cdriver = "chromedriver";
             final String browserInst = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
             final long amount = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(cdriver))
                             .orElse(false)).count();
             final long amountBro = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(browserInst))
                             .orElse(false)).count();
             //Если есть запущенные chromedriver, то убиваем их
             if (amount > 0) {
                 processKiller(cdriver);
             }
             //если есть запущенные Инстансы Селениума, то и их киляем
             if (amountBro > 0) {
                 processKiller("test-type=webdriver");
             }
         }
         if (name.equals("firefox")) {
             final String gdriver = "geckodriver";
             final String browserInst = "Firefox";
             final long amount = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(gdriver))
                             .orElse(false)).count();
             final long amountBro = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(browserInst))
                             .orElse(false)).count();
             //Если есть запущенные chromedriver, то убиваем их
             if (amount > 0) {
                 processKiller(gdriver);
             }
             //если есть запущенные Инстансы Селениума, то и их киляем
             if (amountBro > 0) {
                 processKiller("Firefox");
             }
         }
     }

     private void processKiller(String name){
         try {
             String[] COMPOSED_COMMAND = {
                     "/bin/bash",
                     "-c",
                     "/usr/bin/pgrep -f \""+name+"\" | xargs kill",};
            // Process p = Runtime.getRuntime().exec(COMPOSED_COMMAND);
             Runtime.getRuntime().exec(COMPOSED_COMMAND);
             log.warn("Процесс с PID: {} завершен принудительно", name);
         } catch (IOException e) {
             log.error("Failed when try to kill process {}", name);
         }
     }
    /**
     * Метод удаляет процесс по PID пока не нужно, но может пригодится
     */
     private void processKillerByPID(String pid){
         String command = "kill -9 "+ pid;
         try {
             Runtime.getRuntime().exec(command);
             log.warn("Процесс с PID: {} завершен принудительно", pid);
         } catch (IOException e) {
             log.error("Failed when try to kill process {}", pid);
         }
     }

    /**
     * Метод поиска PID у процесса
     * @param args массив объектов ProcessHandle
     * @param browserName Имя объекта для поиска
     * @return возвращает PID
     */
     private Long pidFinder(Object [] args, String browserName){
         Long pid;
         for (String arg:(String[]) args[0]){
             if (arg.contains("webdriver")){
                 pid=ProcessHandle
                         .allProcesses()
                         .filter(p -> p.info().commandLine().map(c -> c.contains(browserName))
                                 .orElse(false)).findFirst().get().pid();
                 log.warn("Найден старый инстанс браузера запущенный Selenium с PID: {}", pid);
                 return pid;
             }
         }
         return null;
     }

    public BrowseHelper get() { return browseHelper; }
    public PerformHelper perform() { return performHelper; }
    public ReachHelper reach() { return reachHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public GrabHelper grab() { return grabHelper; }
    public Shop shopping() { return shopHelper; }
    public User user() { return userHelper; }
    public Checkout checkout() { return checkoutHelper; }
    public Administration admin() { return administrationHelper; }
    public WaitingHelper await() { return waitingHelper; }
    public InstamartApiHelper apiV2() { return instamartApiHelper; }
}
