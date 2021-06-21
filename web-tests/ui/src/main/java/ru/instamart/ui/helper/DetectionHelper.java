package ru.instamart.ui.helper;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.pagesdata.CheckoutStepData;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.testdata.pagesdata.LoyaltiesData;
import ru.instamart.kraken.testdata.pagesdata.WidgetData;
import ru.instamart.ui.Elements;
import ru.instamart.ui.data.ElementData;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;

@RequiredArgsConstructor
@Slf4j
public final class DetectionHelper extends HelperBase {

    private final AppManager kraken;

    /** Определить в каком тестовом окружении находимся */
    public boolean environment(String environment) {
        return EnvironmentData.INSTANCE.getName().equals(environment);
    }

    /** Определить серверное окружение */
    public boolean server(String server) {
        return EnvironmentData.INSTANCE.getServer().equals(server);
    }

    /** Определить тенант */
    public boolean tenant(String tenant) {
        return EnvironmentData.INSTANCE.getTenant().equals(tenant);
    }

    /**
     * Определить показан ли алерт на странице
     */
    protected boolean isAlertPresent() {
        try {
            AppManager.getWebDriver().switchTo().alert();
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
            AppManager.getWebDriver().findElement(element.getLocator());
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
            return AppManager.getWebDriver().findElement(element.getLocator()).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить можно ли кликнуть по элементу
     */
    public boolean isElementClickable(ElementData element) {
        try {
            kraken.await().fluentlyMS(ExpectedConditions.elementToBeClickable(element.getLocator()),
                    "невозможно кликнуть на элемент",300);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить доступен ли элемент
     */
    public boolean isElementEnabled(ElementData element) {
        return AppManager.getWebDriver().findElement(element.getLocator()).isEnabled();
    }

    /**
     * Определить проставлен ли чекбокс
     */
    public boolean isCheckboxSet(ElementData element) {
        return AppManager.getWebDriver().findElement(element.getLocator()).isSelected();
    }

    /**
     * Определить проставлен ли чекбокс
     */
    public boolean isCheckboxSet(WebElement element) {
        return element.isSelected();
    }

    /**
     * Определить выбрана ли радиокнопка
     */
    public boolean isRadioButtonSelected(ElementData element) {
        return AppManager.getWebDriver().findElement(element.getLocator()).isSelected();
    }

    /**
     * Определить пустое ли поле
     */
    public boolean isFieldEmpty(ElementData element) {
        return AppManager.getWebDriver().findElement(element.getLocator()).getAttribute("value").equals("");
    }

    /**
     * Определить находимся на лендинге или нет
     */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Landings.SbermarketLanding.MainBlock.Stores.homeLanding());
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
        return kraken.grab().currentURL().equals(EnvironmentData.INSTANCE.getBasicUrl() + Pages.UserProfile.favorites().getPath());
    }

    /**
     * Определить 404 ошибку на текущей странице
     */
    @Step("Определяем 404 ошибку на текущей странице")
    public boolean is404() {
        if (AppManager.getWebDriver().getTitle().contains("404")) {
            log.warn("404 на {}", kraken.grab().currentURL());
            return true;
        } else return false;
    }

    /**
     * Определить 500 ошибку на текущей странице
     */
    @Step("Определяем 500 ошибку на текущей странице")
    public boolean is500() {
        if (AppManager.getWebDriver().getTitle().contains("500")) {
            log.warn("⚠ 500 на {}", kraken.grab().currentURL());
            return true;
        } else return false;
    }

    /**
     * Определить 502 ошибку на текущей странице
     */
    @Step("Определяем 502 ошибку на текущей странице")
    public boolean is502() {
        if (AppManager.getWebDriver().getTitle().contains("502")) {
            log.warn("⚠ 502 на {}", kraken.grab().currentURL());
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
     * Определить открыт ли модалка "Адрес"
     */
    public boolean isAddressModalOpen() {
        try {
            kraken.await().fluently(
                    ExpectedConditions
                            .visibilityOfElementLocated(Elements.Modals.AddressModal.popup().getLocator()),
                    "попап с адресом доставки не появился",5);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    /**
     * Определить открыт ли модалка "Адрес" с выбором доставки
     */
    public boolean isAddressModalOpenWithDeliveryButton() {
        try {
            kraken.await().fluently(
                    ExpectedConditions
                            .visibilityOfElementLocated(Elements.Modals.AddressModal.deliveryButton().getLocator()),
                    "попап с вводом адреса и выбором способа доставки не появился",3);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * Определить показана ли заглушка "Адрес вне зоны доставки" в адресной модалке
     */
    public boolean isAddressOutOfZone() {
        if (isElementPresent(Elements.Modals.AddressModal.titleOutOfZone())) {
            log.info("Адрес не в зоне доставки");
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
            log.info("Пустой результат поиска");
            return true;
        } else return false;
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isSearchProductSuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Header.Search.productSuggest());
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isSearchSuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Header.Search.searchListResult());
    }


    // ======= Авторизация =======

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        if (isElementDisplayed(Elements.Modals.AuthModal.popup())) {
            log.info("> модалка авторизации открыта");
            return true;
        } else {
            log.info("> модалка авторизации закрыта");
            return false;
        }
    }
    /** Определить присутствует ли на стартовой странице рекламный банер */
    public boolean isPromoModalOpen(ElementData data){
        if(isElementDisplayed(data)){
            log.info("> на странице присутствует рекламный банер");
            return true;
        }else {
            return false;
        }
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        log.info("Проверяем авторизованность...");
        if (kraken.detect().isInAdmin()) {
            log.info("> в админке");
            if (kraken.detect().isOnAdminLoginPage()) {
                log.warn("Не авторизован");
                return false;
            } else {
                log.info("✓ Авторизован");
                return true;
            }
        } else {
            log.info("> на сайте");
            if (kraken.detect().isElementPresent(Elements.Header.profileButton())
                    && !kraken.detect().isElementPresent(Elements.Header.loginButton()) ) {
                log.info("✓ Авторизован");
                return true;
            } else {
                log.warn("Не авторизован");
                return false;
            }
        }
    }
    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorisedSTF() {
        log.info("Проверяем авторизованность...");
        if (kraken.detect().isElementPresent(Elements.Header.profileButton())
                || !kraken.detect().isElementPresent(Elements.Header.loginButton())
                && !kraken.detect().isElementPresent(Elements.Landings.SbermarketLanding.Header.loginButton())) {
            log.info("✓ Авторизован");
            return true;
        } else {
            log.warn("Не авторизован");
            return false;
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoveryRequested(){
        if (kraken.detect().isElementPresent(Elements.Modals.PasswordRecoveryModal.successRecoveryRequestText())) {
            log.info("Запрошено восстановление пароля");
            return true;
        } else {
            log.warn("Запрос восстановления пароля не отправлен");
            return false;
        }
    }


    // ======= История заказов =======

    /** Определить активен ли верхний заказ на странице списка заказов */
    public boolean isOrdersHistoryEmpty() {
        if(kraken.detect().isElementPresent(Elements.UserProfile.OrdersHistoryPage.allOrdersPlaceholder())) {
            log.info("У пользователя нет заказов на странице истории заказов");
            return true;
        } else {
            log.warn("У пользователя есть заказы на странице истории заказов");
            return false;
        }
    }

    // ======= Детали заказа =======

    /** Определить оформлен ли заказ на странице деталей */
    @Step("Проверяем открытие страницы заказа")
    public boolean isOrderPlaced() {
        log.info("Проверяем страницу заказа...");
        if (isElementPresent(Elements.UserProfile.OrderDetailsPage.OrderStatus.placed())) {
        log.info("✓ Заказ оформлен");
            return true;
        } else return false;
    }

    /** Определить отменен ли заказ на странице деталей */
    public boolean isOrderCanceled(){
        if (isInAdmin()) {
            if (isElementPresent(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.canceledOrderAttribute())) {
                log.info("Заказ отменен");
                return true;
            } else {
                log.warn("Заказ активен");
                return false;
            }
        } else {
            return isElementPresent(Elements.UserProfile.OrderDetailsPage.OrderStatus.canceled());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementPresent((Elements.Header.currentShipAddress()))) {
            log.info("Выбран адрес доставки: {}", kraken.grab().currentShipAddress());
            return true;
        } else {
            log.warn("Адрес доставки не выбран");
            return false;
        }
    }

    /** Определить показаны ли адресные саджесты */
    public boolean isShippingAddressSuggestsPresent() {
        return isElementPresent(Elements.Modals.AddressModal.addressSuggest());
    }


    // ======= Модалка выбора магазинов =======

    public boolean isStoresModalOpen() {
        return kraken.detect().isElementDisplayed(Elements.Modals.StoresModal.popup());
    }

    // ======= Шторка выбора магазинов =======

    /** Определить открыта ли шторка выбора магазина */
    public boolean isStoresDrawerOpen() {
        return kraken.detect().isElementDisplayed(Elements.StoreSelector.drawer());
    }

    /** Определить отображается ли сообщение об ошибке на модалке выбора магазина */
    public boolean isStoresErrorPresented() {
        return kraken.detect().isElementDisplayed(Elements.StoreSelector.errorMessageStore());
    }

    /** Определить пуст ли селектор */
    public boolean isStoresDrawerEmpty() {
        return kraken.detect().isElementDisplayed(Elements.StoreSelector.placeholder());
    }

    /**Определить доступен ли селектор магазинов*/
    public boolean isStoreButtonDisplayed(){
        if (kraken.detect().isElementDisplayed(Elements.Header.storeButton())){
            log.info("Селектор магазинов доступен");
            return true;
        } else {
            log.warn("Селектор магазинов недоступен");
            return false;
        }
    }


    //========= Шторка каталога ==========

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        if (kraken.detect().isElementDisplayed(Elements.CatalogDrawer.drawer())) {
            log.info("Шторка каталога открыта");
            return true;
        } else {
            log.warn("Шторка каталога закрыта");
            return false;
        }
    }


    // ======= Каталог =======

    /** Определить есть ли товары на странице */
    @Step("Проверяем наличие товаров на странице")
    public boolean isProductAvailable() {
        if(kraken.detect().isElementPresent(Elements.Catalog.Product.snippet())){
            log.info("✓ Есть доступные товары");
            return true;
        } else {
            log.warn("Нет доступных товаров!");
            return false;
        }
    }

    @Step("Проверяем наличие товаров на странице поиска")
    public boolean isProductAvailableOnSearch() {
        if(kraken.detect().isElementPresent(Elements.Catalog.Search.snippet())){
            log.info("✓ Есть доступные товары");
            return true;
        } else {
            log.warn("Нет доступных товаров!");
            return false;
        }
    }

    /** Определить есть ли любимые товары на странице */
    @Step("Смотрим наличие товара в любимых")
    public boolean isFavoriteProductAvailable() {
        kraken.perform().hoverOn(Elements.Catalog.Product.snippet());
        if(kraken.detect().isElementPresent(Elements.Catalog.Product.favButtonActive())){
            log.info("✓ Есть доступные любимые товары");
            return true;
        } else {
            log.warn("Нет доступных любимых товаров!");
            return false;
        }
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(kraken.detect().isElementPresent(Elements.ItemCard.popup())){
            log.info("> открыта карточка товара {}", kraken.grab().currentURL());
            return true;
        } else {
            log.warn("Карточка товара закрыта");
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
            kraken.get().userFavoritesPage();
        }
        if(kraken.detect().isElementPresent(Elements.Favorites.placeholder())){
            log.info("Нет любимых товаров");
           return true;
        } else {
            log.warn("Есть любимые товары");
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
            log.info("Корзина открыта");
            return true;
        } else {
            log.warn("Корзина закрыта");
            return false;
        }
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        Shop.Cart.open();
        if (isElementDisplayed(Elements.Cart.placeholder())) {
            log.info("Корзина пуста");
            return true;
        } else {
            log.warn("Корзина не пуста");
            return false;
        }
    }

    public boolean isEmptyCart() {
        return kraken.await().shouldBeVisible(Elements.Cart.placeholder());
    }

    public boolean notEmptyCart() {
        return kraken.await().shouldBeVisible(Elements.Cart.item.openButton());
    }

    /** Определить активна ли кнопка перехода в чекаут в корзине */
    public boolean isCheckoutButtonActive() {
        Shop.Cart.open();
        if(isElementEnabled(Elements.Cart.checkoutButton())){
            log.info("Кнопка перехода в чекаут активна");
            return true;
        } else {
            log.warn("Кнопка перехода в чекаут неактивна");
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
        //TODO здесь костыль тк на стейдже бага, но это чисто для отладки, не включать
//        if(!kraken.detect().isElementPresent(Elements.Checkout.header())){
//            kraken.getBasicUrl();
//            kraken.reach().ru.instamart.test.ui.checkout();
//        }
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
            log.info("✓ Промокод применён");
            return true;
        } else {
            log.warn("Промокод не применён");
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
            log.warn("В детекторе не найден провайдер виджета");
            return false;
    }


    // ======= Jivosite =======

    /** Определить открыт ли виджет Jivosite */
    public boolean isJivositeOpen() {
        if (isElementDisplayed(Elements.Jivosite.sendMessageButton())){
            log.info("Чат Jivosite развернут");
            return true;
        } else {
            log.warn("Чат Jivosite свернут");
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
            log.info("Сообщение отправлено");
            return true;
        } else {
            log.warn("Сообщение не отправлено");
            return false;
        }
    }

    /** Определить доступен ли виджет Jivosite*/
    public boolean isJivositeWidgetAvailable () {
        if (isElementDisplayed(Elements.Jivosite.widget())){
            log.info("Виджет Jivosite доступен\n");
            return true;
        } else {
            log.warn("Виджет Jivosite недоступен\n");
            return false;
        }
    }

    /** Определить содержит ли элемент необходимый текст*/
    public boolean isTextElementContainsText(String text, ElementData element){
        if(AppManager.getWebDriver().findElement(element.getLocator()).getText().contains(text)){
            log.info("> элемент содержит искомый текст");
            return true;
        }else {
            log.info("> элемент не содержит искомый текст");
            return false;
        }
    }
}
