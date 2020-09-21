package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import instamart.api.common.RestAddresses;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.CheckoutTests.enableContactsStepTests;

public class CheckoutContactsStepTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        UserData user = User.Do.registration();

        kraken.rest().fillCart(user, RestAddresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutAndOpenContactsStep() {
        kraken.reach().checkout();
        Checkout.AddressStep.next();
    }

    @Test(  enabled = enableContactsStepTests,
            description = "Тест валидации шага Контакты в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1200
    )
    public void successValidateDefaultContactsStep() {
        assertPageIsAvailable();

        assertPresence(Elements.Checkout.ContactsStep.panel());
        assertPresence(Elements.Checkout.ContactsStep.icon());
        assertPresence(Elements.Checkout.ContactsStep.title());

        assertPresence(Elements.Checkout.ContactsStep.firstNameInputField());
        assertPresence(Elements.Checkout.ContactsStep.lastNameInputField());
        assertPresence(Elements.Checkout.ContactsStep.emailInputField());
        assertPresence(Elements.Checkout.ContactsStep.phonesTitle());
        assertPresence(Elements.Checkout.ContactsStep.phoneInputField());
        assertPresence(Elements.Checkout.ContactsStep.sendEmailsCheckbox());

        assertPresence(Elements.Checkout.ContactsStep.nextButton());
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
