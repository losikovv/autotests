package ru.instamart.test.reforged.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.AddressDetailsData;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;


@Epic("STF UI")
@Feature("Чекаут. Шаг #1. Способ получения")
public class CheckoutAddressStepTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
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
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1691)
    @Test(description = "Тест успешного прохода далее только с измененным значением радиокнопки Тип адреса",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successProceedNextWithOnlyTypeChanged() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1692)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Квартира/офис",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyApartmentProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(data.getApartment());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1693)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Этаж",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyFloorProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().fillFloor(data.getFloor());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1694)
    @Test(description = "Тест успешного прохода далее только с измененным значением чекбокса Есть лифт",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyElevatorChanged() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1695)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Подъезд",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyEntranceProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
//        home().checkPageIsAvailable();
//        shop().checkFirstProductCard();
        checkout().goToPage();
        checkout().setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1696)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Код домофона",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyDoorPhoneProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseId(1697)
    @Test(description = "Тест успешного прохода далее только с заполненным полем Комментарии по доставке",
            groups = {"sbermarket-regression"}
    )
    public void successProceedNextWithOnlyCommentariesProvided() {
        helper.dropAndFillCart(checkoutUser, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().fillComments(data.getCommentaries());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().checkDeliveryOptionMinimized();
    }

    @CaseIDs({@CaseId(1698), @CaseId(1699), @CaseId(1700), @CaseId(1701)})
    @Test(description = "Тесты заполнения, изменения и очистки всех полей",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successFillAllFieldsAndProceedNext() {
        helper.dropAndFillCart(checkoutUser, 1);
        AddressDetailsData changeData = TestVariables.testChangeAddressData();

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutUser);
        shop().interactHeader().checkProfileButtonVisible();
        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().clickToForSelf();
        checkout().setDeliveryOptions().fillApartment(data.getApartment());
        checkout().setDeliveryOptions().fillFloor(data.getFloor());
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout().setDeliveryOptions().checkContactlessDelivery();
        checkout().setDeliveryOptions().fillComments(data.getCommentaries());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().checkDeliveryOptionMinimized();
        shop().refresh();
        checkout().setDeliveryOptions().checkForSelfIsSelected(checkout().setDeliveryOptions().getForSelfState());
        checkout().setDeliveryOptions().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), data.getApartment());
        checkout().setDeliveryOptions().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), data.getFloor());
        checkout().setDeliveryOptions().checkElevatorIsSelected(checkout().setDeliveryOptions().getElevatorState());
        checkout().setDeliveryOptions().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), data.getEntrance());
        checkout().setDeliveryOptions().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), data.getDomofon());
        checkout().setDeliveryOptions().checkContactlessDeliveryIsSelected(checkout().setDeliveryOptions().getContactlessDeliveryState());
        checkout().setDeliveryOptions().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), data.getCommentaries());
        checkout().assertAll();

        checkout().setDeliveryOptions().clearApartment();
        checkout().setDeliveryOptions().clearFloor();
        checkout().setDeliveryOptions().uncheckElevator();
        checkout().setDeliveryOptions().clearEntrance();
        checkout().setDeliveryOptions().clearDoorPhone();
        checkout().setDeliveryOptions().uncheckContactlessDelivery();
        checkout().setDeliveryOptions().clearComments();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        shop().refresh();
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
        checkout().setDeliveryOptions().fillComments(changeData.getCommentaries());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        shop().refresh();
        checkout().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), changeData.getApartment());
        checkout().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), changeData.getFloor());
        checkout().checkElevatorIsSelected(checkout().setDeliveryOptions().getElevatorState());
        checkout().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), changeData.getEntrance());
        checkout().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), changeData.getDomofon());
        checkout().checkContactlessDeliveryIsSelected(checkout().setDeliveryOptions().getContactlessDeliveryState());
        checkout().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), changeData.getCommentaries());
        checkout().assertAll();
    }
}