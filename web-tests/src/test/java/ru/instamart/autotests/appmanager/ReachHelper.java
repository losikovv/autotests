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
            ShopHelper.Cart.proceedToCheckout();
        }
    }

    public void admin() {
        admin("");
    }

    public void admin(Pages page) {
        admin(getPagePath());
    }

    public void admin(String path) {
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
