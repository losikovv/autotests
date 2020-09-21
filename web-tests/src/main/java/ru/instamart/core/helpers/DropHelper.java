package instamart.core.helpers;

import org.openqa.selenium.WebDriver;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;

public class DropHelper extends HelperBase {

    // TODO выпилить этот хелпер
    public DropHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Деавторизоваться, оставшись на текущей странице */
    public void auth() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            User.Logout.quickly();
            kraken.get().url(currentURL);
        }
    }

    /** Очистить список избранного, удалив все любимые товары */
    public void favorites() {
        if (!kraken.detect().isFavoritesEmpty()) {
            Shop.Favorites.Item.removeFromFavorites();
            kraken.perform().refresh();
            favorites();
        }
    }
}
