package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.LineItem;
import instamart.api.requests.v2.LineItemsRequest;
import instamart.api.requests.v2.ProductsRequest;
import instamart.api.responses.v2.LineItemResponse;
import instamart.api.responses.v2.ProductsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class LineItemsV2Test extends RestBase {
    private String orderNumber;
    private long productId;
    private long lineItemId;

    @BeforeClass(alwaysRun = true, description = "Получение номера заказа и id продукта")
    public void preconditions() {
        orderNumber = apiV2.getCurrentOrderNumber();
        productId = ProductsRequest
                .GET(1, "")
                .as(ProductsResponse.class)
                .getProducts()
                .get(0)
                .getId();
    }

    @CaseId(8)
    @Test(  description = "Добавляем товар в корзину",
            groups = {"api-instamart-smoke"})
    public void postLineItems() {
        response = LineItemsRequest.POST(productId,1, orderNumber);
        assertStatusCode200(response);
        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();
        assertNotNull(lineItem, "Не добавился товар в корзину");
        lineItemId = lineItem.getId();
    }

    @CaseId(18)
    @Test(  description = "Удаляем товар из корзины",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "postLineItems")
    public void deleteLineItems() {
        response = LineItemsRequest.DELETE(lineItemId);
        assertStatusCode200(response);
        assertNotNull(response.as(LineItemResponse.class).getLine_item(), "Не удалился товар из корзины");
    }

}
