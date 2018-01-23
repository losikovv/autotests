package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;

public class NavigationHelper {
    private WebDriver driver;

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLandingPage() {
        driver.get("https://instamart.ru/");
    }

    public void goToRetailerPage() {
            driver.get("https://instamart.ru/metro");
    }

}
