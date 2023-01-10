package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.data_provider.CityProvider;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.enums.ShipmentStates.ACCEPTED_STATE;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersCitiesTests {

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData.get());
    }

    @TmsLinks(value = {@TmsLink("1650"), @TmsLink("1651"), @TmsLink("1652"), @TmsLink("1653"), @TmsLink("1654"), @TmsLink("1655"),
            @TmsLink("1656"), @TmsLink("1657"), @TmsLink("1658"), @TmsLink("1659"), @TmsLink("1660"), @TmsLink("1661"),
            @TmsLink("1662"), @TmsLink("1663"), @TmsLink("1664"), @TmsLink("1665")})
    @Test(description = "Тест заказа в METRO в разных городах",
            groups = {REGRESSION_STF},
            dataProviderClass = CityProvider.class,
            dataProvider = "city")
    public void successOrderFromCity(final AddressV2 address) {
        userData.set(UserManager.getQaUser());
        helper.dropAndFillCart(userData.get(), "METRO", address);
        helper.setAddress(userData.get(), address);

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

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipment().waitPageLoad();
        userShipment().checkActiveShipmentState(ACCEPTED_STATE.getName());
    }
}
