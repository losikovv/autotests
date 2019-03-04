package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.EnvironmentData;

public class ShoppingHelper extends HelperBase {

    ShoppingHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
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

    /** Добавляем в корзину первый товар в каталоге или списке любимых товаров */
    public void addFirstItemOnPageToCart() {
        hitFirstItemPlusButton();
    }

    /** Открываем карточку первого товара в каталоге или списке любимых товаров */
    public void openFirstItemCard() {
        if (kraken.detect().isElementPresent(Elements.Site.Favorites.product())) {
            kraken.perform().click(Elements.Site.Favorites.product());
        } else if (kraken.detect().isElementPresent(Elements.Site.SeoCatalog.product())) {
            kraken.perform().click(Elements.Site.SeoCatalog.product());
        } else { kraken.perform().click(Elements.Site.Catalog.product()); }

        kraken.perform().waitingFor(1); // Ожидание открытия карточки товара
        kraken.perform().switchToActiveElement();
        if(!kraken.detect().isItemCardOpen()) {
            printMessage("⚠ Проблемы с производительностью: слишком медленно открывается карточка товара\n");
            kraken.perform().waitingFor(2); // Дополнительное ожидание открытия карточки товара при тормозах
        }
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
        kraken.perform().waitingFor(1); // Ожидание добавления товара в корзину
    }

    /** Добавить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemAddToFavoritesButton() {
        hoverToFirstItem();
        kraken.perform().click(Elements.Site.Catalog.addToFavoritesButton());
        kraken.perform().waitingFor(1); // Ожидание добавления любимого товара
    }

    /** Удалить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemDeleteFromFavoritesButton() {
        hoverToFirstItem();
        kraken.perform().click(Elements.Site.Favorites.deleteFromFavoritesButton());
        kraken.perform().waitingFor(1); // Ожидание добавления товара в корзину из избранного
    }


    // ======= Любимые товары =======

    /** Открыть любимые товары по кнопке */
    public void openFavorites() {
        kraken.perform().click(Elements.Site.Header.favoritesButton());
        kraken.perform().waitingFor(2); // Ожидание открытия Любимых товаров
    }

    /** Отобразить все любимые товары */
    public void filterFavoritesAllItems() {
        kraken.perform().click(Elements.Site.Favorites.allItemsFilterButton());
        kraken.perform().waitingFor(1); // Ожидание открытия вкладки Все Товары в избранном
    }

    /** Отобразить любимые товары, которые есть в наличии */
    public void filterFavoritesInStock() {
        kraken.perform().click(Elements.Site.Favorites.inStockFilterButton());
        kraken.perform().waitingFor(1); // Ожидание открытия вкладки В наличии в избранном
    }

    /** Отобразить любимые товары, которых нет в наличии */
    public void filterFavoritesNotInStock() {
        kraken.perform().click(Elements.Site.Favorites.outOfStockFilterButton());
        kraken.perform().waitingFor(1); // Ожидание открытия вкладки Не в наличии в избранном
    }

    /** Нажать кнопку "Показать ещё" в списке любимых товаров */
    public void hitShowMoreFavorites() {
        kraken.perform().click(Elements.Site.Favorites.showMoreButton());
        kraken.perform().waitingFor(1); // Ожидание загрузки следующей страницы любимых товаров
    }


    // ======= Карточка товара  =======

    /** Нажать кнопку [+] в карточке товара */
    public void hitPlusButton() {
        // TODO добавить проверку на обновление цен
        if (!kraken.detect().isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            printMessage("⚠ Кнопка 'Плюс' неактивна, ждем и пробуем еще раз");
            kraken.perform().waitingFor(3); // Ожидание раздизабливания кнопки +1 в карточке товара
        }
        kraken.perform().click(Elements.Site.ItemCard.plusButton());
        kraken.perform().waitingFor(1); // Ожидание добавления +1 товара в карточке
    }

    /** Нажать кнопку [-] в карточке товара */
    public void hitMinusButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
            kraken.perform().waitingFor(1); // Ожидание убавления -1 товара карточке
        } else {
            printMessage("⚠ Кнопка 'Минус' не отображается");
        }
    }

    /** Нажать кнопку добавления любимого товара в карточке товара */
    public void hitAddToFavoritesButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.addToFavoritesButton())) {
            kraken.perform().click(Elements.Site.ItemCard.addToFavoritesButton());
            kraken.perform().waitingFor(1); // Ожидание добавления любимого товара
        } else {
            printMessage("⚠ Нет кнопки добавления любимого товара");
        }
    }

    /** Нажать кнопку удаления любимого товара в карточке товара */
    public void hitDeleteFromFavoritesButton() {
        if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.deleteFromFavoritesButton())) {
            kraken.perform().click(Elements.Site.ItemCard.deleteFromFavoritesButton());
            kraken.perform().waitingFor(1); // Ожидание удаления любимого товара
        } else {
            printMessage("⚠ Нет кнопки удаления любимого товара");
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

    /** Убрать товар из корзины */
    public void removeItemFromCart() {
            kraken.perform().hoverOn(Elements.Site.Cart.item());
            kraken.perform().click(Elements.Site.Cart.removeItemButton());
            kraken.perform().waitingFor(1); // Ожидание удаления продукта из корзины
    }

    /** Увеличить количество товара в корзине */
    public void increaseItemNumberInCart() {
        kraken.perform().hoverOn(Elements.Site.Cart.item());
        kraken.perform().click(Elements.Site.Cart.upArrowButton());
        kraken.perform().waitingFor(1); // Ожидание увеличения количества товара в корзине
    }

    /** Уменьшить количество товара в корзине */
    public void decreaseItemNumberInCart() {
        kraken.perform().hoverOn(Elements.Site.Cart.item());
        kraken.perform().click(Elements.Site.Cart.downArrowButton());
        kraken.perform().waitingFor(1); // Ожидание уменьшения количества товара в корзине
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
            if(!kraken.detect().isProductAvailable()) {kraken.get().page(Pages.Site.Retailers.metro());}
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