package instamart.ui.helpers;

import instamart.core.helpers.HelperBase;
import instamart.core.testdata.UserManager;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReachHelper extends HelperBase {

    private static final Logger log = LoggerFactory.getLogger(ReachHelper.class);

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
        log.info("Пытаемся попасть на страницу {} админки...", path);
        kraken.get().adminPage(path);
        kraken.await().simply(1);// Ожидание редиректа
        if (kraken.detect().isOnAdminLoginPage()) {
            log.warn("> недостаточно прав, перелогиниваемся суперадмином на логин-странице админки");
            User.Auth.withEmail(UserManager.getDefaultAdmin());
        }
        kraken.get().adminPage(path);
        log.info("✓ Готово");
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
