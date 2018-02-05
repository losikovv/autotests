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

    // переменные хелперов
    private SiteNavHelper siteNavHelper;
    private SiteHelper siteHelper;
    private AdminNavHelper adminNavHelper;
    private AuthorisationHelper authorisationHelper;
    private RegistrationHelper registrationHelper;

    public String baseUrl;

    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        //
        if (browser == BrowserType.FIREFOX) {
            driver = new FirefoxDriver();
        } else if (browser == BrowserType.CHROME) {
            driver = new ChromeDriver();
        } else if (browser == BrowserType.SAFARI) {
            driver = new SafariDriver();
        } else if (browser == BrowserType.IE) {
            driver = new InternetExplorerDriver();
        }

        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // инициализация хелперов
        siteNavHelper = new SiteNavHelper(driver);
        siteHelper = new SiteHelper(driver);
        adminNavHelper = new AdminNavHelper(driver);
        authorisationHelper = new AuthorisationHelper(driver);
        registrationHelper = new RegistrationHelper(driver);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    // геттеры хелперов
    public SiteNavHelper getSiteNavHelper() {
        return siteNavHelper;
    }

    public SiteHelper getSiteHelper() {
        return siteHelper;
    }

    public AdminNavHelper getAdminNavHelper() {
        return adminNavHelper;
    }

    public AuthorisationHelper getAuthorisationHelper() {
        return authorisationHelper;
    }

    public RegistrationHelper getRegistrationHelper() {
        return registrationHelper;
    }

}
