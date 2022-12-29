package ru.instamart.test.api.admin;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.PropertiesAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreePropertiesDao;
import ru.instamart.kraken.data.Generate;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.*;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Свойства")
public class PropertiesAdminTest extends RestBase {
    private Long propertiesId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @TmsLink("1941")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение всех свойств")
    public void getAllProperties() {
        final Response response = PropertiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @TmsLink("1942")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание свойства")
    public void createProperty() {
        String postfix = Generate.literalString(6);
        final Response response = PropertiesAdminRequest.POST("property-name-" + postfix, "property-presentation-" + postfix);
        checkStatusCode302(response);
        var spreeProperties = SpreePropertiesDao.INSTANCE.getByName("property-name-" + postfix);
        Allure.step("Проверка на null данных из БД", () -> assertNotNull(spreeProperties, "Данные из БД вернулись пустые"));
        Allure.step("Проверка на соответствии данных в БД",
                () -> assertEquals(spreeProperties.getPresentation(), "property-presentation-" + postfix, "brand-name не совпадает с введенным"));
        propertiesId = spreeProperties.getId();
    }

    @TmsLink("1943")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Редактирование свойства",
            dependsOnMethods = "createProperty")
    public void patchProperty() {
        String postfix = Generate.literalString(6);
        final Response response = PropertiesAdminRequest.PATCH(propertiesId.toString(), "property-name-" + postfix, "property-presentation-" + postfix);
        checkStatusCode302(response);
        var spreeProperties = SpreePropertiesDao.INSTANCE.getById(propertiesId);
        Allure.step("Проверка на null данных из БД", () -> assertNotNull(spreeProperties, "Данные из БД вернулись пустые"));
        Allure.step("Проверка на соответствии данных в БД", () -> {
            assertEquals(spreeProperties.getName(), "property-name-" + postfix, "name не совпадает с введенным");
            assertEquals(spreeProperties.getPresentation(), "property-presentation-" + postfix, "presentation не совпадает с введенным");
        });
    }

    @TmsLink("1944")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Удаление свойства",
            dependsOnMethods = "patchProperty")
    public void deleteProperty() {
        final Response response = PropertiesAdminRequest.DELETE(propertiesId.toString());
        checkStatusCode302(response);
        var spreeProperties = SpreePropertiesDao.INSTANCE.getById(propertiesId);
        Allure.step("Проверка на null данных из БД", () -> assertNull(spreeProperties, "Значение из БД не удалено"));
    }
}
