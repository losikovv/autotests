package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.LineItemsV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.api.response.v2.OrdersV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Epic("ApiV2")
@Feature("Заказы")
public class OrdersV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        OrdersV2Request.POST();
    }

    @CaseId(4)
    @Test(  description = "Получаем заказы",
            groups = {"api-instamart-smoke", "MRAutoCheck", "api-instamart-prod"})
    public void getOrders() {
        response = OrdersV2Request.GET();
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrdersV2Response.class).getOrders(), "Не вернулись заказы");
    }

    @CaseId(5)
    @Test(  description = "Получаем текущий заказ",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getCurrentOrder() {
        response = OrdersV2Request.Current.GET();
        response.prettyPrint();
        InstamartApiCheckpoints.checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order, "Не вернулся текущий заказ");
    }

    @CaseId(9)
    @Test(  description = "Получаем заказ",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOrder() {
        response = OrdersV2Request.GET(apiV2.getCurrentOrderNumber());
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrderV2Response.class).getOrder(), "Не вернулся заказ по номеру");
    }

    @CaseId(19)
    @Test(  description = "Получаем заказы для оценки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getUnratedOrders() {
        response = OrdersV2Request.Unrated.GET();
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(OrdersV2Response.class).getOrders(), "Не вернулись заказы для оценки");
    }

    @CaseId(16)
    @Test(  description = "Получаем товары в заказе",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getOrderLineItems() {
        response = OrdersV2Request.LineItems.GET(apiV2.getCurrentOrderNumber());
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(LineItemsV2Response.class).getLineItems(), "Не вернулись товары заказа");
    }

    @CaseId(313)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий id")
    public void orderWithPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        OrderV2Response orderV2Response = response.getBody().as(OrderV2Response.class);
        assertNotNull(orderV2Response.getOrder().getPromotionCodes(), "Промокод не применился");
        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        orderV2Response = response.getBody().as(OrderV2Response.class);
        assertTrue(orderV2Response.getOrder().getPromotionCodes().isEmpty(), "Промокод не удалился");
    }

    @CaseId(314)
    @Story("Применение промокода")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий id")
    public void orderWithInvalidPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        final Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "failCode");
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Не вернулась ошибки");
    }

    @CaseId(316)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий id заказа")
    public void deletePromoCodeForInvalidOrder() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        final OrderV2Response orderV2Response = response.getBody().as(OrderV2Response.class);
        assertNotNull(orderV2Response.getOrder().getPromotionCodes(), "Промокод не применился");
        response = OrdersV2Request.Promotions.DELETE("failOrder", "auto300lomxs4");
        final ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);
        assertNotNull(errorResponse, "Промокод удалился");
    }

    @CaseId(318)
    @Story("Удаление промокода")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий promoCode")
    public void deleteInvalidPromoCode() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        Response response = OrdersV2Request.Promotions.POST(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        final OrderV2Response orderV2Response = response.getBody().as(OrderV2Response.class);
        assertNotNull(orderV2Response.getOrder().getPromotionCodes(), "Промокод не применился");
        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "failCode");
        final ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);
        assertNotNull(errorResponse, "Промокод удалился");
    }
}
