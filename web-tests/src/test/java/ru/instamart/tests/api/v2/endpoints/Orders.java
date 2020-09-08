package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.Order;
import ru.instamart.application.rest.objects.responses.OrderResponse;
import ru.instamart.application.rest.objects.responses.OrdersResponse;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Orders extends RestBase {
    private String orderNumber;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.rest().authorisation(AppManager.session.admin);
    }

    @Test(  description = "Получаем заказы",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 4)
    public void getOrders() {
        response = Requests.getOrders();
        List<Order> orders = response.as(OrdersResponse.class).getOrders();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(orders, "Не вернулись заказы");
        orderNumber = orders.get(0).getNumber();
    }

    @Test(  description = "Получаем текущий заказ",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 5)
    public void getCurrentOrder() {
        response = Requests.getOrdersCurrent();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся текущий заказ");
    }

    @Test(  description = "Получаем заказ",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 9,
            dependsOnMethods = "getOrders")
    public void getOrder() {
        response = Requests.getOrder(orderNumber);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся заказ по номеру");
    }
}
