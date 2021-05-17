package ru.instamart.tests.ui.checkout;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.common.pagesdata.UserData;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.checkout.AddressSteps;
import ru.instamart.ui.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class CheckoutContactsStepTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    // TODO расширить тесты, см тудушки - ATST-238

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        UserData user = User.Do.registration();
        kraken.apiV2().fillCart(user, RestAddresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().checkout();
        AddressSteps.next();
    }

    @Test(  description = "Тест валидации шага Контакты в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successValidateDefaultContactsStep() {
        baseChecks.checkPageIsAvailable();

        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.panel());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.icon());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.title());

        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.firstNameInputField());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.lastNameInputField());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.emailInputField());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.phonesTitle());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.phoneInputField());
        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.sendEmailsCheckbox());

        baseChecks.checkIsElementPresent(Elements.Checkout.ContactsStep.nextButton());
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
