package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v3.NotificationTypesV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.request.v3.NotificationsV3Request.POST;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsPositiveV3Test extends RestBase {
    private OrderV2 order;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58);
    }

//    @CaseId(1461)
//    @Test(  description = "Canceled после создания",
//            groups = "api-instamart-regress")
//    public void cancelOrder() {
//        Notifications notifications = Notifications.builder()
//                .event(Event.builder()
//                        .type(NotificationTypesV3.CANCELED.getValue())
//                        .payload(Payload.builder()
//                                .orderId(order.getShipments().get(0).getNumber())
//                                .build())
//                        .build())
//                .build();
//
//        Response response = POST(notifications);
//        checkStatusCode200(response);
//
//        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
//        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
//    }

    @Story("Позитивные тесты")
    @CaseId(1461)
    @Test(  description = "Canceled после создания",
            groups = "api-instamart-regress")
    public void cancelOrder() {
        Response response = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.CANCELED.getValue());
        checkStatusCode200(response);

        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
    }
}
