package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;

import static ru.instamart.autotests.application.Pages.getPagePath;

public class ReachHelper extends HelperBase {
    ReachHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    public void checkout() {
        kraken.get().checkoutPage();
        if(!kraken.detect().isOnCheckout()){
            kraken.perform().refresh(); // Скипаем возможный алерт о минимальном заказе
            kraken.shopping().collectItems();
            kraken.shopping().proceedToCheckout();
        }
    }

    public void admin() throws Exception{
        admin("");
    }

    public void admin(Pages page) throws Exception {
        admin(getPagePath());
    }

    public void admin(String path) throws Exception{
        kraken.get().adminURL();
        if (kraken.detect().isOnSite()) {
            kraken.perform().quickLogout();
            if(!kraken.environment.getTenant().equalsIgnoreCase("instamart")){
                kraken.get().adminURL();
            }
            kraken.perform().authorisation(Users.superadmin());
        }
        kraken.get().adminPage(path);
    }

    public void seoCatalog() {
        kraken.perform().quickLogout();
        deleteAllCookies();
        kraken.get().seoCatalogPage();
    }

}
