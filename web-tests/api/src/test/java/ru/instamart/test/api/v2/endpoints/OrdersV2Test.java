package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.*;
import ru.instamart.jdbc.dao.UserIdDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.common.RestStaticTestData.*;
import static ru.instamart.api.helper.K8sHelper.execRakeTaskAddBonus;
import static ru.instamart.api.helper.PromotionCode.getExpiredPromotionCode;
import static ru.instamart.api.helper.PromotionCode.getPromotionCode;

@Epic("ApiV2")
@Feature("Заказы (orders)")
public class OrdersV2Test extends RestBase {

    private String promoCode;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        OrdersV2Request.POST();
        promoCode = getPromotionCode();
    }

    @CaseId(1419)
    @Story("Получение заказов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получаем заказы")
    public void getOrders() {
        final Response response = OrdersV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrdersV2Response.class);
    }

    @Deprecated
    @Test(description = "Получаем заказ",
            groups = {})
    public void getOrder() {
        response = OrdersV2Request.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(OrderV2Response.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @Deprecated
    @Test(description = "Получаем заказы для оценки",
            groups = {})
    public void getUnratedOrders() {
        response = OrdersV2Request.Unrated.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OrdersV2Response.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @Deprecated
    @Test(description = "Получаем товары в заказе",
            groups = {})
    public void getOrderLineItems() {
        response = OrdersV2Request.LineItems.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertNotNull(response.as(LineItemsV2Response.class).getLineItems(), "Не вернулись товары заказа");
    }

    @CaseId(313) //TODO:добавить caseId 838 после выполнения задачи ATST-847
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-smoke"},
            description = "Существующий id")
    public void orderWithPromoCode() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), promoCode);
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        checkFieldIsNotEmpty(order.getPromotionCodes(), "промокоды");

//        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), promoCode);
//        checkStatusCode200(response);
//        order = response.as(OrderV2Response.class).getOrder();
//        assertTrue(order.getPromotionCodes().isEmpty(), "Промокод не удалился");
    }

    @CaseId(314)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий номер заказа")
    public void orderWithPromoCodeAndInvalidOrderNumber() {
        final Response response = OrdersV2Request.Promotions.POST("failedOrder", promoCode);
        checkError(response, "Заказ не существует");
    }

    @CaseId(840)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий промокод")
    public void orderWithInvalidPromoCode() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "failCode");
        checkError(response, "Промокод не существует");
    }

    @CaseId(839)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Истекший промокод")
    public void orderWithExpiredPromoCode() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        final Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), getExpiredPromotionCode());
        checkError(response, "Данный промокод истек");
    }

    @CaseId(1111)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Заказ с пустой корзиной")
    public void orderWithPromoCodeAndEmptyCart() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        String orderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();
        final Response response = OrdersV2Request.Promotions.POST(orderNumber, promoCode);
        checkError(response, "Промокод неприменим");
        SessionFactory.clearSession(SessionType.API_V2);
    }

    @CaseId(315)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Существующий id")
    public void deletePromoCodeForOrder() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), promoCode);
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        checkFieldIsNotEmpty(order.getPromotionCodes(), "промокоды");

        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), promoCode);
        checkStatusCode200(response);
        order = response.as(OrderV2Response.class).getOrder();
        assertTrue(order.getPromotionCodes().isEmpty(), "Промокод не удалился");

    }

    @CaseId(316)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий id заказа")
    public void deletePromoCodeForInvalidOrder() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), promoCode);
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE("failOrder", promoCode);
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(318)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий promoCode")
    public void deleteInvalidPromoCode() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), promoCode);
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "failCode");
        checkStatusCode404(response);
        checkError(response, "Промокод не существует");
    }

    @Deprecated
    @Story("Получение списка позиций по заказу")
    @Test(groups = {},
            description = "Получение списка позиций по заказу. Существующий id")
    public void getingListOfItemsForOrder() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
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

    @Deprecated
    @Story("Получение списка позиций по заказу")
    @Test(groups = {},
            description = "Несуществующий id")
    public void retrievingListOfItemsForOrderForNonExistentId() {
        response = OrdersV2Request.LineItems.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @Deprecated
    @Story("Получение line_items для shipments")
    @Test(groups = {},
            description = "Получение line_items для shipments с существующим id")
    public void getShipmentLineItems200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        response = ShipmentsV2Request.LineItems.GET(apiV2.getShipmentsNumber());
        assertNotNull(response.as(LineItemsV2Response.class).getLineItems(), "Не вернулись товары заказа");
    }

    @Deprecated
    @Story("Получение line_items для shipments")
    @Test(groups = {},
            description = "Получение line_items для shipments для заказа с несуществующим id")
    public void getShipmentLineItems404() {
        response = ShipmentsV2Request.LineItems.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @Deprecated
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {},
            description = "Получение списка отмененных позиций по заказу с существующим id")
    public void getLineItemCancellations200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        response = OrdersV2Request.LineItemCancellations.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        assertTrue(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty(), "Невалидная ошибка");

    }

    @Deprecated
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {},
            description = "Получение списка отмененных позиций по заказу с несуществующим id")
    public void getLineItemCancellations404() {
        response = OrdersV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @Deprecated
    @Story("Получение списка отмененных позиций по подзаказу")
    @Test(groups = {},
            description = "Получение списка отмененных позиций по подзаказу с несуществующим id")
    public void getShipmentLineItem404() {
        response = ShipmentsV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @Deprecated
    @Story("Получение списка замененных позиций по заказу")
    @Test(groups = {},
            description = "Получение списка замененных позиций по заказу с несуществующим id")
    public void getOrdersLineItemReplacements404() {
        response = OrdersV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @Deprecated
    @Story("Получение списка замененных позиций по подзаказу")
    @Test(groups = {},
            description = "Получение списка замененных позиций по подзаказу c несуществующим id")
    public void getShipmentLineItemReplacements404() {
        response = ShipmentsV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @CaseId(331)
    @Story("Добавление позиции к заказу")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Добавление позиции к заказу с обязательными полями")
    public void setLineItems200() {
        List<ProductV2> products = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID);
        ProductV2 product = products.get(0);

        final Response response = LineItemsV2Request.POST(product.getId(), 1, apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemV2Response.class);
        compareTwoObjects(response.as(LineItemV2Response.class).getLineItem().getProduct(), product);

    }

    @CaseId(332)
    @Story("Добавление позиции к заказу")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "ordersLineItems",
            dataProviderClass = RestDataProvider.class,
            description = "Добавление позиции к заказу с невалидными данными")
    public void setLineItems404(long productId, int quantity, String orderNumber) {
        final Response response = LineItemsV2Request.POST(productId, quantity, orderNumber);
        checkStatusGroup400(response);
        checkErrorTextIsNotEmpty(response);
    }

    @CaseId(333)
    @Story("Редактирование позиции заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Редактирование позиции заказа с существующим id")
    public void changeLineItems200() {
        List<LineItemV2> cart = apiV2.dropAndFillCart(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        Integer productId = cart.get(0).getId();
        Integer internalAmount = cart.get(0).getQuantity();
        final Response response = LineItemsV2Request.PUT(productId, 100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemV2Response.class);

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
        checkErrorTextIsNotEmpty(response);
    }

    @CaseId(335)
    @Story("Удаление позиции заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Успешное удаление позиции заказа")
    public void deleteLineItems200() {
        Integer productId = apiV2.dropAndFillCart(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        ).get(0).getId();

        final Response response = LineItemsV2Request.DELETE(productId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemV2Response.class);
    }

    @CaseId(336)
    @Story("Удаление позиции заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Удаление несуществующей позиции заказа")
    public void deleteLineItems404() {
        final Response response = LineItemsV2Request.DELETE(0);
        checkStatusGroup400(response);
        checkError(response, "Позиция не существует");
    }

    @CaseId(337)
    @Story("Заполнение информации о заказе")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Заполнение информации о заказе с существующим id")
    public void fillingInOrderInformation200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        Integer paymentsId = apiV2.getPaymentTools().get(0).getId();
        Integer shipmentId = apiV2.getShippingWithOrder().getId();
        Integer deliveryWindow = apiV2.getAvailableDeliveryWindow().getId();
        String orderNumber = apiV2.getCurrentOrderNumber();

        final Response response = OrdersV2Request.PUT(1, "", "", paymentsId, shipmentId, deliveryWindow, 0, orderNumber);
        checkStatusCode200(response);

        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(order.getReplacementPolicy().getId().toString(), "1", "Код замены неверен");
        softAssert.assertEquals(order.getReplacementPolicy().getDescription(), "Позвонить мне. Подобрать замену, если не смогу ответить", "Описание замены неверно указанному");
        softAssert.assertEquals(order.getShipments().get(0).getId(), shipmentId, "Id достаки неверный");
        softAssert.assertEquals(order.getShipments().get(0).getDeliveryWindow().getId(), deliveryWindow, "Id окна достаки неверен");
        softAssert.assertEquals(order.getNumber(), orderNumber, "orderNumber неверен");
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
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Отмена заказа с существующим номером заказа")
    public void cancellationsOrders200() {
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID);
        String orderNumber = order.getNumber();
        final Response response = OrdersV2Request.Cancellations.POST(orderNumber, "test");
        checkStatusCode200(response);
        CancellationsV2Response cancellationsV2Response = response.as(CancellationsV2Response.class);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cancellationsV2Response.getCancellation().getReason(), "test", "Причина отмены отличная от исходной");
        softAssert.assertEquals(cancellationsV2Response.getCancellation().getOrder().getNumber(), orderNumber, "Номер заказа неверный");
        softAssert.assertAll();

    }

    @CaseId(342)
    @Story("Отмена заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Отмена заказа с несуществующим номером заказа")
    public void cancellationsOrders404() {
        final Response response = OrdersV2Request.Cancellations.POST("failedOrderNumber", "test");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }


    @CaseId(343)
    @Story("Завершение заказа")
    @Test(groups = {"api-instamart-smoke"},
            description = "Завершение заказа с существующим id")
    public void orderCompletion200() {
        String orderNumber = apiV2.getCurrentOrderNumber();
        apiV2.fillingCartAndOrderAttributesWithoutCompletion(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);

        String shipmentNumber = apiV2.getShipmentsNumber();

        final Response response = OrdersV2Request.Completion.POST(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(order.getNumber(), orderNumber, "Error order number");
        softAssert.assertEquals(order.getShipments().get(0).getNumber(), shipmentNumber, "Error shipments number");

        AddressV2 address = order.getAddress();
        softAssert.assertEquals(address.getFullAddress(), userFullAddress, "Адрес отличается от заполненного");
        softAssert.assertEquals(address.getCity(), userCity, "Город отличается от заполненного");
        softAssert.assertEquals(address.getPhone(), userPhone, "Номер телефона отличается от заполненного");
        softAssert.assertEquals(address.getStreet(), userStreet, "Улица отличается от заполненного");
        softAssert.assertEquals(address.getBuilding(), userBuilding, "Дом отличается от заполненного");
        softAssert.assertEquals(address.getLat().toString(), userLat, "Координаты отличаются");
        softAssert.assertEquals(address.getLon().toString(), userLon, "Координаты отличаются");
        softAssert.assertFalse(address.getDeliveryToDoor(), "delivery_to_door is true");

        softAssert.assertEquals(order.getPayment().getState(), "checkout", "Оплата отличается от выбранного");
        softAssert.assertEquals(order.getReplacementPolicy().getId().toString(), "1", "Код замены товара отличается");
        softAssert.assertEquals(order.getReplacementPolicy().getDescription(), "Позвонить мне. Подобрать замену, если не смогу ответить", "Описание замены товара отличается");
        softAssert.assertAll();
    }

    @CaseId(344)
    @Story("Завершение заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Завершение заказа с несуществующим id")
    public void orderCompletion404() {
        final Response response = OrdersV2Request.Completion.POST("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @Deprecated
    @Story("Очистка заказа")
    @Test(groups = {},
            description = "Очистка заказа с существующим id")
    public void clearOrder200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getCurrentOrderNumber();
        response = OrdersV2Request.Shipments.DELETE(orderNumber);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(order.getNumber(), orderNumber, "Номер заказа отдичается");
        softAssert.assertEquals(order.getTotal().toString(), "0.0", "total не равен 0");
        softAssert.assertEquals(order.getItemCount().toString(), "0", "item_count не равен 0");
        softAssert.assertEquals(order.getItemTotal().toString(), "0.0", "item_total не равен 0");
        softAssert.assertEquals(order.getItemDiscountTotal().toString(), "0.0", "item_discount_total не равен 0");
        softAssert.assertEquals(order.getShipTotal().toString(), "0.0", "ship_total не равен 0");
        softAssert.assertEquals(order.getAdjustmentTotal().toString(), "0.0", "adjustment_total не равен 0");
        softAssert.assertEquals(order.getPromoTotal().toString(), "0.0", "promo_total не равен 0");
        softAssert.assertTrue(order.getShipments().isEmpty(), "Доставка не удалилась");
        softAssert.assertAll();
    }

    @Deprecated
    @Story("Очистка заказа")
    @Test(groups = {},
            description = "Очистка заказа с несуществующим id")
    public void clearOrder404() {
        response = OrdersV2Request.Shipments.DELETE("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(347)
    @Story("Очистка подзаказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Очистка подзаказа с существующим id")
    public void clearShipments200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String shipmentNumber = apiV2.getShipmentsNumber();
        String orderNumber = apiV2.getCurrentOrderNumber();
        final Response response = ShipmentsV2Request.DELETE(shipmentNumber);

        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(order.getNumber(), orderNumber, "Номер заказа отдичается");
        softAssert.assertEquals(order.getTotal().toString(), "0.0", "total не равен 0");
        softAssert.assertEquals(order.getItemCount().toString(), "0", "item_count не равен 0");
        softAssert.assertEquals(order.getItemTotal().toString(), "0.0", "item_total не равен 0");
        softAssert.assertEquals(order.getItemDiscountTotal().toString(), "0.0", "item_discount_total не равен 0");
        softAssert.assertEquals(order.getShipTotal().toString(), "0.0", "ship_total не равен 0");
        softAssert.assertEquals(order.getAdjustmentTotal().toString(), "0.0", "adjustment_total не равен 0");
        softAssert.assertEquals(order.getPromoTotal().toString(), "0.0", "promo_total не равен 0");
        softAssert.assertTrue(order.getShipments().isEmpty(), "Доставка не удалилась");
        softAssert.assertAll();
    }

    @CaseId(348)
    @Story("Очистка подзаказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Очистка подзаказа с несуществующим id")
    public void clearShipments404() {
        response = ShipmentsV2Request.DELETE("failedNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @CaseId(812)
    @Story("Создание нового заказа")
    @Test(groups = {"api-instamart-smoke"},
            description = "Создание нового заказа")
    public void createNewOrder() {
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = OrdersV2Request.POST();
        checkStatusCode200(response);
        OrderV2Response order = response.as(OrderV2Response.class);
        checkResponseJsonSchema(response, OrderV2Response.class);
        compareTwoObjects(order.getOrder().getTotal(), 0.0);
    }

    @CaseId(300)
    @Story("Получение данных о заказе")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение данных о заказе по номеру")
    public void getOrderByNumber() {
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2);
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        final Response response = OrdersV2Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV2Response.class);
        OrderV2 orderFromResponse = response.as(OrderV2Response.class).getOrder();
        compareTwoObjects(order, orderFromResponse);
    }

    @CaseId(301)
    @Story("Получение данных о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных о заказе по несуществующему номеру")
    public void getOrderByNonExistingNumber() {
        final Response response = OrdersV2Request.GET("failedNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(303)
    @Story("Получение текущего заказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение текущего заказа пользователем, у которого есть заказ")
    public void getCurrentOrder() {
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV2Response.class);
    }

    @CaseId(302)
    @Story("Получение текущего заказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение текущего заказа пользователем, у которого нет заказа")
    public void getNonExistingCurrentOrder() {
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode404(response);
        checkError(response, "У пользователя нет текущего заказа");
    }

    @CaseId(304)
    @Story("Мердж заказа без авторизации с текущим заказом пользователя")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Мердж существующего заказа без авторизации с текущим заказом пользователя")
    public void mergeCurrentOrderWithUnauthorizedOrder() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response responseForUnauthorizedOrder = OrdersV2Request.POST();
        checkStatusCode200(responseForUnauthorizedOrder);
        OrderV2 unauthorizedOrder = responseForUnauthorizedOrder.as(OrderV2Response.class).getOrder();
        SessionFactory.makeSession(SessionType.API_V2);

        final Response responseForCurrentOrder = OrdersV2Request.Current.PUT(unauthorizedOrder.getUuid());
        checkStatusCode200(responseForCurrentOrder);
        checkResponseJsonSchema(responseForCurrentOrder, OrderV2Response.class);
        OrderV2Response orderFromResponse = responseForCurrentOrder.as(OrderV2Response.class);
        compareTwoObjects(unauthorizedOrder, orderFromResponse.getOrder());
    }

    @CaseId(305)
    @Story("Мердж заказа без авторизации с текущим заказом пользователя")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Мердж несуществующего заказа без авторизации с текущим заказом пользователя")
    public void mergeCurrentOrderWithNonExistingOrder() {
        final Response response = OrdersV2Request.Current.PUT("failedUuid");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(305)
    @Story("Мердж заказа без авторизации с текущим заказом пользователя")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Мердж несуществующего заказа без авторизации с текущим заказом пользователя")
    public void mergeCurrentOrderWithNullOrder() {
        final Response response = OrdersV2Request.Current.PUT("");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(816)
    @Story("Применение бонуса")
    @Test(groups = {"api-instamart-regress"},
            description = "Использование бонусов для оплаты. Заказ нельзя оплатить бонусами")
    public void checkOrderPayInstacoin() {
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        final UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        String userId = UserIdDao.INSTANCE.findUserId(userData.getPhone());

        execRakeTaskAddBonus(userData.getEmail(), "100", userId);
        final Response response = OrdersV2Request.Instacoins.POST(apiV2.getCurrentOrderNumber(), "100");
        checkStatusCode422(response);
        checkError(response, "Этот заказ можно оплатить бонусами до 0 ₽");
    }

    @CaseId(835)
    @Story("Бонусы спасибо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о возможности оплатить бонусами")
    public void getSpasiboInfo() {
        final Response response = OrdersV2Request.SpasiboInfo.GET(apiV2.getCurrentOrderNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SpasiboV2Response.class);
    }

    @CaseId(1432)
    @Story("Бонусы спасибо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о возможности оплатить бонусами для несуществующего заказа")
    public void getSpasiboInfoForNonexistentOrder() {
        final Response response = OrdersV2Request.SpasiboInfo.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(2142)
    @Story("Бонусы спасибо")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о возможности оплатить бонусами для чужого заказа")
    public void getSpasiboInfoForSomeoneElsesOrder() {
        String currentOrderNumber = apiV2.getCurrentOrderNumber();
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        final Response response = OrdersV2Request.SpasiboInfo.GET(currentOrderNumber);
        checkStatusCode403(response);
        checkError(response, "Пользователь не может выполнить это действие");
    }
}
