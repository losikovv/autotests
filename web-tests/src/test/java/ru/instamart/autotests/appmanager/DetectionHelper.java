package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.*;

public class DetectionHelper extends HelperBase {

    DetectionHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /**
     * Определить в каком тестовом окружении находимся
     */
    public boolean environment(EnvironmentData environment) {
        return kraken.environment.getName().equals(environment.getName());
    }

    /**
     * Определить тенант
     */
    public boolean tenant(String tenantName) {
        return kraken.environment.getTenant().equals(tenantName);
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

    //Todo убрать
    public boolean element(ElementData element) {
        return element(element.getLocator(), element.getText());
    }

    //Todo убрать
    public boolean element(By locator, String text) {
        return isElementPresent(locator) && kraken.grab().text(locator).equals(text);
    }

    // Todo убрать
    public boolean element(String xpath, String text) {
        return isElementPresent(By.xpath(xpath)) && kraken.grab().text(By.xpath(xpath)).equals(text);
    }

    /**
     * Определить отображается ли элемент
     */
    public boolean isElementPresent(ElementData element) {
        return (isElementPresent(element.getLocator()));
    }

    /**
     * Определить отображается ли элемент по локатору
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить показан ли элемент
     */
    public boolean isElementDisplayed(ElementData element) {
        return isElementDisplayed(element.getLocator());
    }

    /**
     * Определить показан ли элемент по локатору
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Определить доступен ли элемент
     */
    public boolean isElementEnabled(ElementData element) {
        return isElementEnabled(element.getLocator());
    }

    /**
     * Определить доступен ли элемент по локатору
     */
    public boolean isElementEnabled(By locator) {
        return driver.findElement(locator).isEnabled();
    }

    /**
     * Определить проставлен ли чекбокс
     */
    public boolean isCheckboxSelected(ElementData element) {
        return isCheckboxSelected(element.getLocator());
    }

    /**
     * Определить проставлен ли чекбокс по локатору
     */
    boolean isCheckboxSelected(By locator) {
        return driver.findElement(locator).isSelected();
    }

    /**
     * Определить находимся на лендинге или нет
     */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Landing.title());
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
        return isElementPresent(Elements.Admin.container());
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
        return kraken.grab().currentURL().equals(baseUrl + Pages.Site.Profile.favorites().getPagePath());
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
        return isElementDisplayed(Elements.Modals.DeliveryModal.popup())
                && element(Elements.Modals.DeliveryModal.title());
    }

    /**
     * Определить открыта ли модалка "Оплата"
     */
    public boolean isPaymentModalOpen() {
        return isElementDisplayed(Elements.Modals.PaymentModal.popup())
                && element(Elements.Modals.PaymentModal.title());
    }

    /**
     * Определить открыт ли модалка "Партнеры"
     */
    public boolean isPartnersModalOpen() {
        return isElementDisplayed(Elements.Modals.PartnersModal.popup())
                && element(Elements.Modals.PartnersModal.title());
    }

    /**
     * Определить открыт ли модалка "Адрес"
     */
    public boolean isAddressModalOpen() {
        return isElementDisplayed(Elements.Modals.AddressModal.popup())
                && element(Elements.Modals.AddressModal.titleSet())
                || element(Elements.Modals.AddressModal.titleChange());
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
        if (kraken.detect().isElementPresent(Elements.Header.profileButton())
                && !kraken.detect().isElementPresent(Elements.Header.loginButton()) ) {
            debugMessage("✓ Авторизован\n");
            return true;
        } else {
            debugMessage("Не авторизован\n");
            return false;
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoveryRequested(){
        if (kraken.detect().element(Elements.Modals.AuthModal.successRecoveryText())) {
            verboseMessage("Запрошено восстановление пароля");
            return true;
        } else {
            verboseMessage("Запрос восстановления пароля не отправлен");
            return false;
        }
    }


    // ======= Детали заказа =======

    /** Определить активен ли верхний заказ на странице списка заказов */
    public boolean isLastOrderActive() {
        if(isElementPresent(Elements.UserProfile.OrdersPage.lastOrderActionButton(2))) {
            verboseMessage("Крайний заказ активен");
            return true;
        } else {
        verboseMessage("Крайний заказ неактивен");
            return false;
        }
    }

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
            if (element(Elements.Admin.Shipments.Order.Details.canceledOrderAttribute())) {
                verboseMessage("Заказ отменен");
                return true;
            } else {
                verboseMessage("Заказ активен");
                return false;
            }
        } else {
            return element(Elements.UserProfile.OrderDetailsPage.canceledOrderAttribute());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementPresent((Elements.Header.currentShipAddress()))) {
            verboseMessage("Выбран адрес доставки: " + kraken.grab().currentShipAddress());
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
        ShopHelper.Cart.open();
        if (isElementPresent(Elements.Cart.placeholder())) {
            debugMessage("Корзина пуста");
            return true;
        } else {
            debugMessage("Корзина не пуста");
            return false;
        }
    }

    /** Определить активна ли кнопка перехода в чекаут в корзине */
    public boolean isCheckoutButtonActive() {
        ShopHelper.Cart.open();
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
        ShopHelper.Cart.open();
        return isElementDisplayed(Elements.Cart.total());
    }


    // ======= Чекаут =======

    /** Определить находимся ли в чекауте */
    public boolean isOnCheckout() {
        return kraken.detect().isElementPresent(Elements.Checkout.header());
    }

    /** Определить введен ли телефон на 2 шаге в чекауте */
    public boolean isPhoneNumberEmpty() {
        return isElementDisplayed(Elements.Checkout.phoneNumberField());
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
        return isElementPresent(Elements.Checkout.Bonus.Program.editButton(bonus.getName()));
    }

    public boolean isBonusActive(LoyaltiesData bonus) {
        return isElementPresent(Elements.Checkout.Bonus.Program.activeSnippet(bonus.getName()));
    }

    public boolean isRetailerCardAdded() {
        //return isElementPresent(Elements.Checkout.loyaltyProgramsEditButton()); // Переделать
        return false;
    }


    /**Определить доступна ли программа лояльности ритейлера в чекауте */
    public boolean isRetailerLoyaltyAvailable() {
        return kraken.detect().element(
                "//aside/div/div[4]/div[2]", "Карты лояльности магазинов"); // TODO вынести в Elements
    }

    /** Определить активен ли шаг чекаута в данный момент, по наличию кнопок "Продолжить" */
    public boolean isCheckoutStepActive(int step) {
        return isElementPresent((By.xpath("(//button[@type='button'])[" + step + "]"))); // TODO вынести в Elements
    }

    /** Определить активна ли кнопка отправки заказа */
    public boolean isSendButtonActive() {
        return isElementEnabled(By.xpath("//aside/div/div[1]/div/button")); // TODO вынести в Elements
    }


    // ======= Widgets =======

    public boolean isWidgetPresent(WidgetData widget){
        kraken.grab().currentURL();
        if(widget.getProvider().equals("RetailRocket")){
            return isElementPresent(Elements.RetailRocket.widget(widget.getId()));
                    //&& kraken.grab().text(Elements.RetailRocket.title(widget.getId())).equals(widget.getName());
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
    public boolean isJivositeMessageSent() {
        if (isElementDisplayed(Elements.Jivosite.sentMessage())){
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
