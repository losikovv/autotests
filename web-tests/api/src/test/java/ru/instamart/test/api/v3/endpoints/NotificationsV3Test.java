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
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

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
    @Test(description = "Отправка нотификации in_work 405", groups = "api-instamart-regress")
    public void postNotificationsInWork405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");

        checkStatusCode(response, 405);
    }

    @CaseId(1448)
    @Test(description = "Отправка нотификации assembled 405", groups = "api-instamart-regress")
    public void postNotificationsAssembled405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.assembled");

        checkStatusCode(response, 405);
    }

    @CaseId(1448)
    @Test(description = "Отправка нотификации ready_for_delivery 405", groups = "api-instamart-regress")
    public void postNotificationsReadyForDelivery405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.ready_for_delivery");

        checkStatusCode(response, 405);
    }

    @CaseId(1448)
    @Test(description = "Отправка нотификации delivered 405", groups = "api-instamart-regress")
    public void postNotificationsDelivered405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.delivered");

        checkStatusCode(response, 405);
    }

    @CaseId(1448)
    @Test(description = "Отправка нотификации canceled 405", groups = "api-instamart-regress")
    public void postNotificationsCanceled405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.canceled");

        checkStatusCode(response, 405);
    }
}
