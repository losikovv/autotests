package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.Address;
import instamart.api.objects.v2.Order;
import instamart.api.requests.v2.OrdersRequest;
import instamart.api.responses.v2.OrderResponse;
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

    private Order order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
        OrdersRequest.POST();
        final Response response = OrdersRequest.Current.GET();
        checkStatusCode200(response);
        order = response.as(OrderResponse.class).getOrder();
        assertNotNull(order, "Не вернулся текущий заказ");
    }

    @CaseId(233)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий id для авторизованных")
    public void testAddressWithAuthAndValidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET(order.getNumber());
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
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token", order.getNumber());
        checkStatusCode403(response);
    }

    @CaseId(236)
    @Test(groups = {"api-instamart-regress"})
    @Story("Несуществующий id для не авторизованных")
    public void testAddressWithoutAuthAndInvalidOrderId() {
        final Response response = OrdersRequest.ShipAddress.GET("invalid_token","66666");
        checkStatusCode404(response);
    }
}
