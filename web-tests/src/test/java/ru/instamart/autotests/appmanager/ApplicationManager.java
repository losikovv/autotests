package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.fail;

// класс управления
// тут стартуем и убиваем веб-драйвер, инициализируем и получаем хелперы
public class ApplicationManager {
    protected WebDriver driver;

    // переменные хелперов
    private  NavigationHelper navigationHelper;
    private  AuthorisationHelper authorisationHelper;
    private  RegistrationHelper registrationHelper;
    private  AdminHelper adminHelper;

    public String baseUrl = "https://instamart.ru/";
    private StringBuffer verificationErrors = new StringBuffer();

    public void init() {
        driver = new FirefoxDriver();
        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // инициализация хелперов
        navigationHelper = new NavigationHelper(driver);
        authorisationHelper = new AuthorisationHelper(driver);
        registrationHelper = new RegistrationHelper(driver);
        adminHelper = new AdminHelper(driver);
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

    public AuthorisationHelper getAuthorisationHelper() {
        return authorisationHelper;
    }

    public RegistrationHelper getRegistrationHelper() {
        return registrationHelper;
    }

    public AdminHelper getAdminHelper() {
        return adminHelper;
    }
}
