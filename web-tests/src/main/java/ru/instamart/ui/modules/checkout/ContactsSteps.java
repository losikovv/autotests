package ru.instamart.ui.modules.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.core.common.AppManager;
import ru.instamart.ui.common.lib.CheckoutSteps;
import ru.instamart.ui.common.pagesdata.ContactsDetailsData;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;

import static io.qameta.allure.Allure.step;

public final class ContactsSteps extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public ContactsSteps(final AppManager kraken) {
        super(kraken);
    }

    @Step("Шаг заполнения контактов")
    public static void fill(ContactsDetailsData contactsDetails) {
        log.info("> заполняем поля с контактной информацией");
        if (contactsDetails.changeFirstName()) {
            fillFirstName(contactsDetails.getFirstName());
        }

        if (contactsDetails.changeLastName()) {
            fillLastName(contactsDetails.getLastName());
        }

        if (contactsDetails.changeEmail()) {
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
        log.info("Очищаем все доступные поля контактов, возвращаем настройки в дефолтное состояние и удаляем все добавленные телефоны");
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
        log.info("> имя: {}", firstName);
        step("Заполнение имени: " +firstName, ()->
                kraken.await().fluently(ExpectedConditions
                                .elementToBeClickable(Elements.Checkout.ContactsStep.firstNameInputField().getLocator()),
                        "Элемент некликабельный", 2));
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.firstNameInputField(), firstName);
    }

    @Step("Заполнение фамилии: {0}")
    public static void fillLastName(String lastName) {
        log.info("> фамилия: {}", lastName);
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.lastNameInputField(), lastName);
    }

    @Step("Заполнение email'a: {0}")
    public static void fillEmail(String email) {
        log.info("> email: {}", email);
        kraken.perform().fillFieldAction(Elements.Checkout.ContactsStep.emailInputField(), email);
    }

    @Step("Заполнение номера телефона: {0}")
    public static void fillPhone(String number) {
        log.info("> телефон: {}", number);
        kraken.perform().fillField(Elements.Checkout.ContactsStep.phoneInputField(), number);
    }

    @Step("Заполнение чекбокса отправлять письма: {0}")
    public static void setSendEmails(boolean value) {
        if(value) log.info("> отправлять письма с апдейтами по заказу: да");
        else log.info("> отправлять письма с апдейтами по заказу: нет");
        kraken.perform().setCheckbox(Elements.Checkout.ContactsStep.sendEmailsCheckbox(), value);
    }
}
