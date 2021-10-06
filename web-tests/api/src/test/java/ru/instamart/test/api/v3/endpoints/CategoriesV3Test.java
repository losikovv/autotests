package ru.instamart.test.api.v3.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.CategoryV3;
import ru.instamart.api.request.v3.CategoriesV3Request;
import ru.instamart.api.response.v3.CategoriesV3Response;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class CategoriesV3Test extends RestBase {
    private CategoryV3 category;

    @Test(groups = {"api-instamart-regress"})
    public void getCategories() {
        Response response = CategoriesV3Request.GET(1);

        checkStatusCode200(response);

        category = response.as(CategoriesV3Response.class).getCategories().get(0);
    }
}
