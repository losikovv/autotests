package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.OptionTypesAdminRequest;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

@Epic("Admin")
@Feature("Товарные опции")
public class OptionTypesAdminTest extends RestBase {
    private String optionTypeId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authAdmin();
    }

    @CaseId(1936)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех товарных опций")
    public void getAllOptionTypes() {
        final Response response = OptionTypesAdminRequest.GET();
        checkStatusCode(response, 200, "text/html");
    }

    @CaseId(1937)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание товарной опции")
    public void createOptionType() {
        String postfix = Generate.literalString(6);
        final Response response = OptionTypesAdminRequest.POST("name-" + postfix, "presentation-" + postfix);
        checkStatusCode(response, 302, "text/html");
        //todo добавить проверку через базу

        String location = response.getHeader("location");
        assertNotNull(location, "Нет хедера location со ссылкой для редиректа");

        String[] split = location.split("/");
        optionTypeId = split[split.length - 2];
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
        checkStatusCode(response, 302, "text/html");
        //todo добавить проверку через базу
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
        checkStatusCode(response, 302, "text/html");
        //todo добавить проверку через базу
    }

    @CaseId(1940)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление товарной опции",
            dependsOnMethods = {"updateOptionType", "createOptionValue"})
    public void deleteOptionType() {
        Response response = OptionTypesAdminRequest.DELETE(optionTypeId);
        checkStatusCode(response, 302, "text/html");
        //todo добавить проверку через базу
    }
}
