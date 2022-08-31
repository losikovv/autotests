package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.common.ProductsFilterParams;
import ru.instamart.api.model.v1.ProductV1;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.request.v3.ProductsV3Request;
import ru.instamart.api.response.v1.ProductV1Response;
import ru.instamart.api.response.v3.ProductsV3Response;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.jdbc.entity.stf.SpreeProductsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV1")
@Feature("Продукты")
public class ProductsV1Tests extends RestBase {

    @CaseId(45)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте",
            groups = {"api-instamart-regress", "api-instamart-smoke"})
    public void getProductInfo() {
        SpreeProductsEntity spreeProductsEntity = SpreeProductsDao.INSTANCE.getProduct();
        final Response response = StoresV1Request.Products.GET(EnvironmentProperties.DEFAULT_SID, spreeProductsEntity.getPermalink());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductV1Response.class);
        ProductV1 productFromResponse = response.as(ProductV1Response.class).getProduct();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productFromResponse.getId(), spreeProductsEntity.getId(), softAssert);
        compareTwoObjects(productFromResponse.getSku(), spreeProductsEntity.getSku(), softAssert);
        compareTwoObjects(productFromResponse.getName(), spreeProductsEntity.getName(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(45)
    @Issue("STF-9811")
    @Story("Получить данные о продукте")
    @Test(enabled = false,
            description = "Получаем данные о продукте",
            groups = {"api-instamart-prod"})
    public void getProductInfoProd() {
        final Response response = StoresV1Request.Products.GET(EnvironmentProperties.DEFAULT_SID, "banany-a0c7489");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductV1Response.class);
    }

    @CaseId(1381)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продукте c несуществующим permalink",
            groups = {"api-instamart-regress", "api-instamart-smoke", "api-instamart-prod"})
    public void getProductInfoWithInvalidPermalink() {
        final Response response = StoresV1Request.Products.GET(EnvironmentProperties.DEFAULT_SID, "fgdgdfgdfgdfg");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
