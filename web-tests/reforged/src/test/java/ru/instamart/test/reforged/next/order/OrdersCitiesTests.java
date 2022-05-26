package ru.instamart.test.reforged.next.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.data_provider.CityProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.next.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersCitiesTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseIDs(value = {@CaseId(1650), @CaseId(1651), @CaseId(1652), @CaseId(1653), @CaseId(1654), @CaseId(1655),
            @CaseId(1656), @CaseId(1657), @CaseId(1658), @CaseId(1659), @CaseId(1660), @CaseId(1661),
            @CaseId(1662), @CaseId(1663), @CaseId(1664), @CaseId(1665)})
    @Test(description = "Тест заказа в METRO в разных городах",
            groups = {"regression", "acceptance"},
            dataProviderClass = CityProvider.class,
            dataProvider = "city")
    public void successOrderFromCity(final AddressV2 address) {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, "METRO", address);
        helper.setAddress(userData, address);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
        userShipments().checkStatusShipmentReady();
    }
}
