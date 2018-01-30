package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// в этом классе только глобальная навигация на сайте по прямым ссылкам
public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver) {
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
