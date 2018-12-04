package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Loyalties;

public class DetectionHelper extends HelperBase {

    private ApplicationManager kraken;

    DetectionHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Определить показан ли алерт на странице */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }


    /** Задетектить элемент */
    public boolean element(Elements element) {
        // DEBUG
        // printMessage("Detecting element " + Elements.text());
        return element(Elements.locator(),Elements.text());
    }

    /** Задетектить элемент по локатору*/
    public boolean element(By locator, String text) {
        return isElementPresent(locator) && kraken.grab().text(locator).equals(text);
    }

    // Todo убрать метод, везде заменить на element(Elements element)
    public boolean element(String xpath, String text) {
        return isElementPresent(By.xpath(xpath)) && kraken.grab().text(By.xpath(xpath)).equals(text);
    }

    /** Определить отображается ли элемент */
    public boolean isElementPresent(Elements element) {
        return isElementPresent(Elements.locator());
    }

    /** Определить отображается ли элемент по локатору */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /** Определить показан ли элемент */
    public boolean isElementDisplayed(Elements element){
        return isElementDisplayed(Elements.locator());
    }

    /** Определить показан ли элемент по локатору */
    public boolean isElementDisplayed(By locator){
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /** Определить доступен ли элемент */
    public boolean isElementEnabled(Elements element){
        return isElementEnabled(Elements.locator());
    }

    /** Определить доступен ли элемент по локатору */
    public boolean isElementEnabled(By locator){
        return driver.findElement(locator).isEnabled();
    }

    /** Определить проставлен ли чекбокс */
    boolean isCheckboxSelected(Elements element) {
        return isCheckboxSelected(Elements.locator());
    }

    /** Определить проставлен ли чекбокс по локатору */
    boolean isCheckboxSelected(By locator) {
        return driver.findElement(locator).isSelected();
    }

    /** Определить находимся на лендинге или нет */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Site.Landing.header());
    }

    /** Определить находимся на сайте или нет */
    public boolean isOnSite() { return isElementPresent(Elements.Site.footer()); }

    /** Определить находимся в админке или нет */
    public boolean isInAdmin() {
        return isElementPresent(Elements.Admin.container());
    }

    /** Определить 404 ошибку */
    public boolean is404() {
        return element(Elements.Page404.title());
    }

    /** Определить 500 ошибку */
    public boolean is500() {
        return element(Elements.Page500.placeholder());
    }


    // ======= Модалки =======

    /** Определить открыта ли модалка "Модалка" */
    public boolean isDeliveryModalOpen() {
        return isElementDisplayed(Elements.Site.DeliveryModal.popup())
                && element(Elements.Site.DeliveryModal.title());
    }

    /** Определить открыта ли модалка "Оплата" */
    public boolean isPaymentModalOpen() {
        return isElementDisplayed(Elements.Site.PaymentModal.popup())
                && element(Elements.Site.PaymentModal.title());
    }

    /** Определить открыт ли модалка "Партнеры" */
    public boolean isPartnersModalOpen() {
        return isElementDisplayed(Elements.Site.PartnersModal.popup())
                && element(Elements.Site.PartnersModal.title());
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
            printMessage("Empty search results");
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
        printMessage("Checking authorisation...");
        if (element(Elements.Site.Header.profileButton())) {
            printMessage("✓ Authorised\n");
            return true;
        } else {
            printMessage("No auth!\n");
            return false;
        }
    }


    // ======= Восстановление пароля =======

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoverySent(){
        if (!isElementDisplayed(Elements.Site.AuthModal.popup())
                || isElementPresent(Elements.Site.AuthModal.emailField())) {
            printMessage("Recovery not sent!");
            return false;
        } else {
            printMessage("✓ Recovery requested");
            return true;
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
        printMessage("Checking order page...");
        if (element(Elements.Site.OrderDetailsPage.activeOrderAttribute())) {
            printMessage("✓ Order is active\n");
            return true;
        } else {
            printMessage("Experiencing performance troubles");
            kraken.perform().waitingFor(1);
            if (element(Elements.Site.OrderDetailsPage.activeOrderAttribute())) {
                printMessage("✓ Order is active\n");
                return true;
            } else return false;
        }
    }

    /** Определить отменен ли заказ на странице деталей */
    public boolean isOrderCanceled(){
        if (isInAdmin()) {
            return element(Elements.Admin.Shipments.OrderDetailsPage.canceledOrderAttribute());
        } else {
            return element(Elements.Site.OrderDetailsPage.canceledOrderAttribute());
        }
    }


    // ======= Адрес доставки =======

    /** Определяем пуст ли адрес доставки */
    public boolean isShippingAddressEmpty() {
        return element(Elements.Site.Header.setShipAddressButton());
    }

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (element((Elements.Site.Header.changeShipAddressButton()))) {
            kraken.grab().currentShipAddress();
            return true;
        } else {
            printMessage("Shipping address is not set\n");
            return false;
        }
    }

    /** Определить показаны ли адресные саджесты */
    public boolean isAnyAddressSuggestsAvailable() {
        return isElementPresent(Elements.Site.AddressModal.addressSuggest());
    }


    // ======= Шторка выбора магазинов =======

    /** Определить открыта ли шторка выбора магазина */
    public boolean isShopSelectorOpen() {
        return kraken.detect().isElementDisplayed(Elements.Site.ShopSelector.drawer());
    }

    /** Определить пуст ли селектор */
    public boolean isShopSelectorEmpty() {
        return kraken.detect().isElementDisplayed(Elements.Site.ShopSelector.placeholder());
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
            printMessage("✓ Products available");
            return true;
        } else {
            printMessage("No products available!");
            return false;
        }
    }


    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(kraken.detect().isElementPresent(Elements.Site.ItemCard.popup())){
            printMessage("✓ Item card open");
            return true;
        } else return false;
    }


    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        kraken.perform().waitingFor(1); // Пауза, на случай если штокра медленно отображается
        return kraken.detect().isElementDisplayed(Elements.Site.Cart.drawer());
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        kraken.shopping().openCart();
        kraken.perform().waitingFor(1); // Пауза на случай, тормозов с корзиной
        return kraken.detect().isElementPresent(Elements.Site.Cart.placeholder());
    }

    /** Определить активна ли кнопка "Сделать заказ" в корзине */
    public boolean isCheckoutButtonActive() {
        kraken.shopping().openCart();
        kraken.perform().waitingFor(1); // Пауза на случай, если стостояние кнопки долго обновляется
        return kraken.detect().isElementEnabled(Elements.Site.Cart.checkoutButton());
    }


    // ======= Чекаут =======

    /** Определить находимся ли в чекауте */
    public boolean isOnCheckout() {
        return kraken.detect().isElementPresent(Elements.Site.Checkout.header());
    }

    /** Определить добавлен ли промокод в чекауте */
    public boolean isPromocodeApplied() {
        if (kraken.detect().element(Elements.Site.Checkout.appliedPromocodeAttribute())) {
            printMessage("✓ Promocode applied");
            return true;
        } else {
            printMessage("No promocode applied");
            return false;
        }
    }

    /** Определить применена ли программа лояльности */
    public boolean isLoyaltyApplied(String name) {
        return kraken.detect().isElementPresent(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]/div[2]"));
    }

    /**Определить доступна ли программа лояльности ритейлера в чекауте */
    public boolean isRetailerLoyaltyAvailable() {
        return kraken.detect().element(
                "//aside/div/div[4]/div[2]",
                "Карты лояльности магазинов");
    }

    /** Определить активен ли шаг чекаута в данный момент, по наличию кнопок "Продолжить" */
    public boolean isStepActive(int step) {
        return kraken.detect().isElementPresent((By.xpath("(//button[@type='button'])[" + step + "]")));
    }

    /** Определить активна ли кнопка отправки заказа */
    public boolean isSendButtonActive() {
        return kraken.detect().isElementEnabled(By.xpath("//aside/div/div[1]/div/button"));
    }
}
