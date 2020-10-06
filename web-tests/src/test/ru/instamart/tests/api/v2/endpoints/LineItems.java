package ru.instamart.tests.api.v2.endpoints;

import instamart.api.v2.objects.LineItem;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.v2.responses.LineItemResponse;
import instamart.api.v2.responses.ProductsResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class LineItems extends RestBase {
    private long productId;
    private long lineItemId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        kraken.apiV2().getCurrentOrderNumber();
        productId = ApiV2Requests
                .getProducts(1, "")
                .as(ProductsResponse.class)
                .getProducts()
                .get(0)
                .getId();
    }

    @Test(  description = "Добавляем товар в корзину",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 8)
    public void postLineItems() {
        response = ApiV2Requests.postLineItems(productId,1);

        assertEquals(response.getStatusCode(), 200);
        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();
        assertNotNull(lineItem, "Не добавился товар в корзину");
        lineItemId = lineItem.getId();
    }

    @Test(  description = "Удаляем товар из корзины",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 19,
            dependsOnMethods = "postLineItems")
    public void deleteLineItems() {
        response = ApiV2Requests.deleteLineItems(lineItemId);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(LineItemResponse.class).getLine_item(), "Не удалился товар из корзины");
    }

}
