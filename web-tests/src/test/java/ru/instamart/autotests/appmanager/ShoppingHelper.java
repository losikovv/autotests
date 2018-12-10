package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

import static ru.instamart.autotests.application.Config.minOrderSum;

public class ShoppingHelper extends HelperBase {

    private ApplicationManager kraken;

    ShoppingHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }


    // ======= Шторка выбора магазинов =======

    /** Открыть шторку выбора магазина */
    public void openShopSelector() {
        kraken.perform().click(Elements.Site.Header.changeStoreButton());
        kraken.perform().waitingFor(1);
    }

    /** Закрыть шторку выбора магазина */
    public void closeShopSelector() {
        kraken.perform().click(Elements.Site.ShopSelector.closeButton());
        kraken.perform().waitingFor(1);
    }


    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Can't open catalog drawer - already opened");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Can't close catalog drawer - already closed");
    }


    // ======= Каталог =======

    /**
     * Add the first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstItemOnPageToCart() {
        openFirstItemCard();
        hitPlusButton();
        kraken.perform().waitingFor(1);
        closeItemCard();
        kraken.perform().waitingFor(1);
    }

    /** Открываем карточку первого товара */
    private void openFirstItemCard() {
        kraken.perform().click(Elements.Site.Catalog.firstItem());
        kraken.perform().waitingFor(1); // Ожидание открытия карточки товара
        kraken.perform().switchToActiveElement();
    }


    // ======= Карточка товара  =======

    /** Нажать кнопку [+] в карточке товара */
    private void hitPlusButton() {
        // TODO добавить проверку на обновление цен
        if (kraken.detect().isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.plusButton());
        } else {
            printMessage("'Plus' button is disabled");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    private void hitMinusButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
        } else {
            printMessage("'Minus' button is not displayed");
        }
    }

    /** Закрыть карточку товара */
    private void closeItemCard() {
        kraken.perform().click(Elements.Site.ItemCard.closeButton());
        kraken.perform().waitingFor(1); // Ожидание закрытия карточки товара
    }


    // ======= Корзина =======

    /** Открыть корзину */
    public void openCart() {
        if (!kraken.detect().isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.openCartButton());
            kraken.perform().waitingFor(1); // Ожидание открытия корзины
        }
    }

    /** Закрыть корзину */
    public void closeCart() {
        if (kraken.detect().isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.closeButton());
            kraken.perform().waitingFor(1); // Ожидание закрытия корзины
        }
    }

    /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
    public void proceedToCheckout() {
        openCart(); // todo убрать?
        if(kraken.detect().isCheckoutButtonActive()){
            kraken.perform().click(Elements.Site.Cart.checkoutButton());
        } else {
            kraken.perform().printMessage("Кнопка перехода в чекаут неактивна");
        }
    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void grabCart() {
        if(!kraken.detect().isCheckoutButtonActive()) {
            closeCart();
            openFirstItemCard();
            printMessage("Adding items to cart for minimal order on " + minOrderSum + "р...");
            int quantity = (minOrderSum / round(kraken.grab().text(Elements.Site.ItemCard.price()))) + 1;
            printMessage("Quantity for minimal order : " + quantity + "\n");
            for (int i = 1; i <= quantity; i++) {
                hitPlusButton();
                kraken.perform().waitingFor(1);
            }
            closeItemCard();
        } else { printMessage("Skip grab cart, already have enough items");}
    }

    /** Набрать корзину на указанную сумму */
    public void grabCart(int sum) {
        openFirstItemCard();

        int quantity = (sum / round(kraken.grab().text(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for " + sum + "р : " + quantity);

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            kraken.perform().waitingFor(1);
        }
        closeItemCard();
    }
}