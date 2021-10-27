package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.data_provider.CityProvider;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersCitiesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getQaUser();

    @CaseIDs(value = {@CaseId(1650), @CaseId(1651), @CaseId(1652), @CaseId(1653), @CaseId(1654),@CaseId(1655),
            @CaseId(1656), @CaseId(1657), @CaseId(1658), @CaseId(1659), @CaseId(1660), @CaseId(1661),
            @CaseId(1662), @CaseId(1663), @CaseId(1664), @CaseId(1665)})
    @Test(  description = "Тест заказа в METRO",
            groups = {"regression", "acceptance"},
            dataProviderClass = CityProvider.class,
            dataProvider = "city")
    public void successOrderFromCity(final AddressV2 address) {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID, address);

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

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());
    }

    @AfterMethod(description = "Отмена активных заказов")
    public void cancelOrder() {
        helper.cancelAllActiveOrders(userData);
    }
}
