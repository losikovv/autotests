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
            waitingFor(1);
            kraken.perform().click(Elements.Site.Landing.addressSuggest());
            kraken.perform().click(Elements.Site.Landing.selectStoreButton());
        } else {
            kraken.perform().click(Elements.Site.Header.setShipAddressButton());

            // DEBUG
            // printMessage("Modal opened: [" + fetchText(Elements.Site.AddressModal.header()) + "]");

            kraken.perform().fillField(Elements.Site.AddressModal.addressField(), address);
            selectAddressSuggest();
            kraken.perform().click(Elements.Site.AddressModal.saveButton());
        }
        waitingFor(1);
    }

    /**
     * Изменить адрес доставки
     */
    public void change(String newAddress) {
        printMessage("Changing shipping address");
        kraken.perform().click(Elements.Site.Header.changeShipAddressButton());

        // DEBUG
        // printMessage("Modal opened: [" + fetchText(Elements.Site.AddressModal.header()) + "]");

        kraken.perform().fillField(Elements.Site.AddressModal.addressField(), newAddress);
        selectAddressSuggest();
        kraken.perform().click(Elements.Site.AddressModal.saveButton());
        waitingFor(2);
    }

    /**
     * Выбрать первый адресный саджест
     */
    private void selectAddressSuggest() {
        if (kraken.detect().isAnyAddressSuggestsAvailable()) {
            kraken.perform().click(Elements.Site.AddressModal.addressSuggest());
            waitingFor(1); // Пауза, чтобы дать время обновиться кнопке "сохранить адрес"
        } else {
            printMessage("Can't click address suggest - there are no such");
        }
    }

}