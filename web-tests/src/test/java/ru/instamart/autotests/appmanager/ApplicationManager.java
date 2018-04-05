package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

// класс управления
// тут стартуем и убиваем веб-драйвер, инициализируем и получаем хелперы

public class ApplicationManager {

    protected WebDriver driver;
    //public String baseUrl = "https://instamart.ru/";

    // объявление переменных хелперов
    private NavigationHelper navigationHelper;
    private ErrorPageHelper errorPageHelper;
    private SessionHelper sessionHelper;
    private ShoppingHelper shoppingHelper;
    private ShippingAddressHelper shippingAddressHelper;
    private ShoppingCartHelper shoppingCartHelper;
    private CheckoutHelper checkoutHelper;
    private ProfileHelper profileHelper;

    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        } else if (browser.equals(BrowserType.SAFARI)) {
            driver = new SafariDriver();
        }
        // there is no IE driver for mac yet :(
        else if (browser.equals(BrowserType.IE)) {
            driver = new InternetExplorerDriver();
        }

        //baseUrl = getBaseUrl();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // инициализация хелперов
        navigationHelper = new NavigationHelper(driver);
        errorPageHelper = new ErrorPageHelper(driver);
        sessionHelper = new SessionHelper(driver);
        shoppingHelper = new ShoppingHelper(driver);
        shippingAddressHelper = new ShippingAddressHelper(driver);
        shoppingCartHelper = new ShoppingCartHelper(driver);
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

    // геттеры хелперов
    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public ErrorPageHelper getErrorPageHelper() {
        return errorPageHelper;
    }

    public SessionHelper getSessionHelper() { return sessionHelper; }

    public ShoppingHelper getShoppingHelper() {
        return shoppingHelper;
    }

    public ShippingAddressHelper getShippingAddressHelper() {
        return shippingAddressHelper;
    }

    public ShoppingCartHelper getShoppingCartHelper() {
        return shoppingCartHelper;
    }

    public CheckoutHelper getCheckoutHelper() {
        return checkoutHelper;
    }

    public ProfileHelper getProfileHelper() {
        return profileHelper;
    }

}
