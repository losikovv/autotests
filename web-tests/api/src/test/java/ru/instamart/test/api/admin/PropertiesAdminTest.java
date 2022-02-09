package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.PropertiesAdminRequest;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Свойства")
public class PropertiesAdminTest extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authAdmin();
    }

    @CaseId(1941)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех свойств")
    public void getAllProperties() {
        final Response response = PropertiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1942)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание свойства")
    public void createProperty() {
        String postfix = Generate.literalString(6);
        final Response response = PropertiesAdminRequest.POST("property-name-" + postfix, "property-presentation-" + postfix);
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }

    @CaseId(1943)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование свойства")
    public void patchProperty() {
        String postfix = Generate.literalString(6);
        final Response response = PropertiesAdminRequest.PATCH("12", "property-name-" + postfix, "property-presentation-" + postfix);
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }

    @CaseId(1944)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление свойства")
    public void deleteProperty() {
        final Response response = PropertiesAdminRequest.DELETE("12");
        checkStatusCode302(response);
        //todo добавить проверку через базу
    }
}
