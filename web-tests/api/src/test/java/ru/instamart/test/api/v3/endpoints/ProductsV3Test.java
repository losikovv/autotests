package ru.instamart.test.api.v3.endpoints;

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
import ru.instamart.api.model.v3.ProductV3;
import ru.instamart.api.request.v3.ProductsV3Request;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.ProductsV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSort;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV3")
@Feature("Продукты")
public class ProductsV3Test extends RestBase {

    private ProductV3 product;

    @Skip(onServer = Server.STAGING)
    @CaseId(1368)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithValidSid() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .tid(EnvironmentProperties.DEFAULT_TID)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        List<ProductV3> products = productsV3Response.getProducts();
        product = products.get(0);
    }

    @Skip //todo ожидаем фикса
    @CaseId(2709)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "В категории больше 3 дефолтных фильтров",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithManyFilters() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .tid(EnvironmentProperties.DEFAULT_FILTERS_TID)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        List<FacetV2> facets = productsV3Response.getFacets();
        Allure.step("Проверяем, что категорий больше трех", () -> {
            assertTrue(facets.size() > 3, "Пришло три дефолтных фильтра или меньше");
        });
    }

    @CaseId(1369)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Несуществующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithInvalidSid() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .tid(EnvironmentProperties.DEFAULT_TID)
                .build(), 0);
        checkStatusCode404(response);
        compareTwoObjects(response.as(ErrorsV3Response.class).getErrors().get(0).getMessage(), "Магазин не существует");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1370)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Существующий sid",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsWithValidSidAndQuery() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final List<ProductV3> products = response.as(ProductsV3Response.class).getProducts();

        Allure.step("Проверяем, что пришли продукты по запросу", () -> {
            products.forEach(product -> assertFalse(
                    product.getName().matches("/хлеб/"),
                    "Продукт не хлеб " + "[" + product.getName() + "]"
            ));
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1371)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по популярности",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsSortedByPopularity() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .sort(ProductSortTypeV2.POPULARITY.getKey())
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        checkSort(ProductSortTypeV2.POPULARITY, productsV3Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1372)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по возрастанию цены",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsSortedByPriceAsc() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .sort(ProductSortTypeV2.PRICE_ASC.getKey())
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        checkSort(ProductSortTypeV2.PRICE_ASC, productsV3Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1373)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные продукты по убыванию цены",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsSortedByPriceDesc() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .sort(ProductSortTypeV2.PRICE_DESC.getKey())
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        checkSort(ProductSortTypeV2.PRICE_DESC, productsV3Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1374)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по наличию скидки продукты",
            groups = {"api-instamart-regress"}) //, "api-instamart-prod"}) todo отключили по ошибке каталога https://mattermost.sbermarket.tech/sbermarket/pl/rkm4xshmy3gj9p3eygj15trpwh
    public void getProductsFilteredByDiscount() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("сыр")
                .discountedFilter(1)
                .page(1)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        final List<ProductV3> products = productsV3Response.getProducts();
        Allure.step("Проверяем, что пришли отфильтрованные по наличию скидки продукты", () -> {
            products.forEach(product -> assertTrue(product.getDiscount() > 0.0, "Продукты по скидке не отфильтровались"));
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1375)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по бренду продукты",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsFilteredByBrand() {
        final String brandName = "valio";
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("сыр")
                .brandFilter(EnvironmentProperties.DEFAULT_BRAND_ID)
                .page(1)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        final List<ProductV3> products = productsV3Response.getProducts();
        Allure.step("Проверяем, что пришли отфильтрованные по бренду ( " + brandName + ") продукты", () -> {
            products.forEach(product -> {
                assertTrue(product.getName().toLowerCase().contains(brandName), "Пришел неверный бренд для продукта \"" + product.getName() + "\" c id=" + product.getId());
            });
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1376)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отфильтрованные по стране изготовителя продукты",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsFilteredByCountry() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("сыр")
                .countryFilter(EnvironmentProperties.DEFAULT_PRODUCT_COUNTRY_ID)
                .page(1)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV2Response = response.as(ProductsV3Response.class);

        step("Проверка содержимого ответа", () -> {
            final List<FacetV2> facets = productsV2Response.getFacets().stream().filter(facet -> facet.getKey().equals(ProductFilterTypeV2.COUNTRY.getValue())).collect(Collectors.toList());
            Assert.assertFalse(facets.isEmpty(), "Фильтры пустые");
            final List<OptionV2> countries = facets.get(0).getOptions();
            countries.stream().filter(country -> country.getValue() == EnvironmentProperties.DEFAULT_PRODUCT_COUNTRY_ID).forEach(country -> assertTrue(country.getActive(), "Выбранная страна неактивна"));
        });
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1377)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем отсортированные по выгодному весу",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsSortedByWeightPrice() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .tid(EnvironmentProperties.DEFAULT_TID)
                .sort(ProductSortTypeV2.UNIT_PRICE_ASC.getKey())
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        checkSort(ProductSortTypeV2.UNIT_PRICE_ASC, productsV3Response.getSort());
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1378)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по категории с одиннадцатой страницы",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductWithTidAndPage() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .tid(EnvironmentProperties.DEFAULT_TID)
                .page(11)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsV3Response.getProducts().size(), productsV3Response.getMeta().getPerPage(), softAssert);
        compareTwoObjects(11, productsV3Response.getMeta().getCurrentPage(), softAssert);
        compareTwoObjects(10, productsV3Response.getMeta().getPreviousPage(), softAssert);
        softAssert.assertAll();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(1379)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу со второй страницы",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductWithQueryAndPage() {
        final Response responseFirstPage = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .page(1)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(responseFirstPage);
        MetaV2 meta = responseFirstPage.as(ProductsV2Response.class).getMeta();

        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .page(meta.getTotalPages())
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsV3Response.class);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        int elements = productsV3Response.getMeta().getTotalCount() - productsV3Response.getMeta().getPerPage() * (productsV3Response.getMeta().getTotalPages() - 1);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsV3Response.getProducts().size(), elements, softAssert);
        compareTwoObjects(meta.getTotalPages(), productsV3Response.getMeta().getCurrentPage(), softAssert);
        compareTwoObjects(meta.getTotalPages() - 1, productsV3Response.getMeta().getPreviousPage(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(1380)
    @Story("Получить список доступных продуктов (Поиск)")
    @Test(description = "Получаем список продуктов по запросу с несуществующей страницы",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getProductsOnNonExistingPage() {
        final Response response = ProductsV3Request.GET(ProductsFilterParams.builder()
                .query("хлеб")
                .page(10000)
                .build(), EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        final ProductsV3Response productsV3Response = response.as(ProductsV3Response.class);
        Allure.step("Проверяем, что не пришли продукты", () -> {
            Assert.assertTrue(productsV3Response.getProducts().isEmpty(), "Список продуктов не пустой");
        });
    }
}
