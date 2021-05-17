package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.SearchesV2Request;
import ru.instamart.api.response.v2.SearchSuggestionsV2Response;
import ru.instamart.core.dataprovider.RestDataProvider;

import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Поиск")
public class SearchesV2Test extends RestBase {

    @CaseId(3)
    @Test(  description = "Получаем поисковые подсказки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getSearchSuggestions200() {
        response = SearchesV2Request.Suggestions.GET(1);
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(SearchSuggestionsV2Response.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }

    @CaseId(272)
    @Test(  description = "Получаем поисковые подсказки",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getSearchSuggestions404() {
        response = SearchesV2Request.Suggestions.GET(0);
        InstamartApiCheckpoints.checkStatusCode404(response);
    }

    @CaseId(273)
    @Test(  description = "Получаем поисковые подсказки по слову",
            groups = {"api-instamart-regress"},
            dataProvider = "query",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithQuery(int sid, String query, int statusCode) {
        response = SearchesV2Request.Suggestions.GET(sid, query);
        response.then().statusCode(statusCode);
    }
}
