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
import ru.instamart.application.models.ServerData;
import ru.instamart.application.models.SessionData;
import ru.instamart.testdata.generate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;
import static ru.instamart.application.Config.CoreSettings.basicTimeout;
import static ru.instamart.application.Config.CoreSettings.fullScreenMode;
import static ru.instamart.application.Config.CoreSettings.multiSessionMode;
import static ru.instamart.application.Config.TestVariables.TestParams.testMark;

public class AppManager {

    public final static String testrunId = generate.testRunId();

    protected WebDriver driver;
    public ServerData server;
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

    private StringBuffer verificationErrors = new StringBuffer();

    public AppManager(String browser) {
        this.browser = browser;
    }

    public void rise() throws IOException {
        initTestSession();
        initEnvironment();
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        driver.get(server.getBaseURL(true));
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

    private void initEnvironment() {
        server = Config.CoreSettings.server;
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
        browseHelper = new BrowseHelper(driver, server, this);
        performHelper = new PerformHelper(driver, server, this);
        reachHelper = new ReachHelper(driver, server, this);
        detectionHelper = new DetectionHelper(driver, server, this);
        grabHelper = new GrabHelper(driver, server,this);
        dropHelper = new DropHelper(driver, server,this);
        shopHelper = new Shop(driver, server, this);
        userHelper = new User(driver, server, this);
        checkoutHelper = new Checkout(driver, server, this);
        administrationHelper = new Administration(driver, server, this);
        cleanupHelper = new CleanupHelper(driver, server, this);
        waitingHelper = new WaitingHelper(driver, server, this);
    }

    private void applyOptions() {
        if (fullScreenMode) {driver.manage().window().fullscreen(); }
        if (basicTimeout > 0) {driver.manage().timeouts().implicitlyWait(basicTimeout, TimeUnit.SECONDS);}
    }

    private void revealKraken() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("banner.txt"));
        String line = in.readLine();
        while(line !=null) {
            System.out.println(line);
            line = in.readLine();
        }
        in.close();

        System.out.println("\nENVIRONMENT: " + server.getName() + " ( " + server.getHost() + " )");

        if(multiSessionMode) {
            System.out.println("\nTEST RUN: " + session.id);
        } else {
            System.out.println("\nTEST RUN: " + session.id + " (SOLO MODE)");
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
}
