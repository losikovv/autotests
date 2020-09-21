package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import instamart.api.common.Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.Product;
import instamart.api.objects.responses.ProductResponse;
import instamart.api.objects.responses.ProductsResponse;

import java.util.List;

import static org.testng.Assert.*;

public class Products extends RestBase {
    private long productId;

    @Test(  description = "Получаем продукты",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 2)
    public void getProducts() {
        response = Requests.getProducts(1, "");
        List<Product> products = response.as(ProductsResponse.class).getProducts();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(products, "Не вернулись продукты");

        productId = products.get(0).getId();
    }

    @Test(  description = "Получаем продукт",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 10,
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = Requests.getProducts(productId);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ProductResponse.class).getProduct(), "Не вернулся продукт");
    }

}
