package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Checkout;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.CheckoutTests.enableContactsStepTests;

public class CheckoutContactsStepTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        User.Do.registration();
        Shop.Cart.collect();
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutAndOpenContactsStep() {
        kraken.reach().checkout();
        Checkout.AddressStep.next();
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
