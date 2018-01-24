package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.fail;

public class ApplicationManager {
    protected WebDriver driver;

    // helpers
    private  AuthorisationHelper authorisationHelper;
    private  RegistrationHelper registrationHelper;
    private  NavigationHelper navigationHelper;
    private  EmptyHelper emptyHelper;
    // helpers

    public String baseUrl = "https://instamart.ru/";
    private StringBuffer verificationErrors = new StringBuffer();

    public void init() {
        driver = new FirefoxDriver();
        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        authorisationHelper = new AuthorisationHelper(driver);
        navigationHelper = new NavigationHelper(driver);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public AuthorisationHelper getAuthorisationHelper() {
        return authorisationHelper;
    }

    public RegistrationHelper getRegistrationHelper() {
        return registrationHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public EmptyHelper getEmptytyHelper() {
        return emptyHelper;
    }
}
