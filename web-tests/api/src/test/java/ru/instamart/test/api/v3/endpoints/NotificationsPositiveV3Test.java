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
        Response responseСanceled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.CANCELED.getValue());
        checkStatusCode200(responseСanceled);

        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1462)
    @Test(  description = "Canceled после order.in_work",
            groups = "api-instamart-regress")
    public void cancelOrderInWork() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseСanceled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.CANCELED.getValue());
        checkStatusCode200(responseСanceled);

        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1463)
    @Test(  description = "Canceled после order.assembled",
            groups = "api-instamart-regress")
    public void cancelOrderAssembled() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);
        Response responseСanceled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.CANCELED.getValue());
        checkStatusCode200(responseСanceled);

        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1464)
    @Test(  description = "Canceled после order.ready_for_delivery",
            groups = "api-instamart-regress")
    public void cancelOrderReadyForDelivery() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.READY_FOR_DELIVERY.getValue());
        checkStatusCode200(responseReadyForDelivery);
        Response responseСanceled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.CANCELED.getValue());
        checkStatusCode200(responseСanceled);

        OrderV2 canceledOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1466)
    @Test(  description = "In_work после создания",
            groups = "api-instamart-regress")
    public void orderInWork() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1467)
    @Test(  description = "Assembled после order.in_work",
            groups = "api-instamart-regress")
    public void orderAssembled() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);

        OrderV2 collectingOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus());
    }

    @Story("Позитивные тесты")
    @CaseId(1468)
    @Test(  description = "Ready_for_delivery после order.in_work",
            groups = "api-instamart-regress")
    public void orderReadyForDelivery() {
        Response responseInWork = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(order.getShipments().get(0).getNumber(), NotificationTypesV3.READY_FOR_DELIVERY.getValue());
        checkStatusCode200(responseReadyForDelivery);

        OrderV2 collectingOrder = apiV2.getOrder(order.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus());
        //добавить проверку лайн айтемов
    }

}
