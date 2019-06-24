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

    /** Шторка выбора магазина */
    public static class StoreSelector {

        /** Открыть шторку выбора магазина */
        public static void open() {
            kraken.perform().click(Elements.Header.storeButton());
            kraken.await().implicitly(1); // Ожидание открытия шторки выбора магазина
        }

        /** Закрыть шторку выбора магазина */
        public static void close() {
            kraken.perform().click(Elements.StoreSelector.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия шторки выбора магазина
        }
    }

    /** Шторка каталога категорий */
    public static class CatalogDrawer {

        public static void open() {
            debugMessage("Открываем шторку каталога категорий");
            if (!kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.Header.catalogButton());
                kraken.await().simply(1); // Ожидание анимации открытия шторки каталога
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.CatalogDrawer.closeButton().getLocator()),"Не открылась шторка каталога\n");
            }
        }

        public static void goToDepartment(String name) {
            verboseMessage("Переходим в департамент \"" + name + "\" в шторке каталога категорий");
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
        }

        public static void goToTaxon(String name) {
            verboseMessage("Переходим в таксон \"" + name + "\" в шторке каталога категорий");
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
        }

        public static void close() {
            debugMessage("Закрываем шторку каталога категорий");
            if (kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.CatalogDrawer.closeButton());
                kraken.await().simply(1); // Ожидание анимации закрытия шторки каталога
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.CatalogDrawer.drawer().getLocator()));
            }
        }
    }

    /** Всплывающее меню профиля */
    public static class AccountMenu {

        public static void open() {
            debugMessage("Открываем всплывающее меню профиля");
            if(!kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else verboseMessage("Пропускаем открытие меню аккаунта, уже открыто");
        }

        public static void close() {
            debugMessage("Закрываем всплывающее меню профиля");
            if(kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else verboseMessage("Пропускаем закрытие меню аккаунта, уже закрыто");
        }
    }

    /** Каталог */
    public static class Catalog {

        /** Сниппет товара */
        public static class Item {

            /** Добавить товар в корзину через сниппет товара в каталоге */
            public static void addToCart() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.plusButton());
                kraken.await().implicitly(1); // Ожидание добавления товара в корзину
            }

            /** Добавить товар в корзину через сниппет товара в партнерском виджете */
            public static void addToCart(WidgetData widget) {
                verboseMessage("Добавляем в корзину товар из виджета " + widget.getId());
                if (widget.getProvider().equals("RetailRocket")) {
                    kraken.perform().hoverOn(Elements.RetailRocket.item(widget.getId()));
                    kraken.perform().click(Elements.RetailRocket.addButton(widget.getId()));
                }
                kraken.await().implicitly(3); // Ожидание добавления товара в корзину из виджета
            }

            /** Удалить товар из корзины через сниппет товара в каталоге */
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.minusButton());
                kraken.await().implicitly(1); // Ожидание удаления товара из корзины
            }

            //TODO public void removeFromCart(WidgetData widget) {}

            /** Добавить товар в любимые через сниппет товара в каталоге */
            public static void addToFavorites() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.favButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Открыть карточку товара в каталоге */
            public static void open() {
                kraken.perform().click(Elements.Catalog.Product.snippet());
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.popup().getLocator()),
                        "Не открывается карточка товара");
                kraken.perform().switchToActiveElement();
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.image().getLocator()),
                        "Не отображается контент в карточке товара");
            }

            /** Открыть карточку товара в партнерском виджете */
            public static void open(WidgetData widget) {
                verboseMessage("Открываем карточку товара из виджета " + widget.getId());

                if (widget.getProvider().equals("RetailRocket")) {
                    kraken.perform().click(Elements.RetailRocket.item(widget.getId()));
                }

                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.popup().getLocator()),
                            "Не открывается карточка товара из виджета");
                kraken.perform().switchToActiveElement();
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.image().getLocator()),
                            "Не отображается контент в карточке товара из виджета");
            }
        }
    }

    /** Любимые товары */
    public static class Favorites {

        /** Открыть любимые товары по кнопке в шапке сайта */
        public static void open() {
            kraken.perform().click(Elements.Header.favoritesButton());
            kraken.await().implicitly(2); // Ожидание открытия Любимых товаров
        }

        /** Показать все любимые товары */
        public static void applyFilterAllItems() {
            kraken.perform().click(Elements.Favorites.allItemsFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "Все товары" в избранном
        }

        /** Показать любимые товары, которые есть в наличии */
        public static void applyFilterInStock() {
            kraken.perform().click(Elements.Favorites.inStockFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "В наличии" в избранном
        }

        /** Показать любимые товары, которых нет в наличии */
        public static void applyFilterNotInStock() {
            kraken.perform().click(Elements.Favorites.outOfStockFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "Не в наличии" в избранном
        }

        /** Подгрузить следующую страницу любимых товаров по кнопке "Показать ещё" */
        public static void showMore() {
            kraken.perform().click(Elements.Favorites.showMoreButton());
            kraken.await().implicitly(1); // Ожидание загрузки следующей страницы любимых товаров
        }

        /** Сниппет любимого товара */
        public static class Item {

            /** Добавить первый любимый товар в корзину через сниппет товара */
            public static void addToCart() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.plusButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Удалить первый любимый товар из корзины через сниппет товара */
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.minusButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Удалить первый любимый товар из любимых через сниппет товара */
            public static void removeFromFavorites() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.favButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }
        }
    }

    /** Карточка товара */
    public static class ItemCard {

        /** Добавить товар в корзину по кнопке [+] в карточке товара */
        public static void addToCart() {
            ElementData button = Elements.ItemCard.plusButton();
            // Побеждаем модалку обновления цен
            if(kraken.detect().isElementPresent(Elements.Modals.PricesModal.popup())) {
                kraken.perform().click(Elements.Modals.PricesModal.refreshPricesButton());
            }
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator()),
                        "Некликабельна кнопка добавления товара в корзину"
            );
            debugMessage("Жмем +1");
            kraken.perform().click(Elements.ItemCard.plusButton());
            //kraken.await().simply(1); // Ожидание добавления +1 товара в карточке
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator()),
                        "Не раздизаблилась кнопка добавления товара в корзину"
            );
        }

        /** Убрать товар из корзины по кнопке [-] в карточке товара */
        public static void removeFromCart() {
            if (kraken.detect().isElementDisplayed(Elements.ItemCard.minusButton())) {
                kraken.perform().click(Elements.ItemCard.minusButton());
                kraken.await().simply(1); // Ожидание убавления -1 товара в карточке
            } else {
                message("⚠ Кнопка 'Минус' не отображается");
            }
        }

        /** Нажать кнопку любимого товара в карточке товара */
        public static void addToFavorites() {
            if (kraken.detect().isElementDisplayed(Elements.ItemCard.addToFavoritesButton())) {
                kraken.perform().click(Elements.ItemCard.addToFavoritesButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            } else {
                message("⚠ Нет кнопки добавления любимого товара");
            }
        }

        /** Закрыть карточку товара */
        public static void close() {
            verboseMessage("> закрываем карточку товара");
            kraken.perform().click(Elements.ItemCard.closeButton());
            kraken.await().implicitly(1); // Ожидание анимации закрытия карточки товара
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.ItemCard.popup().getLocator()));
        }
    }

    public static class Cart {

        /** Открыть корзину */
        public static void open() {
            if (!kraken.detect().isCartOpen()) {
                kraken.perform().refresh(); // Доджим рандомные подвисания, из-за которых иногда не сразу открывается корзина
                debugMessage("> открываем корзину");
                kraken.perform().click(Elements.Header.cartButton());
                kraken.await().simply(1); // Ожидание анимации открытия корзины
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.closeButton().getLocator()),
                        "Не открылась корзина\n");
            } else {
                debugMessage("Пропускаем открытие корзины, уже открыта");
            }
        }

        /** Закрыть корзину */
        public static void close() {
            if (kraken.detect().isCartOpen()) {
                debugMessage("> закрываем корзину");
                kraken.perform().click(Elements.Cart.closeButton());
                kraken.await().simply(1);
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Cart.drawer().getLocator()),
                        "\nНе закрылась корзина\n");
            } else {
                verboseMessage("Пропускаем закрытие корзины, уже закрыта");
            }
        }

        /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
        public static void proceedToCheckout() {
            if (kraken.detect().isCheckoutButtonActive()) {
                kraken.perform().click(Elements.Cart.checkoutButton());
            } else {
                message("Кнопка перехода в чекаут неактивна");
                throw new AssertionError("Не удается перейти в чекаут");
            }
        }

        /** Сниппет товара в корзине */
        public static class Item {

            /** Открыть карточку верхнего товара в корзине */
            public static void open() {
                    kraken.perform().click(Elements.Cart.item());
            }

            /** Убрать верхний товар из корзины */
            public static void remove() {
                kraken.perform().hoverOn(Elements.Cart.item());
                kraken.await().implicitly(1);// TODO заменить на fluent-ожидание
                kraken.perform().click(Elements.Cart.itemRemoveButton());
                kraken.await().implicitly(1); // Ожидание удаления продукта из корзины
            }

            /** Увеличить количество верхнего товара в корзине */
            public static void increaseQuantity() {
                kraken.perform().hoverOn(Elements.Cart.item());
                kraken.perform().click(Elements.Cart.itemUpButton());
                kraken.await().implicitly(1); // Ожидание увеличения количества товара в корзине
            }

            /** Уменьшить количество верхнего товара в корзине */
            public static void decreaseQuantity() {
                kraken.perform().hoverOn(Elements.Cart.item());
                kraken.perform().click(Elements.Cart.itemDownButton());
                kraken.await().implicitly(1); // Ожидание уменьшения количества товара в корзине
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
            Catalog.Item.open();
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
        int quantity = kraken.grab().itemQuantity();
        debugMessage("В каунтере " + quantity);
        if (quantity == 1) kraken.await().implicitly(2);
        if (kraken.grab().itemQuantity() < neededQuantity) {
            ItemCard.addToCart();
            addItem(neededQuantity);
        }
    }
}