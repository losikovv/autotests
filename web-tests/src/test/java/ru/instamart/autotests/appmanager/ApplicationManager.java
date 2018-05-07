package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import ru.instamart.autotests.models.EnvironmentData;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;



    // App management class
    // Contains all core processes of the testing application



public class ApplicationManager {

    protected WebDriver driver;
    protected EnvironmentData environment = new EnvironmentData("production"); // use "production" or "staging"

    protected String environmentName = environment.getEnvironmentName();
    protected String host = environment.getHost();
    protected String baseUrl = environment.getBaseURL();

    // helpers
    private Helper helper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private ShoppingHelper shoppingHelper;
    private CheckoutHelper checkoutHelper;
    private ProfileHelper profileHelper;
    private OrderHelper orderHelper;

    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {

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
        helper = new Helper(driver, environment);
        navigationHelper = new NavigationHelper(driver, environment);
        sessionHelper = new SessionHelper(driver, environment);
        shoppingHelper = new ShoppingHelper(driver, environment);
        checkoutHelper = new CheckoutHelper(driver, environment);
        profileHelper = new ProfileHelper(driver, environment);
        orderHelper = new OrderHelper(driver, environment);

        // Options
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS); // Basic timeout
        // driver.manage().window().fullscreen(); // Open browser in fullscreen mode

        System.out.println("\n!!!!!!!!!!!! RELEASING THE KRAKEN !!!!!!!!!!!!\n");
        System.out.println("ENVIRONMENT: " + environmentName + " ( " + host + " ) \n");
        driver.get(baseUrl);

    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // Helpers getters
    public Helper getHelper() { return helper; }
    public NavigationHelper getNavigationHelper() { return navigationHelper; }
    public SessionHelper getSessionHelper() { return sessionHelper; }
    public ShoppingHelper getShoppingHelper() { return shoppingHelper; }
    public CheckoutHelper getCheckoutHelper() { return checkoutHelper; }
    public ProfileHelper getProfileHelper() { return profileHelper; }
    public OrderHelper getOrderHelper() { return orderHelper; }

}
