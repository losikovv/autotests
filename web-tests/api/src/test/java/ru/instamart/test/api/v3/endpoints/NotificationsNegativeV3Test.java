package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v3.NotificationTypesV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v3.NotificationsV3Request;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsNegativeV3Test extends RestBase {
    private OrderV2 orderShopper;
    private OrderV2 orderForAccounting;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        orderShopper = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        orderForAccounting = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 7);
    }

    @Story("Негативные тесты")
    @CaseId(1448)
    @Test(  description = "Ошибка 405 (Интеграция для учета)",
            groups = "api-instamart-regress",
            dataProvider = "notificationTypes",
            dataProviderClass = ApiV3DataProvider.class)
    public void orderForAccounting405(NotificationTypesV3 type) {
        Response response = NotificationsV3Request.POST(orderForAccounting.getShipments().get(0).getNumber(), type.getValue());

        checkStatusCode(response, 405);

        OrderV2 readyOrder = apiV2.getOrder(orderForAccounting.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }

    @Story("Негативные тесты")
    @CaseId(1504)
    @Test(  description = "Ошибка 405 (Шоппер)",
            groups = "api-instamart-regress",
            dataProvider = "notificationTypes",
            dataProviderClass = ApiV3DataProvider.class)
    public void postNotifications405(NotificationTypesV3 type) {
        Response response = NotificationsV3Request.POST(orderShopper.getShipments().get(0).getNumber(), type.getValue());

        checkStatusCode(response, 405);

        OrderV2 readyOrder = apiV2.getOrder(orderShopper.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }

    @Story("Негативные тесты")
    @CaseId(1503)
    @Test(description = "Ошибка 422 (передан неизвестный статус)", groups = "api-instamart-regress")
    public void postNotificationsUnknown422() {
        Response response = NotificationsV3Request.POST(orderShopper.getShipments().get(0).getNumber(), "order.unknown");

        checkStatusCode422(response);

        OrderV2 readyOrder = apiV2.getOrder(orderShopper.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }

}
