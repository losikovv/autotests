package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v3.NotificationsV3Request;
import ru.instamart.api.response.v3.ProductsV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsV3Test extends RestBase {
    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1448)
    @Test(description = "Отправка нотификации 405", groups = "api-instamart-regress")
    public void postNotifications405() {
        Response responseInWork = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");
        Response responseAssembled = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.assembled");
        Response responseReadyForDelivery = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.ready_for_delivery");
        Response responseDelivered = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.delivered");
        Response responseCanceled = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode(responseInWork, 405);
        checkStatusCode(responseAssembled, 405);
        checkStatusCode(responseReadyForDelivery, 405);
        checkStatusCode(responseDelivered, 405);
        checkStatusCode(responseCanceled, 405);
    }

    @CaseId(796)
    @Test(description = "Отправка нотификации unknown 422", groups = "api-instamart-regress")
    public void postNotificationsUnknown422() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.unknown");

        checkStatusCode422(response);
    }

    /*

    @CaseId(1466)
    @Test(description = "Отправка нотификации in_work после создания заказа", groups = "api-instamart-regress")
    public void postNotificationsInWork() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");

        checkStatusCode200(response);
    }

    @CaseId(1467)
    @Test(description = "Отправка нотификации assembled после in_work", groups = "api-instamart-regress")
    public void postNotificationsAssembled() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.assembled");

        checkStatusCode200(response);
    }

    @CaseId(1468)
    @Test(description = "Отправка нотификации ready_for_delivery после in_work", groups = "api-instamart-regress")
    public void postNotificationsReadyForDelivery() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.ready_for_delivery");

        checkStatusCode200(response);
    }

    @CaseId(1461)
    @Test(description = "Отправка нотификации canceled после создания заказа", groups = "api-instamart-regress")
    public void postNotificationsCanceled() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode200(response);
    }

    @CaseId(1462)
    @Test(description = "Отправка нотификации canceled после in_work", groups = "api-instamart-regress")
    public void postNotificationsCanceledInWork() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode200(response);
    }

    @CaseId(1463)
    @Test(description = "Отправка нотификации canceled после assembled", groups = "api-instamart-regress")
    public void postNotificationsCanceledAssembled() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.assembled");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode200(response);
    }

    @CaseId(1464)
    @Test(description = "Отправка нотификации canceled после ready_for_delivery", groups = "api-instamart-regress")
    public void postNotificationsCanceledReadyForDelivery() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.ready_for_delivery");
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode200(response);
    }

    */
}
