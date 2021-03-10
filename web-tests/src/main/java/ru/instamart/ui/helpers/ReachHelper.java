package instamart.ui.helpers;

import instamart.core.common.AppManager;
import instamart.core.helpers.HelperBase;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.PageData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReachHelper extends HelperBase {

    private static final Logger log = LoggerFactory.getLogger(ReachHelper.class);

    public ReachHelper(final AppManager kraken) {
        super(kraken);
    }

    public void checkout() {
        kraken.get().checkoutPage();
        if(!kraken.detect().isOnCheckout()){
            kraken.perform().refresh(); // Скипаем возможный алерт о минимальном заказе
            Shop.Cart.collect();
            Shop.Cart.proceedToCheckout();
        }
    }
    private void adminPageOpen(String path){
        log.info("> пытаемся попасть на страницу {} админки...", path);
        kraken.get().adminPage(path);
    }

    public void admin() {
        admin("");
    }

    public void admin(PageData page) {
        admin(page.getPath());
    }

    public void admin(String path) {
        adminPageOpen(path);
        if (kraken.detect().isOnAdminLoginPage()) {
            log.info("> находимся на странице логина админки, авторизуемся");
            User.Auth.withEmail(UserManager.getDefaultAdmin());
        }
        log.info("✓ Готово");
    }

    public void admin(String email,String password,String role){
        adminPageOpen("");
        User.Auth.withEmail(email,password,role);
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
