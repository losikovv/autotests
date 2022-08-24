package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.CategoriesV2Request;
import ru.instamart.api.response.v2.CategoriesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Получение категорий")
public final class CategoriesV2Test extends RestBase {

    @CaseId(1459)
    @Story("Получение полной иерархии категорий")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"},
            description = "Существующий id")
    public void testCategoriesWithId() {
        final Response response = CategoriesV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CategoriesV2Response.class);
    }

    @CaseId(1460)
    @Story("Получение полной иерархии категорий")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Не существующий id")
    public void testCategoriesWithInvalidId() {
        final Response response = CategoriesV2Request.GET(0);
        checkStatusCode(response, 404, ContentType.JSON);
        assertNull(response.as(CategoriesV2Response.class).getCategories(), "Вернулись категории у несуществующего магазина");
    }
}
