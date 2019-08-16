package ru.instamart.autotests.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.CheckoutHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.CheckoutTests.enableContactsStepTests;

public class CheckoutContactsStepTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        kraken.perform().quickLogout();
        kraken.perform().registration();
        kraken.shopping().collectItems();
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutAndOpenContactsStep() {
        kraken.reach().checkout();
        CheckoutHelper.AddressStep.next();
    }

    @Test(  enabled = enableContactsStepTests,
            description = "Тест валидации шага Контакты в чекауте",
            groups = {"acceptance","regression"},
            priority = 1200
    )
    public void successValidateDefaultContactsStep() {
        assertPageIsAvailable();

        assertElementPresence(Elements.Checkout.ContactsStep.panel());
        assertElementPresence(Elements.Checkout.ContactsStep.icon());
        assertElementPresence(Elements.Checkout.ContactsStep.title());

        assertElementPresence(Elements.Checkout.ContactsStep.firstNameInputField());
        assertElementPresence(Elements.Checkout.ContactsStep.lastNameInputField());
        assertElementPresence(Elements.Checkout.ContactsStep.emailInputField());
        assertElementPresence(Elements.Checkout.ContactsStep.phonesTitle());
        assertElementPresence(Elements.Checkout.ContactsStep.phoneInputField());
        assertElementPresence(Elements.Checkout.ContactsStep.sendEmailsCheckbox());

        assertElementPresence(Elements.Checkout.ContactsStep.nextButton());
    }

    // todo noProceedNextWithEmptyFields

    // todo noProceedNextWithWrongFields

    // todo successFillAllFieldsAndProceedNext

    // todo successAddNewPhone

    // todo successSelectPhone

    // todo noEditPhoneOnClose

    // todo noEditPhoneOnCancel

    // todo successEditNotActivePhone

    // todo successEditActivePhone

    // todo successDeleteNotActivePhone

    // todo successDeleteActivePhone

    // todo successDeleteAllPhones
}
