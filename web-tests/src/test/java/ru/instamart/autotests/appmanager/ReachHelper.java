package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Tenants;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.PageData;

public class ReachHelper extends HelperBase {
    ReachHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    public void checkout() {
        kraken.get().checkoutPage();
        if(!kraken.detect().isOnCheckout()){
            kraken.perform().refresh(); // Скипаем возможный алерт о минимальном заказе
            ShopHelper.Cart.collect();
            ShopHelper.Cart.proceedToCheckout();
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
            kraken.perform().quickLogout();
            if(!kraken.environment.getTenant().getAlias().equals(Tenants.instamart().getAlias())){
                kraken.get().page(Pages.Admin.login());
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
