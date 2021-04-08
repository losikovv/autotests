package ru.instamart.tests.api.v2.endpoints;

import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.objects.v2.Address;
import ru.instamart.api.objects.v2.ShipAddressChange;
import ru.instamart.api.requests.v2.OrdersRequest;
import ru.instamart.api.responses.v2.ShipAddressChangeResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Получение адреса доставки заказа")
public final class ShipAddressV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(233)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"})
    @Story("Существующий id для авторизованных")
    public void testAddressWithAuthAndValidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(Address.class));
    }

    @CaseId(234)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"})
    @Story("Несуществующий id для авторизованных")
    public void testAddressWithAuthAndInvalidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("66666");
        checkStatusCode404(response);
    }

    @CaseId(235)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"})
    @Story("Существующий id для не авторизованных")
    public void testAddressWithoutAuthAndValidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @CaseId(236)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"})
    @Story("Несуществующий id для не авторизованных")
    public void testAddressWithoutAuthAndInvalidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token","66666");
        checkStatusCode404(response);
    }

    @CaseId(237)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Существующий id для авторизованных")
    public void testGetChangeAddressWithValidIdAndAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        final ShipAddressChange shipAddressChange = response.as(ShipAddressChange.class);
        assertNotNull(shipAddressChange);
    }

    @CaseId(238)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id для авторизованных")
    public void testGetChangeAddressWithInvalidIdAndValidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("66666");
        checkStatusCode404(response);
    }

    @CaseId(239)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Существующий id для не авторизованных")
    public void testGetChangeAddressWithValidIdAndInvalidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @CaseId(240)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id для не авторизованных")
    public void testGetChangeAddressWithInvalidIdAndInvalidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("invalid_token", "6666666");
        checkStatusCode404(response);
    }

    @CaseId(241)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Существующий id для авторизованных")
    public void testChangeAddressWithValidIdAndValidAuth() {
        final Address address = Address.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersRequest.ShipAddressChange.PUT(address, apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        final ShipAddressChangeResponse shipAddressChange = response.as(ShipAddressChangeResponse.class);
        assertNotNull(shipAddressChange);
        assertEquals(address.getCity(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getCity());
        assertEquals(address.getStreet(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getStreet());
    }

    @CaseId(242)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id для авторизованных")
    public void testChangeAddressWithInvalidIdAndValidAuth() {
        final Address address = Address.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersRequest.ShipAddressChange.PUT(address, "0");
        checkStatusCode404(response);
    }

    @CaseId(243)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Существующий id для не авторизованных")
    public void testChangeAddressWithValidIdAndInvalidAuth() {
        final Address address = Address.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersRequest.ShipAddressChange.PUT("invalid_token", address, apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @CaseId(244)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id для не авторизованных")
    public void testChangeAddressWithInvalidIdAndInvalidAuth() {
        final Address address = Address.builder()
                .city("Москва")
                .street("Street")
                .build();
        final Response response = OrdersRequest.ShipAddressChange.PUT("invalid_token", address, "0");
        checkStatusCode404(response);
    }

    @CaseId(245)
    @Story("Изменить адрес доставки для заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Указание существующего ship_address[address_id]")
    public void testChangeAddressWithExistShipAddressId() {
        final String orderNumber = apiV2.getCurrentOrderNumber();
        final Address address = Address.builder()
                .city("Москва")
                .street("Street")
                .build();

        Response response = OrdersRequest.ShipAddressChange.PUT(address, orderNumber);
        checkStatusCode200(response);

        ShipAddressChangeResponse shipAddressChange = response.as(ShipAddressChangeResponse.class);
        assertNotNull(shipAddressChange);

        address.setId(shipAddressChange.getShipAddressChange().getOrder().getAddress().getId());
        address.setCity("Удмуртия");
        response = OrdersRequest.ShipAddressChange.PUT(address, orderNumber);
        checkStatusCode200(response);
        shipAddressChange = response.as(ShipAddressChangeResponse.class);
        assertNotNull(shipAddressChange);

        assertEquals(address.getCity(), shipAddressChange.getShipAddressChange().getOrder().getAddress().getCity());
    }
}
