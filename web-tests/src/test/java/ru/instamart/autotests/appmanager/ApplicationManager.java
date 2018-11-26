package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.instamart.autotests.configuration.Environments;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;



    // App management class
    // Contains all core processes of the testing application



public class ApplicationManager {

    protected WebDriver driver;
    protected Environments environment = new Environments("staging"); // use "production" or "staging"
    private String browser;

    protected String environmentName = environment.getEnvironmentName();
    protected String host = environment.getHost();
    protected String baseUrl = environment.getBaseURL(true);

    // Helpers
    private PerformHelper performHelper;
    private DetectionHelper detectionHelper;
    private BrowseHelper browseHelper;
    private NavigationHelper navigationHelper;
    private GrabHelper grabHelper;

    private SessionHelper sessionHelper;
    private ShoppingHelper shoppingHelper;
    private SearchHelper searchHelper;
    private CheckoutHelper checkoutHelper;
    private ProfileHelper profileHelper;
    private AdministrationHelper administrationHelper;

    private StringBuffer verificationErrors = new StringBuffer();

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void rise() {

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
            // there is no IE driver for mac yet :(
            case BrowserType.IE:
                driver = new InternetExplorerDriver();
                break;
        }

        // Helpers init
        performHelper = new PerformHelper(driver, environment, this);
        detectionHelper = new DetectionHelper(driver, environment, this);
        browseHelper = new BrowseHelper(driver, environment, this);
        navigationHelper = new NavigationHelper(driver, environment, this);
        grabHelper = new GrabHelper(driver,environment,this);

        sessionHelper = new SessionHelper(driver, environment, this);
        shoppingHelper = new ShoppingHelper(driver, environment, this);
        searchHelper = new SearchHelper(driver, environment, this);
        checkoutHelper = new CheckoutHelper(driver, environment, this);
        profileHelper = new ProfileHelper(driver, environment, this);
        administrationHelper = new AdministrationHelper(driver, environment, this);


        // Options
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); // Basic timeout
        //driver.manage().window().fullscreen();                              // Open browser in fullscreen mode

        System.out.println("\n!!!!!!!!!!!! ВЫПУСКАЮ КРАКЕНА !!!!!!!!!!!!\n");
        System.out.println("ENVIRONMENT: " + environmentName + " ( " + host + " ) \n");
        driver.get(baseUrl);

    }

    public void stop() {

        // todo перенести сюда вызов cleanup

        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // Helpers getters
    public BrowseHelper get() { return browseHelper; }
    public NavigationHelper go() { return navigationHelper; }
    public PerformHelper perform() { return performHelper; }
    public DetectionHelper detect() { return detectionHelper; }
    public GrabHelper grab() { return grabHelper; }

    public SessionHelper getSessionHelper() { return sessionHelper; }
    public ShoppingHelper getShoppingHelper() { return shoppingHelper; }
    public SearchHelper search() { return searchHelper; }
    public CheckoutHelper getCheckoutHelper() { return checkoutHelper; }
    public ProfileHelper getProfileHelper() { return profileHelper; }
    public AdministrationHelper admin() { return administrationHelper; }
}
