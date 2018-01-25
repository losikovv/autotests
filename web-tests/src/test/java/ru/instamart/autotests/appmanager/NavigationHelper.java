package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.RetailerData;

// в этом классе только глобальная навигация по прямым ссылкам
// навигация по страницам описываем в классах-наследниках (PageNavHelpers)
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
