package instamart.core.helpers;

import org.openqa.selenium.WebDriver;
import instamart.core.common.AppManager;
import instamart.core.testdata.Users;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.PageData;

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
