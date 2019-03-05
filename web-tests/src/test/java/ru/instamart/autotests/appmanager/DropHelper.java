package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;

public class DropHelper extends HelperBase {

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

    /** Очистить корзину, удалив все товары */
    public void cart() {
        //if(!kraken.detect().isOnSite()) { kraken.get().baseUrl();}
        if (!kraken.detect().isCartEmpty()) {
            kraken.shopping().removeItemFromCart();
            cart();
        }
        kraken.shopping().closeCart();
    }

    /** Очистить список избранного, удалив все любимые товары */
    public void favorites() {
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.shopping().hitFirstItemDeleteFromFavoritesButton();
            kraken.perform().refresh();
            favorites();
        }
    }
}
