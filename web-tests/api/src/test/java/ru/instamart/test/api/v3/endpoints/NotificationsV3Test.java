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
import ru.instamart.kraken.config.EnvironmentProperties;

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

    //@CaseId(793)
    @Test(description = "Отправка нотификации", groups = "api-instamart-regress")
    public void postNotifications405() {
        Response response = NotificationsV3Request.POST(order.getShipments().get(0).getNumber(), "order.in_work");

        checkStatusCode(response, 405);
    }
}
