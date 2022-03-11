package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.LoyaltiesV2Request;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic(value = "ApiV2")
@Feature(value = "Программа лояльности")
public class LoyaltiesV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Story("Список программ лояльности")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверка статус сообщения об отключении программы лояльности")
    public void loyalities422() {
        final Response response = LoyaltiesV2Request.GET();
        checkStatusCode422(response);
        checkError(response, "Функционал сервиса Loyalty отключен");
    }

}
