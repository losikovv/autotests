package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class AddressHelper extends HelperBase {

    private ApplicationManager kraken;

    AddressHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /**
     * Установить адрес доставки
     */
    public void set(String address) {
        printMessage("Setting shipping address...");

        if (kraken.grab().currentURL().equals(fullBaseUrl)) {
            kraken.perform().fillField(Elements.Site.Landing.addressField(), address);
            kraken.perform().waitingFor(1);
            kraken.perform().click(Elements.Site.Landing.addressSuggest());
            kraken.perform().click(Elements.Site.Landing.selectStoreButton());
            kraken.perform().waitingFor(1);
        } else {
            kraken.shipAddress().fill(address);
            kraken.shipAddress().submit();
        }
    }

    /**
     * Изменить адрес доставки
     */
    public void change(String address) {
        printMessage("Changing shipping address");
        kraken.shipAddress().fill(address);
        kraken.shipAddress().submit();
    }

    /**
     * Выбрать первый адресный саджест
     */
    private void selectAddressSuggest() {
        if (kraken.detect().isAnyAddressSuggestsAvailable()) {
            kraken.perform().click(Elements.Site.AddressModal.addressSuggest());
            kraken.perform().waitingFor(1); // Пауза, чтобы дать время обновиться кнопке "сохранить адрес"
        } else {
            printMessage("Can't click address suggest, there are no such");
        }
    }

    /**
     * Открыть модалку адреса
     */
    public void openAddressModal() {
        if (kraken.detect().isAddressModalOpen()) {
            printMessage("Address modal is already opened");
        }
        else
            if (kraken.detect().isShippingAddressEmpty()) {
                kraken.perform().click(Elements.Site.Header.setShipAddressButton());
            } else {
                kraken.perform().click(Elements.Site.Header.changeShipAddressButton());
            }
        }

    /**
     * Закрыть модалку адреса
     */
    public void closeAddressModal() {
        kraken.perform().click(Elements.Site.AddressModal.closeButton());
        kraken.perform().waitingFor(1);
    }

    /**
     * Сохранить адрес в модалке
     */
    private void submit() {
        kraken.perform().click(Elements.Site.AddressModal.saveButton());
        kraken.perform().waitingFor(2);
    }

    /**
     * Ввести новый адрес в модалке
     */
    public void fill(String address) {
        kraken.shipAddress().openAddressModal();

        // DEBUG
        // printMessage("Modal opened: [" + text(Elements.Site.AddressModal.header()) + "]");

        kraken.perform().fillField(Elements.Site.AddressModal.addressField(), address);
        selectAddressSuggest();
    }
}