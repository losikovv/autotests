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
        kraken.perform().click(Elements.Site.StoreSelector.closeButton());
        kraken.perform().waitingFor(1);
    }


    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Пропускаем открытие шторки каталога, уже открыта");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            kraken.perform().waitingFor(1);
        } else printMessage("Пропускаем закрытие шторки каталога, уже закрыта");
    }


    // ======= Каталог =======

    /**
     * Add the first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstItemOnPageToCart() {
        openFirstItemCard();
        hitPlusButton();
        closeItemCard();
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
            kraken.perform().waitingFor(1); // Ожидание добавления +1 товара в корзину
        } else {
            printMessage("Кнопка 'Плюс' не активна");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    private void hitMinusButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
            kraken.perform().waitingFor(1); // Ожидание удаления -1 товара в корзину
        } else {
            printMessage("Кнопка 'Минус' не отображается");
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
            kraken.perform().waitingFor(2); // Ожидание открытия корзины
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
        if(kraken.detect().isCheckoutButtonActive()){
            kraken.perform().click(Elements.Site.Cart.checkoutButton());
        } else {
            kraken.perform().printMessage("Кнопка перехода в чекаут неактивна");
        }
    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void collectItems() {
        collectItems(Config.minOrderSum);
    }

    /** Набрать корзину на указанную сумму */
    public void collectItems(int sum) {
        if(!kraken.detect().isCheckoutButtonActive()) {

            printMessage("Собираем корзину товаров на сумму " + sum + "р...");
            int roundedCartTotal = round(kraken.grab().currentCartTotal());
            printMessage("> текущая корзина: " + roundedCartTotal + "p");
            closeCart();

            openFirstItemCard();
            int itemPrice;
            if(kraken.detect().isItemOnSale()){
                itemPrice = round(kraken.grab().text(Elements.Site.ItemCard.salePrice()));
                printMessage("> скидочная цена товара: " + itemPrice + "p");
            } else {
                itemPrice = round(kraken.grab().text(Elements.Site.ItemCard.price()));
                printMessage("> цена товара: " + itemPrice + "p");
            }

            int quantity = ((sum - roundedCartTotal) / itemPrice) + 1;
            printMessage("> добавляем в корзину " + quantity + "шт\n");
            for (int i = 1; i <= quantity; i++) {
                hitPlusButton();
            }
            closeItemCard();

            openCart();
        } else { printMessage("Пропускаем набор товаров, в корзине достаточно товаров для оформления заказа");}
    }
}