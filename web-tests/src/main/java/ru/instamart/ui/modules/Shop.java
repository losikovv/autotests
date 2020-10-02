package instamart.ui.modules;

import instamart.core.common.AppManager;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.common.pagesdata.WidgetData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import instamart.core.settings.Config;
import io.qameta.allure.Step;
import instamart.ui.common.pagesdata.EnvironmentData;

import java.util.List;

public class Shop extends Base {

    public Shop(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static class AuthModal {

        @Step("Открываем модалку авторизации")
        public static void open() {
            catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
            if (!kraken.detect().isAuthModalOpen()) {
                verboseMessage("> открываем модалку авторизации");
                if (kraken.detect().isOnLanding()) {
                    try {
                        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
                    }catch (Exception ex){
                        kraken.perform().click(Elements.Header.loginButton());
                    }
                } else {
                    try {
                        kraken.perform().click(Elements.Header.loginButton());
                    }catch (Exception ex){
                        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
                    }

                }
                kraken.await().implicitly(1); // Ожидание открытия модалки авторизации/регистрации
                try{
                    kraken.await().fluently(
                            ExpectedConditions.visibilityOfElementLocated(
                                    Elements.Modals.AuthModal.popup().getLocator()),
                            "\n> Превышено время ожидания открытия модалки авторизации/регистрации");
                }catch (Exception ex){
                    kraken.await().fluently(
                            ExpectedConditions.visibilityOfElementLocated(
                                    Elements.Modals.AuthModal.popupMail().getLocator()),
                            "\n> Превышено время ожидания открытия модалки авторизации/регистрации");
                }
            }
        }
        @Step("Закрываем форму авторизации")
        public static void close() {
            debugMessage("> закрываем форму авторизации");
            kraken.perform().click(Elements.ItemCard.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
        }
        @Step("Переключаемся на вкладку авторизации")
        public static void switchToAuthorisationTab() {
            verboseMessage("> переключаемся на вкладку авторизации");
            if(driver.findElement(Elements.Modals.AuthModal.authorisationTab()
                    .getLocator()).getAttribute("class").contains("undefined")) return;
            kraken.perform().click(Elements.Modals.AuthModal.authorisationTab());
        }
        @Step("Переключаемся на вкладку регистрации")
        public static void switchToRegistrationTab() {

            verboseMessage("> переключаемся на вкладку регистрации");
            kraken.await().fluently(ExpectedConditions.elementToBeClickable(driver.findElement(Elements.Modals.AuthModal.popupMail().getLocator())));
            WebElement parent = driver.findElement(Elements.Modals.AuthModal.popupMail().getLocator());
            WebElement element = kraken.perform().findChildElementByTagAndText(parent,By.tagName("button"),"Регистрация");
            kraken.perform().click(element);
        }
        @Step("Проверяем тип модалки авторизации")
        public static String checkAutorisationModalDialog(){
            verboseMessage("> проверяем тип модалки авторизации");
            List<WebElement> phone =driver.findElements(Elements.Modals.AuthModal.phoneNumber().getLocator());
            if(phone.size()>0){
                return "модалка с телефоном";
            }else {
                return "модалка с почтой";
            }
        }
        @Step("Переходим в форму восстановления пароля")
        public static void proceedToPasswordRecovery() {
            verboseMessage("> переходим в форму восстановления пароля");
            kraken.perform().click(Elements.Modals.AuthModal.forgotPasswordButton());
        }
        @Step("Заполняем поля формы авторизации")
        public static void fillAuthorisationForm(String email, String password) {
            verboseMessage("> заполняем поля формы авторизации");
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
        }
        @Step("Заполняем поля формы регистрации")
        public static void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
            verboseMessage("> заполняем поля формы регистрации");
            kraken.perform().fillField(Elements.Modals.AuthModal.nameField(), name);
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordConfirmationField(), passwordConfirmation);
            if (!agreementConfirmation) {
                kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
            }
        }
        @Step("Заполняем поля формы регистрации по телефону")
        public static void fillRegistrationFormByPhone(String phone, String smsCode){
            verboseMessage("> заполняем поля формы регистрации по телефону");
            kraken.perform().fillField(Elements.Modals.AuthModal.phoneNumber(),phone);
            kraken.perform().click(Elements.Modals.AuthModal.continueButton());
        }
        @Step("Отправляем форму")
        public static void submit() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.AuthModal.submitButton());
            kraken.await().implicitly(2); // Ожидание авторизации/регистрации
        }
        @Step("Нажимаем кнопку авторизация через Vkontakte")
        public static void hitVkontakteButton() {
            verboseMessage("> нажимаем кнопку авторизация через Vkontakte\n");
            WebElement vkontakteButton =
                    kraken.perform().findChildElementByTagAndIndex(
                            Elements.Modals.AuthModal.socialButtonsSectionParent(),
                            By.tagName("button"),0);
            kraken.perform().click(vkontakteButton);
        }
        @Step("Нажимаем кнопку авторизация через Facebook")
        public static void hitFacebookButton() {
            verboseMessage("> нажимаем кнопку авторизация через Facebook\n");
            WebElement facebookButton =
                    kraken.perform().findChildElementByTagAndIndex(
                            Elements.Modals.AuthModal.socialButtonsSectionParent(),
                            By.tagName("button"),1);
            kraken.perform().click(facebookButton);
        }
        @Step("Нажимаем кнопку авторизация через MailRu")
        public static void hitMailRuButton() {
            verboseMessage("> нажимаем кнопку авторизация через MailRu\n");
            WebElement mailButton =
                    kraken.perform().findChildElementByTagAndIndex(
                            Elements.Modals.AuthModal.socialButtonsSectionParent(),
                            By.tagName("button"),2);
            kraken.perform().click(mailButton);
        }
        @Step("Нажимаем кнопку авторизация через SberID")
        public static void hitSberIdButton() {
            verboseMessage("> нажимаем кнопку авторизация через SberID\n");
            kraken.perform().click(Elements.Modals.AuthModal.sberButton());
        }
    }

    public static class RecoveryModal {
        @Step("Закрываем модалку")
        public static void close() {
            verboseMessage("> закрываем модалку\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.closeButton());
        }
        @Step("Возвращаемся назад")
        public static void proceedBack() {
            verboseMessage("> возвращаемся назад\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.backButton());
            kraken.await().simply(1); // Ожидание возврата назад из формы восстановления пароля
        }
        @Step("Заполняем форму запроса восстановления пароля по email: {0}")
        public static void fillRequestForm(String email) {
            verboseMessage("> заполняем форму запроса восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.emailField(), email);
        }
        @Step("Заполняем форму восстановления пароля: {0}, {1}")
        public static void fillRecoveryForm(String password, String passwordConfirmation) {
            verboseMessage("> заполняем форму восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordConfirmationField(), passwordConfirmation);
        }
        @Step("Отправляем форму")
        public static void submitRequest() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRequestButton());
            kraken.await().simply(5); // Ожидание отправки письма восстановлкния пароля
        }
        @Step("Отправляем форму")
        public static void submitRecovery() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRecoveryButton());
            kraken.await().implicitly(1); // Ожидание восстановления пароля
        }
    }

    /** Адрес доставки */
    public static class ShippingAddressModal {

        /** Открыть модалку ввода адреса */
        @Step("Проверяем открыта ли модалка авторизации, если нет открываем")
        public static void open() {
            if (kraken.detect().isAddressModalOpen()) {
                verboseMessage("> пропускаем открытие модалки адреса, она уже открыта");
            } else {
                catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
                kraken.perform().click(Elements.Header.shipAddressButton());
                kraken.await().simply(1); // Ожидание анимации открытия адресной модалки
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Modals.AddressModal.popup().getLocator()),
                        "Не открылась модалка ввода адреса доставки\n");
            }
        }

        /** Очистить поле в адресной модалке */
        @Step("Очищаем поле в адресной модалке")
        public static void clearAddressField() {
            kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
        }

        /** Ввести адрес в адресной модалке */
        @Step("Вводим адрес в адресной модалке: {0}")
        public static void fill(String address) {
            kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), address);
            kraken.await().simply(1); // Ожидание адресных подсказок
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AddressModal.addressSuggest().getLocator()),
                    "Не подтянулись адресные подсказки\n"
            );
            selectAddressSuggest();
        }

        /** Выбрать первый адресный саджест */
        @Step("Выбираем первый первый предложенный адрес")
        private static void selectAddressSuggest() {
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.spinner().getLocator())
            );
            if (kraken.detect().isShippingAddressSuggestsPresent()) {
                kraken.perform().click(Elements.Modals.AddressModal.addressSuggest());
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(Elements.Modals.AddressModal.submitButton().getLocator()),
                        "Неактивна кнопка сохранения адреса");
            } else {
                throw new AssertionError("Нет адресных подсказок, невозможно выбрать адрес");
            }
        }

        /** Применить введенный адрес в адресной модалке */
        @Step("Применяем введенный адрес в адресной модалке")
        public static void submit() throws AssertionError {
            kraken.perform().click(Elements.Modals.AddressModal.submitButton());
            if (kraken.detect().isAddressOutOfZone()) {
                verboseMessage("> выбранный адрес вне зоны доставки");
                kraken.perform().refresh();
            } else {
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Modals.AddressModal.popup().getLocator()),
                        "Превышено время ожидания применения адреса доставки\n");
            }
        }

        /** Выбрать первый в списке предыдущий адрес в адресной модалке */
        @Step("Выбираем первый в списке предыдущий адрес в адресной модалке")
        public static void chooseRecentAddress() {
            kraken.perform().click(Elements.Modals.AddressModal.recentAddress());
            kraken.await().implicitly(1); // Ожидание применения предыдущего адреса
        }

        /** Закрыть модалку адреса */
        @Step("Закрываем модалку адреса")
        public static void close() {
            if (kraken.detect().isAddressModalOpen()) {
                kraken.perform().click(Elements.Modals.AddressModal.closeButton());
                kraken.await().implicitly(1); // Ожидание анимации закрытия адресной модалки
            } else {
                message("> пропускаем закрытие модалки адреса, она уже закрыта");
            }
        }
    }

    /** Модалка выбора магазина */
    public static class StoresModal {

        /** Выбрать первый доступный магазин */
        @Step("Выбираем первый доступный магазин")
        public static void selectFirstStore() {
            if(kraken.detect().isStoresModalOpen()) {
                kraken.perform().click(Elements.Modals.StoresModal.firstStoreAvailable());
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                (Elements.Modals.StoresModal.popup().getLocator())));
            } else {
                throw new AssertionError(
                        "Не открылась модалка выбора магазинов по новому адресу");
            }
        }
    }

    /** Шторка выбора магазина */
    public static class StoresDrawer {

        /** Выбрать первый доступный магазин */
        @Step("Выбираем первый доступный магазин")
        public static void selectFirstStore() {
            if(!kraken.detect().isStoresDrawerOpen()) {
                open();
            }
            kraken.perform().click(Elements.StoreSelector.storeCard());
        }

        /** Открыть шторку выбора магазина */
        @Step("Открываем шторку выбора магазина")
        public static void open() {
            kraken.perform().click(Elements.Header.storeButton());
            kraken.await().implicitly(1); // Ожидание открытия шторки выбора магазина
        }

        /** Закрыть шторку выбора магазина */
        @Step("Закрываем шторку выбора магазина")
        public static void close() {
            kraken.perform().click(Elements.StoreSelector.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия шторки выбора магазина
        }
    }

    /** Шторка каталога категорий */
    public static class CatalogDrawer {
        @Step("Открываем шторку каталога категорий")
        public static void open() {
            verboseMessage("> открываем шторку каталога категорий");
            if (!kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.Header.catalogButton());
                kraken.await().simply(1); // Ожидание анимации открытия шторки каталога
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.CatalogDrawer.closeButton().getLocator()),"Не открылась шторка каталога\n");
            }
        }
        @Step("Переходим в департамент: {0} в шторке каталога категорий")
        public static void goToDepartment(String name) {
            verboseMessage("> переходим в департамент \"" + name + "\" в шторке каталога категорий");
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
            kraken.await().implicitly(1); // Ожидание разворота категории-департамента
        }
        @Step("Переходим в таксон: {0} в шторке каталога категорий")
        public static void goToTaxon(String name) {
            verboseMessage("> переходим в таксон \"" + name + "\" в шторке каталога категорий");
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
            kraken.await().implicitly(1); // Ожидание разворота категории-таксона
        }
        @Step("Закрываем шторку каталога категорий")
        public static void close() {
            verboseMessage("> закрываем шторку каталога категорий");
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
        @Step("Открываем всплывающее меню профиля")
        public static void open() {
            verboseMessage("> открываем всплывающее меню профиля");
            if(!kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else verboseMessage("> пропускаем открытие меню аккаунта, уже открыто");
        }
        @Step("Закрываем всплывающее меню профиля")
        public static void close() {
            verboseMessage("> закрываем всплывающее меню профиля");
            if(kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else verboseMessage("> пропускаем закрытие меню аккаунта, уже закрыто");
        }
    }

    /**  Виджет чата Jivosite */
    public static class Jivosite {

        /** Открыть чат jivosite */
        @Step("Открываем чат jivosite")
        public static void open() {
            if(!kraken.detect().isJivositeChatAvailable()) {
                verboseMessage("> разворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.openButton());
                kraken.await().implicitly(1); // Ожидание разворачивания виджета Jivosite
            } else {
                verboseMessage("> виджет Jivosite развернут");
            }
        }

        /** Свернуть чат jivosite */
        @Step("Сворачиваем чат jivosite")
        public static void close() {
            if(kraken.detect().isJivositeChatAvailable()) {
                verboseMessage("> сворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.closeButton());
                kraken.await().implicitly(1); // Ожидание сворачивания виджета Jivosite
            } else {
                verboseMessage("> виджет Jivosite свернут");
            }
        }

        /** Отправить сообщение в Jivosite */
        @Step("Отправляем сообщение в Jivosite: {0}")
        public static void sendMessage(String text) {
            verboseMessage("Jivosite");
            kraken.await().implicitly(2);
            open();
            verboseMessage("> отправляем сообщение: " + text);
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
            @Step("Добавляем товар в корзину через сниппет товара в каталоге")
            public static void addToCart() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.plusButton());
                kraken.await().implicitly(1); // Ожидание добавления товара в корзину
            }

            /** Добавить товар в корзину через сниппет товара в партнерском виджете */
            @Step("Добавляем товар в корзину через сниппет товара в партнерском виджете")
            public static void addToCart(WidgetData widget) {
                verboseMessage("> добавляем в корзину товар из виджета " + widget.getId());
                if (widget.getProvider().equals("RetailRocket")) {
                    kraken.perform().hoverOn(Elements.RetailRocket.item(widget.getId()));
                    kraken.perform().click(Elements.RetailRocket.addButton(widget.getId()));
                }
                kraken.await().implicitly(3); // Ожидание добавления товара в корзину из виджета
            }

            /** Удалить товар из корзины через сниппет товара в каталоге */
            @Step("Удаляем товар из корзины через сниппет товара в каталоге")
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.minusButton());
                kraken.await().implicitly(1); // Ожидание удаления товара из корзины
            }

            //TODO public void removeFromCart(WidgetData widget) {}

            /** Добавить товар в любимые через сниппет товара в каталоге */
            @Step("Добавляем товар в любимые товары через сниппет товара в каталоге")
            public static void addToFavorites() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.favButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Открыть карточку товара в каталоге */
            @Step("Открываем карточку товара в каталоге")
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
            @Step("Открываем карточку товара в партнерском виджете")
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
        @Step("Ищем не существующий товар")
        public static void nonexistingItem() {
            item(Config.TestVariables.TestParams.ItemSearch.emptyResultsQuery);
        }

        // TODO придумать решение для nonfood-магазинов - поиск заведомо существующего товара
        @Step("Ищем существующий товар")
        public static void existingItem() {
            item(Config.TestVariables.TestParams.ItemSearch.testQuery);
        }
        @Step("Ищем товары по запросу: {0}")
        public static void item(String query) {
            verboseMessage("> поиск товаров по запросу '" + query + "'...");
            Field.fill(query);
            Button.hit();
            kraken.await().implicitly(1); // Ожидание загрузки результатов поиска
        }

        public static class Field {
            @Step("Заполняем поле поиска: {0}")
            public static void fill(String query) {
                verboseMessage("> заполняем поле поиска: " + query);
                kraken.perform().fillField(Elements.Header.Search.inputField(), query);
                kraken.await().implicitly(1); // Ожидание загрузки поисковых саджестов
            }
        }

        public static class Button {
            @Step("Нажимаем кнопку поиска")
            public static void hit() {
                debugMessage("> нажимаем кнопку поиска");
                kraken.perform().click((Elements.Header.Search.sendButton()));
            }
        }

        public static class CategorySuggest {
            @Step("Нажимаем категорийную подсказку в поиске")
            public static void hit() {
                debugMessage("> нажимаем категорийную подсказку в поиске");
                kraken.perform().click(Elements.Header.Search.categorySuggest());
            }
        }

        public static class ProductSuggest {
            @Step("Нажимаем товарную подсказку в поиске")
            public static void hit() {
                verboseMessage("> нажимаем товарную подсказку в поиске");
                kraken.perform().click(Elements.Header.Search.productSuggest());
            }
        }
    }

    public static class UserProfile {

        public static class OrderHistory {
            @Step("Нажимаем кнопку фильтра всех заказов в истории заказов")
            public static void applyFilterAll() {
                kraken.await().simply(1); // Ожидание подгрузки фильтров
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.allOrdersFilterButton());
            }
            @Step("Нажимаем кнопку фильтра завершенных заказов в истории заказов")
            public static void applyFilterComplete() {
                kraken.await().simply(1); // Ожидание подгрузки фильтров
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.completeOrdersFilterButton());
            }
            @Step("Нажимаем кнопку фильтра активных заказов в истории заказов")
            public static void applyFilterActive() {
                kraken.await().simply(1); // Ожидание подгрузки фильтров
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.activeOrdersFilterButton());
            }
            @Step("Нажимаем кнопку 'Перейти к покупкам' на плейсхолдере пустой истории заказов")
            public static void goShopping() {
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.goShoppingButton());
            }
        }
    }

    /** Любимые товары */
    public static class Favorites {

        /** Открыть любимые товары по кнопке в шапке сайта */
        @Step("Открываем любимые товары по кнопке в шапке сайта")
        public static void open() {
            kraken.perform().click(Elements.Header.favoritesButton());
            kraken.await().implicitly(2); // Ожидание открытия Любимых товаров
        }

        /** Показать все любимые товары */
        @Step("Показываем все любимые товары")
        public static void applyFilterAllItems() {
            kraken.perform().click(Elements.Favorites.allItemsFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "Все товары" в избранном
        }

        /** Показать любимые товары, которые есть в наличии */
        @Step("Показываем любимые товары, которые есть в наличии")
        public static void applyFilterInStock() {
            kraken.perform().click(Elements.Favorites.inStockFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "В наличии" в избранном
        }

        /** Показать любимые товары, которых нет в наличии */
        @Step("Показываем любимые товары, которых нет в наличии")
        public static void applyFilterNotInStock() {
            kraken.perform().click(Elements.Favorites.outOfStockFilterButton());
            kraken.await().implicitly(1); // Ожидание открытия вкладки "Не в наличии" в избранном
        }

        /** Подгрузить следующую страницу любимых товаров по кнопке "Показать ещё" */
        @Step("Подгружаем следующую страницу любимых товаров по кнопке \"Показать ещё\"")
        public static void showMore() {
            kraken.perform().click(Elements.Favorites.showMoreButton());
            kraken.await().implicitly(1); // Ожидание загрузки следующей страницы любимых товаров
        }

        /** Сниппет любимого товара */
        public static class Item {

            /** Добавить первый любимый товар в корзину через сниппет товара */
            @Step("Добавляем первый любимый товар в корзину через сниппет товара")
            public static void addToCart() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.plusButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Удалить первый любимый товар из корзины через сниппет товара */
            @Step("Удаляем первый любимый товар из корзины через сниппет товара")
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.minusButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            }

            /** Удалить первый любимый товар из любимых через сниппет товара */
            @Step("Удаляем первый любимый товар из любимых через сниппет товара")
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
        @Step("Добавляем товар в корзину по кнопке [+] в карточке товара")
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
            verboseMessage("> жмем +1");
            kraken.perform().click(Elements.ItemCard.plusButton());
            //kraken.await().simply(1); // Ожидание добавления +1 товара в карточке
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator()),
                        "Не раздизаблилась кнопка добавления товара в корзину"
            );
        }

        /** Убрать товар из корзины по кнопке [-] в карточке товара */
        @Step("Убираем товар из корзины по кнопке [-] в карточке товара")
        public static void removeFromCart() {
            if (kraken.detect().isElementDisplayed(Elements.ItemCard.minusButton())) {
                kraken.perform().click(Elements.ItemCard.minusButton());
                kraken.await().simply(1); // Ожидание убавления -1 товара в карточке
            } else {
                verboseMessage("> Кнопка 'Минус' не отображается");
            }
        }

        /** Нажать кнопку любимого товара в карточке товара */
        @Step("Нажимаем кнопку любимого товара в карточке товара")
        public static void addToFavorites() {
            if (kraken.detect().isElementDisplayed(Elements.ItemCard.addToFavoritesButton())) {
                kraken.perform().click(Elements.ItemCard.addToFavoritesButton());
                kraken.await().implicitly(1); // Ожидание добавления любимого товара
            } else {
                throw new AssertionError("⚠ Нет кнопки добавления любимого товара\n");
            }
        }

        /** Закрыть карточку товара */
        @Step("Закрываем карточку товара")
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
        @Step("Открываем корзину")
        public static void open() {
            if (!kraken.detect().isCartOpen()) {
                kraken.perform().refresh(); // Доджим рандомные подвисания, из-за которых иногда не сразу открывается корзина
                verboseMessage("> открываем корзину");
                kraken.perform().click(Elements.Header.cartButton());
                kraken.await().implicitly(1); // Ожидание анимации открытия корзины
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.closeButton().getLocator()),
                        "Не открылась корзина\n\n");
            } else {
                verboseMessage("> пропускаем открытие корзины, уже открыта");
            }
        }

        /** Закрыть корзину */
        @Step("Закрываем корзину")
        public static void close() {
            if (kraken.detect().isCartOpen()) {
                verboseMessage("> закрываем корзину");
                kraken.perform().click(Elements.Cart.closeButton());
                kraken.await().simply(1);
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Cart.drawer().getLocator()),
                        "Не закрылась корзина\n\n");
            } else {
                verboseMessage("> пропускаем закрытие корзины, уже закрыта");
            }
        }

        /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
        @Step("Переходим в чекаут нажатием кнопки \"Сделать заказ\" в корзине")
        public static void proceedToCheckout() {
            if (kraken.detect().isCheckoutButtonActive()) {
                kraken.perform().click(Elements.Cart.checkoutButton());
            } else {
                verboseMessage("> кнопка перехода в чекаут неактивна");
                throw new AssertionError("\n\n> Не удается перейти в чекаут из корзины, кнопка перехода неактивна");
            }
        }

        /** Очистить корзину, удалив все товары */
        @Step("Очищаем корзину, удалив все товары")
        public static void drop() {
            verboseMessage("> очищаем козину, удаляя все товары...");
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
        @Step("Набираем корзину на минимальную сумму, достаточную для оформления заказа")
        public static void collect() {
            if(!kraken.detect().isCheckoutButtonActive()) {
                Cart.close();
                collect(Config.TestVariables.DeliveryPrices.minOrderSum);
            } else { verboseMessage("> пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
        }

        /** Набрать корзину на указанную сумму */
        @Step("Набираем корзину на указанную сумму: {0}")
        public static void collect(int orderSum) {
            if(!kraken.detect().isShippingAddressSet()) {
                User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
            }
            verboseMessage("> собираем корзину товаров на сумму " + orderSum + "\u20BD...");
            int cartTotal = kraken.grab().cartTotalRounded();
            if(cartTotal < orderSum) {
                Cart.close();
                if(!kraken.detect().isProductAvailable()) {
                    verboseMessage("> нет товаров на текущей странице " + kraken.grab().currentURL());
                    kraken.get().page(Pages.Retailers.metro());}
                Catalog.Item.open();
                int itemPrice = kraken.grab().itemPriceRounded();

                // Формула расчета кол-ва товара
                int neededQuantity;
                if(cartTotal > 0) {
                    neededQuantity = ((orderSum - cartTotal) / (itemPrice - 1)) + 2;
                } else {
                    neededQuantity = ((orderSum - cartTotal) / (itemPrice - 1)) + 1;
                }

                verboseMessage("> добавляем в корзину \""
                        + kraken.grab().itemName() + "\" x " + neededQuantity + " шт\n> " + kraken.grab().currentURL() + "\n");
                addItemByText(neededQuantity);
                ItemCard.close();
                Cart.open();
            } else {
                verboseMessage("> в корзине достаточно товаров");
            }
        }

        /** Луп набора товара до необходимого количества */
        private static void addItem(int neededQuantity) {
            int quantity = kraken.grab().itemQuantity();
            verboseMessage("> в каунтере " + quantity);
            if (quantity == 1) kraken.await().implicitly(2);
            if (kraken.grab().itemQuantity() < neededQuantity) {
                ItemCard.addToCart();
                addItem(neededQuantity);
            }
        }

        /** Луп набора товара до необходимого количества */
        private static void addItemByText(int neededQuantity) {
            String quantity = kraken.grab().itemQuantityByText();
            verboseMessage("> количество товара: " + quantity);
            kraken.grab().addItemCard();
            kraken.await().fluently(
                    ExpectedConditions.elementToBeClickable(
                            Elements.ItemCard.cartNew().getLocator()),
                    "иконка корзины не появилась\n");
            if (neededQuantity==1) kraken.await().implicitly(2);
            else kraken.grab().addItemCard(neededQuantity);
            /*
            if (kraken.grab().itemQuantity() < neededQuantity) {
                ItemCard.addToCart();
                addItem(neededQuantity);
            }
             */
        }

        /** Сниппет товара в корзине */
        public static class Item {

            /** Открыть карточку верхнего товара в корзине */
            @Step("Открываем карточку верхнего товара в корзине")
            public static void open() {
                    kraken.perform().click(Elements.Cart.item.openButton());
            }

            /** Убрать верхний товар из корзины */
            @Step("Убираем верхний товар из корзины")
            public static void remove() {
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.await().simply(1);
                kraken.perform().hoverOn(Elements.Cart.item.snippet()); // небольшой костылек для стобильности
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.item.removeButton().getLocator()),
                                    "Не появилась кнопка удаления товара в корзине\n\n");
                kraken.perform().click(Elements.Cart.item.removeButton());
                kraken.await().implicitly(1); // Ожидание удаления продукта из корзины
            }

            /** Увеличить количество верхнего товара в корзине */
            @Step("Увеличиваем количество верхнего товара в корзине")
            public static void increaseQuantity() {
                kraken.await().implicitly(1); // Ожидание для стабильности
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.increaseButton());
                kraken.await().implicitly(1); // Ожидание увеличения количества товара в корзине
            }

            /** Уменьшить количество верхнего товара в корзине */
            @Step("Уменьшаем количество верхнего товара в корзине")
            public static void decreaseQuantity() {
                kraken.await().implicitly(1); // Ожидание для стабильности
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.decreaseButton());
                kraken.await().implicitly(1); // Ожидание уменьшения количества товара в корзине
            }
        }
    }
}
