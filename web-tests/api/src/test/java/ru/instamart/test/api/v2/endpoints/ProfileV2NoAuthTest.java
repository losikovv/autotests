package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ProfileV2Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV2")
@Feature("Профиль пользователя")
public class ProfileV2NoAuthTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void before(){
        SessionFactory.clearSession(SessionType.API_V2);
    }

    @CaseId(160)
    @Test(description = "Получение данных профиля пользователя. Запрос без токена",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void getProfile401() {
        final Response response = ProfileV2Request.GET();
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(151)
    @Test(description = "Обновление профиля пользователя",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"})
    public void putProfile422() {
        final String newEmail = "test###autotestmail.dev";
        final String newFirstName = "!@#$%";
        final String newLastName = "9&%^&%#@&^#@*&^*&@";

        ProfileV2Request.Profile buildProfile = ProfileV2Request.Profile.builder()
                .email(newEmail)
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();
        final Response response = ProfileV2Request.PUT(buildProfile);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
