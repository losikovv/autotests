package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v2.ProductV2;
import ru.instamart.api.requests.v2.ProductsV2Request;
import ru.instamart.api.responses.v2.ProductV2Response;
import ru.instamart.api.responses.v2.ProductsV2Response;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Продукты")
public class ProductsV2Test extends RestBase {
    private long productId;

    @CaseId(2)
    @Test(  description = "Получаем продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProducts() {
        response = ProductsV2Request.GET(1, "");
        checkStatusCode200(response);
        List<ProductV2> products = response.as(ProductsV2Response.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(10)
    @Test(  description = "Получаем продукт",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "getProducts")
    public void getProduct() {
        response = ProductsV2Request.GET(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(ProductV2Response.class).getProduct(), "Не вернулся продукт");
    }
}
