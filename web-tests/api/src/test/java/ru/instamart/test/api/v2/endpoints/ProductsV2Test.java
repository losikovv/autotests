package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ProductFilterTypeV2;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.model.common.ProductsFilterParams;
import ru.instamart.api.model.v2.FacetV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.OptionV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.ProductsV2Request;
import ru.instamart.api.response.v2.ProductV2Response;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSort;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Продукты")
public final class ProductsV2Test extends RestBase {
    private ProductV2 product;

    @Skip(onServer = Server.STAGING)
    @CaseIDs(value = {@CaseId(265), @CaseId(869)})
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            dependsOnMethods = "getProductsWithValidSid")
    public void getProductInfo() {
        final Response response = ProductsV2Request.GET(product.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductV2Response.class);
        ProductV2 productFromResponse = response.as(ProductV2Response.class).getProduct();
        Allure.step(String.format("Проверка продукта с id = %n (%s)", product.getId(), product.getName()), ()->{
            assertEquals(productFromResponse.getName(), product.getName(), "Наименование продукта не совпадает");
            assertEquals(productFromResponse.getGramsPerUnit(), product.getGramsPerUnit(), "grams_per_unit не совпадает");
            assertEquals(productFromResponse.getOriginalPrice(), product.getOriginalPrice(), "original_price не совпадает");
            assertEquals(productFromResponse.getOriginalUnitPrice(), product.getOriginalUnitPrice(), "original_unit_price не совпадает");
            assertEquals(productFromResponse.getPrice(), product.getPrice(), "price не совпадает");
            assertEquals(productFromResponse.getPriceType(), product.getPriceType(), "price_type не совпадает");
        });
    }

    @CaseId(560)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте c невалидным id",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void getProductInfoWithInvalidId() {
        final Response response = ProductsV2Request.GET(6666666L);
        checkStatusCode404(response);
        checkError(response, "Продукт не существует");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(262)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-instamart-regress", "api-v2"})
    public void getProductsWithValidSid() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .tid(EnvironmentProperties.DEFAULT_TID)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        List<ProductV2> products = productsV2Response.getProducts();
        product = products.get(0);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2708)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "В категории больше 3 дефолтных фильтров",
            groups = {"api-instamart-prod", "api-instamart-regress", "api-v2"})
    public void getProductsWithManyFilters() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .tid(EnvironmentProperties.DEFAULT_FILTERS_TID)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        List<FacetV2> facets = productsV2Response.getFacets();
        Allure.step("Проверяем, что категорий больше трех", () -> {
            assertTrue(facets.size() > 3, "Пришло три дефолтных фильтра или меньше");
        });
    }

    @CaseId(263)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Несуществующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void getProductsWithInvalidSid() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(6666666)
                .build());
        checkStatusCode404(response);
        checkError(response, "Магазин не существует");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(264)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid", groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsWithValidSidAndQuery() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("хлеб")
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final List<ProductV2> products = response.as(ProductsV2Response.class).getProducts();
        Allure.step("Проверяем, что пришли продукты по запросу", () -> {
            products.forEach(product -> assertFalse(
                    product.getName().matches("/хлеб/"),
                    "Продукт не хлеб " + "[" + product.getName() + "]"
            ));
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(638)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по популярности",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsSortedByPopularity() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("хлеб")
                .sort(ProductSortTypeV2.POPULARITY.getKey())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.POPULARITY, productsV2Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(639)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по возрастанию цены",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsSortedByPriceAsc() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("хлеб")
                .sort(ProductSortTypeV2.PRICE_ASC.getKey())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.PRICE_ASC, productsV2Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(640)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по убыванию цены",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void getProductsSortedByPriceDesc() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("хлеб")
                .sort(ProductSortTypeV2.PRICE_DESC.getKey())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.PRICE_DESC, productsV2Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(806)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по наличию скидки продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsFilteredByDiscount() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("сыр")
                .discountedFilter(1)
                .page(1)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final List<ProductV2> products = productsV2Response.getProducts();
        Allure.step("Проверяем, что пришли отфильтрованные по наличию скидки продукты", () -> {
            products.forEach(product -> assertTrue(product.getDiscount() > 0.0, "Продукты по скидке не отфильтровались: " + product.getName() ));
        });

    }

    @Skip(onServer = Server.STAGING)
    @CaseId(807)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по бренду продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsFilteredByBrand() {
        final String brandName = "metro";
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("сыр")
                .brandFilter(EnvironmentProperties.DEFAULT_BRAND_ID)
                .page(1)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final List<ProductV2> products = productsV2Response.getProducts();
        Allure.step("Проверяем, что пришли отфильтрованные по бренду ( " + brandName + ") продукты", () -> {
            products.forEach(product -> {
                assertTrue(product.getName().toLowerCase().contains(brandName), "Пришел неверный бренд для продукта \"" + product.getName() + "\" c id=" + product.getId());
            });
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(808)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по стране изготовителя продукты",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsFilteredByCountry() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("сыр")
                .countryFilter(EnvironmentProperties.DEFAULT_PRODUCT_COUNTRY_ID)
                .page(1)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);

        step("Проверка содержимого ответа", () -> {
            final List<FacetV2> facets = productsV2Response.getFacets().stream().filter(facet -> facet.getKey().equals(ProductFilterTypeV2.COUNTRY.getValue())).collect(Collectors.toList());
            Assert.assertFalse(facets.isEmpty(), "Фильтры пустые");
            final List<OptionV2> countries = facets.get(0).getOptions();
            countries.stream().filter(country -> country.getValue() == EnvironmentProperties.DEFAULT_PRODUCT_COUNTRY_ID).forEach(country -> assertTrue(country.getActive(), "Выбранная страна неактивна"));
        });
    }

    @Skip(onServer = Server.STAGING) //todo починить 400 на стейдже "empty category_ids"
    @CaseId(809)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные по выгодному весу",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductsSortedByWeightPrice() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .tid(EnvironmentProperties.DEFAULT_TID)
                .sort(ProductSortTypeV2.UNIT_PRICE_ASC.getKey())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        checkSort(ProductSortTypeV2.UNIT_PRICE_ASC, productsV2Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1175)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по категории с десятой страницы",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductWithTidAndPage() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .tid(EnvironmentProperties.DEFAULT_TID)
                .page(10)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsV2Response.getProducts().size(), productsV2Response.getMeta().getPerPage(), softAssert);
        compareTwoObjects(10, productsV2Response.getMeta().getCurrentPage(), softAssert);
        compareTwoObjects(9, productsV2Response.getMeta().getPreviousPage(), softAssert);
        softAssert.assertAll();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1176)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу со второй страницы",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"})
    public void getProductWithQueryAndPage() {
        final Response responseFirstPage = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("сыр")
                .page(1)
                .build());
        checkStatusCode200(responseFirstPage);
        MetaV2 meta = responseFirstPage.as(ProductsV2Response.class).getMeta();

        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("сыр")
                .page(meta.getTotalPages())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV2Response.class);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        int elements = productsV2Response.getMeta().getTotalCount() - productsV2Response.getMeta().getPerPage() * (productsV2Response.getMeta().getPreviousPage());
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productsV2Response.getProducts().size(), elements, "productsV2Response.getProducts().size() не совпадает");
        softAssert.assertEquals(meta.getTotalPages(), productsV2Response.getMeta().getCurrentPage(), "get total page не совпадает");
        softAssert.assertEquals((Integer) (meta.getTotalPages() - 1), productsV2Response.getMeta().getPreviousPage(), "getPreviousPage не совпадает");
        softAssert.assertAll();
    }

    @CaseId(1177)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу с несуществующей страницы",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void getProductsOnNonExistingPage() {
        final Response response = ProductsV2Request.GET(ProductsFilterParams.builder()
                .sid(EnvironmentProperties.DEFAULT_SID)
                .query("хлеб")
                .page(10000)
                .build());
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        Allure.step("Проверяем, что не пришли продукты", () -> {
            Assert.assertTrue(productsV2Response.getProducts().isEmpty(), "Список продуктов не пустой");
        });
    }
}
