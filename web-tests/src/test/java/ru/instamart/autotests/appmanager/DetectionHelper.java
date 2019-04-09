package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.*;

import static ru.instamart.autotests.application.Config.verbose;

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

    /**
     * Задетектить элемент
     */
    public boolean element(ElementData element) {
        return element(element.getLocator(), element.getText());
    }

    /**
     * Задетектить элемент по локатору
     */
    public boolean element(By locator, String text) {
        return isElementPresent(locator) && kraken.grab().text(locator).equals(text);
    }

    // Todo убрать метод, везде заменить на element(Elements element)
    public boolean element(String xpath, String text) {
        return isElementPresent(By.xpath(xpath)) && kraken.grab().text(By.xpath(xpath)).equals(text);
    }

    /**
     * Определить отображается ли элемент
     */
    public boolean isElementPresent(ElementData element) {
        return isElementPresent(element.getLocator());
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
     * Определить показан ли текст
     */
    public boolean isTextShown(ElementData element) {
        return isElementDisplayed(element) && kraken.grab().text(element) != null;
    }

    /**
     * Определить находимся на лендинге или нет
     */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Landing.header());
    }

    /**
     * Определить находимся на сайте или нет
     */
    public boolean isOnSite() {
        return isElementPresent(Elements.Site.footer());
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
        return isElementPresent(Elements.Site.UserProfile.menu());
    }

    /**
     * Определить 404 ошибку на текущей странице
     */
    public boolean is404() {
        if (element(Elements.Page404.title())) {
            if(verbose) { kraken.perform().printMessage("⚠ " + kraken.grab().currentURL()+ " - 404\n"); }
            return true;
        } else return false;
    }

    /**
     * Определить 500 ошибку на текущей странице
     */
    public boolean is500() {
        if (element(Elements.Page500.placeholder())) {
            if(verbose) { kraken.perform().printMessage("⚠ " + kraken.grab().currentURL()+ " - 500\n"); }
            return true;
        } else return false;
    }


    // ======= Модалки =======

    /**
     * Определить открыта ли модалка "Доставка"
     */
    public boolean isDeliveryModalOpen() {
        return isElementDisplayed(Elements.Site.DeliveryModal.popup())
                && element(Elements.Site.DeliveryModal.title());
    }

    /**
     * Определить открыта ли модалка "Оплата"
     */
    public boolean isPaymentModalOpen() {
        return isElementDisplayed(Elements.Site.PaymentModal.popup())
                && element(Elements.Site.PaymentModal.title());
    }

    /**
     * Определить открыт ли модалка "Партнеры"
     */
    public boolean isPartnersModalOpen() {
        return isElementDisplayed(Elements.Site.PartnersModal.popup())
                && element(Elements.Site.PartnersModal.title());
    }

    /**
     * Определить открыт ли модалка "Адрес"
     */
    public boolean isAddressModalOpen() {
        return isElementDisplayed(Elements.Site.AddressModal.popup())
                && element(Elements.Site.AddressModal.titleSet())
                || element(Elements.Site.AddressModal.titleChange());
    }

    /**
     * Определить показана ли заглушка "Адрес вне зоны доставки" в адресной модалке
     */
    public boolean isAddressOutOfZone() {
        if (element(Elements.Site.AddressModal.titleOutOfZone())) {
            if(verbose) { printMessage("Адрес не в зоне доставки"); }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определить открыта ли модалка "Выберите магазин"
     */
    public boolean isChangeStoreModalOpen() {
        return isElementDisplayed(Elements.Site.StoresModal.popup());
    }


    // ======= Меню "Профиль" =======

    /** Определить открыто ли меню аккаунта */
    public boolean isAccountMenuOpen() {
        return isElementDisplayed(Elements.Site.AccountMenu.popup());
    }


    // ======= Поиск =======

    /** Детектим пустой результат поиска */
    public boolean isSearchResultsEmpty() {
        if(isElementPresent(Elements.Site.Catalog.emptySearchPlaceholder())){
            if(verbose) { printMessage("Пустой результат поиска"); }
            return true;
        } else return false;
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isProductSuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Site.Header.Search.productSuggest());
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isCategorySuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Site.Header.Search.categorySuggest());
    }


    // ======= Авторизация =======

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        return isElementDisplayed(Elements.Site.AuthModal.popup());
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        if(verbose) {  printMessage("Проверяем авторизованность..."); }
        if (element(Elements.Site.Header.profileButton()) || isInAdmin()) {
            if(verbose) { printMessage("✓ Авторизован\n"); }
            return true;
        } else {
            if(verbose) { printMessage("Не авторизован\n"); }
            return false;
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoveryRequested(){
        if (kraken.detect().element(Elements.Site.AuthModal.successRecoveryText())) {
            if(verbose) { printMessage("Запрошено восстановление пароля"); }
            return true;
        } else {
            if(verbose) { printMessage("Запрос восстановления пароля не отправлен"); }
            return false;
        }
    }


    // ======= Детали заказа =======

    /** Распознавание документов к заказу на странице деталей */
    public String orderDocument(int position) {
        String docName = kraken.grab().text(Elements.Site.UserProfile.OrderDetailsPage.documentation(position).getLocator());
        if (docName != null) {
            if(verbose) { printMessage("Скачиваем: " + docName); }
            return docName;
        } else {
            if(verbose) { printMessage("Документ отсутствует\n"); }
            return null;
        }
    }

    /** Определить активен ли верхний заказ на странице списка заказов */
    public boolean isLastOrderActive() {
        if(isElementPresent(Elements.Site.UserProfile.OrdersPage.lastOrderActionButton(2))) {
            if (verbose) { printMessage("Крайний заказ активен"); }
            return true;
        } else {
            if(verbose) { printMessage("Крайний заказ неактивен"); }
            return false;
        }
    }

    /** Определить активен ли заказ на странице деталей */
    public boolean isOrderActive() {
        if(verbose) {  printMessage("Проверяем страницу заказа..."); }
        if (isElementDisplayed(Elements.Site.UserProfile.OrderDetailsPage.activeOrderAttribute()) && !isOrderCanceled()) {
            if(verbose) {  printMessage("✓ Заказ активен\n"); }
            return true;
        } else return false;
    }

    /** Определить отменен ли заказ на странице деталей */
    public boolean isOrderCanceled(){
        if (isInAdmin()) {
            if (element(Elements.Admin.Shipments.Order.Details.canceledOrderAttribute())) {
                if(verbose) { printMessage("Заказ отменен"); }
                return true;
            } else {
                if(verbose) { printMessage("Заказ активен"); }
                return false;
            }
        } else {
            return element(Elements.Site.UserProfile.OrderDetailsPage.canceledOrderAttribute());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementPresent((Elements.Site.Header.currentShipAddress()))) {
            if(verbose) { printMessage("Выбран адрес доставки: " + kraken.grab().currentShipAddress()); }
            return true;
        } else {
            if(verbose) { printMessage("Адрес доставки не выбран\n"); }
            return false;
        }
    }

    /** Определить показаны ли адресные саджесты */
    public boolean isAnyAddressSuggestsAvailable() {
        return isElementPresent(Elements.Site.AddressModal.addressSuggest());
    }


    // ======= Шторка выбора магазинов =======

    /** Определить открыта ли шторка выбора магазина */
    public boolean isStoreSelectorOpen() {
        return kraken.detect().isElementDisplayed(Elements.Site.StoreSelector.drawer());
    }

    /** Определить пуст ли селектор */
    public boolean isStoreSelectorEmpty() {
        return kraken.detect().isElementDisplayed(Elements.Site.StoreSelector.placeholder());
    }

    /**Определить доступен ли селектор магазинов*/
    public boolean isStoreSelectorAvailaible(){
        if (kraken.detect().isElementDisplayed(Elements.Site.Header.changeStoreButton())){
            printMessage("Селектор магазинов доступен");
            return true;
        } else {
            printMessage("Селектор магазинов недоступен");
            return false;
        }
    }


    //========= Шторка каталога ==========

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        if (kraken.detect().isElementDisplayed(Elements.Site.CatalogDrawer.drawer())) {
            if(verbose) { printMessage("Шторка каталога открыта"); }
            return true;
        } else {
            if(verbose) { printMessage("Шторка каталога закрыта"); }
            return false;
        }
    }


    // ======= Каталог =======

    /** Определить есть ли товары на странице */
    public boolean isProductAvailable() {
        if(kraken.detect().isElementPresent(Elements.Site.Catalog.product())){
            if(verbose) { printMessage("✓ Есть доступные товары"); }
            return true;
        } else {
            if(verbose) { printMessage("Нет доступных товаров!"); }
            return false;
        }
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(kraken.detect().isElementPresent(Elements.Site.ItemCard.popup())){
            if(verbose) { printMessage("> открыта карточка товара " + kraken.grab().currentURL()); }
            return true;
        } else return false;
    }

    /** Определить есть ли скидка на товар */
    public boolean isItemOnSale() {
        return element(Elements.Site.ItemCard.saleBadge());
    }


    // ======= Любимые товары =======

    /** Определить наличие пустого списка любимых товаров */
    public boolean isFavoritesEmpty() {
        if(!kraken.grab().currentURL().equals(fullBaseUrl + Pages.Site.Profile.favorites().getPagePath())) {
            kraken.get().favoritesPage();
        }
        if(kraken.detect().isElementPresent(Elements.Site.Favorites.placeholder())){
            if(verbose) { printMessage("Нет любимых товаров\n"); }
           return true;
        } else {
            if(verbose) { printMessage("Есть любимые товары\n"); }
            return false;
        }
    }

    /** Определить выбран ли фильтр Все товары */
    public boolean isFavoritesFiltered(String filter) {
        String activeFilter = kraken.grab().text(Elements.Site.Favorites.activeFilter());
        switch (filter) {
            case "all" :
                return activeFilter.equals(kraken.grab().text(Elements.Site.Favorites.allItemsFilterButton()));
            case "inStock" :
                return activeFilter.equals(kraken.grab().text(Elements.Site.Favorites.inStockFilterButton()));
            case "outOfStock" :
                return activeFilter.equals(kraken.grab().text(Elements.Site.Favorites.outOfStockFilterButton()));
            default: return false;
        }
    }


    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        if (isElementDisplayed(Elements.Site.Cart.drawer())){
            if(verbose) {printMessage("Корзина открыта");}
            return true;
        } else {
            if(verbose) {printMessage("Корзина закрыта");}
            return false;
        }
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        kraken.shopping().openCart();
        if (isElementPresent(Elements.Site.Cart.placeholder())) {
            if(verbose) {printMessage("Корзина пуста");}
            return true;
        } else {
            if(verbose) {printMessage("Корзина не пуста");}
            return false;
        }
    }

    /** Определить активна ли кнопка перехода в чекаут в корзине */
    public boolean isCheckoutButtonActive() {
        kraken.shopping().openCart();
        if(isElementEnabled(Elements.Site.Cart.checkoutButton())){
            if(verbose) {printMessage("Кнопка перехода в чекаут активна");}
            return true;
        } else {
            if(verbose) {printMessage("Кнопка перехода в чекаут неактивна");}
            return false;
        }
    }

    /** Определить отображается ли сумма заказа */
    public boolean isCartTotalDisplayed() {
        kraken.shopping().openCart();
        return isElementDisplayed(Elements.Site.Cart.total());
    }


    // ======= Чекаут =======

    /** Определить находимся ли в чекауте */
    public boolean isOnCheckout() {
        return kraken.detect().isElementPresent(Elements.Site.Checkout.header());
    }

    /** Определить введен ли телефон на 2 шаге в чекауте */
    public boolean isPhoneNumberEntered() {
        return isElementDisplayed(Elements.Site.Checkout.phoneIcon());
    }

    /** Определить введены ли данные юрлица на 4 шаге в чекауте */
    public boolean isPaymentCardEntered() {
        return isElementDisplayed(Elements.Site.Checkout.paymentCardTitle(1));
    }

    /** Определить введены ли данные второго юрлица на 4 шаге в чекауте */
    public boolean isSecondPaymentCardEntered() {
        return isElementDisplayed(Elements.Site.Checkout.paymentCardTitle(2));
    }

    /** Определить введены ли данные юрлица на 4 шаге в чекауте */
    public boolean isJuridicalEntered() {
        return isElementDisplayed(Elements.Site.Checkout.juridicalTitle(1));
    }

    /** Определить введены ли данные второго юрлица на 4 шаге в чекауте */
    public boolean isSecondJuridicalEntered() {
        return isElementDisplayed(Elements.Site.Checkout.juridicalTitle(2));
    }

    /** Определить отображаются ли слоты доставки в чекауте */
    public boolean isDeliveryWindowSelectorShown() {
        return isElementPresent(Elements.Site.Checkout.deliveryWindowSelector());
    }

    /** Определить добавлен ли промокод в чекауте */
    public boolean isPromocodeApplied() {
        if (kraken.detect().element(Elements.Site.Checkout.appliedPromocodeAttribute())) {
            if(verbose) { printMessage("✓ Промокод применён\n"); }
            return true;
        } else {
            if(verbose) { printMessage("Промокод не применён\n"); }
            return false;
        }
    }

    public boolean isBonusAdded(BonusProgramData bonus) {
        return isElementPresent(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]/div[2]")); // TODO вынести в  Elements
    }

    public boolean isLoyaltyAdded(LoyaltyProgramData loyalty) {
        return isElementPresent(By.xpath("//aside/div/div[3]/div[2]/div[" + loyalty.getPosition() + "]/div[2]")); // TODO вынести в  Elements
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

    //**Определить доступны ли программы лояльности в чекауте*/
    public boolean isLoyaltyAvailaible(){
    return isElementDisplayed(Elements.Site.Checkout.loyaltyPrograms());
    }


    // ======= Widgets =======

    public boolean isWidgetPresent(WidgetData widget){
        printMessage(kraken.grab().currentURL());
        if(widget.getProvider().equals("RetailRocket")){
            return isElementPresent(Elements.RetailRocket.widget(widget.getId()));
                    //&& kraken.grab().text(Elements.RetailRocket.title(widget.getId())).equals(widget.getName());
        } else
            printMessage("В детекторе не найден провайдер виджета");
            return false;
    }


    // ======= Jivosite =======

    /** Определить открыт ли виджет Jivosite */
    public boolean isJivositeOpen() {
        if (isElementDisplayed(Elements.Site.Jivosite.sendMessageButton())){
            if(verbose) { printMessage("Чат Jivosite развернут\n"); }
            return true;
        } else {
            if(verbose) { printMessage("Чат Jivosite свернут\n"); }
            return false;
        }
    }

    /** Определить отображается ли чат в виджете Jivosite */
    public boolean isJivositeChatAvailable() {
        return isElementDisplayed(Elements.Site.Jivosite.chatArea());
    }

    /** Определить отправлено ли сообщение в Jivosite */
    public boolean isJivositeMessageSent() {
        if (isElementDisplayed(Elements.Site.Jivosite.sentMessage())){
            if(verbose) {  printMessage("Сообщение отправлено"); }
            return true;
        } else {
            if(verbose) { printMessage("Сообщение не отправлено"); }
            return false;
        }
    }

    /** Определить доступен ли виджет Jivosite*/
    public boolean isJivositeWidgetAvailable () {
        kraken.await().implicitly(2); // Ожидание подгрузки виджета Jivosite
        if (isElementDisplayed(Elements.Site.Jivosite.widget())){
            if(verbose) { printMessage("Виджет Jivosite доступен\n"); }
            return true;
        } else {
            if(verbose) { printMessage("Виджет Jivosite недоступен\n"); }
            return false;
        }
    }

    // ======= Tenant =======

    /** Определить доступны ли Контакты в меню профиля*/
    public boolean isContactsOnProfileAvailaible() {
        kraken.perform().click(Elements.Site.Header.profileButton());
        if (isElementDisplayed(Elements.Site.AccountMenu.contactsLink())){
            printMessage("Контакты в меню профиля доступны\n");
            return true;
        } else {
            printMessage("Контакты недоступны в меню профиля\n");
            return false;
        }
    }

    //** Определить доступен ли тенант-футер*/
    public boolean isTenantFooterAvailable() {
        if (isElementDisplayed(Elements.Tenant.tenantFooter())){
            printMessage("Тенант-футер доступен\n");
            return true;
        } else {
            printMessage("Тенант-футер недоступен\n");
            return false;
        }
    }
}
