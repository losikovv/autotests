package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.WidgetData;

import java.util.concurrent.TimeUnit;

import static ru.instamart.autotests.application.Config.verbose;
import static ru.instamart.autotests.application.Config.waitingTimeout;

public class ShoppingHelper extends HelperBase {

    ShoppingHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }


    // ======= Шторка выбора магазинов =======

    /** Открыть шторку выбора магазина */
    public void openStoreSelector() {
        kraken.perform().click(Elements.Site.Header.changeStoreButton());
        kraken.await().implicitly(1); // Ожидание открытия шторки выбора магазина
    }

    /** Закрыть шторку выбора магазина */
    public void closeStoreSelector() {
        kraken.perform().click(Elements.Site.StoreSelector.closeButton());
        kraken.await().implicitly(1); // Ожидание закрытия шторки выбора магазина
    }


    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            new FluentWait<>(driver)
                    .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                    .withMessage("Не открыввется шторка каталога")
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .until(ExpectedConditions.visibilityOfElementLocated(Elements.Site.CatalogDrawer.closeCatalogButton().getLocator()));
        } else {
            if(verbose) printMessage("Пропускаем открытие шторки каталога, уже открыта");
        }
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(kraken.detect().isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            new FluentWait<>(driver)
                    .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                    .withMessage("Не закрывается шторка каталога")
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .until(ExpectedConditions.invisibilityOfElementLocated(Elements.Site.CatalogDrawer.drawer().getLocator()));
        } else {
            if(verbose) printMessage("Пропускаем закрытие шторки каталога, уже закрыта");
        }
    }


    // ======= Каталог =======

    /** Добавляем в корзину первый товар из указанного виджета */
    public void addItem(WidgetData widget) {
        if(verbose) {kraken.perform().printMessage("Добавляем в корзину товар из виджета " + widget.getId());}
        if(widget.getProvider().equals("RetailRocket")) {
            kraken.perform().hoverOn(Elements.RetailRocket.item(widget.getId()));
            kraken.perform().click(Elements.RetailRocket.addButton(widget.getId()));
        }
        kraken.await().implicitly(2); // Ожидание добавления товара в корзину из виджета
    }

    /** Открываем карточку первого товара */
    public void openItemCard(WidgetData widget) {
        if(verbose) {kraken.perform().printMessage("Открываем карточку товара из виджета " + widget.getId());}
        if(widget.getProvider().equals("RetailRocket")) {
            kraken.perform().click(Elements.RetailRocket.item(widget.getId()));
        }

        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.popup().getLocator()),
                "Не открывается карточка товара из виджета");

        kraken.perform().switchToActiveElement();

        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.image().getLocator()),
                "Не отображается контент в карточке товара из виджета");
    }


    /** Добавляем в корзину первый товар в каталоге или списке любимых товаров */
    public void addFirstItemOnPageToCart() {
        hitFirstItemPlusButton();
    }

    /** Открываем карточку первого товара в каталоге или списке любимых товаров */
    public void openFirstItemCard() {
        kraken.perform().click(Elements.Site.Catalog.product());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.popup().getLocator()),
                "Не открывается карточка товара");
        kraken.perform().switchToActiveElement();
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.image().getLocator()),
                "Не отображается контент в карточке товара");
    }

    /** Навестись на первый товар из каталога или списка любимых товаров */
    private void hoverToFirstItem() {
        if (kraken.detect().isElementPresent(Elements.Site.Favorites.product())) {
            kraken.perform().hoverOn(Elements.Site.Favorites.product());
        } else { kraken.perform().hoverOn(Elements.Site.Catalog.product()); }
    }

    /** Добавить в корзину первый товар из каталога или списка любимых товаров */
    public void hitFirstItemPlusButton() {
        hoverToFirstItem();
        kraken.perform().click(Elements.Site.Catalog.plusButton());
        kraken.await().implicitly(1); // Ожидание добавления товара в корзину
    }

    /** Добавить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemAddToFavoritesButton() {
        hoverToFirstItem();
        kraken.perform().click(Elements.Site.Catalog.addToFavoritesButton());
        kraken.await().implicitly(1); // Ожидание добавления любимого товара
    }

    /** Удалить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemDeleteFromFavoritesButton() {
        hoverToFirstItem();
        kraken.perform().click(Elements.Site.Favorites.deleteFromFavoritesButton());
        kraken.await().implicitly(1); // Ожидание добавления товара в корзину из избранного
    }


    // ======= Любимые товары =======

    /** Открыть любимые товары по кнопке */
    public void openFavorites() {
        kraken.perform().click(Elements.Site.Header.favoritesButton());
        kraken.await().implicitly(2); // Ожидание открытия Любимых товаров
    }

    /** Отобразить все любимые товары */
    public void filterFavoritesAllItems() {
        kraken.perform().click(Elements.Site.Favorites.allItemsFilterButton());
        kraken.await().implicitly(1); // Ожидание открытия вкладки Все Товары в избранном
    }

    /** Отобразить любимые товары, которые есть в наличии */
    public void filterFavoritesInStock() {
        kraken.perform().click(Elements.Site.Favorites.inStockFilterButton());
        kraken.await().implicitly(1); // Ожидание открытия вкладки В наличии в избранном
    }

    /** Отобразить любимые товары, которых нет в наличии */
    public void filterFavoritesNotInStock() {
        kraken.perform().click(Elements.Site.Favorites.outOfStockFilterButton());
        kraken.await().implicitly(1); // Ожидание открытия вкладки Не в наличии в избранном
    }

    /** Нажать кнопку "Показать ещё" в списке любимых товаров */
    public void hitShowMoreFavorites() {
        kraken.perform().click(Elements.Site.Favorites.showMoreButton());
        kraken.await().implicitly(1); // Ожидание загрузки следующей страницы любимых товаров
    }


    // ======= Карточка товара  =======

    /** Нажать кнопку [+] в карточке товара */
    public void hitPlusButton() {
        ElementData button = Elements.Site.ItemCard.plusButton();
        // TODO добавить проверку на наличие модалки обновления цен
        kraken.await().fluently(
                ExpectedConditions
                        .elementToBeClickable(button.getLocator())
        );
        kraken.perform().click(Elements.Site.ItemCard.plusButton());
        kraken.await().simply(1); // Ожидание добавления +1 товара в карточке
        kraken.await().fluently(
                ExpectedConditions
                        .elementToBeClickable(button.getLocator())
        );
    }

    /** Нажать кнопку [-] в карточке товара */
    public void hitMinusButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
            kraken.await().simply(1); // Ожидание убавления -1 товара в карточке
        } else {
            printMessage("⚠ Кнопка 'Минус' не отображается");
        }
    }

    /** Нажать кнопку добавления любимого товара в карточке товара */
    public void hitAddToFavoritesButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.addToFavoritesButton())) {
            kraken.perform().click(Elements.Site.ItemCard.addToFavoritesButton());
            kraken.await().implicitly(1); // Ожидание добавления любимого товара
        } else {
            printMessage("⚠ Нет кнопки добавления любимого товара");
        }
    }

    /** Нажать кнопку удаления любимого товара в карточке товара */
    public void hitDeleteFromFavoritesButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.deleteFromFavoritesButton())) {
            kraken.perform().click(Elements.Site.ItemCard.deleteFromFavoritesButton());
            kraken.await().implicitly(1); // Ожидание удаления любимого товара
        } else {
            printMessage("⚠ Нет кнопки удаления любимого товара");
        }
    }

    /** Закрыть карточку товара */
    public void closeItemCard() {
        kraken.perform().click(Elements.Site.ItemCard.closeButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(Elements.Site.ItemCard.popup().getLocator()),
                "Не закрывается карточка товара");
    }


    // ======= Корзина =======

    /** Открыть корзину */
    public void openCart() {
        if (!kraken.detect().isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.openCartButton());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Site.Cart.closeButton().getLocator()),
                    "Не открывается корзина");
        } else {
            if(verbose) printMessage("Пропускаем открытие корзины, уже открыта");
        }
    }

    /** Закрыть корзину */
    public void closeCart() {
        if (kraken.detect().isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.closeButton());
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.Site.Cart.drawer().getLocator()),
                    "Не закрывается корзина");
        }
        else {
            if(verbose) printMessage("Пропускаем закрытие корзины, уже закрыта");
        }
    }

    /** Убрать товар из корзины */
    public void removeItemFromCart() {
        kraken.perform().hoverOn(Elements.Site.Cart.item());
        kraken.perform().click(Elements.Site.Cart.removeItemButton());
        kraken.await().implicitly(1); // Ожидание удаления продукта из корзины
    }

    /** Увеличить количество товара в корзине */
    public void increaseItemNumberInCart() {
        kraken.perform().hoverOn(Elements.Site.Cart.item());
        kraken.perform().click(Elements.Site.Cart.upArrowButton());
        kraken.await().implicitly(1); // Ожидание увеличения количества товара в корзине
    }

    /** Уменьшить количество товара в корзине */
    public void decreaseItemNumberInCart() {
        kraken.perform().hoverOn(Elements.Site.Cart.item());
        kraken.perform().click(Elements.Site.Cart.downArrowButton());
        kraken.await().implicitly(1); // Ожидание уменьшения количества товара в корзине
    }

    public void proceedToCheckout(boolean strictly) {
        if (strictly) {
            if(!kraken.detect().isCheckoutButtonActive()){
                kraken.perform().click(Elements.Site.Cart.upArrowButton());
                kraken.perform().click(Elements.Site.Cart.checkoutButton());
            }
        }
        proceedToCheckout();
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
            closeCart();
            collectItems(Config.minOrderSum);
        } else { printMessage("Пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
    }

    /** Набрать корзину на указанную сумму */
    public void collectItems(int orderSum) {
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }
        printMessage("Собираем корзину товаров на сумму " + orderSum + "\u20BD...");
        int cartTotal = kraken.grab().cartTotalRounded();
        if(cartTotal < orderSum) {
            closeCart();
            if(!kraken.detect().isProductAvailable()) {
                kraken.perform().printMessage(" > Нет товаров на текущей странице " + kraken.grab().currentURL());
                kraken.get().page(Pages.Site.Retailers.metro());}
            openFirstItemCard();
            int itemPrice = kraken.grab().itemPriceRounded();
            // Формула расчета кол-ва товара
            int quantity = ((orderSum - cartTotal) / itemPrice) + 1;
            printMessage("> добавляем в корзину " + quantity + " шт\n");
            // Накидываем товар
            for (int i = 1; i <= quantity; i++) {
                hitPlusButton();
            }
            closeItemCard();
            openCart();
        } else {
            printMessage("В корзине достаточно товаров");
        }
    }
}