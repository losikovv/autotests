package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import instamart.api.requests.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Product;
import instamart.api.responses.v2.ProductResponse;
import instamart.api.responses.v2.ProductsResponse;

import java.util.List;

import static org.testng.Assert.*;

public class Products extends RestBase {
    private long productId;

    @Test(  description = "Получаем продукты",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 2)
    public void getProducts() {
        response = ApiV2Requests.getProducts(1, "");

        assertEquals(response.getStatusCode(), 200);
        List<Product> products = response.as(ProductsResponse.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @Test(  description = "Получаем продукт",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 10,
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = ApiV2Requests.getProducts(productId);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ProductResponse.class).getProduct(), "Не вернулся продукт");
    }

}
