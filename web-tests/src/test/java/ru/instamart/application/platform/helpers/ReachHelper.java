package ru.instamart.application.platform.helpers;

import org.openqa.selenium.WebDriver;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.lib.Users;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.models.ServerData;
import ru.instamart.application.models.PageData;

public class ReachHelper extends HelperBase {

    public ReachHelper(WebDriver driver, ServerData environment, AppManager app) {
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
        kraken.await().simply(1);// Ожидание редиректа в админку если авторизованы админом
        if (!kraken.detect().isInAdmin()) {
            User.Do.quickLogout();
            User.Do.login(Users.superadmin());
        } else {
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
