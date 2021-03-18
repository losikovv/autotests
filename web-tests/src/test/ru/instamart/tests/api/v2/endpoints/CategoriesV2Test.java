package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.v2.CategoriesRequest;
import instamart.api.responses.v2.CategoriesResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

@Epic("ApiV2")
@Feature("Получение категорий")
public final class CategoriesV2Test {

    @CaseId(247)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий id")
    public void testCategoriesWithId() {
        final Response response = CategoriesRequest.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(CategoriesResponse.class).getCategories(), "Не вернулись категории");
    }

    @CaseId(248)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Не существующий id")
    public void testCategoriesWithInvalidId() {
        final Response response = CategoriesRequest.GET(66666);
        checkStatusCode404(response);
        assertNull(response.as(CategoriesResponse.class).getCategories(), "Вернулись категории у несуществующего магазина");
    }
}
