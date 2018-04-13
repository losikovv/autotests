package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;



    // App management class
    // Contains all core processes of the testing application



public class ApplicationManager {

    protected WebDriver driver;
    protected String baseUrl;

    // helpers
    private Helper helper;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private ShoppingHelper shoppingHelper;
    private CheckoutHelper checkoutHelper;
    private ProfileHelper profileHelper;

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

        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

        // init helpers
        helper = new Helper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper = new SessionHelper(driver);
        shoppingHelper = new ShoppingHelper(driver);
        checkoutHelper = new CheckoutHelper(driver);
        profileHelper = new ProfileHelper(driver);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // get helpers
    public Helper getHelper() { return helper; }
    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
    public SessionHelper getSessionHelper() { return sessionHelper; }
    public ShoppingHelper getShoppingHelper() {
        return shoppingHelper;
    }
    public CheckoutHelper getCheckoutHelper() {
        return checkoutHelper;
    }
    public ProfileHelper getProfileHelper() {
        return profileHelper;
    }

}
