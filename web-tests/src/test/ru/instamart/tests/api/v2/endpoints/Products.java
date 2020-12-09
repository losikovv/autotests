package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Product;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.ProductResponse;
import instamart.api.responses.v2.ProductsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;

public class Products extends RestBase {
    private long productId;

    @CaseId(2)
    @Test(  description = "Получаем продукты",
            groups = {"api-v2-smoke"})
    public void getProducts() {
        response = ApiV2Requests.Products.GET(1, "");
        ApiV2Checkpoints.assertStatusCode200(response);
        List<Product> products = response.as(ProductsResponse.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(10)
    @Test(  description = "Получаем продукт",
            groups = {"api-v2-smoke"},
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = ApiV2Requests.Products.GET(productId);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(ProductResponse.class).getProduct(), "Не вернулся продукт");
    }
}
