package instamart.core.common;

import instamart.api.helpers.InstamartApiHelper;
import instamart.core.helpers.*;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static instamart.core.settings.Config.*;
import static instamart.core.testdata.TestVariables.TestParams.testMark;
import static org.testng.Assert.fail;

public class AppManager {

    public final static String testrunId = Generate.testRunId();

    public WebDriver driver;
    static public BrowserData browserData;
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
    private InstamartApiHelper instamartApiHelper;

    //TODO: Он не наполняется, только занимает место в памяти instamart/core/common/AppManager.java:193
    private StringBuffer verificationErrors = new StringBuffer();

    public void rise() {
        initTestSession();
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        getBasicUrl();
    }

    public void getBasicUrl() {
        driver.get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    private void initTestSession() {
        if (MULTI_SESSION_MODE) {
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
        if (DOCKER) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(DEFAULT_BROWSER); // Chrome, FF, Opera
            caps.setCapability("enableVNC", true);
            caps.setCapability("enableVideo", VIDEO);
            caps.setCapability("timeZone", "Europe/Moscow");
            try {
                driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), caps);
                getCapabilities(driver);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            switch (DEFAULT_BROWSER) {
                case BrowserType.FIREFOX:
                    if(DO_CLEANUP_BEFORE_TEST_RUN){
                        cleanProcessByName(BrowserType.FIREFOX);
                    }
                    //System.setProperty("webdriver.gecko.driver", "/Users/tinwelen/Documents/GitHub/automag/web-tests/geckodriver");
                    driver = new FirefoxDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.CHROME:
                    if(DO_CLEANUP_BEFORE_TEST_RUN){
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
                    if(DO_CLEANUP_BEFORE_TEST_RUN){
                        cleanProcessByName(BrowserType.SAFARI);
                    }
                    driver = new SafariDriver();
                    getCapabilities(driver);
                    break;
                case BrowserType.IE:
                    if(DO_CLEANUP_BEFORE_TEST_RUN){
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
        browseHelper = new BrowseHelper(driver,  this);
        performHelper = new PerformHelper(driver, this);
        reachHelper = new ReachHelper(driver, this);
        detectionHelper = new DetectionHelper(driver, this);
        grabHelper = new GrabHelper(driver,this);
        shopHelper = new Shop(driver, this);
        userHelper = new User(driver, this);
        checkoutHelper = new Checkout(driver, this);
        administrationHelper = new Administration(driver, this);
        cleanupHelper = new CleanupHelper(driver, this);
        waitingHelper = new WaitingHelper(driver, this);
        instamartApiHelper = new InstamartApiHelper();
    }

    private void applyOptions() {
        if (FULL_SCREEN_MODE) {driver.manage().window().fullscreen(); }
        if (BASIC_TIMEOUT > 0) {driver.manage().timeouts().implicitlyWait(BASIC_TIMEOUT, TimeUnit.SECONDS);}
    }

    private void revealKraken() {
        IS_KRAKEN_REVEALEN = true; // это вот как раз то самое место где она в true ставиться
        // на данный момент решил вопрос через группы при старте тест сьютов
        System.out.println("\nENVIRONMENT: " + EnvironmentData.INSTANCE.getName() + " ( " + EnvironmentData.INSTANCE.getBasicUrl() + " )");

        if (MULTI_SESSION_MODE) {
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
             long amount = ProcessHandle.allProcesses()
                     .filter(p -> p.info().commandLine().map(c -> c.contains(cdriver))
                             .orElse(false)).count();
             long amountBro = ProcessHandle.allProcesses()
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
             System.out.printf("\nПроцесс с PID: %1$s завершен принудительно%n", name);
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
             System.out.printf("\nПроцесс с PID: %1$s завершен принудительно%n", pid);
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
                 System.out.printf("\nНайден старый инстанс браузера запущенный Selenium с PID: %1$s%n", pid);
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
    public InstamartApiHelper apiV2() { return instamartApiHelper; }
}
