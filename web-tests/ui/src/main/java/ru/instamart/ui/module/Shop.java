package ru.instamart.ui.module;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.ui.data.ElementData;
import ru.instamart.kraken.testdata.pagesdata.WidgetData;
import ru.instamart.ui.Elements;

import java.util.List;

import static io.qameta.allure.Allure.step;

@Slf4j
public final class Shop extends Base {

    public Shop(final AppManager kraken) {
        super(kraken);
    }

    public static class AuthModal {

        @Step("Открываем модальное окно")
        public static void openAuthOnLanding() {
            log.info("> открываем модалку авторизации");
            kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
            log.info("> форма авторизации открыта");
        }

        @Step("Открываем модалку авторизации")
        public static void open() {
            catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
            if (!kraken.detect().isAuthModalOpen()) {
                log.info("> открываем модалку авторизации");
                if (kraken.detect().isOnLanding()) {
                    try {
                        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
                        kraken.await().implicitly(1);//это здесь нужно из-за работы с js на форме авторизации
                    } catch (Exception ex){
                        kraken.perform().click(Elements.Header.loginButton());
                    }
                } else {
                    try {
                        kraken.perform().click(Elements.Header.loginButton());
                    } catch (Exception ex){
                        kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
                    }

                }
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Modals.AuthModal.phoneNumber().getLocator()),
                        "\n> Превышено время ожидания открытия модалки авторизации/регистрации",2);
            }
        }

        @Step("Открываем модалку авторизации на странице ретейлера")
        public static void openAuthRetailer(){
            catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
            log.info("> открываем модалку авторизации");
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Header.storeButton().getLocator()));
            kraken.perform().click(Elements.Header.loginButton());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AuthModal.phoneNumber().getLocator()),
                    "\n> Превышено время ожидания открытия модалки авторизации/регистрации",2);
            log.info("> форма авторизации открыта");
        }

        @Step("Открываем модалку авторизации на Лендинге")
        public static void openAuthLending(){
            log.info("> открываем модалку авторизации");
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Landings.SbermarketLanding.MainBlock.Stores.homeLanding().getLocator()));
            kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AuthModal.phoneNumber().getLocator()),
                    "\n> Превышено время ожидания открытия модалки авторизации/регистрации",2);
            log.info("> форма авторизации открыта");
        }

        @Step("Закрываем форму авторизации")
        public static void close() {
            log.info("> закрываем форму авторизации");
            kraken.perform().click(Elements.ItemCard.closeButton());
//            kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
        }
        @Step("Переключаемся на вкладку авторизации")
        public static void switchToAuthorisationTab() {
            log.info("> переключаемся на вкладку авторизации");
            if (AppManager.getWebDriver().findElement(Elements.Modals.AuthModal.authorisationTab()
                    .getLocator()).getAttribute("class").contains("undefined")) return;
            kraken.perform().click(Elements.Modals.AuthModal.authorisationTab());
        }

        @Step("Проверяем тип модалки авторизации")
        public static String checkAutorisationModalDialog(){
            log.info("> проверяем тип модалки авторизации");
            List<WebElement> phone = AppManager.getWebDriver().findElements(Elements.Modals.AuthModal.phoneNumber().getLocator());
            if (phone.size()>0) {
                return "модалка с телефоном";
            } else {
                return "модалка с почтой";
            }
        }
        @Step("Переходим в форму восстановления пароля")
        public static void proceedToPasswordRecovery() {
            log.info("> переходим в форму восстановления пароля");
            kraken.perform().click(Elements.Modals.AuthModal.forgotPasswordButton());
        }
        @Step("Заполняем поля формы авторизации")
        public static void fillAuthorisationForm(String email, String password) {
            log.info("> заполняем поля формы авторизации");
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
        }
        @Step("Заполняем поля формы регистрации")
        public static void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
            log.info("> заполняем поля формы регистрации");
            kraken.perform().fillField(Elements.Modals.AuthModal.nameField(), name);
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordConfirmationField(), passwordConfirmation);
            if (!agreementConfirmation) {
                kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
            }
        }

        @Step("Отправляем форму")
        public static void submit() {
            log.info("> отправляем форму");
            kraken.perform().click(Elements.Modals.AuthModal.submitButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.Modals.AuthModal.submitButton().getLocator()),
                    "не закрывается форма регистрации",Config.BASIC_TIMEOUT);
        }
        @Step("Отправляем форму регистрации")
        public static void submitRegistration() {
            log.info("> отправляем форму");
            kraken.perform().click(Elements.Modals.AuthModal.submitButtonRegistration());
        }
        @Step("Нажимаем кнопку авторизация через Vkontakte")
        public static void hitVkontakteButton() {
            log.info("> нажимаем кнопку авторизация через Vkontakte");
            kraken.perform().setWindow();
            kraken.perform().click(Elements.Modals.AuthModal.vkontakteButton());
        }
        @Step("Нажимаем кнопку авторизация через Facebook")
        public static void hitFacebookButton() {
            log.info("> нажимаем кнопку авторизация через Facebook");
            kraken.perform().click(Elements.Modals.AuthModal.facebookButton());
        }
        @Step("Нажимаем кнопку авторизация через MailRu")
        public static void hitMailRuButton() {
            log.info("> нажимаем кнопку авторизация через MailRu");
            kraken.perform().click(Elements.Modals.AuthModal.mailruButton());
        }
        @Step("Нажимаем кнопку авторизация через SberID")
        public static void hitSberIdButton() {
            log.info("> нажимаем кнопку авторизация через SberID");
            kraken.perform().click(Elements.Modals.AuthModal.sberButton());
        }
    }

    public static class RecoveryModal {
        @Step("Закрываем модалку")
        public static void close() {
            log.info("> закрываем модалку");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.closeButton());
        }
        @Step("Возвращаемся назад")
        public static void proceedBack() {
            log.info("> возвращаемся назад");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.backButton());
            ThreadUtil.simply(1); // Ожидание возврата назад из формы восстановления пароля
        }
        @Step("Заполняем форму запроса восстановления пароля по email: {0}")
        public static void fillRequestForm(String email) {
            log.info("> заполняем форму запроса восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.emailField(), email);
        }
        @Step("Заполняем форму восстановления пароля: {0}, {1}")
        public static void fillRecoveryForm(String password, String passwordConfirmation) {
            log.info("> заполняем форму восстановления пароля");
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.PasswordRecoveryModal.passwordConfirmationField(), passwordConfirmation);
        }
        @Step("Отправляем форму")
        public static void submitRequest() {
            log.info("> отправляем форму");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRequestButton());
            ThreadUtil.simply(5); // Ожидание отправки письма восстановлкния пароля
        }
        @Step("Отправляем форму")
        public static void submitRecovery() {
            log.info("> отправляем форму");
            kraken.perform().click(Elements.Modals.PasswordRecoveryModal.submitRecoveryButton());
        }
    }

    /** Модалка выбора магазина */
    public static class StoresModal {

        /** Выбрать первый доступный магазин */
        @Step("Выбираем первый доступный магазин")
        public static void selectFirstStore() {
            log.info("> выбираем первый доступный магазин");
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
            log.info("> выбираем первый доступный магазин");
            if(!kraken.detect().isStoresDrawerOpen()) {
                open();
            }
            kraken.perform().click(Elements.StoreSelector.storeCard());
        }

        /** Открыть шторку выбора магазина */
        @Step("Открываем шторку выбора магазина")
        public static void open() {
            log.info("> открываем шторку выбора магазина");
            kraken.perform().click(Elements.Header.storeButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.StoreSelector.drawer().getLocator()),"не открывается шторка выбора магазинов",
                    Config.BASIC_TIMEOUT);
        }

        /** Закрыть шторку выбора магазина */
        @Step("Закрываем шторку выбора магазина")
        public static void close() {
            log.info("> закрываем шторку выбора магазина");
            kraken.perform().click(Elements.StoreSelector.closeButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.StoreSelector.drawer().getLocator()),"не закрывается выбора магазинов",
                    Config.BASIC_TIMEOUT);
        }
    }

    /** Шторка каталога категорий */
    public static class CatalogDrawer {
        @Step("Открываем шторку каталога категорий")
        public static void open() {
            log.info("> открываем шторку каталога категорий");
            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),2);
            kraken.perform().click(Elements.Header.catalogButton());
            kraken.await().fluently(
                    ExpectedConditions.elementToBeClickable(
                            Elements.CatalogDrawer.closeButton().getLocator()),"Не открылась шторка каталога\n");
        }
        @Step("Переходим в департамент: {0} в шторке каталога категорий")
        public static void goToDepartment(String name) {
            log.info("> переходим в департамент {} в шторке каталога категорий", name);
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.CatalogDrawer.categoryFirstLevel(name).getLocator()));
            kraken.perform().hoverOn(Elements.CatalogDrawer.categoryFirstLevel(name));
            kraken.perform().click(Elements.CatalogDrawer.categoryFirstLevel(name));
        }
        @Step("Переходим в таксон: {0} в шторке каталога категорий")
        public static void goToTaxon(String name) {
            log.info("> переходим в таксон {} в шторке каталога категорий", name);
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.CatalogDrawer.category(name).getLocator()));
            kraken.perform().hoverOn(Elements.CatalogDrawer.category(name));
            kraken.perform().click(Elements.CatalogDrawer.category(name));
        }
        @Step("Закрываем шторку каталога категорий")
        public static void close() {
            log.info("> закрываем шторку каталога категорий");
            if (kraken.detect().isCatalogDrawerOpen()) {
                kraken.perform().click(Elements.CatalogDrawer.closeButton());
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
            log.info("> открываем всплывающее меню профиля");
            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(Elements.Header.profileButton().getLocator()),
                    "кнопка перехода в профиль пользователя недоступна");
            if(!kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else log.info("> пропускаем открытие меню аккаунта, уже открыто");
        }

        @Step("Закрываем всплывающее меню профиля")
        public static void close() {
            log.info("> закрываем всплывающее меню профиля");
            if(kraken.detect().isAccountMenuOpen()) {
                kraken.perform().click(Elements.Header.profileButton());
            } else log.info("> пропускаем закрытие меню аккаунта, уже закрыто");
        }

        @Step("Открываем модалку с зонами доставки из меню профиля")
        public static void openDelivery(){
            log.info("> открываем модалку с зонами доставки из меню профиля");
            kraken.perform().click(Elements.AccountMenu.deliveryButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Modals.DeliveryModal.popup().getLocator()),
                    "не открывается меню с зонами доставки",Config.BASIC_TIMEOUT);
            log.info("> меню с зонами доставки открыто");
        }

        @Step("Закрываем модалку с зонами доставки из меню профиля")
        public static void closeDelivery(){
            log.info("> закрываем модалку с зонами доставки из меню профиля");
            kraken.perform().click(Elements.AccountMenu.deliveryModalButtonClose());
            log.info("> меню с зонами доставки закрыто");
        }
    }

    /**  Виджет чата Jivosite */
    public static class Jivosite {

        /** Открыть чат jivosite */
        @Step("Открываем чат jivosite")
        public static void open() {
            if(!kraken.detect().isJivositeChatAvailable()) {
                log.info("> разворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.openButton());
                //TODO fluent Ожидание разворачивания виджета Jivosite
            } else {
                log.info("> виджет Jivosite развернут");
            }
        }

        /** Свернуть чат jivosite */
        @Step("Сворачиваем чат jivosite")
        public static void close() {
            if(kraken.detect().isJivositeChatAvailable()) {
                log.info("> сворачиваем виджет Jivosite");
                kraken.perform().click(Elements.Jivosite.closeButton());
                //TODO fluent Ожидание сворачивания виджета Jivosite
            } else {
                log.info("> виджет Jivosite свернут");
            }
        }

        /** Отправить сообщение в Jivosite */
        @Step("Отправляем сообщение в Jivosite: {0}")
        public static void sendMessage(String text) {
            log.info("Jivosite");
            open();
            log.info("> отправляем сообщение: {}", text);
            kraken.perform().fillField(Elements.Jivosite.messageField(), text);
            kraken.perform().click(Elements.Jivosite.sendMessageButton());
            //TODO fluent Ожидание отправки сообщения в Jivosite
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
                //TODO fluent Ожидание добавления товара в корзину
            }

            /** Добавить товар в корзину через сниппет товара в партнерском виджете */
            @Step("Добавляем товар в корзину через сниппет товара в партнерском виджете")
            public static void addToCart(WidgetData widget) {
                log.info("> добавляем в корзину товар из виджета {}", widget.getId());
                if (widget.getProvider().equals("RetailRocket")) {
                    kraken.perform().hoverOn(Elements.RetailRocket.item(widget.getId()));
                    kraken.perform().click(Elements.RetailRocket.addButton(widget.getId()));
                }
                //TODO fluent Ожидание добавления товара в корзину из виджета
            }

            /** Удалить товар из корзины через сниппет товара в каталоге */
            @Step("Удаляем товар из корзины через сниппет товара в каталоге")
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
                kraken.perform().click(Elements.Catalog.Product.minusButton());
                //TODO fluent Ожидание удаления товара из корзины
            }

            //TODO public void removeFromCart(WidgetData widget) {}

            /** Добавить товар в любимые через сниппет товара в каталоге */
            @Step("Добавляем товар в любимые товары через сниппет товара в каталоге")
            public static void addToFavorites() {
                log.info("Добавляем товар в любимые товары через сниппет товара в каталоге");
                catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),2);
                step("Наводим курсор на элемент", () ->
                        kraken.perform().hoverOn(Elements.Catalog.Product.snippet()));
                step("Добавляем товар в любимые", ()->
                        kraken.perform().click(Elements.Catalog.Product.favButton()));
                //TODO fluent Ожидание добавления любимого товара
            }

            /** Открыть карточку товара в каталоге */
            @Step("Открываем карточку товара в каталоге")
            public static void open() {
                catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),2);
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
                log.info("Открываем карточку товара из виджета {}", widget.getId());

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

        @Step("Ищем товары по запросу: {0}")
        public static void searchItem(String query) {
            log.info("> поиск товаров по запросу '{}'...", query);
            kraken.perform().fillField(Elements.Header.Search.inputField(), query);
            log.info("> нажимаем кнопку поиска");
            kraken.perform().click((Elements.Header.Search.sendButton()));
        }

        @Step("Заполняем поле поиска: {0}")
        public static void searchField(String query) {
            log.info("> заполняем поле поиска: {}", query);
            kraken.perform().fillField(Elements.Header.Search.inputField(), query);
            Actions actions = new Actions(AppManager.getWebDriver());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Header.Search.searchListResult().getLocator()),
                    "не подтянулись поисковые подсказки", 15);
            actions.sendKeys(Keys.ENTER).perform();
        }

        @Step("Заполняем поле поиска: {0}")
        public static void searchField(String query,Boolean submit) {
            log.info("> заполняем поле поиска: {}", query);
            kraken.perform().fillField(Elements.Header.Search.inputField(), query);
            if(submit){
                Actions actions = new Actions(AppManager.getWebDriver());
                kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Header.Search.searchListResult().getLocator()),
                        "не подтянулись поисковые подсказки", 15);
                actions.sendKeys(Keys.ENTER).perform();
            }
        }

        @Step("Нажимаем категорийную подсказку в поиске")
        public static void selectCategorySuggest() {
            log.info("> нажимаем категорийную подсказку в поиске");
            kraken.perform().click(Elements.Header.Search.categorySuggest());
        }

        @Step("Нажимаем товарную подсказку в поиске")
        public static void selectProductSuggest() {
            log.info("> нажимаем товарную подсказку в поиске");
            kraken.perform().click(Elements.Header.Search.productSuggest());
        }
    }

    public static class UserProfile {

        public static class OrderHistory {
            @Step("Нажимаем кнопку фильтра всех заказов в истории заказов")
            public static void applyFilterAll() {
                ThreadUtil.simply(1); // Ожидание подгрузки фильтров
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.allOrdersFilterButton());
            }
            @Step("Нажимаем кнопку фильтра завершенных заказов в истории заказов")
            public static void applyFilterComplete() {
                ThreadUtil.simply(1); // Ожидание подгрузки фильтров
                kraken.perform().click(Elements.UserProfile.OrdersHistoryPage.completeOrdersFilterButton());
            }
            @Step("Нажимаем кнопку фильтра активных заказов в истории заказов")
            public static void applyFilterActive() {
                ThreadUtil.simply(1); // Ожидание подгрузки фильтров
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

        @Step("Открыть страницу любимых товаров")
        public static void openFavorites() {
            log.info("> открыть страницу любимых товаров");
            kraken.get().userFavoritesPage();
        }

        @Step("Получаем количество избранных товаров")
        public static int count() {
            log.info("> получаем количество избранных товаров");
            final int count = kraken.perform().getElementCount(Elements.ItemCard.addToFavoritesButton());
            log.info("> {} избранных", count);
            return count;
        }

        /** Открыть любимые товары по кнопке в шапке сайта */
        @Step("Открываем любимые товары по кнопке в шапке сайта")
        public static void open() {
            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(Elements.Header.favoritesButton().getLocator()),
                    "кнопка перехода в любимые товары недоступна");
            kraken.perform().click(Elements.Header.favoritesButton());
            try {
                kraken.await().fluently(
                        ExpectedConditions
                                .visibilityOfElementLocated(Elements.Favorites.placeholder().getLocator()),
                        "Нет любимых товаров", 5);
            } catch (TimeoutException e){
                kraken.await().fluently(
                        ExpectedConditions
                                .visibilityOfElementLocated(Elements.Catalog.Product.FirstSnippetFavorite().getLocator()),
                        "Есть любимые товары", 5);
                log.info("Есть любимые товары");
            }
        }

        /** Открыть карточку товара в каталоге */
        @Step("Открываем карточку любимого товара в каталоге")
        public static void openFavoritesSnipet() {
            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),2);
            kraken.perform().click(Elements.Catalog.Product.FirstSnippetFavorite());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.popup().getLocator()),
                    "Не открывается карточка любимого товара");
            kraken.perform().switchToActiveElement();
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.ItemCard.image().getLocator()),
                    "Не отображается контент в карточке товара");
        }

        @Step("Открываем карточку любимого товара")
        public static void openFavoritesCard() {
            kraken.await().shouldBeClickable(Elements.Catalog.Product.FirstSnippetFavorite()).click();
        }

        /** Показать все любимые товары */
        @Step("Показываем все любимые товары")
        public static void applyFilterAllItems() {
            kraken.perform().click(Elements.Favorites.allItemsFilterButton());
            //TODO fluent Ожидание открытия вкладки "Все товары" в избранном
        }

        /** Показать любимые товары, которые есть в наличии */
        @Step("Показываем любимые товары, которые есть в наличии")
        public static void applyFilterInStock() {
            kraken.perform().click(Elements.Favorites.inStockFilterButton());
        }

        /** Показать любимые товары, которых нет в наличии */
        @Step("Показываем любимые товары, которых нет в наличии")
        public static void applyFilterNotInStock() {
            kraken.perform().click(Elements.Favorites.outOfStockFilterButton());
        }

        /** Подгрузить следующую страницу любимых товаров по кнопке "Показать ещё" */
        @Step("Подгружаем следующую страницу любимых товаров по кнопке \"Показать ещё\"")
        public static void showMore() {
            kraken.perform().click(Elements.Favorites.showMoreButton());
        }

        @Step("Удалить любимый товар из списка")
        public static void removeFavorite() {
            kraken.perform().click(Elements.ItemCard.addToFavoritesButton());
        }

        /** Очистить список избранного, удалив все любимые товары */
        @Step("Очистить список избранного, удалив все любимые товары")
        public static void cleanFavorites() {
            if (!kraken.detect().isFavoritesEmpty()) {
                kraken.perform().click(Elements.ItemCard.addToFavoritesButton());
                cleanFavorites();
            }
        }

        /** Сниппет любимого товара */
        public static class Item {

            /** Добавить первый любимый товар в корзину через сниппет товара */
            @Step("Добавляем первый любимый товар в корзину через сниппет товара")
            public static void addToCart() {
                kraken.perform().click(Elements.Favorites.Product.addToCart());
            }

            @Step("Добавляем первый любимый товар в корзину через карточку товара")
            public static void addToCartByButton() {
                kraken.perform().click(Elements.ItemCard.buyButton());
            }

            /** Удалить первый любимый товар из корзины через сниппет товара */
            @Step("Удаляем первый любимый товар из корзины через сниппет товара")
            public static void removeFromCart() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.minusButton());
                //TODO написать нормальное Ожидание добавления любимого товара
            }

            /** Удалить первый любимый товар из любимых через сниппет товара */
            @Step("Удаляем первый любимый товар из любимых через сниппет товара")
            public static void removeFromFavorites() {
                kraken.perform().hoverOn(Elements.Favorites.Product.snippet());
                kraken.perform().click(Elements.Favorites.Product.favButton());
                 //TODO написать нормальное ожиданиеудаления любимого товара
            }
        }
    }

    /** Карточка товара */
    public static class ItemCard {

        /** Добавить товар в корзину по кнопке [купить] в карточке товара */
        @Step("Добавляем товар в корзину по кнопке [купить] в карточке товара")
        public static void addToCart() {
            ElementData button = Elements.ItemCard.buyButton();
            // Побеждаем модалку обновления цен
            if (kraken.detect().isElementPresent(Elements.Modals.PricesModal.popup())) {
                //kraken.perform().click(Elements.Modals.PricesModal.refreshPricesButton());
            }
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(button.getLocator()),
                        "Некликабельна кнопка добавления товара в корзину"
            );
            log.info("> жмем купить");
            kraken.perform().click(Elements.ItemCard.buyButton());
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
                ThreadUtil.simply(1); // Ожидание убавления -1 товара в карточке
            } else {
                log.info("> Кнопка 'Минус' не отображается");
            }
        }

        /** Нажать кнопку любимого товара в карточке товара */
        @Step("Нажимаем кнопку любимого товара в карточке товара")
        public static void addToFavorites() {
            kraken.perform().click(Elements.ItemCard.addToFavoritesButton());
        }

        /** Закрыть карточку товара */
        @Step("Закрываем карточку товара")
        public static void close() {
            log.info("> закрываем карточку товара");
            kraken.perform().click(Elements.ItemCard.closeButton());
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(Elements.ItemCard.popup().getLocator()),
                    "не закрыватестя корзина",Config.BASIC_TIMEOUT);
        }
    }

    public static class Cart {

        /** Открыть корзину */
        @Step("Открываем корзину")
        public static void open() {
            if (!kraken.detect().isCartOpen()) {
                log.info("> открываем корзину");
                kraken.perform().click(Elements.Header.cartButton());
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.closeButton().getLocator()),
                        "Не открылась корзина\n\n",Config.BASIC_TIMEOUT);
            } else {
                log.info("> пропускаем открытие корзины, уже открыта");
            }
        }

        /** Закрыть корзину */
        @Step("Закрываем корзину")
        public static void close() {
            if (kraken.detect().isCartOpen()) {
                log.info("> закрываем корзину");
                kraken.perform().click(Elements.Cart.closeButton());
                ThreadUtil.simply(1);
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Cart.drawer().getLocator()),
                        "Не закрылась корзина\n\n");
            } else {
                log.info("> пропускаем закрытие корзины, уже закрыта");
            }
        }

        /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
        @Step("Переходим в чекаут нажатием кнопки \"Сделать заказ\" в корзине")
        public static void proceedToCheckout() {
            if (kraken.detect().isCheckoutButtonActive()) {
                kraken.perform().click(Elements.Cart.checkoutButton());
            } else {
                log.info("> кнопка перехода в чекаут неактивна");
                throw new AssertionError("\n\n> Не удается перейти в чекаут из корзины, кнопка перехода неактивна");
            }
        }

        /** Очистить корзину, удалив все товары */
        @Step("Очищаем корзину, удалив все товары")
        public static void drop() {
            log.info("> очищаем козину, удаляя все товары...");
            open();
            if (!kraken.detect().isCartEmpty()) {
                Item.remove();
                if(kraken.detect().isElementPresent(Elements.Cart.item.snippet())) {
                    drop();
                }
            }
            log.info("✓ Готово");
            close();
        }

        @Step("Очистить всю корзину")
        public static void dropAll() {
            log.info("> очищаем козину, удаляя все товары...");
            kraken.perform().click(Elements.Cart.item.removeAllButton());
            kraken.perform().click(Elements.Cart.item.removeAllConformation());
        }

        /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
        @Step("Набираем корзину на минимальную сумму, достаточную для оформления заказа")
        public static void collect() {
            if(!kraken.detect().isCheckoutButtonActive()) {
                Cart.close();
                int sid = kraken.grab().sidFromUrl();
                collect(kraken.apiV2().getMinOrderAmount(sid));
            } else { log.info("> пропускаем набор товаров, в корзине достаточно товаров для оформления минимального заказа");}
        }

        /** Набрать корзину на минимальную сумму, достаточную для оформления первого заказа */
        @Step("Набираем корзину на минимальную сумму, достаточную для оформления первого заказа")
        public static void collectFirstTime() {
            int sid = kraken.grab().sidFromUrl();
            collect(kraken.apiV2().getMinFirstOrderAmount(sid));
        }

        /** Набрать корзину на указанную сумму */
        @Step("Набираем корзину на указанную сумму: {0}")
        public static void collect(int orderSum) {
            if (!kraken.detect().isShippingAddressSet()) {
                ru.instamart.ui.module.shop.ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
                ru.instamart.ui.module.shop.ShippingAddressModal.selectAddressSuggest();
            }
            log.info("> проверяем  корзину. Минимальная сумма для заказа {}...", orderSum);
            int cartTotal = kraken.grab().cartTotalRounded();
            if (cartTotal < orderSum) {
                log.info("> сумма в корзине меньше минимальной суммы заказа, заполняем корзину");
                Cart.close();
                if (kraken.detect().isProductAvailable()) {
                    if (kraken.detect().isFavoriteProductAvailable()) {
                        log.info("> любимый продукт найден");
                        Shop.Favorites.open();
                        Favorites.openFavoritesSnipet();
                    } else {
                        log.info("> нет любимых товаров на текущей странице {}", kraken.grab().currentURL());
                        //kraken.get().page(Pages.Retailers.metro());
                        AppManager.getBasicUrl();
                        Catalog.Item.open();
                    }
                }
                int itemPrice = kraken.grab().itemPriceRounded();
                // Формула расчета кол-ва товара
                int neededQuantity;
                if (cartTotal > 0) {
                    neededQuantity = ((orderSum - cartTotal) / (itemPrice - 1)) + 2;
                } else {
                    neededQuantity = ((orderSum - cartTotal) / (itemPrice - 1)) + 1;
                }
                log.info("> добавляем в корзину {}х{}шт > {}", kraken.grab().itemName(), neededQuantity, kraken.grab().currentURL());
                addItemByText(neededQuantity);
                ItemCard.close();
                //kraken.get().page(Config.DEFAULT_RETAILER);
                Cart.open();
            } else {
                log.info("> в корзине достаточно товаров");
            }
        }

        /** Луп набора товара до необходимого количества */
        private static void addItem(int neededQuantity) {
            int quantity = kraken.grab().itemQuantity();
            log.info("> в каунтере {}", quantity);
            if (quantity == 1) kraken.await().implicitly(2);
            if (kraken.grab().itemQuantity() < neededQuantity) {
                ItemCard.addToCart();
                addItem(neededQuantity);
            }
        }

        /** Луп набора товара до необходимого количества */
        private static void addItemByText(int neededQuantity) {
            String quantity = kraken.grab().itemQuantityByText();
            log.info("> количество товара: {}", quantity);
            kraken.grab().addItemCard();
            if (neededQuantity==1) kraken.await().implicitly(2);
            else {
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.ItemCard.plusButton().getLocator()),
                        "кнопка + не появилась\n");
                kraken.grab().addItemCard(neededQuantity);
            }
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
                kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Cart.item.snippet().getLocator()),
                        "не подгружается снипет товара",Config.BASIC_TIMEOUT);
                kraken.perform().hoverOn(Elements.Cart.item.snippet()); // небольшой костылек для стобильности
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Cart.item.removeButton().getLocator()),
                                    "Не появилась кнопка удаления товара в корзине\n\n");
                kraken.perform().click(Elements.Cart.item.removeButton());
            }

            /** Увеличить количество верхнего товара в корзине */
            @Step("Увеличиваем количество верхнего товара в корзине")
            public static void increaseQuantity() {
                kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Cart.item.snippet().getLocator()),
                        "не подгружается снипет товара",Config.BASIC_TIMEOUT);
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.increaseButton());
            }

            /** Уменьшить количество верхнего товара в корзине */
            @Step("Уменьшаем количество верхнего товара в корзине")
            public static void decreaseQuantity() {
                kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Cart.item.snippet().getLocator()),
                        "не подгружается снипет товара",Config.BASIC_TIMEOUT);
                kraken.perform().hoverOn(Elements.Cart.item.snippet());
                kraken.perform().click(Elements.Cart.item.decreaseButton());
            }
        }
    }
}
