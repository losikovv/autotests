package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.BrandsAdminRequest;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Бренды")
public class BrandsAdminTest extends RestBase {
    private String permalink;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1945)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех брендов")
    public void getAllBrands() {
        final Response response = BrandsAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1946)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание бренда")
    public void createBrand() {
        String postfix = Generate.literalString(6);
        permalink = "brand-permalink-" + postfix;
        final Response response = BrandsAdminRequest.POST(
                "brand-name-" + postfix,
                permalink,
                "brand-keyword-" + postfix);
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }

    @CaseId(1947)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование бренда",
            dependsOnMethods = "createBrand")
    public void patchBrand() {
        String postfix = Generate.literalString(6);
        final Response response = BrandsAdminRequest.PATCH(
                "brand-name-" + postfix,
                permalink,
                "brand-keyword-" + postfix);
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }

    @CaseId(1948)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление бренда",
            dependsOnMethods = "patchBrand")
    public void deleteBrand() {
        final Response response = BrandsAdminRequest.DELETE(permalink);
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }
}
