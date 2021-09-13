package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;

public final class PhoneActions extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public PhoneActions(final AppManager kraken) {
        super(kraken);
    }

    //Todo public static class Modal > fill > submit > cancel > close

    public static void editTopEntry(String newNumber) {
        log.debug("> изменяем верхний добавленный номер телефона на {}", newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
        kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
    }

    public static void deleteTopEntry() {
        log.debug("> удаляем верхний добавленный номер телефона");
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
    }

    public static void editActiveEntry(String newNumber) {
        log.debug("> изменяем активный добавленный номер телефона на " + newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.ActiveEntry.changeButton());
        kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
    }

    public static void deleteActiveEntry() {
        log.debug("> удаляем активный добавленный номер телефона");
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.ActiveEntry.changeButton());
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
    }

    public static void editNotActiveEntry(String newNumber) {
        log.debug("> изменяем неактивный добавленный номер телефона на {}", newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.NotActiveEntry.changeButton());
        kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
    }

    public static void deleteNotActiveEntry() {
        log.debug("> удаляем неактивный добавленный номер телефона");
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.NotActiveEntry.changeButton());
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
    }

    @Step("Добавляем новый номер телефона: {0}")
    public static void addNewPhone(String number) {
        log.debug("> добавляем новый номер телефона: {}", number);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.addNewPhoneButton());
        kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),number);
        kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
    }

    @Step("Удаляем все добавленные номера телефонов")
    public static void deleteAll() {
        log.debug("> удаляем все добавленные номера телефонов");
        if (kraken.detect().isElementPresent(Elements.Checkout.ContactsStep.phoneInputField())) {
            kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
            kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
            deleteAll();
        }
    }
}
