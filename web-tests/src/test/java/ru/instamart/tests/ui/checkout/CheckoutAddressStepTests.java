package ru.instamart.tests.ui.checkout;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;
import ru.instamart.ui.data.pagesdata.AddressDetailsData;
import ru.instamart.ui.data.pagesdata.UserData;
import ru.instamart.ui.module.User;
import ru.instamart.ui.module.checkout.AddressSteps;
import ru.instamart.ui.Elements;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

import static ru.instamart.core.testdata.TestVariables.testOrderDetails;
import static ru.instamart.ui.data.lib.CheckoutSteps.addressStep;

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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
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
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successProceedNextWithDefaultStepState() {
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyTypeChanged() {
        AddressSteps.setType();
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        AddressSteps.fillApartment(testOrderDetails().getAddressDetails().getApartment());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyFloorProvided() {
        AddressSteps.fillFloor(testOrderDetails().getAddressDetails().getFloor());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        AddressSteps.setElevator(testOrderDetails().getAddressDetails().isElevatorAvailable());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        AddressSteps.fillEntrance(testOrderDetails().getAddressDetails().getEntrance());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyDomofonProvided() {
        AddressSteps.fillDomofon(testOrderDetails().getAddressDetails().getDomofon());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        AddressSteps.fillCommentaries(testOrderDetails().getAddressDetails().getCommentaries());
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее с очищенными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successProceedNextWithClearedFields() {
        AddressSteps.fill();
        AddressSteps.next();
        kraken.perform().refresh();

        AddressSteps.clear();
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного прохода далее с заполненными полями",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successFillAllFieldsAndProceedNext() {
        AddressSteps.fill();
        AddressSteps.next();

        baseChecks.checkIsElementPresent(Elements.Checkout.MinimizedStep.panel(addressStep()));
    }

    @Test(  description = "Тест успешного запоминания ранее заполненных полей",
            groups = {"sbermarket-regression"}
    )
    public void successKeepProvidedStepData() {
        SoftAssert softAssert = new SoftAssert();

        AddressSteps.clear();
        AddressSteps.fill();
        AddressSteps.next();

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
            groups = {"sbermarket-regression"}
    )
    public void successChangeProvidedAddressData() {
        AddressSteps.fill();
        AddressSteps.next();
        AddressSteps.change();

        AddressSteps.fill(
                new AddressDetailsData(
                        "home",
                        "5",
                        "55",
                        false,
                        "555",
                        "5555",
                        "55555")
        );
        AddressSteps.next();

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
