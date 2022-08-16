package ru.instamart.test.api.admin;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.admin.BrandsAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreeBrandsDao;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Objects;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Бренды")
public final class BrandsAdminTest extends RestBase {

    private String permalink;
    private Long brandId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @BeforeMethod(alwaysRun = true, description = "Повторная авторизация если токен протух или invalid")
    public void auth() {
        if (Objects.equals(SessionFactory.getSession(SessionType.ADMIN).getToken(), "invalid")) {
            admin.auth();
        }
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
        var brand = SpreeBrandsDao.INSTANCE.getBrandByName("brand-name-" + postfix);
        Allure.step("Проверка на null данных из БД", () -> assertNotNull(brand, "Данные из БД вернулись пустые"));

        brandId = brand.getId();
        Allure.step("Проверка на соответствии данных в БД", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(brand.getKeywords(), "brand-keyword-" + postfix, "brand-keyword не совпадает с введенным");
            softAssert.assertEquals(brand.getPermalink(), permalink, "brand-permalink не совпадает с введенным");
            softAssert.assertAll();
        });

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

        var brand = SpreeBrandsDao.INSTANCE.getBrandById(brandId);
        Allure.step("Проверка на null данных из БД", () -> assertNotNull(brand, "Данные из БД вернулись пустые"));
        Allure.step("Проверка на соответствии данных в БД", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(brand.getName(), "brand-name-" + postfix, "brand-name не совпадает с введенным");
            softAssert.assertEquals(brand.getKeywords(), "brand-keyword-" + postfix, "brand-keyword не совпадает с введенным");
            softAssert.assertEquals(brand.getPermalink(), permalink, "brand-permalink не совпадает с введенным");
            softAssert.assertAll();
        });
    }

    @CaseId(1948)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление бренда",
            dependsOnMethods = "patchBrand")
    public void deleteBrand() {
        final Response response = BrandsAdminRequest.DELETE(permalink);
        checkStatusCode302(response);
        var brand = SpreeBrandsDao.INSTANCE.getBrandById(brandId);
        Allure.step("Проверка удаления " + permalink + " из БД", () -> assertNull(brand, "Бренд не удалился"));
    }
}
