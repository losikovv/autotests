package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.PaymentToolsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.PaymentToolsV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.*;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorTextIsNotEmpty;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;
import static ru.instamart.api.common.RestStaticTestData.*;

@Epic("ApiV2")
@Feature("Заказы")
public class OrdersV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        OrdersV2Request.POST();
    }

    @CaseId(4)
    @Test(description = "Получаем заказы",
            groups = {"api-instamart-smoke", "MRAutoCheck", "api-instamart-prod"})
    public void getOrders() {
        response = OrdersV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OrdersV2Response.class).getOrders(), "Не вернулись заказы");
    }

    @CaseId(5)
    @Test(description = "Получаем текущий заказ",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getCurrentOrder() {
        response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order, "Не вернулся текущий заказ");
    }

    @CaseId(9)
    @Test(description = "Получаем заказ",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOrder() {
        response = OrdersV2Request.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(OrderV2Response.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @CaseId(19)
    @Test(description = "Получаем заказы для оценки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getUnratedOrders() {
        response = OrdersV2Request.Unrated.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OrdersV2Response.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @CaseId(16)
    @Test(description = "Получаем товары в заказе",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOrderLineItems() {
        response = OrdersV2Request.LineItems.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(LineItemsV2Response.class).getLineItems(), "Не вернулись товары заказа");
    }

    @CaseId(313)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-smoke"},
            description = "Существующий id")
    public void orderWithPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

//        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
//        checkStatusCode200(response);
//        order = response.as(OrderV2Response.class).getOrder();
//        assertTrue(order.getPromotionCodes().isEmpty(), "Промокод не удалился");
    }

    @CaseId(314)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий id")
    public void orderWithInvalidPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "failCode");
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Не вернулась ошибки");
    }

    @CaseId(315)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Существующий id")
    public void deletePromoCodeForOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        order = response.as(OrderV2Response.class).getOrder();
        assertTrue(order.getPromotionCodes().isEmpty(), "Промокод не удалился");

    }

    @CaseId(316)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий id заказа")
    public void deletePromoCodeForInvalidOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE("failOrder", "auto300lomxs4");
        checkStatusCode404(response);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Промокод удалился");
    }

    @CaseId(318)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий promoCode")
    public void deleteInvalidPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "failCode");
        checkStatusCode404(response);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Промокод удалился");
    }

    @CaseId(319)
    @Story("Получение списка позиций по заказу")
    @Test( groups = {"api-instamart-regress"},
            description = "Получение списка позиций по заказу. Существующий id")
    public void getingListOfItemsForOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        response = OrdersV2Request.LineItems.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        LineItemsV2Response lineItemsV2Response = response.as(LineItemsV2Response.class);
        final SoftAssert asserts = new SoftAssert();
        asserts.assertEquals(lineItemsV2Response.getMeta().getCurrentPage(), Integer.valueOf(1), "current_page не равен 1");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalPages(), Integer.valueOf(1), "total_page не равен 2");
        asserts.assertEquals(lineItemsV2Response.getMeta().getPerPage(), Integer.valueOf(20), "get_page не равен 20");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalCount(), Integer.valueOf(1), "total_count не равен 1");
        asserts.assertFalse(lineItemsV2Response.getLineItems().isEmpty(), "В заказе нет позиций");
        asserts.assertAll();
    }

    @CaseId(320)
    @Story("Получение списка позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий id")
    public void retrievingListOfItemsForOrderForNonExistentId() {
        response = OrdersV2Request.LineItems.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(321)
    @Story("Получение line_items для shipments")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение line_items для shipments с существующим id")
    public void getShipmentLineItems200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        response = ShipmentsV2Request.LineItems.GET(apiV2.getShipmentsNumber());
        assertNotNull(response.as(LineItemsV2Response.class).getLineItems(), "Не вернулись товары заказа");
    }

    @CaseId(322)
    @Story("Получение line_items для shipments")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение line_items для shipments для заказа с несуществующим id")
    public void getShipmentLineItems404() {
        response = ShipmentsV2Request.LineItems.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @CaseId(323)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по заказу с существующим id")
    public void getLineItemCancellations200() {
        apiV2.order(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        response = OrdersV2Request.LineItemCancellations.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertTrue(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Невалидная ошибка");

    }

    @CaseId(324)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по заказу с несуществующим id")
    public void getLineItemCancellations404() {
        response = OrdersV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(326)
    @Story("Получение списка отмененных позиций по подзаказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по подзаказу с несуществующим id")
    public void getShipmentLineItem404() {
        response = ShipmentsV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @CaseId(328)
    @Story("Получение списка замененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по заказу с несуществующим id")
    public void getOrdersLineItemReplacements404() {
        response = OrdersV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(330)
    @Story("Получение списка замененных позиций по подзаказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по подзаказу c несуществующим id")
    public void getShipmentLineItemReplacements404() {
        response = ShipmentsV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }

    @CaseId(331)
    @Story("Добавление позиции к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление позиции к заказу с обязательными полями")
    public void setLineItems200() {
        List<ProductV2> products = apiV2.getProductFromEachDepartmentInStore(EnvironmentProperties.DEFAULT_SID);
        Long product = products.get(0).getId();

        response = LineItemsV2Request.POST(product, 1, apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(LineItemV2Response.class).getLineItem(), "Не вернулись товары");

    }

    @CaseId(332)
    @Story("Добавление позиции к заказу")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "ordersLineItems",
            dataProviderClass = RestDataProvider.class,
            description = "Добавление позиции к заказу с невалидными данными")
    public void setLineItems404(long productId, int quantity, String orderNumber) {
        response = LineItemsV2Request.POST(productId, quantity, orderNumber);
        checkStatusGroup400(response);
        errorTextIsNotEmpty(response);
    }

    @CaseId(333)
    @Story("Редактирование позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование позиции заказа с существующим id")
    public void changeLineItems200() {
        List<LineItemV2> cart = apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        Integer productId = cart.get(0).getId();
        Integer internalAmount = cart.get(0).getQuantity();
        response = LineItemsV2Request.PUT(productId, 100);
        checkStatusCode200(response);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(response.as(LineItemV2Response.class).getLineItem().getQuantity(), internalAmount, "Начальное количество не отличается от измененного");
        softAssert.assertEquals(response.as(LineItemV2Response.class).getLineItem().getQuantity().toString(), "100", "Начальное количество не отличается от измененного");
        softAssert.assertAll();
    }

    @CaseId(334)
    @Story("Редактирование позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "changeLineItems",
            dataProviderClass = RestDataProvider.class,
            description = "Редактирование позиции заказа с несуществующим id")
    public void changeLineItems404(long productId, int qty) {
        response = LineItemsV2Request.PUT(productId, qty);
        checkStatusGroup400(response);
        errorTextIsNotEmpty(response);
    }

    @CaseId(335)
    @Story("Удаление позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "")
    public void deleteLineItems200() {
        Integer productId = apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        ).get(0).getId();

        response = LineItemsV2Request.DELETE(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(LineItemV2Response.class).getLineItem(), "Не вернулись товары");
    }

    @CaseId(336)
    @Story("Удаление позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "")
    public void deleteLineItems404() {
        response = LineItemsV2Request.DELETE(0);
        checkStatusGroup400(response);
        errorAssert(response, "Позиция не существует");
    }

    @CaseId(337)
    @Story("Заполнение информации о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Заполнение информации о заказе с существующим id")
    public void fillingInOrderInformation200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        Integer paymentsId = apiV2.getPaymentTools().get(0).getId();
        Integer shipmentId = apiV2.getShippingWithOrder().getId();
        Integer deliveryWindow = apiV2.getAvailableDeliveryWindow().getId();
        String orderNumber = apiV2.getCurrentOrderNumber();

        Response response = OrdersV2Request.PUT(1, "", "", paymentsId, shipmentId, deliveryWindow, 0, orderNumber);
        checkStatusCode200(response);

        OrderV2Response order = response.as(OrderV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(order.getOrder().getReplacementPolicy().getId().toString(), "1", "Код замены неверен");
        softAssert.assertEquals(order.getOrder().getReplacementPolicy().getDescription(), "Позвонить мне. Подобрать замену, если не смогу ответить", "Описание замены неверно указанному");
        softAssert.assertEquals(order.getOrder().getShipments().get(0).getId(), shipmentId, "Id достаки неверный");
        softAssert.assertEquals(order.getOrder().getShipments().get(0).getDeliveryWindow().getId(), deliveryWindow, "Id окна достаки неверен");
        softAssert.assertEquals(order.getOrder().getNumber(), orderNumber, "orderNumber неверен");
        softAssert.assertAll();
    }

    @CaseId(338)
    @Story("Заполнение информации о заказе")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "fillingInOrderInformationDp",
            dataProviderClass = RestDataProvider.class,
            description = "Заполнение информации о заказе с несуществующим id")
    public void fillingInOrderInformation404(int replacementPolicyId,
                                             String phoneNumber,
                                             String instructions,
                                             int paymentToolId,
                                             int shipmentId,
                                             int deliveryWindowId,
                                             int shipmentMethodId,
                                             String orderNumber) {
        response = OrdersV2Request.PUT(replacementPolicyId,
                phoneNumber, instructions, paymentToolId,
                shipmentId, deliveryWindowId, shipmentMethodId,
                orderNumber
        );
        checkStatusCode404(response);
    }

    @CaseId(341)
    @Story("Отмена заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Отмена заказа с существующим номером заказа")
    public void cancellationsOrders200() {
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentProperties.DEFAULT_SID);
        String orderNumber = order.getNumber();
        response = OrdersV2Request.Cancellations.POST(orderNumber, "test");
        checkStatusCode200(response);
        CancellationsV2Response cancellationsV2Response = response.as(CancellationsV2Response.class);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cancellationsV2Response.getCancellation().getReason(), "test", "Причина отмены отличная от исходной");
        softAssert.assertEquals(cancellationsV2Response.getCancellation().getOrder().getNumber(), orderNumber, "Номер заказа неверный");
        softAssert.assertAll();

    }

    @CaseId(342)
    @Story("Отмена заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Отмена заказа с несуществующим номером заказа")
    public void cancellationsOrders404() {
        response = OrdersV2Request.Cancellations.POST("failedOrderNumber", "test");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(682)
    @Story("Получить способы оплаты")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить способы оплаты")
    public void getPaymentMethods() {
        response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        PaymentToolsV2Response paymentToolsV2Response = response.as(PaymentToolsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        paymentToolsV2Response.getPaymentTools().stream()
                .forEach(value -> softAssert.assertNotNull(PaymentToolsV2.getIfKeyIsPresent(value.getType()), "Способ оплаты пустой"));
        paymentToolsV2Response.getPaymentTools().stream()
                .forEach(value -> softAssert.assertNotNull(PaymentToolsV2.getIfNameIsPresent(value.getName()), "Способ оплаты пустой"));
        softAssert.assertAll();

    }

    @CaseId(343)
    @Story("Завершение заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Завершение заказа с существующим id")
    public void orderCompletion200() {
        String orderNumber = apiV2.getCurrentOrderNumber();
        apiV2.fillingCartAndOrderAttributesWithoutCompletition(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);

        String shipmentNumber = apiV2.getShipmentsNumber();

        response = OrdersV2Request.Completion.POST(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        OrderV2Response order = response.as(OrderV2Response.class);
        final SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(order.getOrder().getNumber(), orderNumber, "Error order number");
        softAssert.assertEquals(order.getOrder().getShipments().get(0).getNumber(), shipmentNumber, "Error shipments number");

        softAssert.assertEquals(order.getOrder().getAddress().getFullAddress(), userFullAddress, "Адрес отличается от заполненного");
        softAssert.assertEquals(order.getOrder().getAddress().getCity(), userCity, "Город отличается от заполненного");
        softAssert.assertEquals(order.getOrder().getAddress().getPhone(), userPhone, "Номер телефона отличается от заполненного");
        softAssert.assertEquals(order.getOrder().getAddress().getStreet(), userStreet, "Улица отличается от заполненного");
        softAssert.assertEquals(order.getOrder().getAddress().getBuilding(), userBuilding, "Дом отличается от заполненного");
        softAssert.assertEquals(order.getOrder().getAddress().getLat().toString(), userLat, "Координаты отличаются");
        softAssert.assertEquals(order.getOrder().getAddress().getLon().toString(), userLon, "Координаты отличаются");
        softAssert.assertFalse(order.getOrder().getAddress().getDeliveryToDoor(), "delivery_to_door is true");

        softAssert.assertEquals(order.getOrder().getPayment().getState(), "checkout", "Оплата отличается от выбранного");
        softAssert.assertEquals(order.getOrder().getReplacementPolicy().getId().toString(), "1", "Код замены товара отличается");
        softAssert.assertEquals(order.getOrder().getReplacementPolicy().getDescription(), "Позвонить мне. Подобрать замену, если не смогу ответить", "Описание замены товара отличается");
        softAssert.assertAll();
    }

    @CaseId(344)
    @Story("Завершение заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Завершение заказа с несуществующим id")
    public void orderCompletion404() {
        response = OrdersV2Request.Completion.POST("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(345)
    @Story("Очистка заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Очистка заказа с существующим id")
    public void clearOrder200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getCurrentOrderNumber();
        response = OrdersV2Request.Shipments.DELETE(orderNumber);
        OrderV2Response orderV2Response = response.as(OrderV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(orderV2Response.getOrder().getNumber(), orderNumber, "Номер заказа отдичается");
        softAssert.assertEquals(orderV2Response.getOrder().getTotal().toString(), "0.0", "total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemCount().toString(), "0", "item_count не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemTotal().toString(), "0.0", "item_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemDiscountTotal().toString(), "0.0", "item_discount_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getShipTotal().toString(), "0.0", "ship_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getAdjustmentTotal().toString(), "0.0", "adjustment_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getPromoTotal().toString(), "0.0", "promo_total не равен 0");
        softAssert.assertTrue(orderV2Response.getOrder().getShipments().isEmpty(), "Доставка не удалилась");
        softAssert.assertAll();
    }

    @CaseId(346)
    @Story("Очистка заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Очистка заказа с несуществующим id")
    public void clearOrder404() {
        response = OrdersV2Request.Shipments.DELETE("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }

    @CaseId(347)
    @Story("Очистка подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Очистка подзаказа с существующим id")
    public void clearShipments200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String shipmentNumber = apiV2.getShipmentsNumber();
        String orderNumber = apiV2.getCurrentOrderNumber();
        response = ShipmentsV2Request.DELETE(shipmentNumber);

        checkStatusCode200(response);
        OrderV2Response orderV2Response = response.as(OrderV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(orderV2Response.getOrder().getNumber(), orderNumber, "Номер заказа отдичается");
        softAssert.assertEquals(orderV2Response.getOrder().getTotal().toString(), "0.0", "total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemCount().toString(), "0", "item_count не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemTotal().toString(), "0.0", "item_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getItemDiscountTotal().toString(), "0.0", "item_discount_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getShipTotal().toString(), "0.0", "ship_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getAdjustmentTotal().toString(), "0.0", "adjustment_total не равен 0");
        softAssert.assertEquals(orderV2Response.getOrder().getPromoTotal().toString(), "0.0", "promo_total не равен 0");
        softAssert.assertTrue(orderV2Response.getOrder().getShipments().isEmpty(), "Доставка не удалилась");
        softAssert.assertAll();
    }

    @CaseId(348)
    @Story("Очистка подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Очистка подзаказа с несуществующим id")
    public void clearShipments404() {
        response = ShipmentsV2Request.DELETE("failedNumber");
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }
}
