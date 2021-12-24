package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.ShipAddressChangeV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.ShipAddressChangeV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Адрес доставки")
public final class ShipAddressV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if(EnvironmentProperties.SERVER.equals("production")) {
            SessionFactory.createSessionToken(SessionType.PROD, UserManager.getQaUser());
        } else {
            SessionFactory.makeSession(SessionType.API_V2);
        }
    }

    @Deprecated
    @Test(groups = {})
    @Story("Существующий id для авторизованных")
    public void testAddressWithAuthAndValidOrderId() {
        final Response response = OrdersV2Request.ShipAddress.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Несуществующий id для авторизованных")
    public void testAddressWithAuthAndInvalidOrderId() {
        final Response response = OrdersV2Request.ShipAddress.GET("66666");
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Существующий id для не авторизованных")
    public void testAddressWithoutAuthAndValidOrderId() {
        final Response response = OrdersV2Request.ShipAddress.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @Deprecated
    @Test(groups = {})
    @Story("Несуществующий id для не авторизованных")
    public void testAddressWithoutAuthAndInvalidOrderId() {
        final Response response = OrdersV2Request.ShipAddress.GET("invalid_token","66666");
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Получение списка возможных изменений для заказа")
    @Test(  groups = {},
            description = "Существующий id для авторизованных")
    public void testGetChangeAddressWithValidIdAndAuth() {
        final Response response = OrdersV2Request.ShipAddressChange.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        final ShipAddressChangeV2 shipAddressChange = response.as(ShipAddressChangeV2.class);
        assertNotNull(shipAddressChange, "Ответ вернулся пустым");
    }

    @Deprecated
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {}, description = "Несуществующий id для авторизованных")
    public void testGetChangeAddressWithInvalidIdAndValidAuth() {
        final Response response = OrdersV2Request.ShipAddressChange.GET("66666");
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {}, description = "Существующий id для не авторизованных")
    public void testGetChangeAddressWithValidIdAndInvalidAuth() {
        final Response response = OrdersV2Request.ShipAddressChange.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @Deprecated
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {}, description = "Несуществующий id для не авторизованных")
    public void testGetChangeAddressWithInvalidIdAndInvalidAuth() {
        final Response response = OrdersV2Request.ShipAddressChange.GET("invalid_token", "6666666");
        checkStatusCode404(response);
    }

    @CaseId(241)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Существующий id для авторизованных")
    public void testChangeAddressWithValidIdAndValidAuth() {
        final AddressV2 address = AddressV2.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersV2Request.ShipAddressChange.PUT(address, apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        final ShipAddressChangeV2Response shipAddressChange = response.as(ShipAddressChangeV2Response.class);
        checkFieldIsNotEmpty(shipAddressChange, "ответ");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(address.getCity(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getCity(), "city не рано задааному");
        softAssert.assertEquals(address.getStreet(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getStreet(), "street не равно задачному ");
        softAssert.assertAll();
    }

    @CaseId(242)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id для авторизованных")
    public void testChangeAddressWithInvalidIdAndValidAuth() {
        final AddressV2 address = AddressV2.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersV2Request.ShipAddressChange.PUT(address, "0");
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {}, description = "Существующий id для не авторизованных")
    public void testChangeAddressWithValidIdAndInvalidAuth() {
        final AddressV2 address = AddressV2.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersV2Request.ShipAddressChange.PUT("invalid_token", address, apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @Deprecated
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {}, description = "Несуществующий id для не авторизованных")
    public void testChangeAddressWithInvalidIdAndInvalidAuth() {
        final AddressV2 address = AddressV2.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersV2Request.ShipAddressChange.PUT("invalid_token", address, "0");
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {}, description = "Указание существующего ship_address[address_id]")
    public void testChangeAddressWithExistShipAddressId() {
        final String orderNumber = apiV2.getCurrentOrderNumber();
        final AddressV2 address = AddressV2.builder()
                .city("Москва")
                .street("Street")
                .build();

        Response response = OrdersV2Request.ShipAddressChange.PUT(address, orderNumber);
        checkStatusCode200(response);

        ShipAddressChangeV2Response shipAddressChange = response.as(ShipAddressChangeV2Response.class);
        assertNotNull(shipAddressChange, "Ответ вернулся пустым");

        address.setId(shipAddressChange.getShipAddressChange().getOrder().getAddress().getId());
        address.setCity("Удмуртия");
        response = OrdersV2Request.ShipAddressChange.PUT(address, orderNumber);
        checkStatusCode200(response);
        shipAddressChange = response.as(ShipAddressChangeV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(shipAddressChange, "Ответ вернулся пустым");
        softAssert.assertEquals(address.getCity(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getCity(), "city не равен заданному");
        softAssert.assertAll();
    }
}
