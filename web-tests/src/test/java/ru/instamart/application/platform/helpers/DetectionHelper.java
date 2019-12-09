package ru.instamart.application.platform.helpers;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.application.models.*;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;

public class DetectionHelper extends HelperBase {

    public DetectionHelper(WebDriver driver, ServerData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Определить в каком тестовом окружении находимся */
    public boolean environment(ServerData environment) {
        return kraken.server.getName().equals(environment.getName());
    }

    /** Определить серверное окружение */
    public boolean server(String server) {
        return kraken.server.getEnvironment().equals(server);
    }

    /** Определить тенант */
    public boolean tenant(TenantData tenant) {
        return kraken.server.getTenant().getAlias().equals(tenant.getAlias());
    }

    /**
     * Определить показан ли алерт на странице
     */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Определить представлен ли элемент
     */
    public boolean isElementPresent(ElementData element) {
        try {
            driver.findElement(element.getLocator());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить показан ли элемент
     */
    public boolean isElementDisplayed(ElementData element) {
        try {
            return driver.findElement(element.getLocator()).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить доступен ли элемент
     */
    public boolean isElementEnabled(ElementData element) {
        return driver.findElement(element.getLocator()).isEnabled();
    }

    /**
     * Определить проставлен ли чекбокс
     */
    public boolean isCheckboxSet(ElementData element) {
        return driver.findElement(element.getLocator()).isSelected();
    }

    /**
     * Определить выбрана ли радиокнопка
     */
    public boolean isRadioButtonSelected(ElementData element) {
        return driver.findElement(element.getLocator()).isSelected();
    }

    /**
     * Определить пустое ли поле
     */
    public boolean isFieldEmpty(ElementData element) {
        return driver.findElement(element.getLocator()).getAttribute("value").equals("");
    }

    /**
     * Определить находимся на лендинге или нет
     */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.list());
    }

    /**
     * Определить находимся на сайте или нет
     */
    public boolean isOnSite() {
        return isElementPresent(Elements.Footer.container());
    }

    /**
     * Определить находимся в админке или нет
     */
    public boolean isInAdmin() {
        return isElementPresent(Elements.Administration.insideContainer());
    }

    /**
     * Определить находимся на логин-странице админки или нет
     */
    public boolean isOnAdminLoginPage() {
        return isElementPresent(Elements.Administration.LoginPage.title());
    }

    /**
     * Определить находимся в разделе профиля пользователя или нет
     */
    public boolean isInProfile() {
        return isElementPresent(Elements.UserProfile.menu());
    }

    /**
     * Определить находимся в списке любимых товаров или нет
     */
    public boolean isInFavorites() {
        return kraken.grab().currentURL().equals(baseUrl + Pages.Profile.favorites().getPath());
    }

    /**
     * Определить 404 ошибку на текущей странице
     */
    public boolean is404() {
        if (isElementPresent(Elements.Page404.title())) {
            verboseMessage("404 на " + kraken.grab().currentURL()+ "\n");
            return true;
        } else return false;
    }

    /**
     * Определить 500 ошибку на текущей странице
     */
    public boolean is500() {
        if (isElementPresent(Elements.Page500.placeholder())) {
            verboseMessage("⚠ 500 на " + kraken.grab().currentURL()+ "\n");
            return true;
        } else return false;
    }

    /**
     * Определить 502 ошибку на текущей странице
     */
    public boolean is502() {
        if (isElementPresent(Elements.Page502.title())) {
            verboseMessage("⚠ 502 на " + kraken.grab().currentURL()+ "\n");
            return true;
        } else return false;
    }


    // ======= Модалки =======

    /**
     * Определить открыта ли модалка "Доставка"
     */
    public boolean isDeliveryModalOpen() {
        return isElementDisplayed(Elements.Modals.DeliveryModal.popup());
    }

    /**
     * Определить открыта ли модалка "Оплата"
     */
    public boolean isPaymentModalOpen() {
        return isElementDisplayed(Elements.Modals.PaymentModal.popup());
    }

    /**
     * Определить открыт ли модалка "Партнеры"
     */
    public boolean isPartnersModalOpen() {
        return isElementDisplayed(Elements.Modals.PartnersModal.popup());
    }

    /**
     * Определить открыт ли модалка "Адрес"
     */
    public boolean isAddressModalOpen() {
        return isElementDisplayed(Elements.Modals.AddressModal.popup())
                && isElementDisplayed(Elements.Modals.AddressModal.titleSet())
                || isElementDisplayed(Elements.Modals.AddressModal.titleChange());
    }

    /**
     * Определить показана ли заглушка "Адрес вне зоны доставки" в адресной модалке
     */
    public boolean isAddressOutOfZone() {
        if (isElementPresent(Elements.Modals.AddressModal.titleOutOfZone())) {
            verboseMessage("Адрес не в зоне доставки");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определить открыта ли модалка "Выберите магазин"
     */
    public boolean isChangeStoreModalOpen() {
        return isElementDisplayed(Elements.Modals.StoresModal.popup());
    }


    // ======= Меню "Профиль" =======

    /** Определить открыто ли меню аккаунта */
    public boolean isAccountMenuOpen() {
        return isElementDisplayed(Elements.AccountMenu.popup());
    }


    // ======= Поиск =======

    /** Детектим пустой результат поиска */
    public boolean isSearchResultsEmpty() {
        if(isElementPresent(Elements.Catalog.emptySearchPlaceholder())){
            verboseMessage("Пустой результат поиска");
            return true;
        } else return false;
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isSearchProductSuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Header.Search.productSuggest());
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isSearchCategorySuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Header.Search.categorySuggest());
    }


    // ======= Авторизация =======

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        if (isElementDisplayed(Elements.Modals.AuthModal.popup())) {
            debugMessage("> модалка авторизации открыта");
            return true;
        } else {
            debugMessage("> модалка авторизации закрыта");
            return false;
        }
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        debugMessage("Проверяем авторизованность...");
        if (kraken.detect().isInAdmin()) {
            debugMessage("> в админке");
            if (kraken.detect().isOnAdminLoginPage()) {
                debugMessage("Не авторизован\n");
                return false;
            } else {
                debugMessage("✓ Авторизован\n");
                return true;
            }
        } else {
            debugMessage("> на сайте");
            if (kraken.detect().isElementPresent(Elements.Header.profileButton())
                    && !kraken.detect().isElementPresent(Elements.Header.loginButton()) ) {
                debugMessage("✓ Авторизован\n");
                return true;
            } else {
                debugMessage("Не авторизован\n");
                return false;
            }
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoveryRequested(){
        if (kraken.detect().isElementPresent(Elements.Modals.PasswordRecoveryModal.successRecoveryRequestText())) {
            verboseMessage("Запрошено восстановление пароля");
            return true;
        } else {
            verboseMessage("Запрос восстановления пароля не отправлен");
            return false;
        }
    }


    // ======= История заказов =======

    /** Определить активен ли верхний заказ на странице списка заказов */
    public boolean isOrdersHistoryEmpty() {
        if(kraken.detect().isElementPresent(Elements.UserProfile.OrdersHistoryPage.placeholder())) {
            debugMessage("У пользователя нет заказов на странице истории заказов");
            return true;
        } else {
            debugMessage("У пользователя есть заказы на странице истории заказов");
            return false;
        }
    }

    /** Определить активен ли верхний заказ на странице списка заказов */
    public boolean isLastOrderActive() {
        if(isElementPresent(Elements.UserProfile.OrdersHistoryPage.order.cancelButton())) {
            debugMessage("Крайний заказ активен");
            return true;
        } else {
        debugMessage("Крайний заказ не активен");
            return false;
        }
    }

    // ======= Детали заказа =======

    /** Определить активен ли заказ на странице деталей */
    public boolean isOrderActive() {
        verboseMessage("Проверяем страницу заказа...");
        if (isElementDisplayed(Elements.UserProfile.OrderDetailsPage.activeOrderAttribute()) && !isOrderCanceled()) {
        verboseMessage("✓ Заказ активен\n");
            return true;
        } else return false;
    }

    /** Определить отменен ли заказ на странице деталей */
    public boolean isOrderCanceled(){
        if (isInAdmin()) {
            if (isElementPresent(Elements.Administration.ShipmentsSection.Order.Details.canceledOrderAttribute())) {
                verboseMessage("Заказ отменен");
                return true;
            } else {
                verboseMessage("Заказ активен");
                return false;
            }
        } else {
            return isElementPresent(Elements.UserProfile.OrderDetailsPage.canceledOrderAttribute());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementPresent((Elements.Header.currentShipAddress()))) {
            verboseMessage("Выбран адрес доставки: " + kraken.grab().currentShipAddress() + "\n");
            return true;
        } else {
            verboseMessage("Адрес доставки не выбран\n");
            return false;
        }
    }

    /** Определить показаны ли адресные саджесты */
    public boolean isShippingAddressSuggestsPresent() {
        return isElementPresent(Elements.Modals.AddressModal.addressSuggest());
    }


    // ======= Шторка выбора магазинов =======

    /** Определить открыта ли шторка выбора магазина */
    public boolean isStoreSelectorOpen() {
        return kraken.detect().isElementDisplayed(Elements.StoreSelector.drawer());
    }

    /** Определить пуст ли селектор */
    public boolean isStoreSelectorEmpty() {
        return kraken.detect().isElementDisplayed(Elements.StoreSelector.placeholder());
    }

    /**Определить доступен ли селектор магазинов*/
    public boolean isStoreButtonDisplayed(){
        if (kraken.detect().isElementDisplayed(Elements.Header.storeButton())){
            debugMessage("Селектор магазинов доступен");
            return true;
        } else {
            debugMessage("Селектор магазинов недоступен");
            return false;
        }
    }


    //========= Шторка каталога ==========

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        if (kraken.detect().isElementDisplayed(Elements.CatalogDrawer.drawer())) {
            debugMessage("Шторка каталога открыта");
            return true;
        } else {
            debugMessage("Шторка каталога закрыта");
            return false;
        }
    }


    // ======= Каталог =======

    /** Определить есть ли товары на странице */
    public boolean isProductAvailable() {
        if(kraken.detect().isElementPresent(Elements.Catalog.Product.snippet())){
            debugMessage("✓ Есть доступные товары");
            return true;
        } else {
            debugMessage("Нет доступных товаров!");
            return false;
        }
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(kraken.detect().isElementPresent(Elements.ItemCard.popup())){
            verboseMessage("> открыта карточка товара " + kraken.grab().currentURL());
            return true;
        } else {
            debugMessage("Карточка товара закрыта");
            return false;
        }
    }

    /** Определить есть ли скидка на товар */
    public boolean isItemOnSale() {
        return isElementPresent(Elements.ItemCard.saleBadge());
    }


    // ======= Любимые товары =======

    /** Определить наличие пустого списка любимых товаров */
    public boolean isFavoritesEmpty() {
        if(!kraken.detect().isInFavorites()) {
            kraken.get().favoritesPage();
        }
        if(kraken.detect().isElementPresent(Elements.Favorites.placeholder())){
            verboseMessage("Нет любимых товаров\n");
           return true;
        } else {
            verboseMessage("Есть любимые товары\n");
            return false;
        }
    }

    /** Определить выбран ли фильтр Все товары */
    public boolean isFavoritesFiltered(String filter) {
        String activeFilter = kraken.grab().text(Elements.Favorites.activeFilter());
        switch (filter) {
            case "all" :
                return activeFilter.equals(kraken.grab().text(Elements.Favorites.allItemsFilterButton()));
            case "inStock" :
                return activeFilter.equals(kraken.grab().text(Elements.Favorites.inStockFilterButton()));
            case "outOfStock" :
                return activeFilter.equals(kraken.grab().text(Elements.Favorites.outOfStockFilterButton()));
            default: return false;
        }
    }


    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        if (isElementDisplayed(Elements.Cart.drawer())){
            debugMessage("Корзина открыта");
            return true;
        } else {
            debugMessage("Корзина закрыта");
            return false;
        }
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        Shop.Cart.open();
        if (isElementDisplayed(Elements.Cart.placeholder())) {
            debugMessage("Корзина пуста");
            return true;
        } else {
            debugMessage("Корзина не пуста");
            return false;
        }
    }

    /** Определить активна ли кнопка перехода в чекаут в корзине */
    public boolean isCheckoutButtonActive() {
        Shop.Cart.open();
        if(isElementEnabled(Elements.Cart.checkoutButton())){
            debugMessage("Кнопка перехода в чекаут активна");
            return true;
        } else {
            debugMessage("Кнопка перехода в чекаут неактивна");
            return false;
        }
    }

    /** Определить отображается ли сумма заказа */
    public boolean isCartTotalDisplayed() {
        Shop.Cart.open();
        return isElementDisplayed(Elements.Cart.total());
    }


    // ======= Чекаут =======

    /** Определить находимся ли в чекауте */
    public boolean isOnCheckout() {
        return kraken.detect().isElementPresent(Elements.Checkout.header());
    }

    /** Определить введен ли телефон на 2 шаге в чекауте */
    public boolean isNoPhonesAddedOnContactsStep() {
        return isElementDisplayed(Elements.Checkout.ContactsStep.phoneInputField());
    }

    /** Определить введены ли данные юрлица на 4 шаге в чекауте */
    public boolean isPaymentCardEntered() {
        return isElementDisplayed(Elements.Checkout.paymentCardTitle(1));
    }

    /** Определить введены ли данные второго юрлица на 4 шаге в чекауте */
    public boolean isSecondPaymentCardEntered() {
        return isElementDisplayed(Elements.Checkout.paymentCardTitle(2));
    }

    /** Определить введены ли данные юрлица на 4 шаге в чекауте */
    public boolean isJuridicalEntered() {
        return isElementDisplayed(Elements.Checkout.juridicalTitle(1));
    }

    /** Определить введены ли данные второго юрлица на 4 шаге в чекауте */
    public boolean isSecondJuridicalEntered() {
        return isElementDisplayed(Elements.Checkout.juridicalTitle(2));
    }

    /** Определить добавлен ли промокод в чекауте */
    public boolean isPromocodeApplied() {
        if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.deleteButton())) {
            debugMessage("✓ Промокод применён\n");
            return true;
        } else {
            debugMessage("Промокод не применён\n");
            return false;
        }
    }

    public boolean isBonusAdded(LoyaltiesData bonus) {
        return isElementPresent(Elements.Checkout.Bonuses.Program.editButton(bonus.getName()));
    }

    public boolean isBonusActive(LoyaltiesData bonus) {
        return isElementPresent(Elements.Checkout.Bonuses.Program.activeSnippet(bonus.getName()));
    }

    public boolean isRetailerCardAdded() {
        //return isElementPresent(Elements.Checkout.loyaltyProgramsEditButton()); // Переделать
        return false;
    }

    /** Определить активен ли шаг чекаута в данный момент, по наличию кнопок "Продолжить" */
    public boolean isCheckoutStepActive(CheckoutStepData step) {
        return isElementPresent(Elements.Checkout.Step.nextButton(step));
    }

    public boolean isPaymentTypeAvailable(String paymentType) {
        return isElementPresent(Elements.Checkout.paymentTypeSelector(paymentType));
    }

    public boolean isPaymentTypeActive(String paymentType) {
        return isElementPresent(Elements.Checkout.activePaymentTypeSelector(paymentType));
    }

    // ======= Widgets =======

    public boolean isWidgetPresent(WidgetData widget){
        kraken.grab().currentURL();
        if(widget.getProvider().equals("RetailRocket")){
            return isElementPresent(Elements.RetailRocket.widget(widget.getId()));
                    //&& kraken.grab().text(Elements.RetailRocket.title(widget.getId())).equals(widget.getFirstName());
        } else
            message("В детекторе не найден провайдер виджета");
            return false;
    }


    // ======= Jivosite =======

    /** Определить открыт ли виджет Jivosite */
    public boolean isJivositeOpen() {
        if (isElementDisplayed(Elements.Jivosite.sendMessageButton())){
            verboseMessage("Чат Jivosite развернут\n");
            return true;
        } else {
            verboseMessage("Чат Jivosite свернут\n");
            return false;
        }
    }

    /** Определить отображается ли чат в виджете Jivosite */
    public boolean isJivositeChatAvailable() {
        return isElementDisplayed(Elements.Jivosite.chatArea());
    }

    /** Определить отправлено ли сообщение в Jivosite */
    public boolean isJivositeMessageSent(String text) {
        if (isElementDisplayed(Elements.Jivosite.sentMessage(text))){
            verboseMessage("Сообщение отправлено");
            return true;
        } else {
            verboseMessage("Сообщение не отправлено");
            return false;
        }
    }

    /** Определить доступен ли виджет Jivosite*/
    public boolean isJivositeWidgetAvailable () {
        kraken.await().implicitly(2); // Ожидание подгрузки виджета Jivosite
        if (isElementDisplayed(Elements.Jivosite.widget())){
            verboseMessage("Виджет Jivosite доступен\n");
            return true;
        } else {
            verboseMessage("Виджет Jivosite недоступен\n");
            return false;
        }
    }
}
