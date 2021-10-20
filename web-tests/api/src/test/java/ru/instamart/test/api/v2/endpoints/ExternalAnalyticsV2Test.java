package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ExternalAnalyticsV2Request;
import ru.instamart.api.response.v2.ExternalAnalyticsV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Получение идентификатора устройства для аналитики")
public class ExternalAnalyticsV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseId(791)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получение идентификатора устройства для аналитики с токеном")
    public void testGetDeviceIdWithToken() {
        final Response response = ExternalAnalyticsV2Request.POST();
        checkStatusCode200(response);
        assertNotNull(response.as(ExternalAnalyticsV2Response.class).getAnonymousDevice().getUuid(), "Отсутствует обязательный параметр 'uuid'");
    }

    @CaseId(946)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получение идентификатора устройства для аналитики без токена")
    public void testGetDeviceIdWithoutToken() {
        SessionFactory.clearSession(SessionType.API_V2_FB);
        final Response response = ExternalAnalyticsV2Request.POST();
        checkStatusCode200(response);
        assertNotNull(response.as(ExternalAnalyticsV2Response.class).getAnonymousDevice().getUuid(), "Отсутствует обязательный параметр 'uuid'");
    }
}
