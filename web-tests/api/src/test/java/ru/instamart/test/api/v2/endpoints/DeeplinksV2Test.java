package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.DeeplinksV2Request;
import ru.instamart.api.response.v2.DeeplinkV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Deeplinks")
public class DeeplinksV2Test extends RestBase {

    @Story("Получить диплинк юр лиц")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"},
            description = "Получить диплинк юридического лица с валидным url")
    public void DeeplinkExists() {
        final Response response = DeeplinksV2Request.GET(EnvironmentProperties.Env.FULL_SITE_URL +"metro");
        checkStatusCode200(response);
        assertEquals(response.as(DeeplinkV2Response.class).getDeeplink(), "sbermarket://retailer/1", "Ретейлер не найден");
    }

    @Story("Получить диплинк юр лиц")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"},
            description = "Получить диплинк юридического лица с невалидным url")
    public void DeeplinkNotExists() {
        final Response response = DeeplinksV2Request.GET("notValidURL");
        checkStatusCode200(response);
        assertEquals(response.as(DeeplinkV2Response.class).getDeeplink(), "sbermarket://main_page");
    }
}
