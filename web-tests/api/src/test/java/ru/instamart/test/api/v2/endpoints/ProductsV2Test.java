package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.api.response.v2.ProductV2Response;
import ru.instamart.api.response.v2.ProductsV2Response;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Продукты")
public final class ProductsV2Test extends RestBase {
    private long productId;

    @Deprecated
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Получаем продукты",
            groups = {})
    public void getProducts() {
        response = ProductsV2Request.GET(1, "");
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final List<ProductV2> products = productsV2Response.getProducts();
        assertFalse(products.isEmpty(), "Не вернулись продукты");
        productId = products.get(0).getId();

        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @Deprecated
    @Story("Получить данные о продукте")
    @Test(  description = "Получаем данные о продукте",
            groups = {},
            dependsOnMethods = "getProducts")
    public void getProductInfo() {
        response = ProductsV2Request.GET(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(ProductV2Response.class).getProduct(), "Не вернулся продукт");
    }

    @CaseId(560)
    @Story("Получить данные о продукте")
    @Test(  description = "Получаем данные о продукте c невалидным id",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductInfoWithInvalidId() {
        response = ProductsV2Request.GET(6666666L);
        checkStatusCode404(response);
    }

    //TODO: Сейчас в ответ прилетает 500
    @CaseId(262)
    @Issue("STF-9240")
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            enabled = false)
    public void getProductsWithValidSid() {
        response = ProductsV2Request.GET(1);
        checkStatusCode200(response);
    }

    @CaseId(263)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Несуществующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithInvalidSid() {
        response = ProductsV2Request.GET(6666666);
        checkStatusCode404(response);
    }

    @CaseId(264)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid", groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsWithValidSidAndQuery() {
        response = ProductsV2Request.GET(1, "хлеб");
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        assertNotNull(productsV2Response, "Не вернулся ответ");
        final List<ProductV2> products = productsV2Response.getProducts();
        assertFalse(products.isEmpty(), "Не вернулись продукты");
        products.forEach(product -> assertFalse(
                product.getName().matches("/хлеб/"),
                "Продукт не хлеб " + "[" + product.getName() + "]"
        ));
    }

    @CaseId(638)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Получаем отсортированные продукты по популярности",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPopularity() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.POPULARITY);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        assertFalse(productsV2Response.getProducts().isEmpty(), "Не вернулись продукты");
        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @CaseId(639)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Получаем отсортированные продукты по возрастанию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPriceAsc() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_ASC);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        assertFalse(productsV2Response.getProducts().isEmpty(), "Не вернулись продукты");
        checkSort(ProductSortTypeV2.PRICE_ASC, productsV2Response.getSort());
    }

    @CaseId(640)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(  description = "Получаем отсортированные продукты по убыванию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPriceDesc() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_DESC);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        assertFalse(productsV2Response.getProducts().isEmpty(), "Не вернулись продукты");
        checkSort(ProductSortTypeV2.PRICE_DESC, productsV2Response.getSort());
    }
}
