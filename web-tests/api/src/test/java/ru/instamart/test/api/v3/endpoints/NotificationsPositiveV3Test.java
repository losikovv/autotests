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

//    @CaseId(1461)
//    @Test(  description = "Canceled после создания",
//            groups = {"api-instamart-regress", "api-v3"})
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

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1461)
    @Test(description = "Canceled после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelOrderDeliveryBySbermarket() {
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1461)
    @Test(description = "Canceled после создания (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelOrderDeliveryByRetailer() {
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1462)
    @Test(description = "Canceled после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderInWorkDeliveryBySbermarket() {
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1462)
    @Test(description = "Canceled после order.in_work (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderInWorkDeliveryByRetailer() {
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1463)
    @Test(description = "Canceled после order.assembled (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderAssembledDeliveryBySbermarket() {
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1463)
    @Test(description = "Canceled после order.assembled (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderAssembledDeliveryByRetailer() {
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1464)
    @Test(description = "Canceled после order.ready_for_delivery (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderReadyForDeliveryDeliveryBySbermarket() {
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.READY_FOR_DELIVERY.getValue());
        checkStatusCode200(responseReadyForDelivery);
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1464)
    @Test(description = "Canceled после order.ready_for_delivery (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void cancelOrderReadyForDeliveryDeliveryByRetailer() {
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.READY_FOR_DELIVERY.getValue());
        checkStatusCode200(responseReadyForDelivery);
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);

        simplyAwait(3);
        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1466)
    @Test(description = "In_work после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderInWorkDeliveryBySbermarket() {
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1467)
    @Test(description = "Assembled после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderAssembledDeliveryBySbermarket() {
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1467)
    @Test(description = "Assembled после order.in_work (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderAssembledDeliveryByRetailer() {
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseAssembled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.ASSEMBLED.getValue());
        checkStatusCode200(responseAssembled);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Позитивные тесты")
    @CaseId(1468)
    @Test(description = "Ready_for_delivery после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderReadyForDeliveryDeliveryBySbermarket() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        Response responseDelivering = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.DELIVERING.getValue());
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.DELIVERING.getValue());
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivering = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.DELIVERING.getValue());
        checkStatusCode200(responseDelivering);
        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseRepeatCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
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
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseRepeatCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseDelivered);
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
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
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseInWorkRepeat = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseInWorkRepeat = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        Response responseInWorkRepeat = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        Response responseInWorkRepeat = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseDelivered);
        Response responseInWorkRepeat = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
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
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        Response responseReadyForDeliveryRepeat = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        Response responseReadyForDeliveryRepeat = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReadyForDelivery);
        simplyAwait(3);
        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseDelivered);
        Response responseReadyForDeliveryRepeat = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseReadyForDelivery = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseReadyForDelivery = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
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
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseCanceled = POST(orderDeliveryBySbermarket.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseReadyForDeliveryRepeat = POST(
                orderDeliveryBySbermarket.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        //checkStatusCode422(responseReadyForDeliveryRepeat); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2188)
    @Test(description = "Ready_for_delivery после отмены негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-regress", "api-v3"})
    public void orderReadyForDeliveryAfterCancelOrderDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseCanceled = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.CANCELED.getValue());
        checkStatusCode200(responseCanceled);
        Response responseReadyForDeliveryRepeat = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        //checkStatusCode422(responseReadyForDeliveryRepeat); --ждем фикс на проде

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2993)
    @Test(description = "Delivered после создания негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredAfterCreateDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode422(responseDelivered);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не остались в статусе Ожидают сборки");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Негативные тесты")
    @CaseId(2994)
    @Test(description = "Delivered после создания негатив. (Сборка и доставка ритейлером)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDeliveredAfterInWorkDeliveryByRetailer() {
        String retailerSku = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryByRetailer.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = POST(orderDeliveryByRetailer.getShipments().get(0).getNumber(), NotificationTypeV3.IN_WORK.getValue());
        checkStatusCode200(responseInWork);

        Response responseDelivered = POST(
                orderDeliveryByRetailer.getShipments().get(0).getNumber(),
                NotificationTypeV3.DELIVERED.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode422(responseDelivered);

        OrderV2 shippedOrder = apiV2.getOrder(orderDeliveryByRetailer.getNumber());
        Assert.assertEquals(shippedOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");

        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryByRetailer.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не остались в статусе Ожидают сборки");
    }

    @Test(groups = {})
    public void notificationsAllFields() {
        var body = Notifications.builder()
                .event(Event.builder()
                        .type("order.ready_for_delivery")
                        .payload(Payload.builder()
                                .orderId("H39014497241")
                                .order(Order.builder()
                                        .originalOrderId("H39014497241")
                                        .customer(Customer.builder()
                                                .name("1")
                                                .phone("+79991111111")
                                                .build())
                                        .delivery(Delivery.builder()
                                                .expectedFrom("2021-05-24T13:00:00+03:00")
                                                .expectedTo("2021-05-24T13:00:00+03:00")
                                                .build())
                                        .changed(true)
                                        .position(Position.builder()
                                                .id("75125")
                                                .originalQuantity(2)
                                                .quantity(2)
                                                .price("123")
                                                .discountPrice("12")
                                                .replacedByID("")
                                                .weight("012")
                                                .totalPrice("321")
                                                .totalDiscountPrice("32")
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("789")
                                                        .build())
                                                .markingCodeItem(MarkingCode.builder()
                                                        .value("987")
                                                        .build())
                                                .build())
                                        .total(Total.builder()
                                                .totalPrice("4646")
                                                .discountTotalPrice("4949")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        Response response = POST(body);
        response.prettyPeek();
    }

    @Test(groups = {})
    public void notificationsShortFields() {
        var body = Notifications.builder()
                .event(Event.builder()
                        .type("order.ready_for_delivery")
                        .payload(Payload.builder()
                                .orderId("H39014497241")
                                .build())
                        .build())
                .build();

        Response response = POST(body);
        response.prettyPeek();
    }
}
