package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v3.IntegrationTypeV3;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v3.NotificationsV3Request;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsNegativeV3Test extends RestBase {
    private OrderV2 orderShopper;
    private OrderV2 orderForAccounting;
    private final Integer sidIntegrationForAccounting = 7;
    private final String storeUuid = "dd0f9a61-37ef-47fb-85f2-41d738cdcc13";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        StoreConfigsDao.INSTANCE.updateOrdersApiIntegrationType(sidIntegrationForAccounting.longValue(), IntegrationTypeV3.INTEGRATION_FOR_ACCOUNTING.getValue());
        /*admin.auth();
        admin.authApi();
        admin.editStore(storeUuid, StoresAdminRequest.getStoreLentaElino());*/

        SessionFactory.makeSession(SessionType.API_V2);
        orderShopper = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        orderForAccounting = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), sidIntegrationForAccounting);
    }

    @Story("Негативные тесты")
    @TmsLink("1448")
    @Test(description = "Ошибка 405 (Интеграция для учета)",
            groups = {"api-instamart-smoke", "api-v3"},
            dataProvider = "notificationTypes",
            dataProviderClass = ApiV3DataProvider.class)
    public void orderForAccounting405(NotificationTypeV3 type) {
        Response response = NotificationsV3Request.POST(orderForAccounting.getShipments().get(0).getNumber(), type.getValue());

        checkStatusCode(response, 405);

        OrderV2 readyOrder = apiV2.getOrder(orderForAccounting.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }

    @Story("Негативные тесты")
    @TmsLink("1504")
    @Test(description = "Ошибка 405 (Не применяется)",
            groups = {"api-instamart-smoke", "api-v3"},
            dataProvider = "notificationTypes",
            dataProviderClass = ApiV3DataProvider.class)
    public void postNotifications405(NotificationTypeV3 type) {
        Response response = NotificationsV3Request.POST(orderShopper.getShipments().get(0).getNumber(), type.getValue());

        checkStatusCode(response, 405);

        OrderV2 readyOrder = apiV2.getOrder(orderShopper.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }

    @Story("Негативные тесты")
    @TmsLink("1503")
    @Test(description = "Передан неизвестный статус негатив.", groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void postNotificationsUnknown422() {
        Response response = NotificationsV3Request.POST(orderShopper.getShipments().get(0).getNumber(), "order.unknown");

        checkStatusCode422(response);

        OrderV2 readyOrder = apiV2.getOrder(orderShopper.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY.getStatus());
    }
}
