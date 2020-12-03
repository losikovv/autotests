package instamart.core.common;

import instamart.api.helpers.ApiHelper;
import instamart.api.helpers.ApiV2Helper;
import instamart.api.helpers.ShopperApiHelper;
import instamart.core.helpers.*;
import instamart.core.settings.Config;
import instamart.core.testdata.Users;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.BrowserData;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.SessionData;
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
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static instamart.core.settings.Config.CoreSettings.*;
import static instamart.core.settings.Config.TestVariables.TestParams.testMark;
import static org.testng.Assert.fail;

public class AppManager {

    public final static String testrunId = Generate.testRunId();

    public WebDriver driver;
    static public EnvironmentData environment;
    static public BrowserData browserData;
    private String browser;
    public static SessionData session;

    private BrowseHelper browseHelper;
    private PerformHelper performHelper;
    private ReachHelper reachHelper;
    private DetectionHelper detectionHelper;
    private GrabHelper grabHelper;
    private Shop shopHelper;
    private User userHelper;
    private Checkout checkoutHelper;
    private Administration administrationHelper;
    private CleanupHelper cleanupHelper;
    private WaitingHelper waitingHelper;
    private ApiV2Helper apiV2Helper;
    private ShopperApiHelper shopperApiHelper;
    private ApiHelper apiHelper;

    private StringBuffer verificationErrors = new StringBuffer();
    private static final Logger LOGGER = LoggerFactory.getLogger(AppManager.class);

    public AppManager(String browser) {
        this.browser = browser;
    }

    public AppManager() {
    }

    public void rise() throws IOException {
        setLogs();
        setEnvironment();
        initTestSession();
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        getBasicUrl();
    }

    public void getBasicUrl() {
        driver.get(environment.getBasicUrlWithHttpAuth());
    }

    private void setEnvironment() throws IOException {
        Properties properties = new Properties();
        String env = System.getProperty("env", Config.CoreSettings.defaultEnvironment);
        properties.load(
                new FileReader(
                        new File(String.format("src/test/resources/environment_configs/%s.properties", env))));
        environment = new EnvironmentData(
                properties.getProperty("tenant"),
                properties.getProperty("server"),
                properties.getProperty("basicUrl"),
                properties.getProperty("adminUrl"),
                properties.getProperty("httpAuth"),
                properties.getProperty("shopperUrl"),
                properties.getProperty("defaultSid"),
                properties.getProperty("defaultShopperSid")
        );
    }

    public void riseRest() throws IOException {
        setLogs();
        initTestSession();
        setEnvironment();
        initRestHelpers();
        revealKraken();
    }

    public void setLogs() {
        if (log) {
            SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        }
    }

    private void initTestSession() {
        if (multiSessionMode) {
            session = new SessionData(
                    testrunId, Generate.testCredentials("admin"), Generate.testCredentials("user"),
                    "shipments?search%5Blast_name%5D=" + testrunId + "&search%5Bstate%5D%5B%5D=ready",
                    "users?q%5Bemail_cont%5D=" + testrunId + "-" + testMark
            );
        } else {
            session = new SessionData(
                    testrunId, Users.superadmin(), Users.superuser(),
                    "shipments?search%5Bfirst_name%5D=" + testMark + "&search%5Bstate%5D%5B%5D=ready",
                    "users?q%5Bemail_cont%5D=" + testrunId + "-" + testMark
            );
        }
    }

    private void initDriver() {
        if (docker) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(browser); // Chrome, FF, Opera
            caps.setCapability("enableVNC", true);
            caps.setCapability("enableVideo", video);
            caps.setCapability("timeZone", "Europe/Moscow");
            try {
                driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), caps);
                getCapabilities(driver);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            switch (browser) {
                case BrowserType.FIREFOX:
                    if(doCleanupBeforeTestRun){
                        cleanProcessByName(BrowserType.FIREFOX);
                    }
                    //System.setProperty("webdriver.gecko.driver", "/Users/tinwelen/Documents/GitHub/automag/web-tests/geckodriver");
                    driver = new FirefoxDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.CHROME:
                    if(doCleanupBeforeTestRun){
                        cleanProcessByName(BrowserType.CHROME);
                    }
                    //ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
                    //int port = chromeDriverService.getUrl().getPort();
                    ChromeOptions options = new ChromeOptions();
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
                    if(doCleanupBeforeTestRun){
                        cleanProcessByName(BrowserType.SAFARI);
                    }
                    driver = new SafariDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.IE:
                    if(doCleanupBeforeTestRun){
                        cleanProcessByName(BrowserType.IE);
                    }
                    driver = new InternetExplorerDriver(); // there is no IE driver for mac yet :(
                    getCapabilities(driver);
                    break;
            }
        }
    }

    /**функция получает данные о браузере и тестовой машине */
    private void getCapabilities(WebDriver driver){
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        browserData = new BrowserData(cap.getBrowserName(),
                cap.getPlatform().toString(),cap.getVersion());
    }

    private void initHelpers() {
        browseHelper = new BrowseHelper(driver, environment, this);
        performHelper = new PerformHelper(driver, environment, this);
        reachHelper = new ReachHelper(driver, environment, this);
        detectionHelper = new DetectionHelper(driver, environment, this);
        grabHelper = new GrabHelper(driver, environment,this);
        shopHelper = new Shop(driver, environment, this);
        userHelper = new User(driver, environment, this);
        checkoutHelper = new Checkout(driver, environment, this);
        administrationHelper = new Administration(driver, environment, this);
        cleanupHelper = new CleanupHelper(driver, environment, this);
        waitingHelper = new WaitingHelper(driver, environment, this);
        apiV2Helper = new ApiV2Helper();
        shopperApiHelper = new ShopperApiHelper();
        apiHelper = new ApiHelper();
    }

    private void initRestHelpers() {
        administrationHelper = new Administration(driver, environment, this);
        apiV2Helper = new ApiV2Helper();
        shopperApiHelper = new ShopperApiHelper();
        apiHelper = new ApiHelper();
    }

    private void applyOptions() {
        if (fullScreenMode) {driver.manage().window().fullscreen(); }
        if (basicTimeout > 0) {driver.manage().timeouts().implicitlyWait(basicTimeout, TimeUnit.SECONDS);}
    }

    private void revealKraken() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/test/resources/banner.txt"));
        String line = in.readLine();
        while(line !=null) {
            System.out.println(line);
            line = in.readLine();
        }
        in.close();
        Config.isKrakenRevealen = true; // это вот как раз то самое место где она в true ставиться
        // на данный момент решил вопрос через группы при старте тест сьютов


        System.out.println("\nENVIRONMENT: " + environment.getName() + " ( " + environment.getBasicUrl() + " )");

        if(multiSessionMode) {
            System.out.println("\nTEST RUN ID: " + session.id);
        } else {
            System.out.println("\nTEST RUN ID: " + session.id + " (SOLO MODE)");
        }

        System.out.println("ADMIN: " + session.admin.getLogin() + " / " + session.admin.getPassword());
        System.out.println("USER: " + session.user.getLogin() + " / " + session.user.getPassword() + "\n");
    }

    public void stop() {
        if(driver!=null)driver.quit();

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    /**
     * Этот метод чистииит окружение после тестов и перед ними
     * удаляет процессы с запущенными браузерами, если используется докер, то скипается
     * Удаляет только инстансы созданные селениумом, нормальный браузер не убивается
     * Метод отлажен на MACOS и 100% не будет работать на линухе, или винде
     * @param name имя браузера
     */
     public void cleanProcessByName(String name){
         if(name.equals("chrome")) {
             String cdriver = "chromedriver";
             String browserInst = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
             Long amount = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(cdriver))
                             .orElse(false)).count();
             Long amountBro = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(browserInst))
                             .orElse(false)).count();
             //Если есть запущенные chromedriver, то убиваем их
             if(amount>0){
                 processKiller(cdriver);
             }
             //если есть запущенные Инстансы Селениума, то и их киляем
             if(amountBro>0){
                 processKiller("test-type=webdriver");
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
             System.out.println(String.format("\nПроцесс с PID: %1$s завершен принудительно",name));
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    /**
     * Метод удаляет процесс по PID пока не нужно, но может пригодится
     */
     private void processKillerByPID(String pid){
         String command = "kill -9 "+ pid;
         try {
             Runtime.getRuntime().exec(command);
             System.out.println(String.format("\nПроцесс с PID: %1$s завершен принудительно",pid));
         } catch (IOException e) {
             e.printStackTrace();
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
             if(arg.contains("webdriver")){
                 pid=ProcessHandle
                         .allProcesses()
                         .filter(p -> p.info().commandLine().map(c -> c.contains(browserName))
                                 .orElse(false)).findFirst().get().pid();
                 System.out.println(String.format("\nНайден старый инстанс браузера запущенный Selenium с PID: %1$s",pid));
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
    public CleanupHelper cleanup() { return cleanupHelper; }
    public WaitingHelper await() { return waitingHelper; }
    public ApiV2Helper apiV2() { return apiV2Helper; }
    public ShopperApiHelper shopperApi() { return shopperApiHelper; }
    public ApiHelper api() { return apiHelper; }
}
