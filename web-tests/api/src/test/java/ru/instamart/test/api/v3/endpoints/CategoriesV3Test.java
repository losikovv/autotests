package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.CategoryV3;
import ru.instamart.api.request.v3.AggregatingCategoriesV3Request;
import ru.instamart.api.request.v3.CategoriesV3Request;
import ru.instamart.api.response.v3.CategoriesV3Response;
import ru.instamart.api.response.v3.CategoryV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Категории (categories)")
public class CategoriesV3Test extends RestBase {
    private CategoryV3 category;
    private final Integer sid = EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;

    @CaseId(2314)
    @Test(  groups = {"api-instamart-regress"},
            description = "Получение пользовательских категорий")
    public void getAggregatingCategories()  {
        Response response = AggregatingCategoriesV3Request.GET(sid);
        checkStatusCode200(response);
    }

    @CaseId(2315)
    @Test(  groups = {"api-instamart-smoke"},
            description = "Получение дерева категорий")
    public void getCategories()  {
        Response response = CategoriesV3Request.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CategoriesV3Response.class);
        category = response.as(CategoriesV3Response.class).getCategories().get(0);
    }

    @CaseId(2316)
    @Test(  groups = {"api-instamart-smoke"},
            description = "Получение поддерева категорий по id категории",
            dependsOnMethods = "getCategories")
    public void getCategory()  {
        Response response = CategoriesV3Request.GET(sid, category.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CategoryV3Response.class);
    }
}
