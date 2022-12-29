package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ExternalAnalyticsV2Request;
import ru.instamart.api.response.v2.ExternalAnalyticsV2Response;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.helper.UUIDHelper.isValidUUID;

@Epic("ApiV2")
@Feature("Идентификатор устройства для аналитики")
public class ExternalAnalyticsV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @TmsLink("791")
    @Test(groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получение идентификатора устройства для аналитики с токеном")
    public void testGetDeviceIdWithToken() {
        final Response response = ExternalAnalyticsV2Request.POST();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ExternalAnalyticsV2Response.class);
        Allure.step("Проверка UUID на валидность", ()->{
            ExternalAnalyticsV2Response analyticsV2Response = response.as(ExternalAnalyticsV2Response.class);
            assertTrue(isValidUUID(analyticsV2Response.getAnonymousDevice().getUuid()), "UUID пришел не валидным");
        });
    }

    @TmsLink("946")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получение идентификатора устройства для аналитики без токена")
    public void testGetDeviceIdWithoutToken() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ExternalAnalyticsV2Request.POST();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ExternalAnalyticsV2Response.class);
    }
}
