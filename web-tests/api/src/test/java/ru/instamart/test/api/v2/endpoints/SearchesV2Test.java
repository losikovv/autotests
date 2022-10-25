package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.SuggestionV2;
import ru.instamart.api.request.v2.SearchesV2Request;
import ru.instamart.api.response.v2.SearchSuggestionsV2Response;
import ru.instamart.api.response.v2.TopPhrasesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkSearchSuggestionsNegative;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Поиск")
public class SearchesV2Test extends RestBase {

    @CaseId(271)
    @Test(description = "Получаем поисковые подсказки",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2", "api-bff"})
    public void getSearchSuggestions200() {
        final Response response = SearchesV2Request.Suggestions.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(SearchSuggestionsV2Response.class).getSuggestion(), "поисковые подсказки");
    }

    @CaseId(272)
    @Test(description = "Получаем поисковые подсказки в несуществующем магазине",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"})
    public void getSearchSuggestions404() {
        response = SearchesV2Request.Suggestions.GET(0);
        checkStatusCode404(response);
    }

    @Skip(onServer = Server.STAGING)
    @CaseIDs(value = {@CaseId(273), @CaseId(1431), @CaseId(278)})
    @Test(description = "Получаем поисковые подсказки по слову - позитивные сценарии",
            groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2", "api-bff"},
            dataProvider = "positiveQuery",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithQuery(String query) {
        final Response response = SearchesV2Request.Suggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SearchSuggestionsV2Response.class);
    }

    @CaseIDs(value = {@CaseId(276), @CaseId(277), @CaseId(279)})
    @Test(description = "Получаем поисковые подсказки по слову - негативные сценарии",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"},
            dataProvider = "negativeQuery",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithSqlQuery(String query) {
        final Response response = SearchesV2Request.Suggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode200(response);
        SuggestionV2 suggestion = response.as(SearchSuggestionsV2Response.class).getSuggestion();
        checkSearchSuggestionsNegative(suggestion);
    }

    @CaseId(274)
    @Test(description = "Получаем поисковые подсказки по слову для несуществующего магазина",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"})
    public void getSearchSuggestionsWithQueryAndNonExistentShop() {
        final Response response = SearchesV2Request.Suggestions.GET(0, "сыр");
        checkStatusCode404(response);
        checkError(response, "Магазин не существует");
    }

    @CaseId(275)
    @Test(description = "Получаем поисковые подсказки c пустым запросом",
            groups = {"api-instamart-regress", "api-instamart-prod", "api-v2", "api-bff"},
            dataProvider = "emptyQueries",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestionsWithEmptyQuery(String query) {
        final Response response = SearchesV2Request.Suggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode200(response);
        Allure.step("Проверка поисковых подсказок",
                () -> assertNull(response.as(SearchSuggestionsV2Response.class).getSuggestion().getProducts(), "Вернулись продукты в поисковых подсказках"));
    }

    @CaseId(2472)
    @Story("Список популярных запросов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Список популярных запросов без sid")
    public void topPhrasesTest() {
        final Response response = SearchesV2Request.Suggestions.TopPhrases.GET();
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'sid'");
    }

    @CaseId(2473)
    @Story("Список популярных запросов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Список популярных запросов с несуществующим sid")
    public void topPhrasesTest1() {
        final Response response = SearchesV2Request.Suggestions.TopPhrases.GET(0);
        checkStatusCode(response, 404, ContentType.JSON);
        checkError(response, "Магазин не существует");
    }

    @CaseId(2474)
    @Story("Список популярных запросов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Список популярных запросов с существующим sid")
    public void topPhrasesSidTest() {
        final Response response = SearchesV2Request.Suggestions.TopPhrases.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        TopPhrasesV2Response topPhrases = response.as(TopPhrasesV2Response.class);
        Allure.step("Проверяем что число популярных запросов больше 0", () ->
                assertTrue(topPhrases.getTopPhrases().size() > 0, "TopPhrases вернулся пустым")
        );
    }
}
