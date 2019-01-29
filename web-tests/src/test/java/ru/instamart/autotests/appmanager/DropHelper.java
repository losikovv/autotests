package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Environments;

public class DropHelper extends HelperBase {

    private ApplicationManager kraken;

    DropHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }


    /** Деавторизоваться, оставшись на текущей странице */
    public void auth() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            kraken.perform().quickLogout();
            kraken.get().url(currentURL);
        }
    }

    /** Очистить корзину */
    public void cart() {
        if (!kraken.detect().isCartEmpty()) {
            kraken.shopping().removeItemFromCart();
            kraken.perform().waitingFor(1); // Ожидание удаления продукта из корзины
            cart();
        }
        kraken.shopping().closeCart();
    }

    /** Очистить корзину изменениями адреса доставки ( временный метод, пока не запилят очистку корзины ) */
    public void cartViaAddressSwap() {
        if (!kraken.detect().isCartEmpty()) {
            kraken.shopping().closeCart();
            kraken.shipAddress().swap();
        }
        kraken.shopping().closeCart();
    }

    /** Удалить все любимые товары */
    public void favorites() {
        if (!kraken.detect().isFavoritesEmpty()) {
            kraken.shopping().hitFirstItemDeleteFromFavoritesButton();
            kraken.perform().waitingFor(1); // Ожидание удаления продукта из избранного
            favorites();
        } else {
            printMessage("✓ Все любимые товары удалены\n");
        }
    }

}
