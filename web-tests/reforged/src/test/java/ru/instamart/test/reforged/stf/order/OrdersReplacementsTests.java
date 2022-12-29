package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.data_provider.ReplacePolicyProvider;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersReplacementsTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @BeforeMethod(alwaysRun = true, description = "Создание юзера и наполнение корзины")
    public void beforeTest() {
        this.ordersUser = UserManager.getQaUser();
        this.helper.dropAndFillCart(ordersUser, UiProperties.DEFAULT_AUCHAN_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @TmsLinks(value = {@TmsLink("1634"), @TmsLink("1635"), @TmsLink("1636"), @TmsLink("1637")})
    @Story("Тест заказа с политикой Звонить / Заменять")
    @Test(description = "Тест заказа с политикой Звонить / Заменять",
            groups = REGRESSION_STF,
            dataProviderClass = ReplacePolicyProvider.class,
            dataProvider = "replacementPolicy")
    public void successOrderWithReplacementPolicy(final String replacementPolicy) {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToPolicy(replacementPolicy);
        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }
}
