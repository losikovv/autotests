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
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.LineItemsV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.api.response.v2.OrdersV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

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
        response.prettyPeek();
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        assertNotNull(order.getPromotionCodes(), "Промокод не применился");

        response = OrdersV2Request.Promotions.DELETE(apiV2.getCurrentOrderNumber(), "auto300lomxs4");
        checkStatusCode200(response);
        response.prettyPeek();
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

        //Meta block
        asserts.assertEquals(lineItemsV2Response.getMeta().getCurrentPage().toString(), "1", "1");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalPages().toString(), "1", "2");
        asserts.assertEquals(lineItemsV2Response.getMeta().getPerPage().toString(), "20", "3");
        asserts.assertEquals(lineItemsV2Response.getMeta().getTotalCount().toString(), "1", "4");

        //LineItems
        asserts.assertEquals(lineItemsV2Response.getLineItems().stream().count(), 1, "5");

        LineItemV2 order = lineItemsV2Response.getLineItems().get(0);
        asserts.assertNotNull(order.getId());
        asserts.assertEquals(order.getQuantity().toString(), "4", "7");
        asserts.assertEquals(order.getPacks().toString(), "4", "8");
        asserts.assertEquals(order.getPrice(), 309.0D, "9");
        asserts.assertEquals(order.getTotal(), 1236.0D, "10");
        asserts.assertEquals(order.getPromoTotal(), 0.0D, "11");
        asserts.assertEquals(order.getDiscount(), 0.0D, "12");
        asserts.assertNull(order.getCustomerComment(), "13");
        asserts.assertTrue(order.getProductInStock(), "14");
        asserts.assertEquals(order.getTotalDiff(), 0.0D, "15");
        asserts.assertEquals(order.getUnitPrice(), 309.0D, "16");
        asserts.assertEquals(order.getUnitQuantity().toString(), "4", "17");
        asserts.assertNotNull(order.getUuid(), "18");
        ProductV2 products = order.getProduct();
        asserts.assertEquals(products.getId().toString(), "109406760", "19");
        asserts.assertEquals(products.getSku(), "43130", "20");
        asserts.assertEquals(products.getRetailerSku(), "559099", "21");
        asserts.assertEquals(products.getName(), "Соевый напиток Alpro Coconut for professionals кокос 1,4% 1 л", "22");
        asserts.assertEquals(products.getPrice(), 309.0D, "23");
        asserts.assertEquals(products.getOriginalPrice(), 309.0D, "24");
        asserts.assertEquals(products.getDiscount(), 0.0D, "25");
        asserts.assertEquals(products.getHumanVolume(), "1 л.", "26");
        asserts.assertEquals(products.getVolume(), 1.0D, "27");
        asserts.assertEquals(products.getVolumeType(), "l", "28");
        asserts.assertEquals(products.getItemsPerPack().toString(), "1", "29");
        asserts.assertNull(products.getDiscountEndsAt(), "30");
        asserts.assertEquals(products.getPriceType(), "per_item", "31");
        asserts.assertEquals(products.getGramsPerUnit(), 1000.0D, "32");
        asserts.assertEquals(products.getUnitPrice(), 309.0D, "33");
        asserts.assertEquals(products.getOriginalUnitPrice(), 309.0D, "34");
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
}
