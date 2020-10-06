package ru.instamart.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.AddressDetailsData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import instamart.api.common.RestAddresses;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestVariables.testOrderDetails;
import static instamart.core.settings.Config.TestsConfiguration.CheckoutTests.enableAddressStepTests;
import static instamart.ui.common.lib.CheckoutSteps.addressStep;

public class CheckoutAddressStepTests extends TestBase {

    // TODO актуализировать тесты - ATST-236

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        User.Logout.quickly();
        UserData user = User.Do.registration();

        kraken.apiV2().fillCart(user, RestAddresses.Moscow.defaultAddress());
    }

    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutAndOpenAddressStep() {
        kraken.reach().checkout();
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест валидации дефолтного шага Адрес в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1100
    )
    public void successValidateDefaultAddressStep() {
        assertPageIsAvailable();

        assertPresence(Elements.Checkout.AddressStep.panel());
        assertPresence(Elements.Checkout.AddressStep.icon());
        assertPresence(Elements.Checkout.AddressStep.title());

        assertPresence(Elements.Checkout.AddressStep.deliveryAddress());

        assertRadioButtonIsNotSelected(Elements.Checkout.AddressStep.homeRadioButton());
        assertRadioButtonIsNotSelected(Elements.Checkout.AddressStep.officeRadioButton());

        assertFieldIsEmpty(Elements.Checkout.AddressStep.apartmentInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.floorInputField());
        assertCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.entranceInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.domofonInputField());
        assertFieldIsEmpty(Elements.Checkout.AddressStep.commentariesInputField());

        assertPresence(Elements.Checkout.AddressStep.nextButton());
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее без заполнения деталей адреса",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1101
    )
    public void successProceedNextWithDefaultStepState() {
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-regression"},
            priority = 1102
    )
    public void successProceedNextWithOnlyTypeChanged() {
        Checkout.AddressStep.setType();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"},
            priority = 1103
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        Checkout.AddressStep.fillApartment();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"},
            priority = 1104
    )
    public void successProceedNextWithOnlyFloorProvided() {
        Checkout.AddressStep.fillFloor();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"},
            priority = 1105
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        Checkout.AddressStep.setElevator();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"},
            priority = 1106
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        Checkout.AddressStep.fillEntrance();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"},
            priority = 1107
    )
    public void successProceedNextWithOnlyDomofonProvided() {
        Checkout.AddressStep.fillDomofon();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"},
            priority = 1108
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        Checkout.AddressStep.fillCommentaries();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее с очищенными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1109
    )
    public void successProceedNextWithClearedFields() {
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();
        kraken.perform().refresh();

        Checkout.AddressStep.clear();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного прохода далее с заполненными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1110
    )
    public void successFillAllFieldsAndProceedNext() {
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  enabled = enableAddressStepTests,
            description = "Тест успешного запоминания ранее заполненных полей",
            groups = {"sbermarket-regression"},
            priority = 1111
    )
    public void successKeepProvidedStepData() {
        SoftAssert softAssert = new SoftAssert();

        Checkout.AddressStep.clear();
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();

        assertPresence(Elements.Checkout.MinimizedStep.panel(addressStep()));

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
            groups = {"sbermarket-regression"},
            priority = 1112
    )
    public void successChangeProvidedAddressData() {
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();
        Checkout.AddressStep.change();

        Checkout.AddressStep.fill(
                new AddressDetailsData(
                        "home",
                        "5",
                        "55",
                        false,
                        "555",
                        "5555",
                        "55555")
        );
        Checkout.AddressStep.next();

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
