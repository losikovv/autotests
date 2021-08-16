package ru.instamart.test.reforged.checkout;

import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.stf.page.checkout.CheckoutPage;
import ru.instamart.reforged.stf.page.checkout.step.DeliveryOptionsStep;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.core.service.KrakenDriver.refresh;
import static ru.instamart.reforged.stf.page.StfRouter.*;

public class CheckoutAddressStepTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    CheckoutPage checkout = new CheckoutPage();

    @Test(description = "Тест валидации дефолтного шага Адрес в чекауте",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successValidateDefaultAddressStep() {

        UserData user = UserManager.addressUser();
        helper.dropAndFillCart(user, 1);


        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        //заполнить корзину, выбрать адрес
        checkout().goToPage();


//        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.panel());
//        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.icon());
//        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.title());
//        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.deliveryAddress());
//        baseChecks.checkRadioButtonIsNotSelected(Elements.Checkout.AddressStep.homeRadioButton());
//        baseChecks.checkRadioButtonIsNotSelected(Elements.Checkout.AddressStep.officeRadioButton());
//        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.apartmentInputField());
//        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.floorInputField());
//        baseChecks.checkCheckboxIsNotSet(Elements.Checkout.AddressStep.elevatorCheckbox());
//        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.entranceInputField());
//        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.domofonInputField());
//        baseChecks.checkFieldIsEmpty(Elements.Checkout.AddressStep.commentariesInputField());
//        baseChecks.checkIsElementPresent(Elements.Checkout.AddressStep.nextButton());
    }

    @Test(description = "Тест успешного прохода далее без заполнения деталей адреса",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithDefaultStepState() {

        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithOnlyTypeChanged() {

        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().clickToForSelf();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().fillApartment("22");
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyFloorProvided() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().fillFloor("5");
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().setElevator();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().fillEntrance("1");
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyDoorPhoneProvided() {
        UserData user = UserManager.addressUser();
        helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().fillDoorPhone("1");
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        checkout.setDeliveryOptions().fillComments("test");
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее с очищенными полями",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithClearedFields() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        //checkout.setDeliveryOptions().clearApartment();
        checkout.setDeliveryOptions().clearFloor();
        checkout.setDeliveryOptions().clearEntrance();
        checkout.setDeliveryOptions().clearDoorPhone();
        checkout.setDeliveryOptions().clearComments();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Test(description = "Тест успешного прохода далее с заполненными полями и Тест успешного запоминания ранее заполненных полей",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successFillAllFieldsAndProceedNext() {
        UserData user = UserManager.addressUser();
        //helper.dropAndFillCart(user, 1);
        DeliveryOptionsStep deliveryOption = new DeliveryOptionsStep();
        //проблема с тестовыми данными, т.к. поля после теста не чистятся.
        //чистить поля или ??


        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79998833344");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();

        checkout().goToPage();
        deliveryOption.clickToForSelf();
        deliveryOption.fillApartment("1");
        deliveryOption.fillFloor("1");
        deliveryOption.setElevator();
        deliveryOption.fillEntrance("2");
        deliveryOption.fillDoorPhone("3");
        deliveryOption.setContactlessDelivery();
        deliveryOption.fillComments("My comments");
        deliveryOption.clickToSubmitForDelivery();

        checkout.checkDeliveryOptionMinimized();
        refresh();

        checkout.checkForSelfIsSelected(deliveryOption.getForSelfState());
        checkout.checkApartmentValue(deliveryOption.getApartmentValue(), "1");
        checkout.checkFloorValue(deliveryOption.getFloorValue(), "1");
        checkout.checkElevatorIsSelected(deliveryOption.getElevatorState());
        checkout.checkEntranceValue(deliveryOption.getEntranceValue(), "2");
        checkout.checkDoorPhoneValue(deliveryOption.getDoorPhoneValue(), "3");
        checkout.checkContactlessDeliveryState(deliveryOption.getContactlessDeliveryValue());
        checkout.checkCommentsValue(deliveryOption.getCommentsValue(), "My comments");
        checkout.assertAll();

        deliveryOption.clearApartment();
        deliveryOption.clearFloor();
        deliveryOption.setElevator();//разделить выбор/не выбор
        deliveryOption.clearEntrance();
        deliveryOption.clearDoorPhone();
        deliveryOption.setContactlessDelivery();
        deliveryOption.clearComments();

        deliveryOption.fillApartment("2");
        deliveryOption.fillFloor("");
        deliveryOption.fillEntrance("");
        deliveryOption.fillDoorPhone("");
        deliveryOption.fillComments("My comments");

    }

    @Test(  description = "Тест успешного изменения ранее заполненных полей",
            groups = {"sbermarket-regression"}
    )
    public void successChangeProvidedAddressData() {

    }
}
