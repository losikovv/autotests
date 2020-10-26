package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.objects.v2.LineItem;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import instamart.api.requests.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.LineItemResponse;
import instamart.api.responses.v2.ProductsResponse;

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
        ApiV2Checkpoints.assertStatusCode200(response);
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
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(LineItemResponse.class).getLine_item(), "Не удалился товар из корзины");
    }

}
