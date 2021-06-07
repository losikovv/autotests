package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.api.response.v2.LineItemV2Response;
import ru.instamart.api.response.v2.ProductsV2Response;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Корзина")
public class LineItemsV2Test extends RestBase {
    private String orderNumber;
    private long productId;
    private long lineItemId;

    @BeforeClass(alwaysRun = true, description = "Получение номера заказа и id продукта")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        orderNumber = apiV2.getCurrentOrderNumber();
        Response response = ProductsV2Request.GET(1, "");
        checkStatusCode200(response);
        List<ProductV2> products = response.as(ProductsV2Response.class).getProducts();
        assertFalse(products.isEmpty(), "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(8)
    @Test(  description = "Добавляем товар в корзину",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void postLineItems() {
        response = LineItemsV2Request.POST(productId,1, orderNumber);
        checkStatusCode200(response);
        LineItemV2 lineItem = response.as(LineItemV2Response.class).getLineItem();
        assertNotNull(lineItem, "Не добавился товар в корзину");
        lineItemId = lineItem.getId();
    }

    @CaseId(18)
    @Test(  description = "Удаляем товар из корзины",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "postLineItems")
    public void deleteLineItems() {
        response = LineItemsV2Request.DELETE(lineItemId);
        checkStatusCode200(response);
        assertNotNull(response.as(LineItemV2Response.class).getLineItem(), "Не удалился товар из корзины");
    }
}
