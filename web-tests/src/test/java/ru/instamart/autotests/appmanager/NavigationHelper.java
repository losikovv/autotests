package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
        super(driver);
    }

    public void goToLandingPage() {
        driver.get(baseUrl);
    }

    public void goToRetailerPage(RetailerData retailerData) {
        driver.get(baseUrl+retailerData.getName());
    }
}
