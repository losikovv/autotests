package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.v2.ProductsRequest;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Product;
import instamart.api.responses.v2.ProductResponse;
import instamart.api.responses.v2.ProductsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import java.util.List;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class ProductsTest extends RestBase {
    private long productId;

    @CaseId(2)
    @Test(  description = "Получаем продукты",
            groups = {"api-instamart-smoke"})
    public void getProducts() {
        response = ProductsRequest.GET(1, "");
        assertStatusCode200(response);
        List<Product> products = response.as(ProductsResponse.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(10)
    @Test(  description = "Получаем продукт",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = ProductsRequest.GET(productId);
        assertStatusCode200(response);
        assertNotNull(response.as(ProductResponse.class).getProduct(), "Не вернулся продукт");
    }
}
