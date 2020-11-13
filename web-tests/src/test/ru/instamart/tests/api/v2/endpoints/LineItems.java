package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.LineItem;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.LineItemResponse;
import instamart.api.responses.v2.ProductsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class LineItems extends RestBase {
    private String orderNumber;
    private long productId;
    private long lineItemId;

    @BeforeClass(alwaysRun = true, description = "Получение номера заказа и id продукта")
    public void preconditions() {
        orderNumber = apiV2.getCurrentOrderNumber();
        productId = ApiV2Requests.Products
                .GET(1, "")
                .as(ProductsResponse.class)
                .getProducts()
                .get(0)
                .getId();
    }

    @CaseId(8)
    @Test(  description = "Добавляем товар в корзину",
            groups = {"api-v2-smoke"})
    public void postLineItems() {
        response = ApiV2Requests.LineItems.POST(productId,1, orderNumber);
        ApiV2Checkpoints.assertStatusCode200(response);
        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();
        assertNotNull(lineItem, "Не добавился товар в корзину");
        lineItemId = lineItem.getId();
    }

    @CaseId(18)
    @Test(  description = "Удаляем товар из корзины",
            groups = {"api-v2-smoke"},
            dependsOnMethods = "postLineItems")
    public void deleteLineItems() {
        response = ApiV2Requests.LineItems.DELETE(lineItemId);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(LineItemResponse.class).getLine_item(), "Не удалился товар из корзины");
    }

}
