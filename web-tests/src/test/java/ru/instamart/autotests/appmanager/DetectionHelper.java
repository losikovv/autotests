package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.BonusProgramData;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.LoyaltyProgramData;

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
    public boolean element(Elements element) {
        return element(Elements.locator(), Elements.text());
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
    public boolean isElementPresent(Elements element) {
        return isElementPresent(Elements.locator());
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
    public boolean isElementDisplayed(Elements element) {
        return isElementDisplayed(Elements.locator());
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
    public boolean isElementEnabled(Elements element) {
        return isElementEnabled(Elements.locator());
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
    public boolean isCheckboxSelected(Elements element) {
        return isCheckboxSelected(Elements.locator());
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
    public boolean isTextShown(Elements element) {
        return isElementDisplayed(element) && kraken.grab().text(element) != null;
    }

    /**
     * Определить показана ли пользовательская ошибка
     */
    public boolean isUserErrorShown(Elements element) {
        if (isTextShown(element)) {
            printMessage("Показана пользовательская ошибка: " + kraken.grab().text(element) + "\n");
            return true;
        } else {
            printMessage("Не показана пользовательская ошибка (" + Elements.locator() + ")\n");
            return false;
        }
    }

    /**
     * Определить находимся на лендинге или нет
     */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Site.Landing.header());
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
     * Определить 404 ошибку на текущей странице
     */
    public boolean is404() {
        if (element(Elements.Page404.title())) {
            kraken.perform().printMessage("⚠ " + kraken.grab().currentURL()+ " - 404\n");
            return true;
        } else return false;
    }

    /**
     * Определить 500 ошибку на текущей странице
     */
    public boolean is500() {
        if (element(Elements.Page500.placeholder())) {
            kraken.perform().printMessage("⚠ " + kraken.grab().currentURL()+ " - 500\n");
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
            printMessage("Адрес не в зоне доставки");
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

    /** Определить пустой результат поиска */
    public boolean isSearchResultsEmpty() {
        if(isElementPresent(Elements.Site.Catalog.emptySearchPlaceholder())){
            printMessage("Пустой результат поиска");
            return true;
        } else return false;
    }


    // ======= Авторизация =======

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        return isElementDisplayed(Elements.Site.AuthModal.popup());
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        printMessage("Проверяем авторизованность...");
        if (element(Elements.Site.Header.profileButton()) || isInAdmin()) {
            printMessage("✓ Авторизован\n");
            return true;
        } else {
            printMessage("Не авторизован\n");
            return false;
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoveryRequested(){
        if (kraken.detect().element(Elements.Site.AuthModal.successRecoveryText())) {
            printMessage("Запрошено восстановление пароля");
            return true;
        } else {
            printMessage("Запрос восстановления пароля не отправлен");
            return false;
        }
    }


    // ======= Детали заказа =======

    /** Распознавание документов к заказу на странице деталей */
    public String orderDocument(int position) {
        Elements.Site.OrderDetailsPage.documentation(position);
        String docName = kraken.grab().text(Elements.locator());
        if (docName != null) {
            printMessage("Скачиваем: " + docName);
            return docName;
        } else {
            printMessage("Документ отсутствует\n");
            return null;
        }
    }

    /** Определить активен ли заказ на странице деталей */
    public boolean isOrderActive() {
        printMessage("Проверяем страницу заказа...");
        if (isElementDisplayed(Elements.Site.OrderDetailsPage.activeOrderAttribute()) && !isOrderCanceled()) {
            printMessage("✓ Заказ активен\n");
            return true;
        } else return false;
    }

    /** Определить отменен ли заказ на странице деталей */
    public boolean isOrderCanceled(){
        if (isInAdmin()) {
            return element(Elements.Admin.Shipments.Order.Details.canceledOrderAttribute());
        } else {
            return element(Elements.Site.OrderDetailsPage.canceledOrderAttribute());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementPresent((Elements.Site.Header.currentShipAddress()))) {
            printMessage("Выбран адрес доставки: " + kraken.grab().currentShipAddress());
            return true;
        } else {
            printMessage("Адрес доставки не выбран\n");
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


    //========= Шторка каталога ==========

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        return kraken.detect().isElementDisplayed(Elements.Site.CatalogDrawer.drawer());
    }


    // ======= Каталог =======

    /** Определить есть ли товары на странице */
    public boolean isProductAvailable() {
        if(kraken.detect().isElementPresent(Elements.Site.Catalog.product())){
            printMessage("✓ Есть доступные товары");
            return true;
        } else {
            printMessage("Нет доступных товаров!");
            return false;
        }
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(kraken.detect().isElementPresent(Elements.Site.ItemCard.popup())){
            printMessage("> открыта карточка товара " + kraken.grab().currentURL());
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
        //debug
        //kraken.perform().printMessage("current url:" + kraken.grab().currentURL());
        //kraken.perform().printMessage("target url:" + (fullBaseUrl + Pages.Site.Profile.favorites().getPagePath()));
        if(!kraken.grab().currentURL().equals(fullBaseUrl + Pages.Site.Profile.favorites().getPagePath())) {
            kraken.get().favoritesPage();
        }
        if(kraken.detect().isElementPresent(Elements.Site.Favorites.placeholder()) &&
                !kraken.detect().isElementPresent(Elements.Site.Favorites.product())){
           printMessage("Нет любимых товаров\n");
           return true;
        } else {
            printMessage("✓ Есть любимые товары\n");
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
        return kraken.detect().isElementDisplayed(Elements.Site.Cart.drawer());
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        kraken.shopping().openCart();
        return isElementPresent(Elements.Site.Cart.placeholder());
    }

    /** Определить активна ли кнопка "Сделать заказ" в корзине */
    public boolean isCheckoutButtonActive() {
        kraken.shopping().openCart();
        return isElementEnabled(Elements.Site.Cart.checkoutButton());
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

    /** Определить отображаются ли слоты доставки в чекауте */
    public boolean isDeliveryWindowSelectorShown() {
        return isElementPresent(Elements.Site.Checkout.deliveryWindowSelector());
    }

    /** Определить добавлен ли промокод в чекауте */
    public boolean isPromocodeApplied() {
        if (kraken.detect().element(Elements.Site.Checkout.appliedPromocodeAttribute())) {
            printMessage("✓ Промокод применён\n");
            return true;
        } else {
            printMessage("Промокод не применён\n");
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


    // ======= Jivosite =======

    /** Определить открыт ли виджет Jivosite */
    public boolean isJivositeOpen() {
        if (isElementDisplayed(Elements.Site.Jivosite.sendMessageButton())){
            printMessage("Чат Jivosite развернут\n");
            return true;
        } else {
            printMessage("Чат Jivosite свернут\n");
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
            printMessage("Сообщение отправлено");
            return true;
        } else {
            printMessage("Сообщение не отправлено");
            return false;
        }
    }

    /** Определить доступен ли виджет Jivosite*/
    public boolean isJivositeWidgetAvailable () {
        kraken.perform().waitingFor(2); // Ожидание подгрузки виджета Jivosite
        if (isElementDisplayed(Elements.Site.Jivosite.widget())){
            printMessage("Виджет Jivosite доступен\n");
            return true;
        } else {
            printMessage("Виджет Jivosite недоступен\n");
            return false;
        }
    }
}
