package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.LineItemV1;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v1.LineItemsV1Request;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.response.v1.LineItemV1Response;
import ru.instamart.api.response.v1.LineItemsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeLineItemsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV1")
@Feature("Заказ")
public class LineItemsV1Test extends RestBase {

    private LineItemV1 lineItemFromResponse;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        if (EnvironmentProperties.Env.isProduction()) {
            admin.authApi();
        } else {
            apiV1.authByPhone(UserManager.getQaUser());
        }
    }

    @CaseId(57)
    @Story("Товары в заказе")
    @Test(description = "Получение информации о товарах в заказе",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "createLineItem")
    public void getLineItemsByOrderNumber() {
        final Response response = OrdersV1Request.LineItems.GET(apiV1.getMultiRetailerOrder().getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemsV1Response.class);
        LineItemV1 receivedLineItem = response.as(LineItemsV1Response.class).getLineItems().get(0);
        compareTwoObjects(receivedLineItem, lineItemFromResponse);
    }

    @CaseId(52)
    @Story("Товары в заказе")
    @Test(description = "Добавление товара в заказ",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void createLineItem() {
        ProductV2 product = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0);
        final Response response = LineItemsV1Request.POST(product.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemV1Response.class);
        lineItemFromResponse = response.as(LineItemV1Response.class).getLineItem();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(lineItemFromResponse.getOfferId(), product.getId(), softAssert);
        compareTwoObjects(lineItemFromResponse.getSku(), product.getSku(), softAssert);
        compareTwoObjects(lineItemFromResponse.getOffer().getId(), product.getId(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(53)
    @Story("Товары в заказе")
    @Test(description = "Изменение количества товаров в заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "getLineItemsByOrderNumber")
    public void updateLineItem() {
        lineItemFromResponse.setPacks(5);
        final Response response = LineItemsV1Request.PUT(lineItemFromResponse);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemV1Response.class);
        LineItemV1 updatedLineItem = response.as(LineItemV1Response.class).getLineItem();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedLineItem.getId(), lineItemFromResponse.getId(), softAssert);
        compareTwoObjects(updatedLineItem.getPacks(), 5, softAssert);
        softAssert.assertAll();
    }

    @CaseId(1520)
    @Story("Товары в заказе")
    @Test(description = "Удаление товара из заказа",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "updateLineItem")
    public void deleteLineItem() {
        final Response response = LineItemsV1Request.DELETE(lineItemFromResponse.getId());
        checkStatusCode200(response);
        if(!EnvironmentProperties.Env.isProduction()) {
            Assert.assertTrue(SpreeLineItemsDao.INSTANCE.findById(lineItemFromResponse.getId()).isEmpty());
        }
    }

    @CaseId(1521)
    @Story("Товары в заказе")
    @Test(description = "Получение информации о товарах в несуществующем заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "createLineItem")
    public void getLineItemsByNonexistentOrderNumber() {
        final Response response = OrdersV1Request.LineItems.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(1522)
    @Story("Товары в заказе")
    @Test(description = "Добавление несуществующего товара в заказ",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void createLineItemWithNonexistentOffer() {
        final Response response = LineItemsV1Request.POST(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(1523)
    @Story("Товары в заказе")
    @Test(description = "Изменение количества товаров в несуществующем заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void updateNonExistentLineItem() {
        LineItemV1 lineItem = new LineItemV1();
        lineItem.setId(0L);
        final Response response = LineItemsV1Request.PUT(lineItem);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(1524)
    @Story("Товары в заказе")
    @Test(description = "Удаление товара из заказа",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "updateLineItem")
    public void deleteNonExistentLineItem() {
        final Response response = LineItemsV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
