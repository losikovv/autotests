package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.CompanyPresenceV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class CompanyPresenceV1Tests extends RestBase {
    @BeforeMethod
    public void preconditions() {
    }

    @Story("Web")
    @CaseId(624)
    @Test(description = "Статус регистрации компании (незарегистрирована)",
            groups = {"api-instamart-regress"})
    public void getCompanyNotPresence() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        final Response response = CompanyPresenceV1Request.GET("123456789");
        checkStatusCode404(response);
        assertEquals(response.as(ErrorResponse.class).getError(), "Объект не найден",
                "Невалидная ошибка");
    }
}

