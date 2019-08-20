package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;

public class DropHelper extends HelperBase {

    // TODO выпилить этот хелпер
    DropHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Деавторизоваться, оставшись на текущей странице */
    public void auth() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            kraken.perform().quickLogout();
            kraken.get().url(currentURL);
        }
    }

    /** Очистить список избранного, удалив все любимые товары */
    public void favorites() {
        if (!kraken.detect().isFavoritesEmpty()) {
            ShopHelper.Favorites.Item.removeFromFavorites();
            kraken.perform().refresh();
            favorites();
        }
    }
}
