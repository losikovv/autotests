package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.SuggestionV2;
import ru.instamart.api.request.v2.SearchesV2Request;
import ru.instamart.api.response.v2.SearchSuggestionsV2Response;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSearchSuggestionsNegative;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Поиск")
public class SearchesV2Test extends RestBase {

    @CaseId(271)
    @Test(description = "Получаем поисковые подсказки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getSearchSuggestions200() {
        response = SearchesV2Request.Suggestions.GET(1);
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(SearchSuggestionsV2Response.class).getSuggestion(), "поисковые подсказки");
    }

    @CaseId(272)
    @Test(description = "Получаем поисковые подсказки в несуществующем магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getSearchSuggestions404() {
        response = SearchesV2Request.Suggestions.GET(0);
        checkStatusCode404(response);
    }

    @CaseIDs(value = {@CaseId(273), @CaseId(278)})
    @Test(description = "Получаем поисковые подсказки по слову - позитивные сценарии",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dataProvider = "positiveQuery",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithQuery(int sid, String query) {
        final Response response = SearchesV2Request.Suggestions.GET(sid, query);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SearchSuggestionsV2Response.class);
    }

    @CaseIDs(value = {@CaseId(276), @CaseId(277), @CaseId(279)})
    @Test(description = "Получаем поисковые подсказки по слову - негативные сценарии",
            groups = {"api-instamart-regress"},
            dataProvider = "negativeQuery",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithSqlQuery(int sid, String query) {
        final Response response = SearchesV2Request.Suggestions.GET(sid, query);
        checkStatusCode200(response);
        SuggestionV2 suggestion = response.as(SearchSuggestionsV2Response.class).getSuggestion();
        checkSearchSuggestionsNegative(suggestion);
    }

    @CaseId(274)
    @Test(description = "Получаем поисковые подсказки по слову для несуществующего магазина",
            groups = {"api-instamart-regress"})
    public void getSearchSuggestionsWithQueryAndNonExistentShop() {
        final Response response = SearchesV2Request.Suggestions.GET(0, "сыр");
        checkStatusCode404(response);
        checkError(response, "Магазин не существует");
    }

    @CaseId(275)
    @Test(description = "Получаем поисковые подсказки c пустым запросом",
            groups = {"api-instamart-regress"},
            dataProvider = "emptyQueries",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithEmptyQuery(String query) {
        final Response response = SearchesV2Request.Suggestions.GET(1, query);
        checkStatusCode200(response);
        assertNull(response.as(SearchSuggestionsV2Response.class).getSuggestion().getProducts(), "Вернулись продукты в поисковых подсказках");
    }
}
