package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ProductFilterTypeV2;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.model.v2.FacetV2;
import ru.instamart.api.model.v2.OptionV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.api.response.v2.ProductV2Response;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSort;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Продукты")
public final class ProductsV2Test extends RestBase {
    private long productId;

    @Deprecated
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем продукты",
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
    @Test(description = "Получаем данные о продукте",
            groups = {},
            dependsOnMethods = "getProducts")
    public void getProductInfo() {
        response = ProductsV2Request.GET(productId);
        checkStatusCode200(response);
        assertNotNull(response.as(ProductV2Response.class).getProduct(), "Не вернулся продукт");
    }

    @CaseId(560)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте c невалидным id",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductInfoWithInvalidId() {
        response = ProductsV2Request.GET(6666666L);
        checkStatusCode404(response);
    }

    @CaseId(262)
    @Issue("STF-9240")
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsWithValidSid() {
        response = ProductsV2Request.GET(1, 13610);
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkFieldIsNotEmpty(productsV2Response.getProducts(), "продукты");
    }

    @CaseId(263)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Несуществующий sid",
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
        checkFieldIsNotEmpty(productsV2Response, "все продукты");
        final List<ProductV2> products = productsV2Response.getProducts();

        products.forEach(product -> assertFalse(
                product.getName().matches("/хлеб/"),
                "Продукт не хлеб " + "[" + product.getName() + "]"
        ));
    }

    @CaseId(638)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по популярности",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPopularity() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.POPULARITY);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkFieldIsNotEmpty(productsV2Response.getProducts(), "продукты");
        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @CaseId(639)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по возрастанию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPriceAsc() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_ASC);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        checkFieldIsNotEmpty(productsV2Response.getProducts(), "продукты");
        checkSort(ProductSortTypeV2.PRICE_ASC, productsV2Response.getSort());
    }

    @CaseId(640)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по убыванию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPriceDesc() {
        response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_DESC);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkFieldIsNotEmpty(productsV2Response.getProducts(), "продукты");
        checkSort(ProductSortTypeV2.PRICE_DESC, productsV2Response.getSort());
    }

    @CaseId(806)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по наличию скидки продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByDiscount() {
        response = ProductsV2Request.GET(EnvironmentProperties.DEFAULT_SID, "сыр", 1, ProductFilterTypeV2.DISCOUNTED, 1);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        step("Проверка содержимого ответа", () -> {
            checkFieldIsNotEmpty(productsV2Response, "ответ");
            final List<ProductV2> products = productsV2Response.getProducts();
            checkFieldIsNotEmpty(products, "продукты");
            products.forEach(product -> assertTrue(product.getDiscount() > 0.0));
        });
    }

    @CaseId(807)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по бренду продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByBrand() {
        response = ProductsV2Request.GET(1, "сыр", 1, ProductFilterTypeV2.BRAND, 3661);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        step("Проверка содержимого ответа", () -> {
            checkFieldIsNotEmpty(productsV2Response, "ответ");
            final List<ProductV2> products = productsV2Response.getProducts();
            checkFieldIsNotEmpty(products, "продукты");
            products.forEach(product -> assertTrue(product.getName().contains("Heidi")));
        });
    }

    @CaseId(808)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по стране изготовителя продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByCountry() {
        response = ProductsV2Request.GET(1, "сыр", 1, ProductFilterTypeV2.COUNTRY, 36);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        step("Проверка содержимого ответа", () -> {
            checkFieldIsNotEmpty(productsV2Response, "ответ");
            final List<ProductV2> products = productsV2Response.getProducts();
            checkFieldIsNotEmpty(products, "продукты");
            products.forEach(product -> assertTrue(product.getName().contains("La Paulina")));
            final List<FacetV2> facets = productsV2Response.getFacets().stream().filter(facet -> facet.getKey().equals(ProductFilterTypeV2.COUNTRY.getValue())).collect(Collectors.toList());
            final List<OptionV2> countries = facets.get(0).getOptions();
            countries.stream().filter(country -> country.getValue() == 36).forEach(country -> assertTrue(country.getActive()));
        });
    }

    @CaseId(809)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные по выгодному весу",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByWeightPrice() {
        response = ProductsV2Request.GET(1, 13610, ProductSortTypeV2.UNIT_PRICE_ASC);
        checkStatusCode200(response);

        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkFieldIsNotEmpty(productsV2Response.getProducts(), "продукты");
        checkSort(ProductSortTypeV2.UNIT_PRICE_ASC, productsV2Response.getSort());
    }
}
