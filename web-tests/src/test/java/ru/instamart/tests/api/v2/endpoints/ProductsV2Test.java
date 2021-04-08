package ru.instamart.tests.api.v2.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v2.Product;
import ru.instamart.api.requests.v2.ProductsRequest;
import ru.instamart.api.responses.v2.ProductResponse;
import ru.instamart.api.responses.v2.ProductsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import java.util.List;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;

public class ProductsV2Test extends RestBase {
    private long productId;

    @CaseId(2)
    @Test(  description = "Получаем продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProducts() {
        response = ProductsRequest.GET(1, "");
        checkStatusCode200(response);
        List<Product> products = response.as(ProductsResponse.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(10)
    @Test(  description = "Получаем продукт",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = ProductsRequest.GET(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(ProductResponse.class).getProduct(), "Не вернулся продукт");
    }
}
