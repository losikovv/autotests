package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
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
        if (!kraken.detect().isCartEmpty()) {
            ShopHelper.Cart.removeItem();
            if(kraken.detect().isElementPresent(Elements.Site.Cart.item())) {
                cart();
            }
        }
        ShopHelper.Cart.close();
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
