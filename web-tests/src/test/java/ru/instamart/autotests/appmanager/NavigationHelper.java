package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper {
    public static final String baseUrl = "https://instamart.ru/";
    private WebDriver driver;

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLandingPage() {
        driver.get(baseUrl);
    }

    public void goToRetailerPage(RetailerData retailerData) {
        driver.get(baseUrl+retailerData.getName());
    }
}
