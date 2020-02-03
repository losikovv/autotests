package ru.instamart.application;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.platform.helpers.*;
import ru.instamart.application.platform.modules.Checkout;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.SessionData;
import ru.instamart.application.rest.RestHelper;
import ru.instamart.testdata.generate;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;
import static ru.instamart.application.Config.CoreSettings.basicTimeout;
import static ru.instamart.application.Config.CoreSettings.fullScreenMode;
import static ru.instamart.application.Config.CoreSettings.multiSessionMode;
import static ru.instamart.application.Config.TestVariables.TestParams.testMark;

public class AppManager {

    public final static String testrunId = generate.testRunId();

    protected WebDriver driver;
    static public EnvironmentData environment;
    private String browser;
    public static SessionData session;

    private BrowseHelper browseHelper;
    private PerformHelper performHelper;
    private ReachHelper reachHelper;
    private DetectionHelper detectionHelper;
    private GrabHelper grabHelper;
    private DropHelper dropHelper;
    private Shop shopHelper;
    private User userHelper;
    private Checkout checkoutHelper;
    private Administration administrationHelper;
    private CleanupHelper cleanupHelper;
    private WaitingHelper waitingHelper;
    private RestHelper restHelper;

    private StringBuffer verificationErrors = new StringBuffer();

    public AppManager(String browser) {
        this.browser = browser;
    }

    public AppManager() {
    }

    public void rise() throws IOException {
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
                properties.getProperty("httpAuth")
        );
    }

    public void riseRest() throws IOException {
        initTestSession();
        setEnvironment();
        initRestHelpers();
        revealKraken();
    }

    private void initTestSession() {
        if (multiSessionMode) {
            session = new SessionData(
                    testrunId, generate.testCredentials("admin"), generate.testCredentials("user"),
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
        switch (browser) {
            case BrowserType.FIREFOX:
                //System.setProperty("webdriver.gecko.driver", "/Users/tinwelen/Documents/GitHub/automag/web-tests/geckodriver");
                driver = new FirefoxDriver();
                break;
            case BrowserType.CHROME:
                driver = new ChromeDriver();
                break;
            case BrowserType.SAFARI:
                driver = new SafariDriver();
                break;
            case BrowserType.IE:
                driver = new InternetExplorerDriver(); // there is no IE driver for mac yet :(
                break;
        }
    }

    private void initHelpers() {
        browseHelper = new BrowseHelper(driver, environment, this);
        performHelper = new PerformHelper(driver, environment, this);
        reachHelper = new ReachHelper(driver, environment, this);
        detectionHelper = new DetectionHelper(driver, environment, this);
        grabHelper = new GrabHelper(driver, environment,this);
        dropHelper = new DropHelper(driver, environment,this);
        shopHelper = new Shop(driver, environment, this);
        userHelper = new User(driver, environment, this);
        checkoutHelper = new Checkout(driver, environment, this);
        administrationHelper = new Administration(driver, environment, this);
        cleanupHelper = new CleanupHelper(driver, environment, this);
        waitingHelper = new WaitingHelper(driver, environment, this);
        restHelper = new RestHelper(environment);
    }

    private void initRestHelpers() {
        administrationHelper = new Administration(driver, environment, this);
        restHelper = new RestHelper(environment);
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
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public BrowseHelper get() { return browseHelper; }
    public PerformHelper perform() { return performHelper; }
    public ReachHelper reach() { return reachHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public GrabHelper grab() { return grabHelper; }
    public DropHelper drop() { return dropHelper; }
    public Shop shopping() { return shopHelper; }
    public User user() { return userHelper; }
    public Checkout checkout() { return checkoutHelper; }
    public Administration admin() { return administrationHelper; }
    public CleanupHelper cleanup() { return cleanupHelper; }
    public WaitingHelper await() { return waitingHelper; }
    public RestHelper rest() { return restHelper; }
}
