package ru.instamart.ui.module.shop;

import io.qameta.allure.Step;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.core.setting.Config;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.Elements;

public final class ShippingAddressModal extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);

    public ShippingAddressModal(final AppManager kraken) {
        super(kraken);
    }

    /** Открыть модалку ввода адреса */
    @Step("Открываем модалку ввода адреса доставки")
    public static void open() {
        log.info("> открываем модалку ввода адреса доставки");
//            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
        kraken.perform().click(Elements.Header.shipAddressButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n", Config.BASIC_TIMEOUT);
        log.info("> модака открыта");
    }

    @Step("Открываем модалку авторизации из адресной модалки феникса")
    public static void openAuthModal() {
        log.info("> открываем модалку авторизации из адресной модалки феникса");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(Elements.Modals.AddressModal.authButton().getLocator()),
                "не отображается кнопка входа",2);
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        try {
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()),
                    "форма авторизации не открывается", Config.BASIC_TIMEOUT);
            log.info("> модалка авторизации открыта");
            return;
        }catch (Exception ex){
            kraken.perform().click(Elements.Modals.AddressModal.authButton());
        }
        kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()),
                "форма авторизации не открывается", Config.BASIC_TIMEOUT);
        log.info("> модалка авторизации открыта");
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
        log.info("> водим адрес в адресной модалке: {}",address);
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Modals.AddressModal.adressImageOnMap().getLocator()),
                "Не подгружается карта яндекса",Config.BASIC_TIMEOUT);
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), address);
    }

    /** Выбрать первый адресный саджест */
    @Step("Выбираем первый предложенный адрес")
    public static void selectAddressSuggest() {
        log.info("> выбираем первый предложенный адрес");
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(Elements.spinner().getLocator()),
                "элемент спинер не исчез, выбор саджестов невозможен",5);
        kraken.perform().click(Elements.Modals.AddressModal.addressSuggest());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AddressModal.addressSuggest().getLocator()),
                "саджесты не выбраны и все еще отображаются",Config.BASIC_TIMEOUT);
    }

    /** Применить введенный адрес в адресной модалке */
    @Step("Применяем введенный адрес в адресной модалке")
    public static void submit() throws AssertionError {
        log.info("> применяем введенный адрес в адресной модалке");
        kraken.perform().click(Elements.Modals.AddressModal.submitButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Modals.AddressModal.submitButton().getLocator()),
                "Превышено время ожидания применения адреса доставки",Config.BASIC_TIMEOUT);
    }

    @Step("Нажимаем кнопку изменить адрес доставки")
    public static void selectNewAdress() {
        log.info("> выбираем другой адрес доставки");
        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n",Config.BASIC_TIMEOUT);
        log.info("> очищаем адресную строку");
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
    }

    @Step("Нажимаем кнопку изменить адрес доставки у выбранного ретейлера")
    public static void selectNewAdressRetailer() {
        log.info("> выбираем другой адрес доставки у выбранного ретейлера");
        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressReteilerButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.popup().getLocator()),
                "Не открылась модалка ввода адреса доставки\n",Config.BASIC_TIMEOUT);
        log.info("> очищаем адресную строку");
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
    }

    /** Ищем доступные магазины по введенному адресу */
    @Step("Ищем доступные магазины по введенному адресу")
    public static void findShops() throws AssertionError {
        log.info("> ищем доступные магазины по введенному адресу");
        kraken.perform().click(Elements.Modals.AddressModal.findShopButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.StoreSelector.drawer().getLocator()),
                "Превышено время ожидания применения адреса доставки\n" +
                        " или по выбранному адресу отсутсвует возможность доставки");
        if (kraken.detect().isStoresDrawerOpen()) {
            log.info("> по указанному адресу присутсвует несколько магазинов выбираем первый");
            kraken.perform().click(Elements.StoreSelector.storeCard(1));
        } else if(kraken.detect().isStoresErrorPresented()){
            log.info("> по указанному адресу отсутвуют возможные магазины для доставки");
            throw new ElementNotSelectableException("по указанному адресу отсутвуют возможные магазины для доставки");
        }
    }

    /** Выбрать первый в списке предыдущий адрес в адресной модалке */
    @Step("Выбираем первый в списке предыдущий адрес в адресной модалке")
    public static void chooseRecentAddress() {
        log.info("> выбираем первый в списке предыдущий адрес в адресной модалке");
        kraken.perform().click(Elements.Modals.AddressModal.recentAddress());
//            kraken.await().implicitly(1); // Ожидание применения предыдущего адреса
    }

    /** Закрыть модалку адреса */
    @Step("Закрываем модалку адреса")
    public static void close() {
        log.info("> закрываем модалку адреса");
        kraken.perform().click(Elements.Modals.AddressModal.closeButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Modals.AddressModal.closeButton().getLocator()),
                "Модалка ввода адреса доставки не закрывается",Config.BASIC_TIMEOUT);
    }

    /** Ищем магазины по установленному адресу */
    @Step("Открываем ретейлера по иконке магазина")
    public static void selectFirstRetailer(){
        log.info("> открываем ретейлера по иконке магазина");
        kraken.await().fluently(
                ExpectedConditions
                        .elementToBeClickable(Elements.Landings.SbermarketLanding.MainBlock.Stores.button().getLocator()),
                "кнопка выбора ретейлера недоступна");
        kraken.perform().click(Elements.Landings.SbermarketLanding.MainBlock.Stores.button());
        log.info("> ретейлер выбран");
    }
}
