package ru.instamart.tests.api.v2.endpoints;

import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.v2.objects.Order;
import instamart.api.v2.responses.LineItemsResponse;
import instamart.api.v2.responses.OrderResponse;
import instamart.api.v2.responses.OrdersResponse;
import instamart.core.common.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Orders extends RestBase {
    private String orderNumber;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.apiV2().authorisation(AppManager.session.admin);
    }

    @Test(  description = "Получаем заказы",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 4)
    public void getOrders() {
        response = ApiV2Requests.getOrders();

        assertEquals(response.getStatusCode(), 200);
        List<Order> orders = response.as(OrdersResponse.class).getOrders();
        assertNotNull(orders, "Не вернулись заказы");
        orderNumber = orders.get(0).getNumber();
    }

    @Test(  description = "Получаем текущий заказ",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 5)
    public void getCurrentOrder() {
        response = ApiV2Requests.getOrdersCurrent();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся текущий заказ");
    }

    @Test(  description = "Получаем заказ",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 9,
            dependsOnMethods = "getOrders")
    public void getOrder() {
        response = ApiV2Requests.getOrder(orderNumber);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @Test(  description = "Получаем заказы для оценки",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 16)
    public void getUnratedOrders() {
        response = ApiV2Requests.getUnratedOrders();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы для оценки");
    }


    @Test(  description = "Получаем товары в заказе",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 17,
            dependsOnMethods = "getOrders")
    public void getOrderLineItems() {
        response = ApiV2Requests.getOrderLineItems(orderNumber);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(LineItemsResponse.class).getLine_items(), "Не вернулись товары заказа");
    }
}
