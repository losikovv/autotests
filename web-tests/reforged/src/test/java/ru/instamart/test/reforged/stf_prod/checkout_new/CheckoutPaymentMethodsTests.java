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
import static ru.instamart.reforged.stf.enums.PaymentMethods.BY_BUSINESS_ACCOUNT;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Чекаут V3")
public final class CheckoutPaymentMethodsTests {

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

    @TmsLink("3822")
    @Test(description = "Проверка редиректа на см-бизнес при способе оплаты 'По счету для бизнеса' (Доставка)", groups = {STF_PROD_S})
    public void testTransitionOnB2BIfSelectBusinessPaymentMethodFromDelivery() {
        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().checkSpinnerNotVisible();

        checkoutNew().openPaymentMethodModal();
        checkoutNew().interactPaymentMethodsModal().checkModalVisible();
        checkoutNew().interactPaymentMethodsModal().selectPaymentMethod(BY_BUSINESS_ACCOUNT.getName());
        checkoutNew().interactPaymentMethodsModal().clickConfirm();

        checkoutNew().checkSelectCompanyButtonDisplayed();
        checkoutNew().clickSelectCompany();

        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().checkCheckoutButtonIsVisible();
    }
}
