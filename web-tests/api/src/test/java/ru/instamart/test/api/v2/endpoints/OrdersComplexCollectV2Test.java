package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.LineItemCancellationsV2Response;
import ru.instamart.api.response.v2.LineItemReplacementsV2Response;
import ru.instamart.api.response.v2.OrdersV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertFalse;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.k8s.K8sHelper.changeToShip;

@Epic("ApiV2")
@Feature("Заказы (orders)")
public class OrdersComplexCollectV2Test extends RestBase {

    private String orderNumber, shipmentNumber;

    @BeforeMethod(enabled = false,
            description = "Авторизация, создание комплексного заказа")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);

        OrderV2 order = apiV2.order(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID,
                4
        );
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();
        changeToShip(shipmentNumber);
    }

    @CaseId(306)
    @Story("История заказов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение истории заказов (1-ая страница)")
    public void getPreviousOrder200() {
        SessionFactory.createSessionToken(SessionType.API_V2, UserManager.getDefaultApiUser());
        final Response response = OrdersV2Request.GET(1);
        checkStatusCode200(response);
        OrdersV2Response ordersV2Response = response.as(OrdersV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(ordersV2Response.getOrders(), "Не вернулись заказы");
        softAssert.assertEquals(ordersV2Response.getMeta().getCurrentPage(), Integer.valueOf(1), "Current page not valid");
        softAssert.assertNull(ordersV2Response.getMeta().getNextPage(), "Next page not null");
        softAssert.assertEquals(ordersV2Response.getMeta().getTotalPages(), Integer.valueOf(1), "Total page not valid");
        softAssert.assertEquals(ordersV2Response.getMeta().getPerPage(), Integer.valueOf(10), "Per page not valid");
        softAssert.assertNotNull(ordersV2Response.getMeta().getTotalCount(), "Total count not valid");
        softAssert.assertAll();
    }

    @CaseId(307)
    @Story("История заказов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о предыдущем заказе. Нет предыдущих заказов")
    public void getPreviousOrder() {
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = OrdersV2Request.Previous.GET();
        checkStatusCode404(response);
        checkError(response, "У пользователя нет прошлых заказов");
    }

    @Deprecated
    @CaseId(637)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {},
            description = "Получение списка отмененных позиций по заказу для существующего id")
    public void getLineItemCancellationsWithItem200() {
        response = OrdersV2Request.LineItemCancellations.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Нет отмененных позиций заказа");
    }

    @Deprecated
    @CaseId(325)
    @Story("Получение списка отмененных позиций по подзаказу")
    @Test(groups = {},
            description = "Получение списка отмененных позиций по подзаказу для существующего id")
    public void getShipmentLineItem200() {
        response = ShipmentsV2Request.LineItemCancellations.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Нет отмененных позиций для достаки");
    }

    @Deprecated
    @CaseId(327)
    @Story("Получение списка замененных позиций по заказу")
    @Test(groups = {},
            description = "Получение списка замененных позиций по заказу по существующему id")
    public void getOrdersLineItemReplacements200() {
        response = OrdersV2Request.LineItemReplacements.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemReplacementsV2Response.class).getLineItemReplacements().isEmpty(), "Нет отмененных позиций для достаки");
    }


    @Deprecated
    @CaseId(329)
    @Story("Получение списка замененных позиций по подзаказуу")
    @Test(groups = {},
            description = "Получение списка замененных позиций по подзаказу по существующему id")
    public void getShipmentLineItemReplacements200() {
        response = ShipmentsV2Request.LineItemReplacements.GET(orderNumber);
        checkStatusCode200(response);
        assertFalse(response.as(LineItemReplacementsV2Response.class).getLineItemReplacements().isEmpty(), "Нет отмененных позиций для достаки");
    }
}
