package ru.instamart.test.api.admin;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.OptionTypesAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreeOptionTypesDao;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Товарные опции")
public class OptionTypesAdminTest extends RestBase {
    private String optionTypeId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1936)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех товарных опций")
    public void getAllOptionTypes() {
        final Response response = OptionTypesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1937)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание товарной опции")
    public void createOptionType() {
        String postfix = Generate.literalString(6);
        final Response response = OptionTypesAdminRequest.POST("name-" + postfix, "presentation-" + postfix);
        checkStatusCode302(response);

        var spreeOptionTypes = SpreeOptionTypesDao.INSTANCE.getByName("name-" + postfix);
        Allure.step("Проверка на null ответа БД по name", () -> assertNotNull(spreeOptionTypes, "Значение из БД вернулось пустым"));
        Allure.step("Проверка установленных верных значений",
                () -> assertEquals(spreeOptionTypes.getPresentation(), "presentation-" + postfix, "presentation  не равен введенному"));
        String[] location = response.getHeader("location").split("/");
        optionTypeId = location[location.length - 2];

        var spreeOptionTypesById = SpreeOptionTypesDao.INSTANCE.getById(optionTypeId);
        Allure.step("Проверка на null ответа БД по name", () -> assertNotNull(spreeOptionTypesById, "Значение из БД вернулось пустым"));
        Allure.step("Проверка установленных верных значений",
                () -> {
                    assertEquals(spreeOptionTypesById.getPresentation(), "presentation-" + postfix, "presentation  не равен введенному");
                    assertEquals(spreeOptionTypesById.getName(), "name-" + postfix, "name не равен введенному");
                });
    }

    @CaseId(1938)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение товарной опции",
            dependsOnMethods = "createOptionType")
    public void updateOptionType() {
        String postfix = Generate.literalString(6);
        final Response response = OptionTypesAdminRequest.PATCH(
                optionTypeId,
                "name-" + postfix,
                "presentation-" + postfix);
        checkStatusCode302(response);
        var spreeOptionTypesById = SpreeOptionTypesDao.INSTANCE.getById(optionTypeId);
        Allure.step("Проверка на null ответа БД по name", () -> assertNotNull(spreeOptionTypesById, "Значение из БД вернулось пустым"));
        Allure.step("Проверка установленных верных значений",
                () -> {
                    assertEquals(spreeOptionTypesById.getPresentation(), "presentation-" + postfix, "presentation  не равен введенному");
                    assertEquals(spreeOptionTypesById.getName(), "name-" + postfix, "name не равен введенному");
                });
    }

    @CaseId(1939)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание значения для товарной опции",
            dependsOnMethods = "createOptionType")
    public void createOptionValue() {
        String postfix = Generate.literalString(6);
        final Response response = OptionTypesAdminRequest.PATCH(
                optionTypeId,
                "option-name-" + postfix,
                "option-presentation-" + postfix,
                "0",
                "",
                "value-name-" + postfix,
                "value-presentation-" + postfix,
                false);
        checkStatusCode302(response);
        var spreeOptionTypesById = SpreeOptionTypesDao.INSTANCE.getById(optionTypeId);
        Allure.step("Проверка на null ответа БД по name", () -> assertNotNull(spreeOptionTypesById, "Значение из БД вернулось пустым"));
        Allure.step("Проверка установленных верных значений",
                () -> {
                    assertEquals(spreeOptionTypesById.getName(), "option-name-" + postfix, "name не равен введенному");
                    assertEquals(spreeOptionTypesById.getPresentation(), "option-presentation-" + postfix, "presentation  не равен введенному");
                    assertEquals(spreeOptionTypesById.getValueName(), "value-name-" + postfix, "value name не равен введенному");
                    assertEquals(spreeOptionTypesById.getValuePresentation(), "value-presentation-" + postfix, "value presentation не равен введенному");
                });
    }

    @CaseId(1940)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление товарной опции",
            dependsOnMethods = {"updateOptionType", "createOptionValue"})
    public void deleteOptionType() {
        Response response = OptionTypesAdminRequest.DELETE(optionTypeId);
        checkStatusCode302(response);
        var spreeOptionTypesById = SpreeOptionTypesDao.INSTANCE.getById(optionTypeId);
        Allure.step("Проверка на удаление значения из БД по id: " + optionTypeId, () -> assertNull(spreeOptionTypesById, "Значние из бд не удалено"));
    }
}
