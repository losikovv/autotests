package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Order;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.LineItemsResponse;
import instamart.api.responses.v2.OrderResponse;
import instamart.api.responses.v2.OrdersResponse;
import instamart.core.common.AppManager;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;

public class Orders extends RestBase {
    private String orderNumber;

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (!ApiV2Requests.authorized()) kraken.apiV2().authorisation(AppManager.session.admin);
        ApiV2Requests.postOrder();
    }

    @CaseId(4)
    @Test(  description = "Получаем заказы",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getOrders() {
        response = ApiV2Requests.getOrders();
        ApiV2Checkpoints.assertStatusCode200(response);
        List<Order> orders = response.as(OrdersResponse.class).getOrders();
        assertNotNull(orders, "Не вернулись заказы");
        orderNumber = orders.get(0).getNumber();
    }

    @CaseId(5)
    @Test(  description = "Получаем текущий заказ",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getCurrentOrder() {
        response = ApiV2Requests.getOrdersCurrent();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся текущий заказ");
    }

    @CaseId(9)
    @Test(  description = "Получаем заказ",
            groups = {"rest-smoke","rest-v2-smoke"},
            dependsOnMethods = "getOrders")
    public void getOrder() {
        response = ApiV2Requests.getOrder(orderNumber);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @CaseId(19)
    @Test(  description = "Получаем заказы для оценки",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getUnratedOrders() {
        response = ApiV2Requests.getUnratedOrders();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @CaseId(16)
    @Test(  description = "Получаем товары в заказе",
            groups = {"rest-smoke","rest-v2-smoke"},
            dependsOnMethods = "getOrders")
    public void getOrderLineItems() {
        response = ApiV2Requests.getOrderLineItems(orderNumber);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(LineItemsResponse.class).getLine_items(), "Не вернулись товары заказа");
    }
}
