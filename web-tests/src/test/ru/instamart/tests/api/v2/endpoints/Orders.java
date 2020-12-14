package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Order;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.LineItemsResponse;
import instamart.api.responses.v2.OrderResponse;
import instamart.api.responses.v2.OrdersResponse;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Orders extends RestBase {
    private String orderNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (!apiV2.authorized()) {
            final UserData user = Users.apiUser();
            apiV2.registration(user);
            apiV2.authorisation(user);
        }
        ApiV2Requests.Orders.POST();
    }

    @CaseId(4)
    @Test(  description = "Получаем заказы",
            groups = {"api-v2-smoke","MRAutoCheck"})
    public void getOrders() {
        response = ApiV2Requests.Orders.GET();
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы");
    }

    @CaseId(5)
    @Test(  description = "Получаем текущий заказ",
            groups = {"api-v2-smoke"})
    public void getCurrentOrder() {
        response = ApiV2Requests.Orders.Current.GET();
        response.prettyPrint();
        InstamartApiCheckpoints.assertStatusCode200(response);
        Order order = response.as(OrderResponse.class).getOrder();
        assertNotNull(order, "Не вернулся текущий заказ");
        orderNumber = order.getNumber();
    }

    @CaseId(9)
    @Test(  description = "Получаем заказ",
            groups = {"api-v2-smoke"},
            dependsOnMethods = "getCurrentOrder")
    public void getOrder() {
        response = ApiV2Requests.Orders.GET(orderNumber);
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrderResponse.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @CaseId(19)
    @Test(  description = "Получаем заказы для оценки",
            groups = {"api-v2-smoke"})
    public void getUnratedOrders() {
        response = ApiV2Requests.Orders.Unrated.GET();
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(OrdersResponse.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @CaseId(16)
    @Test(  description = "Получаем товары в заказе",
            groups = {"api-v2-smoke"},
            dependsOnMethods = "getCurrentOrder")
    public void getOrderLineItems() {
        response = ApiV2Requests.Orders.LineItems.GET(orderNumber);
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(LineItemsResponse.class).getLine_items(), "Не вернулись товары заказа");
    }
}
