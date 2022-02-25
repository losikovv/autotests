package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v1.ShoppingContextV1Request;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkAddressInDb;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Настройки")
public class ShoppingContextV1Tests extends RestBase {

    private UserData user;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
    }

    @CaseIDs(value = {@CaseId(1554), @CaseId(1555)})
    @Story("Настройки параметров покупок")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление адреса",
            dataProvider = "shippingMethods",
            dataProviderClass = RestDataProvider.class)
    public void createShoppingContext(String shippingMethod) {
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        final Response response = ShoppingContextV1Request.PUT(address, shippingMethod);
        checkStatusCode200(response);
        checkAddressInDb(address, user);
    }

    @CaseId(1556)
    @Story("Настройки параметров покупок")
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение существующего адреса",
            dependsOnMethods = "createShoppingContext")
    public void changeShoppingContext() {
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        final Response response = ShoppingContextV1Request.PUT(address, ShippingMethodV2.BY_COURIER.getMethod());
        checkStatusCode200(response);
        checkAddressInDb(address, user);
    }
}
