package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.CategoriesV2Request;
import ru.instamart.api.response.v2.CategoriesV2Response;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Получение категорий")
@Deprecated
public final class CategoriesV2Test extends RestBase {

    @Deprecated
    @Story("Получить полную иерархию для категории")
    @Test(  groups = {},
            description = "Существующий id")
    public void testCategoriesWithId() {
        final Response response = CategoriesV2Request.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(CategoriesV2Response.class).getCategories(), "Не вернулись категории");
    }

    @Deprecated
    @Story("Получить полную иерархию для категории")
    @Test(  groups = {},
            description = "Не существующий id")
    public void testCategoriesWithInvalidId() {
        final Response response = CategoriesV2Request.GET(66666);
        checkStatusCode404(response);
        assertNull(response.as(CategoriesV2Response.class).getCategories(), "Вернулись категории у несуществующего магазина");
    }
}
