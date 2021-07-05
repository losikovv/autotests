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
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.*;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.util.List;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

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
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());

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
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());

        Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "failCode");
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Не вернулась ошибки");
    }

    @CaseId(315)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"},
            description = "Существующий id")
    public void deletePromoCodeForOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());

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
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());

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
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());

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
    @Test(groups = {"api-instamart-regress"},
            description = "Существующий id")
    public void getingListOfItemsForOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        response = OrdersV2Request.LineItems.GET(apiV2.getCurrentOrderNumber());

        checkStatusCode200(response);
        LineItemsV2Response lineItemsV2Response = response.as(LineItemsV2Response.class);
        final SoftAssert asserts = new SoftAssert();
        asserts.assertEquals(lineItemsV2Response.getMeta().getCurrentPage().toString(), "1", "1");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalPages().toString(), "1", "2");
        asserts.assertEquals(lineItemsV2Response.getMeta().getPerPage().toString(), "20", "3");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalCount().toString(), "1", "4");
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

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Заказ не существует");
        softAssert.assertAll();

    }

    @CaseId(321)
    @Story("Получение line_items для shipments")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение line_items для shipments с существующим id")
    public void getShipmentLineItems200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
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
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Доставка не существует");
        softAssert.assertAll();
    }

    @CaseId(323)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по заказу с существующим id")
    public void getLineItemCancellations200() {
        apiV2.order(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        response = OrdersV2Request.LineItemCancellations.GET(apiV2.getCurrentOrderNumber());
        assertTrue(response.as(LineItemCancellationsV2Response.class).getLineItemCancellations().isEmpty());

    }

    @CaseId(324)
    @Story("Получение списка отмененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по заказу с несуществующим id")
    public void getLineItemCancellations404() {
        response = OrdersV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Заказ не существует");
        softAssert.assertAll();
    }

    @CaseId(326)
    @Story("Получение списка отмененных позиций по подзаказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка отмененных позиций по подзаказу с несуществующим id")
    public void getShipmentLineItem404() {
        response = ShipmentsV2Request.LineItemCancellations.GET("failedOrderNumber");
        checkStatusCode404(response);

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Доставка не существует");
        softAssert.assertAll();
    }

    @CaseId(328)
    @Story("Получение списка замененных позиций по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по заказу с несуществующим id")
    public void getOrdersLineItemReplacements404() {
        response = OrdersV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Заказ не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Заказ не существует");
        softAssert.assertAll();
    }

    @CaseId(330)
    @Story("Получение списка замененных позиций по подзаказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка замененных позиций по подзаказу c несуществующим id")
    public void getShipmentLineItemReplacements404() {
        response = ShipmentsV2Request.LineItemReplacements.GET("failedOrderNumber");
        checkStatusCode404(response);

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Доставка не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Доставка не существует");
        softAssert.assertAll();
    }

    @CaseId(331)
    @Story("Добавление позиции к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление позиции к заказу с обязательными полями")
    public void setLineItems200() {
        List<ProductV2> products = apiV2.getProductFromEachDepartmentInStore(EnvironmentData.INSTANCE.getDefaultSid());
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
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty());
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty());
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty());
        softAssert.assertAll();
    }

    @CaseId(333)
    @Story("Редактирование позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование позиции заказа с существующим id")
    public void changeLineItems200() {
        List<LineItemV2> cart = apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentData.INSTANCE.getDefaultSid()
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

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty());
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty());
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty());
        softAssert.assertAll();
    }

    @CaseId(335)
    @Story("Удаление позиции заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "")
    public void deleteLineItems200() {
        Integer productId = apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentData.INSTANCE.getDefaultSid()
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

        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getErrors().getBase(), "Позиция не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), "Позиция не существует");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), "Позиция не существует");
        softAssert.assertAll();
    }
}
