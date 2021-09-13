package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.testdata.lib.CheckoutSteps;
import ru.instamart.kraken.testdata.pagesdata.ContactsDetailsData;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;

import static io.qameta.allure.Allure.step;

public final class ContactsSteps extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public ContactsSteps(final AppManager kraken) {
        super(kraken);
    }

    @Step("Шаг заполнения контактов")
    public static void fill(ContactsDetailsData contactsDetails) {
        log.debug("> заполняем поля с контактной информацией");
        if (kraken.detect().isFieldEmpty(Elements.Checkout.ContactsStep.firstNameInputField())) {
            fillFirstName(contactsDetails.getFirstName());
        }

        if (kraken.detect().isFieldEmpty(Elements.Checkout.ContactsStep.lastNameInputField())) {
            fillLastName(contactsDetails.getLastName());
        }

        if (kraken.detect().isFieldEmpty(Elements.Checkout.ContactsStep.emailInputField())) {
            fillEmail(contactsDetails.getEmail());
        }

        if (kraken.detect().isElementPresent(Elements.Checkout.ContactsStep.phoneInputField())) {
            fillPhone(contactsDetails.getPhone());
        } else if(contactsDetails.addNewPhone()) {
            PhoneActions.addNewPhone(contactsDetails.getPhone());
        }

        setSendEmails(contactsDetails.sendEmails());
    }

    @Step("Очищаем все поля контактов")
    public static void clear() {
        log.debug("Очищаем все доступные поля контактов, возвращаем настройки в дефолтное состояние и удаляем все добавленные телефоны");
        fillFirstName("");
        fillLastName("");
        fillEmail("");
        PhoneActions.deleteAll();
        setSendEmails(false);
    }

    public static void next() {
        Checkout.hitNext(CheckoutSteps.contactsStep());
    }

    public static void change() {
        Checkout.hitChange(CheckoutSteps.contactsStep());
    }

    public static void save() {
        Checkout.hitSave(CheckoutSteps.contactsStep());
    }

    @Step("Заполнение имени: {0}")
    public static void fillFirstName(String firstName) {
        log.debug("> имя: {}", firstName);
        step("Заполнение имени: " +firstName, ()->
                kraken.await().fluently(ExpectedConditions
                                .elementToBeClickable(Elements.Checkout.ContactsStep.firstNameInputField().getLocator()),
                        "Элемент некликабельный", 2));
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.firstNameInputField(), firstName);
    }

    @Step("Заполнение фамилии: {0}")
    public static void fillLastName(String lastName) {
        log.debug("> фамилия: {}", lastName);
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.lastNameInputField(), lastName);
    }

    @Step("Заполнение email'a: {0}")
    public static void fillEmail(String email) {
        log.debug("> email: {}", email);
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.emailInputField(), email);
    }

    @Step("Заполнение номера телефона: {0}")
    public static void fillPhone(String number) {
        log.debug("> телефон: {}", number);
        kraken.perform().fillField(Elements.Checkout.ContactsStep.phoneInputField(), number);
    }

    @Step("Заполнение чекбокса отправлять письма: {0}")
    public static void setSendEmails(boolean value) {
        if(value) log.debug("> отправлять письма с апдейтами по заказу: да");
        else log.debug("> отправлять письма с апдейтами по заказу: нет");
        kraken.perform().setCheckbox(Elements.Checkout.ContactsStep.sendEmailsCheckbox(), value);
    }
}
