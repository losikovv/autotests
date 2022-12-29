package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #1. Способ получения")
public final class CheckoutAddressStepTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLinks({@TmsLink("1698"), @TmsLink("1699"), @TmsLink("1700"), @TmsLink("1701")})
    @Test(description = "Тесты заполнения, изменения и очистки всех полей", groups = REGRESSION_STF)
    public void successFillAllFieldsAndProceedNext() {
        var data = TestVariables.testAddressData();
        var changeData = TestVariables.testChangeAddressData();
        var userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, UiProperties.DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();

        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillApartment(data.getApartment());
        checkout().setDeliveryOptions().fillFloor(data.getFloor());
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout().setDeliveryOptions().checkContactlessDelivery();
        checkout().setDeliveryOptions().fillComments(data.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkDeliveryOptionMinimized();
        checkout().refresh();
        checkout().setDeliveryOptions().checkForSelfIsSelected(checkout().setDeliveryOptions().getForSelfState());
        checkout().setDeliveryOptions().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), data.getApartment());
        checkout().setDeliveryOptions().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), data.getFloor());
        checkout().setDeliveryOptions().checkElevatorIsSelected(checkout().setDeliveryOptions().getElevatorState());
        checkout().setDeliveryOptions().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), data.getEntrance());
        checkout().setDeliveryOptions().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), data.getDomofon());
        checkout().setDeliveryOptions().checkContactlessDeliveryIsSelected(checkout().setDeliveryOptions().getContactlessDeliveryState());
        checkout().setDeliveryOptions().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), data.getComments());
        checkout().assertAll();

        checkout().setDeliveryOptions().clearApartment();
        checkout().setDeliveryOptions().clearFloor();
        checkout().setDeliveryOptions().uncheckElevator();
        checkout().setDeliveryOptions().clearEntrance();
        checkout().setDeliveryOptions().clearDoorPhone();
        checkout().setDeliveryOptions().uncheckContactlessDelivery();
        checkout().setDeliveryOptions().clearComments();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().refresh();
        checkout().setDeliveryOptions().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), "");
        checkout().setDeliveryOptions().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), "");
        checkout().setDeliveryOptions().checkElevatorIsNotSelected(checkout().setDeliveryOptions().getElevatorState());
        checkout().setDeliveryOptions().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), "");
        checkout().setDeliveryOptions().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), "");
        checkout().setDeliveryOptions().checkContactlessDeliveryIsNotSelected(checkout().setDeliveryOptions().getContactlessDeliveryState());
        checkout().setDeliveryOptions().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), "");
        checkout().assertAll();

        checkout().setDeliveryOptions().fillApartment(changeData.getApartment());
        checkout().setDeliveryOptions().fillFloor(changeData.getFloor());
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().fillEntrance(changeData.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(changeData.getDomofon());
        checkout().setDeliveryOptions().checkContactlessDelivery();
        checkout().setDeliveryOptions().fillComments(changeData.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().refresh();
        checkout().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), changeData.getApartment());
        checkout().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), changeData.getFloor());
        checkout().checkElevatorIsSelected(checkout().setDeliveryOptions().getElevatorState());
        checkout().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), changeData.getEntrance());
        checkout().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), changeData.getDomofon());
        checkout().checkContactlessDeliveryIsSelected(checkout().setDeliveryOptions().getContactlessDeliveryState());
        checkout().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), changeData.getComments());
        checkout().assertAll();
    }
}