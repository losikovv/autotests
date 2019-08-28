package ru.instamart.application.platform.helpers;

import org.openqa.selenium.WebDriver;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.lib.Tenants;
import ru.instamart.application.lib.Users;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.PageData;

public class ReachHelper extends HelperBase {

    public ReachHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public void checkout() {
        kraken.get().checkoutPage();
        if(!kraken.detect().isOnCheckout()){
            kraken.perform().refresh(); // Скипаем возможный алерт о минимальном заказе
            Shop.Cart.collect();
            Shop.Cart.proceedToCheckout();
        }
    }

    public void admin() {
        admin("");
    }

    public void admin(PageData page) {
        admin(page.getPath());
    }

    public void admin(String path) {
        kraken.get().page(Pages.Admin.login());
        if (kraken.detect().isOnSite()) {
            User.Do.quickLogout();
            User.Do.login(Users.superadmin());
        }
        kraken.get().adminPage(path);
    }

    public void seoCatalog() {
        User.Do.quickLogout();
        deleteAllCookies();
        kraken.get().seoCatalogPage();
    }

}
