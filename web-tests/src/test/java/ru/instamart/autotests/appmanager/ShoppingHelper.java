package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

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

    /** Определить открыта ли шторка выбора магазина */
    public boolean isShopSelectorOpen() {
        return isElementDisplayed(Elements.Site.ShopSelector.drawer());
    }

    /** Определить пуст ли селектор */
    public boolean isShopSelectorEmpty() {
        return isElementDisplayed(Elements.Site.ShopSelector.placeholder());
    }


    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Can't open catalog drawer - already opened");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Can't close catalog drawer - already closed");
    }

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        return isElementDisplayed(Elements.Site.CatalogDrawer.drawer());

    }


    // ======= Каталог =======

    public boolean isProductAvailable() {
        if(isElementPresent(Elements.Site.Catalog.product())){
            printMessage("✓ Products available");
            return true;
        } else {
            printMessage("No products available!");
            return false;
        }
    }

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
        kraken.perform().waitingFor(1);
        switchToActiveElement();
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(isElementPresent(Elements.Site.ItemCard.popup())){
            printMessage("✓ Item card open");
            return true;
        } else return false;
    }

    /** Нажать кнопку [+] в карточке товара */
    private void hitPlusButton() {
        // TODO добавить проверку на обновление цен
        if (isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.plusButton());
        } else {
            printMessage("'Plus' button is disabled");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    private void hitMinusButton() {
        if (isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
        } else {
            printMessage("'Minus' button is not displayed");
        }
    }

    /** Закрыть карточку товара */
    private void closeItemCard() {
        kraken.perform().click(Elements.Site.ItemCard.closeButton());
    }


    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        kraken.perform().waitingFor(1); // Пауза, на случай если штокра медленно отображается
        return isElementDisplayed(Elements.Site.Cart.drawer());
    }

    /** Открыть корзину */
    public void openCart() {
        if (!isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.openCartButton());
            kraken.perform().waitingFor(1);
        }
        // DEBUG
        // else printMessage("Skip open cart, already opened");
    }

    /** Закрыть корзину */
    public void closeCart() {
        if (isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.closeButton());
            kraken.perform().waitingFor(1);
        }
        // DEBUG
        // else printMessage("Skip close cart, already closed");
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        openCart();
        kraken.perform().waitingFor(1); // Пауза на случай, тормозов с корзиной
        return isElementPresent(Elements.Site.Cart.placeholder());
    }

    /** Определить активна ли кнопка "Сделать заказ" в корзине */
    public boolean isCheckoutButtonActive() {
        openCart();
        kraken.perform().waitingFor(1); // Пауза на случай, если стостояние кнопки долго обновляется
        return isElementEnabled(Elements.Site.Cart.checkoutButton());
    }

    /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
    public void proceedToCheckout() {
        openCart();
        kraken.perform().click(Elements.Site.Cart.checkoutButton());
    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void grabCart() {
        openFirstItemCard();

        int quantity = (Config.minOrderSum / round(fetchText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for minimal order : " + quantity );

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            kraken.perform().waitingFor(1);
        }
        closeItemCard();
    }

    /** Набрать корзину на указанную сумму */
    public void grabCart(int sum) {
        openFirstItemCard();

        int quantity = (sum / round(fetchText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for " + sum + "р : " + quantity);

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            kraken.perform().waitingFor(1);
        }
        closeItemCard();
    }
}