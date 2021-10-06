package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.data_provider.CityProvider;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersCitiesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @Test(description = "Тест заказа в METRO",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"
            }, dataProviderClass = CityProvider.class, dataProvider = "city")
    public void successOrderFromCity(final AddressV2 address) {
        helper.auth(userData);
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID, address);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
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
