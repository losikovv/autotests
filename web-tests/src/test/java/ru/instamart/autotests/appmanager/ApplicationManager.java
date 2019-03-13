package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.SessionData;
import ru.instamart.autotests.testdata.generate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;
import static ru.instamart.autotests.application.Config.*;

public class ApplicationManager {

    public final static String testrunId = generate.testRunId();

    protected WebDriver driver;
    protected EnvironmentData environment;
    private String browser;
    public static SessionData session;

    private BrowseHelper browseHelper;
    private NavigationHelper navigationHelper;
    private PerformHelper performHelper;
    private ReachHelper reachHelper;
    private DetectionHelper detectionHelper;
    private CheckHelper checkHelper;
    private GrabHelper grabHelper;
    private DropHelper dropHelper;
    private SocialHelper socialHelper;
    private AddressHelper addressHelper;
    private SearchHelper searchHelper;
    private ShoppingHelper shoppingHelper;
    private CheckoutHelper checkoutHelper;
    private AdministrationHelper administrationHelper;
    private CleanupHelper cleanupHelper;
    private JivositeHelper jivositeHelper;

    private StringBuffer verificationErrors = new StringBuffer();

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void rise() throws IOException {
        initTestSession();
        initEnvironment();
        initDriver();
        initHelpers();
        applyOptions();
        revealKraken();
        driver.get(environment.getBaseURL(true));
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
        environment = Config.environment;
    }

    private void initDriver() {
        switch (browser) {
            case BrowserType.FIREFOX:
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
        navigationHelper = new NavigationHelper(driver, environment, this);
        performHelper = new PerformHelper(driver, environment, this);
        reachHelper = new ReachHelper(driver, environment, this);
        detectionHelper = new DetectionHelper(driver, environment, this);
        checkHelper = new CheckHelper(driver, environment, this);
        grabHelper = new GrabHelper(driver,environment,this);
        dropHelper = new DropHelper(driver,environment,this);
        socialHelper = new SocialHelper(driver, environment, this);
        addressHelper = new AddressHelper(driver, environment, this);
        searchHelper = new SearchHelper(driver, environment, this);
        shoppingHelper = new ShoppingHelper(driver, environment, this);
        checkoutHelper = new CheckoutHelper(driver, environment, this);
        administrationHelper = new AdministrationHelper(driver, environment, this);
        cleanupHelper = new CleanupHelper(driver, environment, this);
        jivositeHelper = new JivositeHelper(driver, environment, this);
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

        System.out.println("\nENVIRONMENT: " + environment.getName() + " ( " + environment.getHost() + " )");

        if(multiSessionMode) {
            System.out.println("\nTEST RUN: " + session.id);
        } else {
            System.out.println("\nTEST RUN: " + session.id + " (SOLO MODE)");
        }

        System.out.println("ADMIN: " + session.admin.getEmail() + " / " + session.admin.getPassword());
        System.out.println("USER: " + session.user.getEmail() + " / " + session.user.getPassword() + "\n");
    }

    public void stop() throws Exception {
        if(doCleanupAfterTestRun) { cleanup().all(); }
        driver.quit();

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public BrowseHelper get() { return browseHelper; }
    public NavigationHelper go() { return navigationHelper; }
    public PerformHelper perform() { return performHelper; }
    public ReachHelper reach() { return reachHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public CheckHelper check() { return checkHelper; }
    public GrabHelper grab() { return grabHelper; }
    public DropHelper drop() { return dropHelper; }
    public SocialHelper social() { return socialHelper; }
    public AddressHelper shipAddress() { return addressHelper; }
    public SearchHelper search() { return searchHelper; }
    public ShoppingHelper shopping() { return shoppingHelper; }
    public CheckoutHelper checkout() { return checkoutHelper; }
    public AdministrationHelper admin() { return administrationHelper; }
    public CleanupHelper cleanup() { return cleanupHelper; }
    public JivositeHelper jivosite () { return jivositeHelper; }
}
