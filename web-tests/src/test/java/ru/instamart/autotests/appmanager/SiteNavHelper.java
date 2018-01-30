package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// хелпер глобальной навигации по сайту
public class SiteNavHelper extends HelperBase {

    public SiteNavHelper(WebDriver driver) {
        super(driver);
    }

    // переход на instamart.ru
    public void goToLandingPage() {
        driver.get(baseUrl);
    }

    // переход на витрину ретейлера
    public void goToRetailerPage(RetailerData retailerData) {
        driver.get(baseUrl+retailerData.getName());
    }

}
