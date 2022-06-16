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
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.data_provider.StoreProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

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

    @CaseIDs(value = {@CaseId(1627), @CaseId(1628), @CaseId(1629), @CaseId(1630), @CaseId(1631)})
    @Test(  description = "Тестовые заказы в ритейлерах Москвы",
            groups = "regression",
            dataProviderClass = StoreProvider.class,
            dataProvider = "storeData" )
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = "COOKIE_ALERT")
    public void successOrderInDifferentRetailers(int storeId, ShopUrl shopUrl) {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, storeId);

        shop().goToPage(shopUrl);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
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
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }
}
