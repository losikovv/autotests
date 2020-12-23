package ru.instamart.tests.ui.checkout;

import instamart.api.common.RestAddresses;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.pagesdata.AddressDetailsData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

import static instamart.core.testdata.TestVariables.testOrderDetails;
import static instamart.ui.common.lib.CheckoutSteps.addressStep;

public class CheckoutAddressStepTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    // TODO актуализировать тесты - ATST-236

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
    }

    @Test(  description = "Тест валидации дефолтного шага Адрес в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1100
    )
    public void successValidateDefaultAddressStep() {
        baseChecks.checkPageIsAvailable();
        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.panel());
        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.icon());
        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.title());
        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.deliveryAddress());
        baseChecks.checkRadioButtonIsNotSelected(Elements.Checkout.AddressStep.homeRadioButton());
        baseChecks.checkRadioButtonIsNotSelected(Elements.Checkout.AddressStep.officeRadioButton());
        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.apartmentInputField());
        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.floorInputField());
        baseChecks.checkCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());
        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.entranceInputField());
        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.domofonInputField());
        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.commentariesInputField());
        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.nextButton());
    }

    @Test(  description = "Тест успешного прохода далее без заполнения деталей адреса",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1101
    )
    public void successProceedNextWithDefaultStepState() {
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-regression"},
            priority = 1102
    )
    public void successProceedNextWithOnlyTypeChanged() {
        Checkout.AddressStep.setType();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"},
            priority = 1103
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        Checkout.AddressStep.fillApartment();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"},
            priority = 1104
    )
    public void successProceedNextWithOnlyFloorProvided() {
        Checkout.AddressStep.fillFloor();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"},
            priority = 1105
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        Checkout.AddressStep.setElevator();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"},
            priority = 1106
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        Checkout.AddressStep.fillEntrance();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"},
            priority = 1107
    )
    public void successProceedNextWithOnlyDomofonProvided() {
        Checkout.AddressStep.fillDomofon();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"},
            priority = 1108
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        Checkout.AddressStep.fillCommentaries();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее с очищенными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1109
    )
    public void successProceedNextWithClearedFields() {
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();
        kraken.perform().refresh();

        Checkout.AddressStep.clear();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее с заполненными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1110
    )
    public void successFillAllFieldsAndProceedNext() {
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного запоминания ранее заполненных полей",
            groups = {"sbermarket-regression"},
            priority = 1111
    )
    public void successKeepProvidedStepData() {
        SoftAssert softAssert = new SoftAssert();

        Checkout.AddressStep.clear();
        Checkout.AddressStep.fill();
        Checkout.AddressStep.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));

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

    @Test(  description = "Тест успешного изменения ранее заполненных полей",
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
        baseChecks.checkRadioButtonIsSelected(Elements.Checkout.AddressStep.homeRadioButton());
        baseChecks.checkFieldIsNotEmpty(Elements.Checkout.AddressStep.apartmentInputField());
        baseChecks.checkFieldIsFilled(Elements.Checkout.AddressStep.apartmentInputField(), "5");
        baseChecks.checkFieldIsNotEmpty(Elements.Checkout.AddressStep.floorInputField());
        baseChecks.checkFieldIsFilled(Elements.Checkout.AddressStep.floorInputField(), "55");
        baseChecks.checkCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());
        baseChecks.checkFieldIsNotEmpty(Elements.Checkout.AddressStep.entranceInputField());
        baseChecks.checkFieldIsFilled(Elements.Checkout.AddressStep.entranceInputField(), "555");
        baseChecks.checkFieldIsNotEmpty(Elements.Checkout.AddressStep.domofonInputField());
        baseChecks.checkFieldIsFilled(Elements.Checkout.AddressStep.domofonInputField(), "5555");
        baseChecks.checkFieldIsNotEmpty(Elements.Checkout.AddressStep.commentariesInputField());
        baseChecks.checkFieldIsFilled(Elements.Checkout.AddressStep.commentariesInputField(), "55555");
    }
}
