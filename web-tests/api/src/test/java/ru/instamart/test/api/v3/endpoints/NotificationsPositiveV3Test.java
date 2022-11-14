package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AssemblyItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.request.v3.NotificationsV3Request.*;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsPositiveV3Test extends RestBase {
    private OrderV2 orderDeliveryBySbermarket;
    private OrderV2 orderDeliveryByRetailer;
    private final Integer sidDeliveryBySbermarket = 58;
    private final String uuidDeliveryBySbermarket = "adaa359e-6c53-462a-928d-307317e399b1";
    private final Integer sidDeliveryByRetailer = 121;
    private final String uuidDeliveryByRetailer = "5f17e133-3405-4422-8f0f-64adcd7c738a";

    @BeforeClass(alwaysRun = false)
    public void preconditionsBeforeClass() {
        apiV3.checkFlipper("allow_export_to_external_services");
        admin.auth();
        admin.authApi();
        admin.editStore(uuidDeliveryBySbermarket, StoresAdminRequest.getStoreLentaOrekhoviyBulvar());
        admin.editStore(uuidDeliveryByRetailer, StoresAdminRequest.getStoreVictoriaTest());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditionsBeforeMethod() {
        SessionFactory.makeSession(SessionType.API_V2);
        orderDeliveryBySbermarket = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), sidDeliveryBySbermarket);
        orderDeliveryByRetailer = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), sidDeliveryByRetailer);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1461)
    @Test(description = "Canceled после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void canceledOrderDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1461)
    @Test(description = "Canceled после создания (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void canceledOrderDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1462)
    @Test(description = "Canceled после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void canceledOrderInWorkDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1462)
    @Test(description = "Canceled после order.in_work (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void canceledOrderInWorkDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1464)
    @Test(description = "Canceled после order.ready_for_delivery (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void canceledOrderReadyForDeliveryDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1464)
    @Test(description = "Canceled после order.ready_for_delivery (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void canceledOrderReadyForDeliveryDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        simplyAwait(3);
        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3087)
    @Test(description = "Canceled после order.delivering",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelOrderDeliveringDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode200(responseDelivering);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        simplyAwait(1);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1466)
    @Test(description = "In_work после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderedInWorkDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1466)
    @Test(description = "In_work после создания (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderInWorkDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1468)
    @Test(description = "Ready_for_delivery после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderReadyForDeliveryDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1468)
    @Test(description = "Ready_for_delivery после order.in_work (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderReadyForDeliveryDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2835)
    @Test(description = "Ошибка 405 Delivering (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveringDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode(responseDelivering, 405);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2827)
    @Test(description = "Delivering после ready_for_delivery (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveringDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode200(responseDelivering);

        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.SHIPPING.getStatus(), "Заказ не перешел в статус Доставляется");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2075)
    @Test(description = "Delivered после ready_for_delivery (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2828)
    @Test(description = "Delivered после delivering (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredAfterDeliveringDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode200(responseDelivering);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2601)
    @Test(description = "Canceled после отмены (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelAfterCancelOrderDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseRepeatCanceled = POST(bodyCanceled);
        //checkStatusCode422(responseRepeatCanceled); ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2601)
    @Test(description = "Canceled после отмены (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelAfterCancelOrderDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseRepeatCanceled = POST(bodyCanceled);
        //checkStatusCode422(responseRepeatCanceled); ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(1465)
    @Test(description = "Canceled после доставки негатив.",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelOrderDeliveredDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode422(responseCanceled);

        simplyAwait(1);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не остался в статусе Доставлен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2189)
    @Test(description = "Повторная отправка in_work негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkRepeatDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2189)
    @Test(description = "Повторная отправка in_work негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkRepeatDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2190)
    @Test(description = "In_work после ready_for_delivery негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkAfterReadyForDelivery() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2190)
    @Test(description = "In_work после ready_for_delivery негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkAfterReadyForDeliveryDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @CaseId(2191)
    @Test(description = "In_work после доставки негатив.",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkAfterDeliveredDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не остался в статусе Доставлен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2192)
    @Test(description = "In_work после отмены негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkAfterCancelOrderDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseInWork = POST(bodyInWork);
        checkStatusCode422(responseInWork);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2192)
    @Test(description = "In_work после отмены негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderInWorkAfterCancelOrderDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseInWork = POST(bodyInWork);
        checkStatusCode422(responseInWork);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2186)
    @Test(description = "Повторная отправка ready_for_delivery негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryRepeatDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseReadyForDeliveryRepeat = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDeliveryRepeat);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2186)
    @Test(description = "Повторная отправка ready_for_delivery негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryRepeatDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseReadyForDeliveryRepeat = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDeliveryRepeat);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2187)
    @Test(description = "Ready_for_delivery после доставки негатив.",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterDeliveredDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);
        Response responseReadyForDeliveryRepeat = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDeliveryRepeat);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не остался в статусе Доставлен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2992)
    @Test(description = "Ready_for_delivery после оформления заказа негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterCreateOrderDeliveryBySbermarket() {
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        //checkStatusCode422(responseReadyForDeliveryRepeat); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2992)
    @Test(description = "Ready_for_delivery после оформления заказа негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterCreateOrderDeliveryByRetailer() {
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        //checkStatusCode422(responseReadyForDeliveryRepeat); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Cоздан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2188)
    @Test(description = "Ready_for_delivery после отмены негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterCancelOrderDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        //checkStatusCode422(responseReadyForDelivery); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2188)
    @Test(description = "Ready_for_delivery после отмены негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterCancelOrderDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        //checkStatusCode422(responseReadyForDelivery); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2993)
    @Test(description = "Delivered после создания негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredAfterCreateDeliveryByRetailer() {
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode422(responseDelivered);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не остались в статусе Ожидают сборки");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2994)
    @Test(description = "Delivered после in_work негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredAfterInWorkDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode422(responseDelivered);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не остались в статусе Ожидают сборки");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2710)
    @Test(description = "Валидация in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2710)
    @Test(description = "Валидация in_work (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3035)
    @Test(description = "Валидация in_work + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkAllDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3035)
    @Test(description = "Валидация in_work + необязательные поля (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkAllDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2716)
    @Test(description = "Валидация canceled (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCanceledDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2716)
    @Test(description = "Валидация canceled (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCanceledDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3036)
    @Test(description = "Валидация canceled + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCanceledAllDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3036)
    @Test(description = "Валидация canceled + необязательные поля (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCancelAllDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2712)
    @Test(description = "Валидация ready_for_delivery (без changed) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2712)
    @Test(description = "Валидация ready_for_delivery (без changed) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3041)
    @Test(description = "Валидация ready_for_delivery (changed true) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryTrueDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3041)
    @Test(description = "Валидация ready_for_delivery (changed true) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryTrueDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3042)
    @Test(description = "Валидация ready_for_delivery (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3042)
    @Test(description = "Валидация ready_for_delivery (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3045)
    @Test(description = "Валидация ready_for_delivery + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryAllDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3045)
    @Test(description = "Валидация ready_for_delivery + необязательные поля (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryAllDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        simplyAwait(3);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не перешел в статус Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2714)
    @Test(description = "Валидация delivered (без changed) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }
    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3046)
    @Test(description = "Валидация delivered (changed true) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredTrueDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3047)
    @Test(description = "Валидация delivered (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredFalseDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3050)
    @Test(description = "Валидация delivered + необязательные поля (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredAllDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode200(responseDelivered);

        simplyAwait(2);
        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPED.getStatus(), "Заказ не перешел в статус Доставлен");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(2832)
    @Test(description = "Валидация delivering (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveringDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode200(responseDelivering);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPING.getStatus(), "Заказ не перешел в статус Доставляется");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(3037)
    @Test(description = "Валидация delivering + необязательные поля (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveringAllDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .customer(Customer.builder()
                                                .name("name")
                                                .phone("+77777777777")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id(retailerSku)
                                                .originalQuantity(quantity)
                                                .quantity(quantity)
                                                .price("1000")
                                                .discountPrice("1000")
                                                .replacedByID("")
                                                .weight("1000")
                                                .totalPrice("1000")
                                                .totalDiscountPrice("1000")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("1000")
                                                .discountTotalPrice("1000")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode200(responseDelivering);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.SHIPPING.getStatus(), "Заказ не перешел в статус Доставляется");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2711)
    @Test(description = "Валидация in_work негатив (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkNegativeDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode422(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2711)
    @Test(description = "Валидация in_work негатив (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationInWorkNegativeDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode422(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2717)
    @Test(description = "Валидация canceled негатив (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCanceledNegativeDeliveryBySbermarket() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode422(responseCanceled);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2717)
    @Test(description = "Валидация canceled негатив (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationCanceledNegativeDeliveryByRetailer() {
        var bodyCanceled = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .build())
                .build();

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode422(responseCanceled);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOrderIdDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOrderIdDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOriginalOrderIdDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOriginalOrderIdDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutChangedDeliveryBySbermarket() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryBySbermarket.getShipments().get(0).getNumber())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3044)
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationReadyForDeliveryFalseWithoutChangedDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2833)
    @Test(description = "Валидация delivering негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveringNegativeDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivering = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode422(responseDelivering);

        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не осталась в статусе Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3049)
    @Test(description = "Валидация delivered негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredFalseWithoutOrderIdDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode422(responseDelivered);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не осталась в статусе Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3049)
    @Test(description = "Валидация delivered негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredFalseWithoutOriginalOrderIdDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode422(responseDelivered);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не осталась в статусе Собран");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(3049)
    @Test(description = "Валидация delivered негатив. (changed false) (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void validationDeliveredFalseWithoutChangedDeliveryByRetailer() {
        var bodyInWork = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .build())
                        .build())
                .build();
        var bodyReadyForDelivery = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
        var bodyDelivered = Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                .order(Order.builder()
                                        .originalOrderId(orderDeliveryByRetailer.getShipments().get(0).getNumber())
                                        .build())
                                .build())
                        .build())
                .build();

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(bodyDelivered);
        checkStatusCode422(responseDelivered);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(readyOrder.getShipmentState(), OrderStatusV2.READY_TO_SHIP.getStatus(), "Заказ не остался в статусе Готов к доставке");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не осталась в статусе Собран");
    }

}
