package ru.instamart.test.reforged.stf_prod.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REMOVE;
import static ru.instamart.reforged.stf.page.StfRouter.checkoutNew;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutSlotsTests {
    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @BeforeMethod(alwaysRun = true, description = "Шаги предусловия")
    public void beforeTest() {
        this.ordersUser = UserManager.getQaUser();
        this.helper.dropAndFillCart(ordersUser, UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseId(3638)
    @Test(description = "Выбор слота доставки", groups = {STF_PROD_S})
    public void testSelectDeliverySlot() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().checkDeliverySlotsVisible();
        checkoutNew().checkActiveDeliverySlotsNotVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);
        checkoutNew().checkSelectedDeliverySlotsCount(1);

        var slotDate = "сегодня";
        var slotTime = checkoutNew().getActiveSlotTime();
        var slotCost = checkoutNew().getActiveSlotCost();

        checkoutNew().openDeliverySlotsModalFromOthers();
        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysVisible();
        //При прогоне по расписанию ночью, слоты на 7-й день не сгенерированы, днём при запуске теста дней - 7. Убрал проверку для стабильности
        //checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysCount(7);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDeliveryDaysCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(1);

        checkoutNew().interactDeliverySlotsModal().checkSelectedDayNameContainsText(slotDate);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().interactDeliverySlotsModal().clickOnDay(2);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(2);

        slotDate = checkoutNew().interactDeliverySlotsModal().getDayName(2);

        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotNotVisible();

        checkoutNew().interactDeliverySlotsModal().clickOnSlot(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);

        slotTime = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryTime();
        slotCost = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryCost();

        checkoutNew().interactDeliverySlotsModal().clickApply();
        checkoutNew().interactDeliverySlotsModal().checkModalNotVisible();

        checkoutNew().checkDeliveryTitleContains(slotDate);
        checkoutNew().checkSelectedDeliverySlotsCount(1);
        checkoutNew().scrollSlotsToStart();

        checkoutNew().checkSlotActive(1);
        checkoutNew().checkSelectedDeliverySlotsCount(1);

        checkoutNew().checkSelectedSlotDayNameContainsText(slotDate);
        checkoutNew().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().openDeliverySlotsModalFromTitle();

        checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysVisible();
        //checkoutNew().interactDeliverySlotsModal().checkAvailableDeliveryDaysCount(7);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDeliveryDaysCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(2);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);

        checkoutNew().interactDeliverySlotsModal().checkSelectedDayNameContainsText(slotDate);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotCostContainsText(slotCost);

        checkoutNew().interactDeliverySlotsModal().clickOnDay(3);
        checkoutNew().interactDeliverySlotsModal().checkSelectedDayPosition(3);

        slotDate = checkoutNew().interactDeliverySlotsModal().getDayName(3);

        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotNotVisible();

        checkoutNew().interactDeliverySlotsModal().clickOnSlot(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotPosition(1);
        checkoutNew().interactDeliverySlotsModal().checkSelectedSlotsCount(1);

        slotTime = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryTime();
        slotCost = checkoutNew().interactDeliverySlotsModal().getSelectedDeliveryCost();

        checkoutNew().interactDeliverySlotsModal().clickApply();
        checkoutNew().interactDeliverySlotsModal().checkModalNotVisible();

        checkoutNew().checkSelectedSlotDayNameContainsText(slotDate);
        checkoutNew().checkSelectedSlotTimeContainsText(slotTime);
        checkoutNew().checkSelectedSlotCostContainsText(slotCost);
    }

    @CaseId(3634)
    @Test(description = "Проверка валидации при невыбранном слоте и нажатии кнопки 'Оплатить'", groups = {STF_PROD_S})
    public void testSelectSlotRequired() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().fillApartment("1");

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REMOVE.getName());

        checkoutNew().clickConfirmOrder();

        checkoutNew().checkDeliverySlotInvalidBorderVisible();
        checkoutNew().checkConfirmOrderVisible();
    }
}
