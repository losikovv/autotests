package ru.instamart.application.platform.helpers;

import org.openqa.selenium.WebDriver;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;

public class DropHelper extends HelperBase {

    // TODO выпилить этот хелпер
    public DropHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Деавторизоваться, оставшись на текущей странице */
    public void auth() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            User.Do.quickLogout();
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
