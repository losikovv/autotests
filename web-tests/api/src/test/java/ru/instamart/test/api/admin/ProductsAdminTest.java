package ru.instamart.test.api.admin;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.AdminProductV1;
import ru.instamart.api.request.admin.ProductsAdminRequest;
import ru.instamart.api.response.v1.admin.ProductV1Response;
import ru.instamart.api.response.v1.admin.ProductsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.jdbc.entity.stf.SpreeProductsEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Продукты")
public class ProductsAdminTest extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(2948)
    @Story("Данные о мерах")
    @Test(groups = {"api-instamart-regress"}, description = "Получение данных о мерах в админке")
    public void getAllProductsReferences() {
        final Response response = ProductsAdminRequest.Preferences.GET();
        checkStatusCode(response, 200);
    }

    @CaseId(2949)
    @Story("Получить данные о продукте")
    @Test(description = "Получение данных о продукте",
            groups = {"api-instamart-regress"})
    public void getProductInfo() {
        SpreeProductsEntity spreeProductsEntity = SpreeProductsDao.INSTANCE.getProduct();
        final Response response = ProductsAdminRequest.GET(spreeProductsEntity.getPermalink());
        checkStatusCode(response, 200);

        checkResponseJsonSchema(response, ProductV1Response.class);
        AdminProductV1 productFromResponse = response.as(ProductV1Response.class).getProduct();
        final SoftAssert softAssert = new SoftAssert();
        Allure.step("Сравниваем поля продукта полученного из бд и запросом в рамках теста", () -> {
            compareTwoObjects(productFromResponse.getId(), spreeProductsEntity.getId(), softAssert);
            compareTwoObjects(productFromResponse.getSku(), spreeProductsEntity.getSku(), softAssert);
            compareTwoObjects(productFromResponse.getName(), spreeProductsEntity.getName(), softAssert);
            softAssert.assertAll();
        });
    }

    @CaseId(2950)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о невалидном продукте",
            groups = {"api-instamart-regress"})
    public void getUnavailableProductInfo() {
        final Response response = ProductsAdminRequest.GET("asdfgh1");
        checkStatusCode(response, 404);
    }

    @CaseId(2951)
    @Story("Получить данные о продукте")
    @Test(description = "Получаем данные о продуктах",
            groups = {"api-instamart-regress"})
    public void getProducts() {
        final Response response = ProductsAdminRequest.GET("Киви", "1,2,3", "1,2,3", "1,2,3", "1,2,3", "", "1", "1", "15");
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, ProductsV1Response.class);
    }
}
