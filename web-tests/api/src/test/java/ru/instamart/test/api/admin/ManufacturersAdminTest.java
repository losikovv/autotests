package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.ManufacturersRequest;
import ru.instamart.jdbc.dao.ManufacturersDao;
import ru.instamart.jdbc.entity.ManufacturersEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Производители")
public class ManufacturersAdminTest extends RestBase {
    private String name = "AutoTest_" + Generate.string(10);
    private ManufacturersEntity manufacturers;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1824)
    @Story("Производители")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о производителях",
            priority = 1)
    public void getManufacturers200() {
        final Response response = ManufacturersRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1825)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание производителя")
    public void postManufacturers302() {
        final Response response = ManufacturersRequest.POST(name);
        checkStatusCode302(response);
        manufacturers = ManufacturersDao.INSTANCE.getIdByName(name);
        assertNotNull(manufacturers, "Производитель не создался в БД");
    }

    @CaseId(1826)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование производителя", dependsOnMethods = "postManufacturers302")
    public void patchManufacturers302() {
        name = "AutoTest_" + Generate.string(10);
        final Response response = ManufacturersRequest.POST("patch", manufacturers.getId().toString(), name);
        checkStatusCode302(response);
        ManufacturersEntity manufacturersPatch = ManufacturersDao.INSTANCE.getIdByName(name);
        assertNotNull(manufacturers, "Производитель не создался в БД");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(manufacturersPatch.getName(), name, "Наименование не совпадает");
        softAssert.assertEquals(manufacturersPatch.getId(), manufacturers.getId(), "id производителя при редактировании изменилось");
        softAssert.assertAll();
    }

    @CaseId(1827)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление производителя", dependsOnMethods = "patchManufacturers302")
    public void delManufacturers302() {
        final Response response = ManufacturersRequest.POST("delete", manufacturers.getId().toString());
        checkStatusCode302(response);
    }
}
