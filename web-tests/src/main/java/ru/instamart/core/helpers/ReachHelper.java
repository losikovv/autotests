package instamart.core.helpers;

import instamart.core.testdata.UserManager;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.openqa.selenium.WebDriver;

public class ReachHelper extends HelperBase {

    public ReachHelper(WebDriver driver, AppManager app) {
        super(driver, app);
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
        verboseMessage("Пытаемся попасть на страницу " + path + " админки...");
        kraken.get().adminPage(path);
        kraken.await().simply(1);// Ожидание редиректа
        if (kraken.detect().isOnAdminLoginPage()) {
            verboseMessage("> недостаточно прав, перелогиниваемся суперадмином на логин-странице админки");
            User.Auth.withEmail(UserManager.getDefaultAdmin());
        }
        kraken.get().adminPage(path);
        verboseMessage("✓ Готово");
    }

    public void seoCatalog() {
        User.Logout.quickly();
        deleteAllCookies();
        kraken.get().seoCatalogPage();
    }

    /** Деавторизоваться, оставшись на текущей странице */
    public void logout() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            User.Logout.quickly();
            kraken.get().url(currentURL);
        }
    }

    /** Очистить список избранного, удалив все любимые товары */
    public void cleanFavorites() {
        if (!kraken.detect().isFavoritesEmpty()) {
            Shop.Favorites.Item.removeFromFavorites();
            kraken.perform().refresh();
            cleanFavorites();
        }
    }

}
