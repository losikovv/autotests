package ru.instamart.application.platform.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.ElementData;
import ru.instamart.application.models.WidgetData;
import ru.instamart.application.Config;
import ru.instamart.application.models.EnvironmentData;

public class Shop extends Base {

    public Shop(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static class AuthModal {

        public static void open() {
            if (!kraken.detect().isAuthModalOpen()) {
                verboseMessage("> открываем модалку авторизации");
                if (kraken.detect().isOnLanding()) {
                    kraken.perform().click(Elements.Landing.MainBlock.loginButton());
                } else {
                    kraken.perform().click(Elements.Header.loginButton());
                }
                kraken.await().implicitly(1); // Ожидание открытия модалки авторизации/регистрации
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Modals.AuthModal.popup().getLocator()),
                        "\n> Превышено время ожидания открытия модалки авторизации/регистрации"
                );
            }
        }

        public static void close() {
            debugMessage("> закрываем форму авторизации");
            kraken.perform().click(Elements.Modals.AuthModal.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
        }

        public static void switchToAuthorisationTab() {
            verboseMessage("> переключаемся на вкладку авторизации");
            kraken.perform().click(Elements.Modals.AuthModal.authorisationTab());
        }

        public static void switchToRegistrationTab() {
            verboseMessage("> переключаемся на вкладку регистрации");
            kraken.perform().click(Elements.Modals.AuthModal.registrationTab());
        }

        public static void proceedToPasswordRecovery() {
            verboseMessage("> переходим в форму восстановления пароля");
            kraken.perform().click(Elements.Modals.AuthModal.forgotPasswordButton());
        }

        public static void fillAuthorisationForm(String email, String password) {
            verboseMessage("> заполняем поля формы авторизации");
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
        }

        public static void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
            verboseMessage("> заполняем поля формы регистрации");
            kraken.perform().fillField(Elements.Modals.AuthModal.nameField(), name);
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordConfirmationField(), passwordConfirmation);
            if (!agreementConfirmation) {
                kraken.perform().click(Elements.Modals.AuthModal.agreementCheckbox());
            }
        }

        // todo hitVkButton()

        // todo hitFbButton()

        // todo hitMailRuButton()

        public static void submit() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.AuthModal.submitButton());
            kraken.await().implicitly(2); // Ожидание авторизации/регистрации
        }
    }

    public static class RecoveryModal {

        public static void close() {
            verboseMessage("> закрываем модалку\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.closeButton());
        }

        public static void proceedBack() {
            verboseMessage("> возвращаемся назад\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.backButton());
            kraken.await().simply(1); // Ожидание возврата назад из формы восстановления пароля
        }

        public static void fillRequestForm(String email) {
            verboseMessage("> заполняем форму запроса восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.emailField(), email);
            submitRequest();
        }

        public static void fillRecoveryForm(String password, String passwordConfirmation) {
            verboseMessage("> заполняем форму восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordConfirmationField(), passwordConfirmation);
            submitRecovery();
        }

        public static void submitRequest() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRequestButton());
            kraken.await().implicitly(1); // Ожидание восстановления пароля
        }

        public static void submitRecovery() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRecoveryButton());
            kraken.await().implicitly(1); // Ожидание восстановления пароля
        }
    }

    /** Адрес доставки */
    public static class ShippingAddress {

        // TODO переименовать в класс ShippingAddressModal, методы set / change / swap - вынести в User

        /** Установить адрес доставки */
        public static void set(String address) {
            message("Устанавливаем адрес доставки >>> " + address + "\n");
            openAddressModal();
            fill(address);
            submit();
        }

        /** Изменить адрес доставки */
        public static void change(String address) {
            message("Изменяем адрес доставки >>> " + address + "\n");
            openAddressModal();
            clearAddressField();
            fill(address);
            submit();
        }

        /** Свапнуть тестовый и дефолтные адреса */
        public static void swap() {
            if (kraken.grab().currentShipAddress().equals(Addresses.Moscow.defaultAddress())) {
                change(Addresses.Moscow.testAddress());
            } else {
                change(Addresses.Moscow.testAddress());
                change(Addresses.Moscow.defaultAddress());
            }
        }

        /** Выбрать первый адресный саджест */
        private static void selectAddressSuggest() {
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.spinner().getLocator())
            );
            if (kraken.detect().isShippingAddressSuggestsPresent()) {
                kraken.perform().click(Elements.Modals.AddressModal.addressSuggest());
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(Elements.Modals.AddressModal.saveButton().getLocator()),
                            "Неактивна кнопка сохранения адреса");
            } else {
                throw new AssertionError("Нет адресных подсказок, невозможно выбрать адрес");
            }
        }

        /** Открыть модалку ввода адреса */
        public static void openAddressModal() {
            if (kraken.detect().isAddressModalOpen()) {
                debugMessage("Пропускаем открытие модалки адреса, она уже открыта");
            } else {
                kraken.perform().click(Elements.Header.shipAddressButton());
                kraken.await().simply(1); // Ожидание анимации открытия адресной модалки
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Modals.AddressModal.popup().getLocator()),
                        "Не открылась модалка ввода адреса доставки\n");
            }
        }

        /** Очистить поле в адресной модалке */
        public static void clearAddressField() {
            kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
        }

        /** Ввести адрес в адресной модалке */
        public static void fill(String address) {
            kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), address);
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AddressModal.addressSuggest().getLocator()),
                    "Не подтянулись адресные подсказки\n"
            );
            selectAddressSuggest();
        }

        /** Применить введенный адрес в адресной модалке */
        public static void submit() throws AssertionError {
            kraken.perform().click(Elements.Modals.AddressModal.saveButton());
            if (kraken.detect().isAddressOutOfZone()) {
                verboseMessage("Выбранный адрес вне зоны доставки");
                kraken.perform().refresh();
            } else {
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Modals.AddressModal.popup().getLocator()),
                        "Превышено время ожидания применения адреса доставки\n");
            }
        }

        /** Выбрать первый в списке предыдущий адрес в адресной модалке */
        public static void choseRecent() {
            kraken.perform().click(Elements.Modals.AddressModal.recentAddress());
            kraken.await().implicitly(1); // Ожидание применения предыдущего адреса
        }

        /** Закрыть модалку адреса */
        public static void closeAddressModal() {
            if (kraken.detect().isAddressModalOpen()) {
                kraken.perform().click(Elements.Modals.AddressModal.closeButton());
                kraken.await().implicitly(1); // Ожидание анимации закрытия адресной модалки
            } else {
                message("Пропускаем закрытие модалки адреса, она уже закрыта");
            }
        }
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
            kraken.perform().click(Elements.CatalogDrawer.category(name));
            // TODO протестить
                kraken.await().implicitly(1); // Ожидание разворота категории-департамента
        }

        public static void goToTaxon(String name) {
            verboseMessage("Переходим в таксон \"" + name + "\" в шторке каталога категорий");
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
            kraken.await().implicitly(1); // Ожидание разворота категории-таксона
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

    /**  Виджет чата Jivosite */
    public static class Jivosite {

        /** Открыть чат jivosite */
        public static void open() {
            if(!kraken.detect().isJivositeChatAvailable()) {
                message("> разворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.openButton());
                kraken.await().implicitly(1); // Ожидание разворачивания виджета Jivosite
            } else {
                message("> виджет Jivosite развернут");
            }
        }

        /** Свернуть чат jivosite */
        public static void close() {
            if(kraken.detect().isJivositeChatAvailable()) {
                message("> сворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.closeButton());
                kraken.await().implicitly(1); // Ожидание сворачивания виджета Jivosite
            } else {
                message("> виджет Jivosite свернут");
            }
        }

        /** Отправить сообщение в Jivosite */
        public static void sendMessage(String text) {
            message("Jivosite");
            kraken.await().implicitly(2);
            open();
            message("> отправляем сообщение: " + text);
            kraken.perform().fillField(Elements.Jivosite.messageField(), text);
            kraken.perform().click(Elements.Jivosite.sendMessageButton());
            kraken.await().implicitly(2); // Ожидание отправки сообщения в Jivosite
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

    /** Поиск товаров */
    public static class Search {

        public static void item(String query) {
            verboseMessage("Поиск товаров по запросу '" + query + "'...");
            Field.fill(query);
            Button.hit();
            kraken.await().implicitly(1); // Ожидание загрузки результатов поиска
        }

        public static class Field {

            public static void fill(String query) {
                debugMessage("> Заполняем поле поиска: " + query);
                kraken.perform().fillField(Elements.Header.Search.inputField(), query);
                kraken.await().implicitly(1); // Ожидание загрузки поисковых саджестов
            }
        }

        public static class Button {

            public static void hit() {
                debugMessage("> Нажимаем кнопку поиска");
                kraken.perform().click((Elements.Header.Search.sendButton()));
            }
        }

        public static class CategorySuggest {

            public static void hit() {
                debugMessage("> Нажимаем категорийную подсказку в поиске");
                kraken.perform().click(Elements.Header.Search.categorySuggest());
            }
        }

        public static class ProductSuggest {

            public static void hit() {
                debugMessage("> Нажимаем товарную подсказку в поиске");
                kraken.perform().click(Elements.Header.Search.productSuggest());
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
                throw new AssertionError("⚠ Нет кнопки добавления любимого товара\n");
            }
        }

        /** Закрыть карточку товара */
        public static void close() {
            debugMessage("> закрываем карточку товара");
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
                kraken.await().implicitly(1); // Ожидание анимации открытия корзины
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.closeButton().getLocator()),
                        "Не открылась корзина\n\n");
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
                        "Не закрылась корзина\n\n");
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
                throw new AssertionError("\n\n> Не удается перейти в чекаут");
            }
        }

        /** Очистить корзину, удалив все товары */
        public static void drop() {
            verboseMessage("Очищаем козину, удаляя все товары...");
            open();
            if (!kraken.detect().isCartEmpty()) {
                Item.remove();
                if(kraken.detect().isElementPresent(Elements.Cart.item.snippet())) {
                    drop();
                }
            }
            verboseMessage("✓ Готово\n");
            close();
        }

        /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
        public static void collect() {
            if(!kraken.detect().isCheckoutButtonActive()) {
                Cart.close();
                collect(Config.TestVariables.DeliveryPrices.minOrderSum);
            } else { message("Пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
        }

        /** Набрать корзину на указанную сумму */
        public static void collect(int orderSum) {
            if(!kraken.detect().isShippingAddressSet()) {
                ShippingAddress.set(Addresses.Moscow.defaultAddress());
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
                int neededQuantity = ((orderSum - cartTotal) / (itemPrice - 1)) + 1;
                message("> добавляем в корзину \""
                        + kraken.grab().itemName() + "\" x " + neededQuantity + " шт\n> " + kraken.grab().currentURL() + "\n");
                addItem(neededQuantity);
                ItemCard.close();
                Cart.open();
            } else {
                message("В корзине достаточно товаров");
            }
        }

        /** Луп набора товара до необходимого количества */
        private static void addItem(int neededQuantity) {
            int quantity = kraken.grab().itemQuantity();
            debugMessage("В каунтере " + quantity);
            if (quantity == 1) kraken.await().implicitly(2);
            if (kraken.grab().itemQuantity() < neededQuantity) {
                ItemCard.addToCart();
                addItem(neededQuantity);
            }
        }

        /** Сниппет товара в корзине */
        public static class Item {

            /** Открыть карточку верхнего товара в корзине */
            public static void open() {
                    kraken.perform().click(Elements.Cart.item.openButton());
            }

            /** Убрать верхний товар из корзины */
            public static void remove() {
                kraken.await().simply(1); // Ожидание для стабильности
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                //TODO добавить fluent-ожидание
                kraken.perform().click(Elements.Cart.item.removeButton());
                kraken.await().implicitly(1); // Ожидание удаления продукта из корзины
            }

            /** Увеличить количество верхнего товара в корзине */
            public static void increaseQuantity() {
                kraken.await().implicitly(1); // Ожидание для стабильности
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.increaseButton());
                kraken.await().implicitly(1); // Ожидание увеличения количества товара в корзине
            }

            /** Уменьшить количество верхнего товара в корзине */
            public static void decreaseQuantity() {
                kraken.await().implicitly(1); // Ожидание для стабильности
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.decreaseButton());
                kraken.await().implicitly(1); // Ожидание уменьшения количества товара в корзине
            }
        }
    }
}