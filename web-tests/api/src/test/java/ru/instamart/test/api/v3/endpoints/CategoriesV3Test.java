package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.CategoryV3;
import ru.instamart.api.request.v3.CategoriesV3Request;
import ru.instamart.api.response.v3.CategoriesV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Категории (categories)")
public class CategoriesV3Test extends RestBase {
    private CategoryV3 category;

    @Test(groups = {"api-instamart-regress"}, description = "Поиск категорий")
    public void getCategories()  {
        Response response = CategoriesV3Request.GET(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CategoriesV3Response.class);
        category = response.as(CategoriesV3Response.class).getCategories().get(0);
    }
}
