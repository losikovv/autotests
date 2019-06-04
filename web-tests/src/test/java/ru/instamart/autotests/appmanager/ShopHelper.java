package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.WidgetData;

public class ShopHelper extends HelperBase {

    ShopHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // Шторка выбора магазина
    public static class StoreSelector {

        /** Открыть шторку выбора магазина */
        public static void open() {
            kraken.perform().click(Elements.Site.Header.storeButton());
            kraken.await().implicitly(1); // Ожидание открытия шторки выбора магазина
        }

        /** Закрыть шторку выбора магазина */
        public static void close() {
            kraken.perform().click(Elements.Site.StoreSelector.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия шторки выбора магазина
        }
    }

    // Шторка каталога категорий
    public static class CatalogDrawer {

        /** Открыть шторку каталога */
        public static void open() {
            if (!kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.Site.Header.catalogButton());
                kraken.await().simply(1); // Ожидание анимации открытия шторки каталога
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Site.CatalogDrawer.closeButton().getLocator()),"Не открылась шторка каталога\n");
            }
        }

        public static void goToDepartment(String name) {
            kraken.perform().click(Elements.Site.CatalogDrawer.category(name));
        }

        public static void goToTaxon(String name) {
            kraken.perform().hoverOn(Elements.Site.CatalogDrawer.category(name));
            kraken.perform().click(Elements.Site.CatalogDrawer.category(name));
        }

        /** Закрыть шторку каталога */
        public static void close() {
            if (kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.Site.CatalogDrawer.closeButton());
                kraken.await().simply(1); // Ожидание анимации закрытия шторки каталога
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Site.CatalogDrawer.drawer().getLocator()));
            }
        }
    }

    // Всплывающее меню профиля
    public static class AccountMenu {

        public static void open() {
            if(!kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Site.Header.profileButton());
            } else verboseMessage("Пропускаем открытие меню аккаунта, уже открыто");
        }

        public static void close() {
            if(kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Site.Header.profileButton());
            } else verboseMessage("Пропускаем закрытие меню аккаунта, уже закрыто");
        }
    }

    // ======= Каталог =======

    /** Добавляем в корзину первый товар из указанного виджета */
    public void addItem(WidgetData widget) {
        verboseMessage("Добавляем в корзину товар из виджета " + widget.getId());
        if(widget.getProvider().equals("RetailRocket")) {
            kraken.perform().hoverOn(Elements.RetailRocket.item(widget.getId()));
            kraken.perform().click(Elements.RetailRocket.addButton(widget.getId()));
        }
        kraken.await().implicitly(3); // Ожидание добавления товара в корзину из виджета
    }

    /** Открываем карточку первого товара */
    public void openItemCard(WidgetData widget) {
        verboseMessage("Открываем карточку товара из виджета " + widget.getId());
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

    /** Открываем карточку первого товара в каталоге или списке любимых товаров */
    public void openFirstItemCard() {
        kraken.perform().click(Elements.Site.Catalog.Product.snippet());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.popup().getLocator()),
                "Не открывается карточка товара");
        kraken.perform().switchToActiveElement();
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Site.ItemCard.image().getLocator()),
                "Не отображается контент в карточке товара");
    }

    /** Добавляем в корзину первый товар в каталоге или списке любимых товаров */
    public void addFirstItemOnPageToCart() {
        hitFirstItemPlusButton();
    }

    /** Навестись на первый товар из каталога или списка любимых товаров */
    private void hoverOnItemSnippet() {
        kraken.perform().hoverOn(Elements.Site.Catalog.Product.snippet());
    }

    /** Добавить в корзину первый товар из каталога или списка любимых товаров */
    public void hitFirstItemPlusButton() {
        hoverOnItemSnippet();
        kraken.perform().click(Elements.Site.Catalog.Product.plusButton());
        kraken.await().implicitly(1); // Ожидание добавления товара в корзину
    }

    /** Удалить из корзины первый товар из каталога или списка любимых товаров */
    public void hitFirstItemMinusButton() {
        hoverOnItemSnippet();
        kraken.perform().click(Elements.Site.Catalog.Product.minusButton());
        kraken.await().implicitly(1); // Ожидание удаления товара из корзины
    }

    /** Добавить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemAddToFavoritesButton() {
        hoverOnItemSnippet();
        kraken.perform().click(Elements.Site.Catalog.Product.favButton());
        kraken.await().implicitly(1); // Ожидание добавления любимого товара
    }

    /** Удалить любимый товар из каталога или списка любимых товаров */
    public void hitFirstItemDeleteFromFavoritesButton() {
        hoverOnItemSnippet();
        kraken.perform().click(Elements.Site.Favorites.Product.minusButton());
        kraken.await().implicitly(1); // Ожидание добавления товара в корзину из избранного
    }


    // ======= Любимые товары =======

    /** Открыть любимые товары по кнопке */
    public void openFavorites() {
        kraken.perform().click(Elements.Site.Header.favoritesButton());
        kraken.await().implicitly(2); // Ожидание открытия Любимых товаров
        //TODO добавить fluent-ожидание
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

    public static class ItemCard {

        /** Нажать кнопку [+] в карточке товара */
        public static void hitPlusButton() {
            ElementData button = Elements.Site.ItemCard.plusButton();
            // Побеждаем модалку обновления цен
            if(kraken.detect().isElementPresent(Elements.Site.PricesModal.popup())) {
                kraken.perform().click(Elements.Site.PricesModal.refreshPricesButton());
            }
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator())
            );
            debugMessage("Жмем +1");
            kraken.perform().click(Elements.Site.ItemCard.plusButton());
            kraken.await().simply(1); // Ожидание добавления +1 товара в карточке
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator())
            );
        }

        /** Нажать кнопку [-] в карточке товара */
        public static void hitMinusButton() {
            if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
                kraken.perform().click(Elements.Site.ItemCard.minusButton());
                kraken.await().simply(1); // Ожидание убавления -1 товара в карточке
            } else {
                message("⚠ Кнопка 'Минус' не отображается");
            }
        }

        /** Нажать кнопку добавления любимого товара в карточке товара */
        public static void addToFavorites() {
            if (kraken.detect().isElementDisplayed(Elements.Site.ItemCard.addToFavoritesButton())) {
                kraken.perform().click(Elements.Site.ItemCard.addToFavoritesButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            } else {
                message("⚠ Нет кнопки добавления любимого товара");
            }
        }

        /** Закрыть карточку товара */
        public static void close() {
            verboseMessage("> закрываем карточку товара");
            kraken.perform().click(Elements.Site.ItemCard.closeButton());
            kraken.await().implicitly(1); // Ожидание анимации закрытия карточки товара
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.Site.ItemCard.popup().getLocator()));
        }
    }

    public static class Cart {

        /** Открыть корзину */
        public static void open() {
            if (!kraken.detect().isCartOpen()) {
                debugMessage("> открываем корзину");
                kraken.perform().click(Elements.Site.Header.cartButton());
                kraken.await().simply(1); // Ожидание анимации открытия корзины
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Site.Cart.closeButton().getLocator()),
                        "Не открылась корзина\n");
            } else {
                verboseMessage("Пропускаем открытие корзины, уже открыта");
            }
        }

        /** Закрыть корзину */
        public static void close() {
            if (kraken.detect().isCartOpen()) {
                debugMessage("> закрываем корзину");
                kraken.perform().click(Elements.Site.Cart.closeButton());
                kraken.await().simply(1);
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Site.Cart.drawer().getLocator()),
                        "\nНе закрылась корзина\n");
            } else {
                verboseMessage("Пропускаем закрытие корзины, уже закрыта");
            }
        }

        /** Убрать товар из корзины */
        public static void removeItem() {
            kraken.perform().hoverOn(Elements.Site.Cart.item());
            kraken.await().implicitly(1);
            kraken.perform().click(Elements.Site.Cart.itemRemoveButton());
            kraken.await().implicitly(1); // Ожидание удаления продукта из корзины
        }

        /** Увеличить количество товара в корзине */
        public static void increaseItemQuantity() {
            kraken.perform().hoverOn(Elements.Site.Cart.item());
            kraken.perform().click(Elements.Site.Cart.itemUpButton());
            kraken.await().implicitly(1); // Ожидание увеличения количества товара в корзине
        }

        /** Уменьшить количество товара в корзине */
        public static void decreaseItemQuantity() {
            kraken.perform().hoverOn(Elements.Site.Cart.item());
            kraken.perform().click(Elements.Site.Cart.itemDownButton());
            kraken.await().implicitly(1); // Ожидание уменьшения количества товара в корзине
        }

        /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
        public static void proceedToCheckout() {
            if (kraken.detect().isCheckoutButtonActive()) {
                kraken.perform().click(Elements.Site.Cart.checkoutButton());
            } else {
                message("Кнопка перехода в чекаут неактивна");
                throw new AssertionError("Не удается перейти в чекаут");
            }
        }

    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void collectItems() {
        if(!kraken.detect().isCheckoutButtonActive()) {
            Cart.close();
            collectItems(Config.minOrderSum);
        } else { message("Пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
    }

    /** Набрать корзину на указанную сумму */
    public void collectItems(int orderSum) {
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }
        message("Собираем корзину товаров на сумму " + orderSum + "\u20BD...");
        int cartTotal = kraken.grab().cartTotalRounded();
        if(cartTotal < orderSum) {
            Cart.close();
            if(!kraken.detect().isProductAvailable()) {
                message(" > Нет товаров на текущей странице " + kraken.grab().currentURL());
                kraken.get().page(Pages.Site.Retailers.metro());}
            openFirstItemCard();
            int itemPrice = kraken.grab().itemPriceRounded();
            // Формула расчета кол-ва товара
            int neededQuantity = ((orderSum - cartTotal) / itemPrice) + 1;
            message("> добавляем в корзину \""
                    + kraken.grab().itemName() + "\" x " + neededQuantity + " шт\n"
                    + kraken.grab().currentURL()
                    + "\n");
            // Накидываем товар, закрываем карточку и открываем корзину
            addItem(neededQuantity);
            ItemCard.close();
            Cart.open();
        } else {
            message("В корзине достаточно товаров");
        }
    }

    private void addItem(int neededQuantity) {
        debugMessage("В каунтере " + kraken.grab().itemQuantity());
        if (kraken.grab().itemQuantity() < neededQuantity) {
            ItemCard.hitPlusButton();
            addItem(neededQuantity);
        }
    }

}