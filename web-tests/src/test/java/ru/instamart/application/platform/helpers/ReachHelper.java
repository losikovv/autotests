package ru.instamart.application.platform.helpers;

import org.openqa.selenium.WebDriver;
import ru.instamart.application.AppManager;
import ru.instamart.application.Users;
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
        debugMessage("Пытаемся попасть на страницу " + path + " админки...");
        kraken.get().adminPage(path);
        kraken.await().simply(1);// Ожидание редиректа
        if (kraken.detect().isOnAdminLoginPage()) {
            debugMessage("> недостаточно прав, перелогиниваемся суперадмином на логин-странице админки");
            User.Auth.withEmail(Users.superadmin());
        }
        kraken.get().adminPage(path);
        debugMessage("✓ Готово");
    }

    public void seoCatalog() {
        User.Logout.quickly();
        deleteAllCookies();
        kraken.get().seoCatalogPage();
    }

}
