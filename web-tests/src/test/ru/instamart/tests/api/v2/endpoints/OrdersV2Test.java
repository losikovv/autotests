package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.Order;
import instamart.api.requests.v2.OrdersRequest;
import instamart.api.responses.v2.LineItemsResponse;
import instamart.api.responses.v2.OrderResponse;
import instamart.api.responses.v2.OrdersResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class OrdersV2Test extends RestBase {
    private String orderNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
        OrdersRequest.POST();
    }

    @CaseId(4)
    @Test(  description = "Получаем заказы",
            groups = {"api-instamart-smoke","MRAutoCheck"})
    public void getOrders() {
        response = OrdersRequest.GET();
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы");
    }

    @CaseId(5)
    @Test(  description = "Получаем текущий заказ",
            groups = {"api-instamart-smoke"})
    public void getCurrentOrder() {
        response = OrdersRequest.Current.GET();
        response.prettyPrint();
        InstamartApiCheckpoints.checkStatusCode200(response);
        Order order = response.as(OrderResponse.class).getOrder();
        assertNotNull(order, "Не вернулся текущий заказ");
        orderNumber = order.getNumber();
    }

    @CaseId(9)
    @Test(  description = "Получаем заказ",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "getCurrentOrder")
    public void getOrder() {
        response = OrdersRequest.GET(orderNumber);
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @CaseId(19)
    @Test(  description = "Получаем заказы для оценки",
            groups = {"api-instamart-smoke"})
    public void getUnratedOrders() {
        response = OrdersRequest.Unrated.GET();
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @CaseId(16)
    @Test(  description = "Получаем товары в заказе",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "getCurrentOrder")
    public void getOrderLineItems() {
        response = OrdersRequest.LineItems.GET(orderNumber);
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(LineItemsResponse.class).getLineItems(), "Не вернулись товары заказа");
    }
}
