package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.RetailerCards;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.enums.Tenant;
import ru.instamart.kraken.listener.Run;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

//TODO не проверялся на next
@Epic("STF UI")
@Feature("Покупка товара со скидочной картой")
public final class OrdersRetailerCardsTests {

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true, description = "Наполнение корзины")
    public void beforeTest() {
        userData.set(UserManager.getQaUser());
        helper.dropAndFillCart(userData.get(), UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData.get());
    }

    @Run(onTenant = Tenant.SBERMARKET, onServer = Server.PRODUCTION)
    @TmsLink("1633")
    @Test(description = "Тест заказа с картой Вкусвилл (только Sbermarket)", groups = REGRESSION_STF)
    public void successOrderWithVkusvillRetailerCard() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().submitOrder();

        checkoutNew().waitPageLoad();
        checkoutNew().clickOrderForBusiness();
        checkoutNew().interactB2BOrderModal().checkModalVisible();
        checkoutNew().interactB2BOrderModal().clickConfirm();
        checkoutNew().interactB2BOrderModal().checkModalNotVisible();

        checkout().waitPageLoad();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().clickToAddRetailerCard();
        checkout().interactEditRetailerCardModal()
                .fillValue(RetailerCards.vkusvill().getCardNumber());
        checkout().interactEditRetailerCardModal()
                .clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }
}
