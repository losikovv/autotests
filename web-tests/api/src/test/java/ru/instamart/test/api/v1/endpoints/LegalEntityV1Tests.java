package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.LegalEntityV1Request;
import ru.instamart.api.response.v1.b2b.LegalEntityV1Response;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class LegalEntityV1Tests extends RestBase {
    @BeforeMethod
    public void preconditions() {
    }

    @Story("Web")
    @CaseId(625)
    @Test(description = "Загрузка реквизитов компании из Контур-Фокус",
            groups = {"api-instamart-regress"})
    public void getWithoutLegalEntity() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        Response response = LegalEntityV1Request.GET("123456789");
        checkStatusCode200(response);
        assertNull(response.as(LegalEntityV1Response.class).getLegalEntity(), "Ответ не пустой");
    }
}
