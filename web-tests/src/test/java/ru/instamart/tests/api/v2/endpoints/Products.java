package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.Product;
import ru.instamart.application.rest.objects.responses.ProductResponse;
import ru.instamart.application.rest.objects.responses.ProductsResponse;

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
