package ru.instamart.test.reforged.stf_prod.checkout_new;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_CARD_TO_COURIER;
import static ru.instamart.reforged.stf.enums.ReplacementPolicies.CALL_AND_REPLACE;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutAddressTests {

    private static final ThreadLocal<UserData> ordersUser = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true, description = "Шаги предусловия")
    public void beforeTest() {
        ordersUser.set(UserManager.getQaUser());
        this.helper.dropAndFillCart(ordersUser.get(), DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser.get());
    }

    @TmsLink("3594")
    @Test(description = "Проверка того, что поле 'Кв, офис' является обязательным к заполнению", groups = {STF_PROD_S})
    public void testCheckRequiredFields() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickConfirmPay();
        checkoutNew().checkApartmentNumberAlertVisible();

        checkoutNew().clickFirstSlot();
        checkoutNew().checkSlotActive(1);

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_CARD_TO_COURIER.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();
        checkoutNew().interactPaymentMethodsModal().checkModalNotVisible();

        checkoutNew().clickReplacementPolicy();
        checkoutNew().selectReplacementPolicyByName(CALL_AND_REPLACE.getName());

        checkoutNew().clickConfirmOrder();
        checkoutNew().checkApartmentNumberAlertVisible();

        checkoutNew().fillApartment("123");
        checkoutNew().fillComment("test");
        checkoutNew().clickConfirmOrder();

        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }

    @TmsLink("3821")
    @Test(description = "Проверка перехода на см-бизнес через клик по 'Заказываю для бизнеса' в блоке адрес (Доставка)", groups = {STF_PROD_S})
    public void testTransitionOnB2BViaCheckoutFromDelivery() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickClose();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().checkCheckoutButtonIsVisible();
    }
}
