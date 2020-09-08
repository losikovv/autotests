package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.responses.LineItemResponse;
import ru.instamart.application.rest.objects.responses.ProductsResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class LineItems extends RestBase {
    private long productId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        kraken.rest().getCurrentOrderNumber();
        productId = Requests
                .getProducts(1, "")
                .as(ProductsResponse.class)
                .getProducts()
                .get(0)
                .getId();
    }

    @Test(  description = "Добавляем товар в корзину",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 8)
    public void postLineItem() {
        response = Requests.postLineItems(productId,1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(LineItemResponse.class).getLine_item(), "Не вернулся line_item");
    }

}
