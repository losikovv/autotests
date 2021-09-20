package ru.instamart.ui.module.shop;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.helper.JsHelper;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;

import java.util.List;

public final class ShippingAddressModal extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);

    public ShippingAddressModal(final AppManager kraken) {
        super(kraken);
    }

    /** Открыть модалку ввода адреса */
    @Step("Открываем модалку ввода адреса доставки")
    public static void open() {
        log.debug("> открываем модалку ввода адреса доставки");
//            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
        kraken.perform().click(Elements.Header.shipAddressButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n", WaitProperties.BASIC_TIMEOUT);
        log.debug("> модака открыта");
    }

    @Step("Открываем модалку авторизации из адресной модалки феникса")
    public static void openAuthModal() {
        log.debug("> открываем модалку авторизации из адресной модалки феникса");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(Elements.Modals.AddressModal.authButton().getLocator()),
                "не отображается кнопка входа",2);
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        try {
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()),
                    "форма авторизации не открывается", WaitProperties.BASIC_TIMEOUT);
            log.debug("> модалка авторизации открыта");
            return;
        }catch (Exception ex){
            kraken.perform().click(Elements.Modals.AddressModal.authButton());
        }
        kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()),
                "форма авторизации не открывается", WaitProperties.BASIC_TIMEOUT);
        log.debug("> модалка авторизации открыта");
    }

    /** Очистить поле в адресной модалке */
    @Step("Очищаем поле в адресной модалке")
    public static void clearAddressField() {
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
    }

    public static void fill(AddressV2 address) {
        fill(address.getCity() + " " + address.getStreet() + " " + address.getBuilding());
    }

    /** Ввести адрес в адресной модалке */
    @Step("Вводим адрес в адресной модалке: {0}")
    public static void fill(String address) {
        log.debug("> водим адрес в адресной модалке: {}",address);
        JsHelper.ymapReady();
        kraken.perform().fill(Elements.Modals.AddressModal.addressField(), address);
    }

    public static void clearField() {
        kraken.perform().clearField(Elements.Modals.AddressModal.addressField());
    }

    /** Выбрать первый адресный саджест */
    @SneakyThrows
    @Step("Выбираем первый предложенный адрес")
    public static void selectAddressSuggest() {
        log.debug("> выбираем первый предложенный адрес");
        final List<WebElement> dropdown = kraken.await().isElementsExist(Elements.Modals.AddressModal.dropdown());
        final WebElement webElement = dropdown.stream().findFirst().orElseThrow();
        webElement.click();
        //TODO: Ожидание смены геопозиции
        Thread.sleep(2000);
    }

    /** Применить введенный адрес в адресной модалке */
    @Step("Применяем введенный адрес в адресной модалке")
    public static void submit() throws AssertionError {
        log.debug("> применяем введенный адрес в адресной модалке");
        kraken.perform().click(Elements.Modals.AddressModal.submitButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Modals.AddressModal.submitButton().getLocator()),
                "Превышено время ожидания применения адреса доставки",3);
    }

    @Step("Нажимаем кнопку изменить адрес доставки")
    public static void selectNewAdress() {
        log.debug("> выбираем другой адрес доставки");
        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n",WaitProperties.BASIC_TIMEOUT);
    }

    @Step("Нажимаем кнопку изменить адрес доставки у выбранного ретейлера")
    public static void selectNewAdressRetailer() {
        log.debug("> выбираем другой адрес доставки у выбранного ретейлера");
        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressReteilerButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n",WaitProperties.BASIC_TIMEOUT);
    }

    /** Ищем доступные магазины по введенному адресу */
    @Step("Ищем доступные магазины по введенному адресу")
    public static void findShops() throws AssertionError {
        log.debug("> ищем доступные магазины по введенному адресу");
        kraken.perform().click(Elements.Modals.AddressModal.findShopButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.StoreSelector.drawer().getLocator()),
                "Превышено время ожидания применения адреса доставки\n" +
                        " или по выбранному адресу отсутсвует возможность доставки");
        if (kraken.detect().isStoresDrawerOpen()) {
            log.debug("> по указанному адресу присутсвует несколько магазинов выбираем первый");
            kraken.perform().click(Elements.StoreSelector.storeCard(1));
        } else if(kraken.detect().isStoresErrorPresented()){
            log.debug("> по указанному адресу отсутвуют возможные магазины для доставки");
            throw new ElementNotSelectableException("по указанному адресу отсутвуют возможные магазины для доставки");
        }
    }

    /** Выбрать первый в списке предыдущий адрес в адресной модалке */
    @Step("Выбираем первый в списке предыдущий адрес в адресной модалке")
    public static void chooseRecentAddress() {
        log.debug("> выбираем первый в списке предыдущий адрес в адресной модалке");
        kraken.perform().click(Elements.Modals.AddressModal.recentAddress());
//            kraken.await().implicitly(1); // Ожидание применения предыдущего адреса
    }

    /** Закрыть модалку адреса */
    @Step("Закрываем модалку адреса")
    public static void close() {
        log.debug("> закрываем модалку адреса");
        kraken.perform().closeAction();
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Modals.AddressModal.closeButton().getLocator()),
                "Модалка ввода адреса доставки не закрывается",WaitProperties.BASIC_TIMEOUT);
    }

    /** Ищем магазины по установленному адресу */
    @Step("Открываем ретейлера по иконке магазина")
    public static void selectFirstRetailer(){
        log.debug("> открываем ретейлера по иконке магазина");
        kraken.await().fluently(
                ExpectedConditions
                        .elementToBeClickable(Elements.Landings.SbermarketLanding.MainBlock.Stores.button().getLocator()),
                "кнопка выбора ретейлера недоступна");
        kraken.perform().click(Elements.Landings.SbermarketLanding.MainBlock.Stores.button());
        log.debug("> ретейлер выбран");
    }
}
