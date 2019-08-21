package ru.instamart.autotests.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.CheckoutHelper;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.models.AddressDetailsData;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.CheckoutSteps.addressStep;
import static ru.instamart.autotests.application.Config.TestsConfiguration.CheckoutTests.enableAddressStepTests;
import static ru.instamart.autotests.application.Config.TestVariables.testOrderDetails;

public class CheckoutAddressStepTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        kraken.perform().quickLogout();
        kraken.perform().registration();
        ShopHelper.Cart.collect();
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutAndOpenAddressStep() {
        kraken.reach().checkout();
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест валидации дефолтного шага Адрес в чекауте",
            groups = {"acceptance","regression"},
            priority = 1100
    )
    public void successValidateDefaultAddressStep() {
        assertPageIsAvailable();

        assertElementPresence(Elements.Checkout.AddressStep.panel());
        assertElementPresence(Elements.Checkout.AddressStep.icon());
        assertElementPresence(Elements.Checkout.AddressStep.title());

        assertElementPresence(Elements.Checkout.AddressStep.deliveryAddress());

        assertRadioButtonIsSelected(Elements.Checkout.AddressStep.homeRadioButton());
        assertRadioButtonIsNotSelected(Elements.Checkout.AddressStep.officeRadioButton());

        assertFieldIsEmpty(Elements.Checkout.AddressStep.apartmentInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.floorInputField());
        assertCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.entranceInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.domofonInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.commentariesInputField());

        assertElementPresence(Elements.Checkout.AddressStep.nextButton());
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее без заполнения деталей адреса",
            groups = {"acceptance","regression"},
            priority = 1101
    )
    public void successProceedNextWithDefaultStepState() {
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"regression"},
            priority = 1102
    )
    public void successProceedNextWithOnlyTypeChanged() {
        CheckoutHelper.AddressStep.setType();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"regression"},
            priority = 1103
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        CheckoutHelper.AddressStep.fillApartment();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"regression"},
            priority = 1104
    )
    public void successProceedNextWithOnlyFloorProvided() {
        CheckoutHelper.AddressStep.fillFloor();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"regression"},
            priority = 1105
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        CheckoutHelper.AddressStep.setElevator();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"regression"},
            priority = 1106
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        CheckoutHelper.AddressStep.fillEntrance();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"regression"},
            priority = 1107
    )
    public void successProceedNextWithOnlyDomofonProvided() {
        CheckoutHelper.AddressStep.fillDomofon();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"regression"},
            priority = 1108
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        CheckoutHelper.AddressStep.fillCommentaries();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее с очищенными полями",
            groups = {"acceptance","regression"},
            priority = 1109
    )
    public void successProceedNextWithClearedFields() {
        CheckoutHelper.AddressStep.fill();
        CheckoutHelper.AddressStep.next();
        kraken.perform().refresh();
        CheckoutHelper.AddressStep.clear();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее с заполненными полями",
            groups = {"acceptance","regression"},
            priority = 1110
    )
    public void successFillAllFieldsAndProceedNext() {
        CheckoutHelper.AddressStep.fill();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного запоминания ранее заполненных полей",
            groups = {"acceptance","regression"},
            priority = 1111
    )
    public void successKeepProvidedStepData() {
        SoftAssert softAssert = new SoftAssert();

        CheckoutHelper.AddressStep.clear();
        CheckoutHelper.AddressStep.fill();
        CheckoutHelper.AddressStep.next();

        assertElementPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));

        kraken.reach().checkout();

        softAssert.assertTrue(
                kraken.detect().isRadioButtonSelected(Elements.Checkout.AddressStep.officeRadioButton()),
                        "\n> Не сохраняется ранее зеачение радиокнопки 'Тип' в шаге 'Адрес'"
        );

        softAssert.assertEquals(
                kraken.grab().value(Elements.Checkout.AddressStep.apartmentInputField()),
                    testOrderDetails().getAddressDetails().getApartment(),
                        "\n> Не сохраняется ранее заполненное поле 'Номер квартиры/офис' в шаге 'Адрес'"
        );

        softAssert.assertEquals(
                kraken.grab().value(Elements.Checkout.AddressStep.floorInputField()),
                    testOrderDetails().getAddressDetails().getFloor(),
                        "\n> Не сохраняется ранее заполненное поле 'Этаж' в шаге 'Адрес'"
        );

        // todo починить
        /*
        softAssert.assertTrue(
                kraken.detect().isCheckboxSet(Elements.Checkout.AddressStep.elevatorCheckbox()),
                "\n> не сохранилось зеачение чекбокса 'Есть лифт' в шаге 'Адрес"
        );
        */

        softAssert.assertEquals(
                kraken.grab().value(Elements.Checkout.AddressStep.entranceInputField()),
                    testOrderDetails().getAddressDetails().getEntrance(),
                        "\n> Не сохраняется ранее заполненное поле 'Подъезд' в шаге 'Адрес'"
        );

        softAssert.assertEquals(
                kraken.grab().value(Elements.Checkout.AddressStep.domofonInputField()),
                    testOrderDetails().getAddressDetails().getDomofon(),
                        "\n> Не сохраняется ранее заполненное поле 'Код домофона' в шаге 'Адрес'"
        );

        softAssert.assertEquals(
                kraken.grab().value(Elements.Checkout.AddressStep.commentariesInputField()),
                    testOrderDetails().getAddressDetails().getCommentaries(),
                        "\n> Не сохраняется ранее заполненное поле 'Комментарии по доставке' в шаге 'Адрес'"
        );

        softAssert.assertAll();
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного изменения ранее заполненных полей",
            groups = {"regression"},
            priority = 1112
    )
    public void successChangeProvidedStepData() {
        CheckoutHelper.AddressStep.fill();
        CheckoutHelper.AddressStep.next();
        CheckoutHelper.AddressStep.change();

        CheckoutHelper.AddressStep.fill(
                new AddressDetailsData(
                        "home",
                        "5",
                        "55",
                        false,
                        "555",
                        "5555",
                        "55555")
        );
        CheckoutHelper.AddressStep.next();

        kraken.reach().checkout();

        assertRadioButtonIsSelected(Elements.Checkout.AddressStep.homeRadioButton());

        assertFieldIsNotEmpty(Elements.Checkout.AddressStep.apartmentInputField());
        assertFieldIsFilled(Elements.Checkout.AddressStep.apartmentInputField(), "5");

        assertFieldIsNotEmpty(Elements.Checkout.AddressStep.floorInputField());
        assertFieldIsFilled(Elements.Checkout.AddressStep.floorInputField(), "55");

        assertCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());

        assertFieldIsNotEmpty(Elements.Checkout.AddressStep.entranceInputField());
        assertFieldIsFilled(Elements.Checkout.AddressStep.entranceInputField(), "555");

        assertFieldIsNotEmpty(Elements.Checkout.AddressStep.domofonInputField());
        assertFieldIsFilled(Elements.Checkout.AddressStep.domofonInputField(), "5555");

        assertFieldIsNotEmpty(Elements.Checkout.AddressStep.commentariesInputField());
        assertFieldIsFilled(Elements.Checkout.AddressStep.commentariesInputField(), "55555");
    }
}
