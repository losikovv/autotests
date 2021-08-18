package ru.instamart.test.reforged.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.AddressDetailsData;
import ru.instamart.reforged.stf.page.checkout.CheckoutPage;
import ru.instamart.reforged.stf.page.checkout.step.DeliveryOptionsStep;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.core.service.KrakenDriver.refresh;
import static ru.instamart.reforged.stf.page.StfRouter.*;


@Epic("STF UI")
@Feature("Чекаут. Шаг #1. Способ получения")
public class CheckoutAddressStepTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    CheckoutPage checkout = new CheckoutPage();
    AddressDetailsData data = TestVariables.testAddressData();
    UserData checkoutUser = UserManager.checkoutUser();

    @CaseId(1690)
    @Test(description = "Тест успешного прохода далее без заполнения деталей адреса",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithDefaultStepState() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1691)
    @Test(description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithOnlyTypeChanged() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().clickToForSelf();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1692)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().fillApartment(data.getApartment());
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1693)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyFloorProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().fillFloor(data.getFloor());
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1694)
    @Test(description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().checkElevator();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1695)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1696)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyDoorPhoneProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseId(1697)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().fillComments(data.getCommentaries());
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @Skip
    @Test(description = "Тест успешного прохода далее с очищенными полями",
            groups = {}
    )
    public void successProceedNextWithClearedFields() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        refresh();
        checkout.setDeliveryOptions().clearFloor();
        checkout.setDeliveryOptions().clearEntrance();
        checkout.setDeliveryOptions().clearDoorPhone();
        checkout.setDeliveryOptions().clearComments();
        checkout.setDeliveryOptions().clickToSubmitForDelivery();
        checkout.checkDeliveryOptionMinimized();
    }

    @CaseIDs({@CaseId(1698), @CaseId(1699), @CaseId(1700), @CaseId(1701)})
    @Test(description = "Тесты заполния, изменения и очистки все полей на шаге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successFillAllFieldsAndProceedNext() {
        helper.dropAndFillCart(checkoutUser, 1);
        DeliveryOptionsStep deliveryOption = new DeliveryOptionsStep();
        AddressDetailsData changeData = TestVariables.testChangeAddressData();

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(checkoutUser.getPhone());
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        home().checkPageIsAvailable();
        shop().checkFirstProductCard();
        checkout().goToPage();
        deliveryOption.clickToForSelf();
        deliveryOption.fillApartment(data.getApartment());
        deliveryOption.fillFloor(data.getFloor());
        deliveryOption.checkElevator();
        deliveryOption.fillEntrance(data.getEntrance());
        deliveryOption.fillDoorPhone(data.getDomofon());
        deliveryOption.checkContactlessDelivery();
        deliveryOption.fillComments(data.getCommentaries());
        deliveryOption.clickToSubmitForDelivery();

        checkout.checkDeliveryOptionMinimized();
        refresh();
        checkout.checkForSelfIsSelected(deliveryOption.getForSelfState());
        checkout.checkApartmentValue(deliveryOption.getApartmentValue(), data.getApartment());
        checkout.checkFloorValue(deliveryOption.getFloorValue(), data.getFloor());
        checkout.checkElevatorIsSelected(deliveryOption.getElevatorState());
        checkout.checkEntranceValue(deliveryOption.getEntranceValue(), data.getEntrance());
        checkout.checkDoorPhoneValue(deliveryOption.getDoorPhoneValue(), data.getDomofon());
        checkout.checkContactlessDeliveryIsSelected(deliveryOption.getContactlessDeliveryState());
        checkout.checkCommentsValue(deliveryOption.getCommentsValue(), data.getCommentaries());
        checkout.assertAll();

        deliveryOption.fillApartment("");
        deliveryOption.fillFloor("");
        deliveryOption.uncheckElevator();
        deliveryOption.fillEntrance("");
        deliveryOption.fillDoorPhone("");
        deliveryOption.uncheckContactlessDelivery();
        deliveryOption.fillComments("");
        deliveryOption.clickToSubmitForDelivery();
        refresh();
        checkout.checkApartmentValue(deliveryOption.getApartmentValue(), "");
        checkout.checkFloorValue(deliveryOption.getFloorValue(), "");
        checkout.checkElevatorIsNotSelected(deliveryOption.getElevatorState());
        checkout.checkEntranceValue(deliveryOption.getEntranceValue(), "");
        checkout.checkDoorPhoneValue(deliveryOption.getDoorPhoneValue(), "");
        checkout.checkContactlessDeliveryIsNotSelected(deliveryOption.getContactlessDeliveryState());
        checkout.checkCommentsValue(deliveryOption.getCommentsValue(), "");
        checkout.assertAll();

        deliveryOption.fillApartment(changeData.getApartment());
        deliveryOption.fillFloor(changeData.getFloor());
        deliveryOption.checkElevator();
        deliveryOption.fillEntrance(changeData.getEntrance());
        deliveryOption.fillDoorPhone(changeData.getDomofon());
        deliveryOption.checkContactlessDelivery();
        deliveryOption.fillComments(changeData.getCommentaries());
        deliveryOption.clickToSubmitForDelivery();
        refresh();
        checkout.checkApartmentValue(deliveryOption.getApartmentValue(), changeData.getApartment());
        checkout.checkFloorValue(deliveryOption.getFloorValue(), changeData.getFloor());
        checkout.checkElevatorIsSelected(deliveryOption.getElevatorState());
        checkout.checkEntranceValue(deliveryOption.getEntranceValue(), changeData.getEntrance());
        checkout.checkDoorPhoneValue(deliveryOption.getDoorPhoneValue(), changeData.getDomofon());
        checkout.checkContactlessDeliveryIsSelected(deliveryOption.getContactlessDeliveryState());
        checkout.checkCommentsValue(deliveryOption.getCommentsValue(), changeData.getCommentaries());
        checkout.assertAll();
    }
}
