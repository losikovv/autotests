package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.enums.v3.IntegrationTypeV3;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AssemblyItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.WaitHelper.withRetriesAsserted;
import static ru.instamart.api.request.v3.NotificationsV3Request.*;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsBySbermarketV3Test extends RestBase {
    private OrderV2 orderDeliveryBySbermarket;
    private final Integer sidDeliveryBySbermarket = 58;
    private final String uuidDeliveryBySbermarket = "adaa359e-6c53-462a-928d-307317e399b1";

    @BeforeClass(alwaysRun = true)
    public void preconditionsBeforeClass() {
        StoreConfigsDao.INSTANCE.updateOrdersApiIntegrationType(sidDeliveryBySbermarket.longValue(), IntegrationTypeV3.DELIVERY_BY_SBERMARKET.getValue());
        //admin.auth();
        //admin.authApi();
        //admin.editStore(uuidDeliveryBySbermarket, StoresAdminRequest.getStoreLentaOrekhoviyBulvar());
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditionsBeforeMethod() {
        SessionFactory.makeSession(SessionType.API_V2);
        orderDeliveryBySbermarket = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), sidDeliveryBySbermarket);
    }

    @Story("Позитивные тесты")
    @TmsLink("1461")
    @Test(description = "Canceled после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void canceledOrder() {
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Story("Позитивные тесты")
    @TmsLink("1462")
    @Test(description = "Canceled после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void canceledOrderInWork() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Story("Позитивные тесты")
    @TmsLink("1464")
    @Test(description = "Canceled после order.ready_for_delivery (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void canceledOrderReadyForDelivery() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Story("Позитивные тесты")
    @TmsLink("1466")
    @Test(description = "In_work после создания (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderInWork() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Story("Позитивные тесты")
    @TmsLink("1468")
    @Test(description = "Ready_for_delivery после order.in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderReadyForDelivery() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @TmsLink("2835")
    @Test(description = "Ошибка 405 Delivering (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void orderDelivering() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyDelivering = apiV3Notifications.bodyDelivering(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseDelivering = POST(bodyDelivering);
        checkStatusCode(responseDelivering, 405);

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @TmsLink("2601")
    @Test(description = "Canceled после отмены (Сборка ритейлера, доставка Сбермаркета)",
            groups = {"api-instamart-smoke", "api-v3"})
    public void cancelAfterCancel() {
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseRepeatCanceled = POST(bodyCanceled);
        checkStatusCode422(responseRepeatCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Story("Негативные тесты")
    @TmsLink("2189")
    @Test(description = "Повторная отправка in_work негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderInWorkRepeat() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не остался в статусе Собирается");
    }

    @Story("Негативные тесты")
    @TmsLink("2190")
    @Test(description = "In_work после ready_for_delivery негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderInWorkAfterReadyForDelivery() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseInWorkRepeat = POST(bodyInWork);
        checkStatusCode422(responseInWorkRepeat);

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не остался в статусе Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @TmsLink("2192")
    @Test(description = "In_work после отмены негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderInWorkAfterCancel() {
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseInWork = POST(bodyInWork);
        checkStatusCode422(responseInWork);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Story("Негативные тесты")
    @TmsLink("2186")
    @Test(description = "Повторная отправка ready_for_delivery негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderReadyForDeliveryRepeat() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);
        Response responseReadyForDeliveryRepeat = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDeliveryRepeat);

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @TmsLink("2992")
    @Test(description = "Ready_for_delivery после оформления заказа негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderReadyForDeliveryAfterCreate() {
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.READY.getStatus(), "Заказ не остался в статусе Создан");
    }

    @Story("Негативные тесты")
    @TmsLink("2188")
    @Test(description = "Ready_for_delivery после отмены негатив. (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void orderReadyForDeliveryAfterCancel() {
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode422(responseReadyForDelivery);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не остался в статусе Отменен");
    }

    @Story("Позитивные тесты")
    @TmsLink("2710")
    @Test(description = "Валидация in_work (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationInWork() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);

        OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(collectingOrder.getShipmentState(), OrderStatusV2.COLLECTING.getStatus(), "Заказ не перешел в статус Собирается");
    }

    @Story("Позитивные тесты")
    @TmsLink("3035")
    @Test(description = "Валидация in_work + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationInWorkAll() {
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

    @Story("Позитивные тесты")
    @TmsLink("2716")
    @Test(description = "Валидация canceled (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationCanceled() {
        var bodyCanceled = apiV3Notifications.bodyCanceled(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseCanceled = POST(bodyCanceled);
        checkStatusCode200(responseCanceled);

        OrderV2 canceledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
        Assert.assertEquals(canceledOrder.getShipmentState(), OrderStatusV2.CANCELED.getStatus(), "Заказ не перешел в статус Отменен");
    }

    @Story("Позитивные тесты")
    @TmsLink("3036")
    @Test(description = "Валидация canceled + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationCanceledAll() {
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

    @Story("Позитивные тесты")
    @TmsLink("2712")
    @Test(description = "Валидация ready_for_delivery (без changed) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDelivery() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
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

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Позитивные тесты")
    @TmsLink("3042")
    @Test(description = "Валидация ready_for_delivery (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDeliveryFalse() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
        var bodyReadyForDelivery = apiV3Notifications.bodyReadyForDelivery(orderDeliveryBySbermarket.getShipments().get(0).getNumber());

        Response responseInWork = POST(bodyInWork);
        checkStatusCode200(responseInWork);
        Response responseReadyForDelivery = POST(bodyReadyForDelivery);
        checkStatusCode200(responseReadyForDelivery);

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Позитивные тесты")
    @TmsLink("3045")
    @Test(description = "Валидация ready_for_delivery + необязательные поля (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDeliveryAll() {
        String retailerSku = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = orderDeliveryBySbermarket.getShipments().get(0).getLineItems().get(0).getPacks();
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
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

        withRetriesAsserted(() -> {
            OrderV2 assembledOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(assembledOrder.getShipmentState(),
                    OrderStatusV2.READY_TO_SHIP.getStatus(),
                    "Заказ не перешел в статус Готов к доставке");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.ASSEMBLED.getValue(), "Позиция не перешла в статус Собран");
    }

    @Story("Негативные тесты")
    @TmsLink("2711")
    @Test(description = "Валидация in_work негатив (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationInWorkNegative() {
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

    @Story("Негативные тесты")
    @TmsLink("2717")
    @Test(description = "Валидация canceled негатив (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationCanceledNegative() {
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

    @Story("Негативные тесты")
    @TmsLink("3044")
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOrderId() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
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

        withRetriesAsserted(() -> {
            OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(collectingOrder.getShipmentState(),
                    OrderStatusV2.COLLECTING.getStatus(),
                    "Заказ не остался в статусе Собирается");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Story("Негативные тесты")
    @TmsLink("3044")
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDeliveryFalseWithoutOriginalOrderId() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
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

        withRetriesAsserted(() -> {
            OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(collectingOrder.getShipmentState(),
                    OrderStatusV2.COLLECTING.getStatus(),
                    "Заказ не остался в статусе Собирается");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

    @Story("Негативные тесты")
    @TmsLink("3044")
    @Test(description = "Валидация ready_for_delivery негатив. (changed false) (Сборка ритейлера, доставка Сбермаркета)",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void validationReadyForDeliveryFalseWithoutChanged() {
        var bodyInWork = apiV3Notifications.bodyInWork(orderDeliveryBySbermarket.getShipments().get(0).getNumber());
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

        withRetriesAsserted(() -> {
            OrderV2 collectingOrder = apiV2.getOrder(orderDeliveryBySbermarket.getNumber());
            Assert.assertEquals(collectingOrder.getShipmentState(),
                    OrderStatusV2.COLLECTING.getStatus(),
                    "Заказ не остался в статусе Собирается");
        }, 30);
        AssemblyItemV2 assemblyItem = apiV2.getAssemblyItems(orderDeliveryBySbermarket.getShipments().get(0).getNumber()).get(0);
        Assert.assertEquals(assemblyItem.getState(), StateV2.PENDING.getValue(), "Позиция не осталась в статусе Ожидает");
    }

}
