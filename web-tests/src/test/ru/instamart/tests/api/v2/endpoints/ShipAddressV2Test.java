package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.Address;
import instamart.api.objects.v2.ShipAddressChange;
import instamart.api.requests.v2.OrdersRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Получение адреса доставки заказа")
public final class ShipAddressV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(233)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий id для авторизованных")
    public void testAddressWithAuthAndValidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(Address.class));
    }

    @CaseId(234)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Несуществующий id для авторизованных")
    public void testAddressWithAuthAndInvalidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("66666");
        checkStatusCode404(response);
    }

    @CaseId(235)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий id для не авторизованных")
    public void testAddressWithoutAuthAndValidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @CaseId(236)
    @Test(groups = {"api-instamart-regress"})
    @Story("Несуществующий id для не авторизованных")
    public void testAddressWithoutAuthAndInvalidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token","66666");
        checkStatusCode404(response);
    }

    @CaseId(237)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий id для авторизованных")
    public void testChangeAddressWithValidIdAndAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        final ShipAddressChange shipAddressChange = response.as(ShipAddressChange.class);
        assertNotNull(shipAddressChange);
    }

    @CaseId(238)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий id для авторизованных")
    public void testChangeAddressWithInvalidIdAndValidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("66666");
        checkStatusCode404(response);
    }

    @CaseId(239)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress"}, description = "Существующий id для не авторизованных")
    public void testChangeAddressWithValidIdAndInvalidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("invalid_token", apiV2.getCurrentOrderNumber());
        checkStatusCode403(response);
    }

    @CaseId(240)
    @Story("Получение списка возможных изменений для заказа")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий id для не авторизованных")
    public void testChangeAddressWithInvalidIdAndInvalidAuth() {
        final Response response = OrdersRequest.ShipAddressChange.GET("invalid_token", "6666666");
        checkStatusCode404(response);
    }
}
