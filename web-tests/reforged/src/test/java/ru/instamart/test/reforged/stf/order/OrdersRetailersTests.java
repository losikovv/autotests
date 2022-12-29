package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.AddressDetailsData;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.data_provider.StoreProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка оформления заказов у разных ретейлеров")
public final class OrdersRetailersTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;
    private final AddressDetailsData data = TestVariables.testAddressData();

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @TmsLinks(value = {@TmsLink("1627"), @TmsLink("1628"), @TmsLink("1629"), @TmsLink("1630"), @TmsLink("1631")})
    @Test(  description = "Тестовые заказы в ритейлерах Москвы",
            groups = REGRESSION_STF,
            dataProviderClass = StoreProvider.class,
            dataProvider = "storeData" )
    public void successOrderInDifferentRetailers(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
        checkout().setDeliveryOptions().fillApartment(data.getApartment());
        checkout().setDeliveryOptions().fillFloor(data.getFloor());
        checkout().setDeliveryOptions().checkElevator();
        checkout().setDeliveryOptions().fillEntrance(data.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(data.getDomofon());
        checkout().setDeliveryOptions().fillComments(data.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }
}
