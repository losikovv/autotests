package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.api.response.v2.ProductV2Response;
import ru.instamart.api.response.v2.ProductsV2Response;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Продукты")
public final class ProductsV2Test extends RestBase {

    private long productId;

    @CaseId(2)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Получаем продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProducts() {
        response = ProductsV2Request.GET(1, "");
        checkStatusCode200(response);
        final List<ProductV2> products = response.as(ProductsV2Response.class).getProducts();
        assertNotNull(products, "Не вернулись продукты");
        productId = products.get(0).getId();
    }

    @CaseId(10)
    @Story("Получить данные о продукте")
    @Test(  description = "Получаем данные о продукте",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "getProducts")
    public void testGetProductInfo() {
        response = ProductsV2Request.GET(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(ProductV2Response.class).getProduct(), "Не вернулся продукт");
    }

    @CaseId(560)
    @Story("Получить данные о продукте")
    @Test(  description = "Получаем данные о продукте c невалидным id",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void testGetProductInfoWithInvalidId() {
        response = ProductsV2Request.GET(6666666L);
        checkStatusCode404(response);
    }

    //TODO: Сейчас в ответ прилетает 500
    @CaseId(262)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            enabled = false
    )
    public void testGetProductWithValidSid() {
        response = ProductsV2Request.GET(1);
        checkStatusCode200(response);
    }

    @CaseId(263)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Несуществующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void testGetProductWithInvalidSid() {
        response = ProductsV2Request.GET(6666666);
        checkStatusCode404(response);
    }

    @CaseId(264)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid", groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void testGetProductWithValidSidAndQuery() {
        response = ProductsV2Request.GET(1, "хлеб");
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        assertNotNull(productsV2Response, "Не вернулся ответ");
        final List<ProductV2> products = productsV2Response.getProducts();
        assertNotNull(products, "Не вернулись продукты");
        products.forEach(product -> assertFalse(
                product.getName().matches("/хлеб/"),
                "Продукт не хлеб " + "[" + product.getName() + "]"
        ));
    }
}
