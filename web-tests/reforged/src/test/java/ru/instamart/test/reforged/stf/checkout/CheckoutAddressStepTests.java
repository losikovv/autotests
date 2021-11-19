package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.data.AddressDetailsData;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут. Шаг #1. Способ получения")
public final class CheckoutAddressStepTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final AddressDetailsData data = TestVariables.testAddressData();
    private final AddressDetailsData changeData = TestVariables.testChangeAddressData();
    private final UserData userData = UserManager.getQaUser();

    @CaseIDs({@CaseId(1698), @CaseId(1699), @CaseId(1700), @CaseId(1701)})
    @Test(description = "Тесты заполнения, изменения и очистки всех полей", groups = {"acceptance", "regression"})
    public void successFillAllFieldsAndProceedNext() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);

        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
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