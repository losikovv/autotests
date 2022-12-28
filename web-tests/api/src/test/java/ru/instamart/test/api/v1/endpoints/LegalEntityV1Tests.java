package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.b2b.LegalEntityV1Request;
import ru.instamart.api.response.v1.b2b.LegalEntityV1Response;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
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
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getWithoutLegalEntity() {
        admin.authApi();

        final Response response = LegalEntityV1Request.GET("123456789");
        checkStatusCode200(response);
        assertNull(response.as(LegalEntityV1Response.class).getLegalEntity(), "Ответ не пустой");
    }
}
