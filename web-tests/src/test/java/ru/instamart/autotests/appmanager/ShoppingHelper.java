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
    public void openStoreSelector() {
        kraken.perform().click(Elements.Site.Header.changeStoreButton());
        kraken.perform().waitingFor(1); // Ожидание открытия шторки выбора магазина
    }

    /** Закрыть шторку выбора магазина */
    public void closeStoreSelector() {
        kraken.perform().click(Elements.Site.StoreSelector.closeButton());
        kraken.perform().waitingFor(1); // Ожидание закрытия шторки выбора магазина
    }


    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            kraken.perform().waitingFor(1); // Ожидание открытия шторки каталога
        } else printMessage("Пропускаем открытие шторки каталога, уже открыта");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            kraken.perform().waitingFor(1); // Ожидание закрытия шторки каталога
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
    public void openFirstItemCard() {
        kraken.perform().click(Elements.Site.Catalog.firstItem());
        kraken.perform().waitingFor(1); // Ожидание открытия карточки товара
        kraken.perform().switchToActiveElement();
        if(!kraken.detect().isItemCardOpen()) {
            printMessage("⚠ Проблемы с производительностью: слишком медленно открывается карточка товара\n");
            kraken.perform().waitingFor(2); // Дополнительное ожидание открытия карточки товара при тормозах
        }
    }


    // ======= Карточка товара  =======

    /** Нажать кнопку [+] в карточке товара */
    public void hitPlusButton() {
        // TODO добавить проверку на обновление цен
        if (kraken.detect().isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.plusButton());
            kraken.perform().waitingFor(1); // Ожидание +1 в карточке товара
        } else {
            printMessage("Кнопка 'Плюс' не активна");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    public void hitMinusButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
            kraken.perform().waitingFor(1); // Ожидание -1 в карточке товара
        } else {
            printMessage("Кнопка 'Минус' не отображается");
        }
    }

    /** Закрыть карточку товара */
    public void closeItemCard() {
        kraken.perform().click(Elements.Site.ItemCard.closeButton());
        kraken.perform().waitingFor(1); // Ожидание закрытия карточки товара
        if(kraken.detect().isItemCardOpen()) {
            printMessage("⚠ Проблемы с производительностью: слишком медленно закрывается карточка товара\n");
            kraken.perform().waitingFor(2); // Дополнительное ожидание закрытия карточки товара при тормозах
        }
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
        if(!kraken.detect().isCheckoutButtonActive()) {
            collectItems(Config.minOrderSum);
        } else { printMessage("Пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
    }

    /** Набрать корзину на указанную сумму */
    public void collectItems(int orderSum) {
        printMessage("Собираем корзину товаров на сумму " + orderSum + "р...");

        openCart();
        int cartTotal = round(kraken.grab().currentCartTotal());
        printMessage("> текущая корзина: " + cartTotal + "p");

        if(cartTotal < orderSum) {
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

            int quantity = ((orderSum - cartTotal) / itemPrice) + 1;
            printMessage("> добавляем в корзину " + quantity + "шт\n");
            for (int i = 1; i <= quantity; i++) {
                hitPlusButton();
            }

            closeItemCard();
            openCart();
        } else { printMessage("В корзине достаточно товаров");}
    }
}