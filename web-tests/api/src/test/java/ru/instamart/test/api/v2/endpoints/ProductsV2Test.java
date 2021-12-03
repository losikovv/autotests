package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSort;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Продукты")
public final class ProductsV2Test extends RestBase {
    private ProductV2 product;

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

        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @CaseIDs(value = {@CaseId(265), @CaseId(869)})
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getProductsWithValidSid")
    public void getProductInfo() {
        final Response response = ProductsV2Request.GET(product.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductV2Response.class);
        ProductV2 productFromResponse = response.as(ProductV2Response.class).getProduct();
        compareTwoObjects(productFromResponse, product);
    }

    @CaseId(560)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте c невалидным id",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductInfoWithInvalidId() {
        final Response response = ProductsV2Request.GET(6666666L);
        checkStatusCode404(response);
        checkError(response, "Продукт не существует");
    }

    @CaseId(262)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-instamart-regress"})
    public void getProductsWithValidSid() {
        final Response response = ProductsV2Request.GET(1, 13610);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        List<ProductV2> products = productsV2Response.getProducts();
        product = products.get(0);
    }

    @CaseId(263)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Несуществующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithInvalidSid() {
        final Response response = ProductsV2Request.GET(6666666);
        checkStatusCode404(response);
        checkError(response, "Магазин не существует");
    }

    @CaseId(264)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid", groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsWithValidSidAndQuery() {
        final Response response = ProductsV2Request.GET(1, "хлеб");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final List<ProductV2> products = response.as(ProductsV2Response.class).getProducts();

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
        final Response response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.POPULARITY);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @CaseId(639)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по возрастанию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsSortedByPriceAsc() {
        final Response response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_ASC);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.PRICE_ASC, productsV2Response.getSort());
    }

    @CaseId(640)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по убыванию цены",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsSortedByPriceDesc() {
        final Response response = ProductsV2Request.GET(1, "хлеб", ProductSortTypeV2.PRICE_DESC);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.PRICE_DESC, productsV2Response.getSort());
    }

    @CaseId(806)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по наличию скидки продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByDiscount() {
        final Response response = ProductsV2Request.GET(EnvironmentProperties.DEFAULT_SID, "сыр", 1, ProductFilterTypeV2.DISCOUNTED, 1);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final List<ProductV2> products = productsV2Response.getProducts();
        products.forEach(product -> assertTrue(product.getDiscount() > 0.0));
    }

    @CaseId(807)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по бренду продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByBrand() {
        final Response response = ProductsV2Request.GET(1, "сыр", 1, ProductFilterTypeV2.BRAND, 3661);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final List<ProductV2> products = productsV2Response.getProducts();
        products.forEach(product -> assertTrue(product.getName().contains("Heidi")));
    }

    @CaseId(808)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по стране изготовителя продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductsFilteredByCountry() {
        final Response response = ProductsV2Request.GET(1, "сыр", 1, ProductFilterTypeV2.COUNTRY, 36);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        step("Проверка содержимого ответа", () -> {
            final List<ProductV2> products = productsV2Response.getProducts();
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
        final Response response = ProductsV2Request.GET(1, 13610, ProductSortTypeV2.UNIT_PRICE_ASC);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.UNIT_PRICE_ASC, productsV2Response.getSort());
    }

    @CaseId(1175)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по категории с одиннадцатой страницы",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductWithTidAndPage() {
        final Response response = ProductsV2Request.GET(1, 13610, 11);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsV2Response.getProducts().size(), productsV2Response.getMeta().getPerPage(), softAssert);
        compareTwoObjects(11, productsV2Response.getMeta().getCurrentPage(), softAssert);
        compareTwoObjects(10, productsV2Response.getMeta().getPreviousPage(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(1176)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу со второй страницы",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getProductWithQueryAndPage() {
        final Response response = ProductsV2Request.GET(1, "хлеб", 2);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsV2Response.getProducts().size(), productsV2Response.getMeta().getTotalCount() - productsV2Response.getMeta().getPerPage(), softAssert);
        compareTwoObjects(2, productsV2Response.getMeta().getCurrentPage(), softAssert);
        compareTwoObjects(1, productsV2Response.getMeta().getPreviousPage(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(1177)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу с несуществующей страницы",
            groups = {"api-instamart-regress"})
    public void getProductsOnNonExistingPage() {
        final Response response = ProductsV2Request.GET(1, "хлеб", 10000);
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        Assert.assertTrue(productsV2Response.getProducts().isEmpty());
    }
}
