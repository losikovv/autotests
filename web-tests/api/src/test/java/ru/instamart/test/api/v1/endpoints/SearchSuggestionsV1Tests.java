package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.SuggestionOfferV1;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.SearchSuggestionsV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Поиск")
public class SearchSuggestionsV1Tests extends RestBase {

    @CaseIDs(value = {@CaseId(1420), @CaseId(1421), @CaseId(1422)})
    @Story("Поисковые подсказки")
    @Test(description = "Получение поисковых подсказок",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
            dataProvider = "positiveQuery",
            dataProviderClass = RestDataProvider.class)
    public void getSearchSuggestions(String query) {
        final Response response = StoresV1Request.SearchSuggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SearchSuggestionsV1Response.class);
        List<SuggestionOfferV1> offersFromResponse = response.as(SearchSuggestionsV1Response.class).getSuggestion().getOffers();
        final SoftAssert softAssert = new SoftAssert();
        offersFromResponse.forEach(o -> softAssert.assertTrue(o.getProduct().getName().toLowerCase().contains(query.toLowerCase()), "Пришли неверные подсказки"));
        softAssert.assertAll();
    }

    @CaseId(1423)
    @Story("Поисковые подсказки")
    @Test(description = "Получение поисковых подсказок с неверной раскладкой",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getSearchSuggestionsWithWrongKeyboardLayout() {
        final Response response = StoresV1Request.SearchSuggestions.GET(EnvironmentProperties.DEFAULT_SID, "мфдшщ");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SearchSuggestionsV1Response.class);
        List<SuggestionOfferV1> offersFromResponse = response.as(SearchSuggestionsV1Response.class).getSuggestion().getOffers();
        final SoftAssert softAssert = new SoftAssert();
        offersFromResponse.forEach(o -> softAssert.assertTrue(o.getProduct().getName().toLowerCase().contains("valio"), "Пришли неверные подсказки"));
        softAssert.assertAll();
    }

    @CaseIDs(value = {@CaseId(1424), @CaseId(1425), @CaseId(1426)})
    @Story("Поисковые подсказки")
    @Test(description = "Получение поисковых подсказок",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "negativeQuery",
            dataProviderClass = RestDataProvider.class)
    public void getInvalidSearchSuggestions(String query) {
        final Response response = StoresV1Request.SearchSuggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode200(response);
        List<SuggestionOfferV1> offersFromResponse = response.as(SearchSuggestionsV1Response.class).getSuggestion().getOffers();
        compareTwoObjects(offersFromResponse.size(), 0);
    }

    @CaseIDs(value = {@CaseId(1427), @CaseId(1428)})
    @Story("Поисковые подсказки")
    @Test(description = "Получение пустых поисковых подсказок",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "emptyQueries",
            dataProviderClass = RestDataProvider.class)
    public void getEmptySearchSuggestions(String query) {
        final Response response = StoresV1Request.SearchSuggestions.GET(EnvironmentProperties.DEFAULT_SID, query);
        checkStatusCode400(response);
        checkErrorText(response, "Bad Request");
    }

    @CaseId(1429)
    @Story("Поисковые подсказки")
    @Test(description = "Получение поисковых подсказок для несуществующего магазина",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getSearchSuggestions() {
        final Response response = StoresV1Request.SearchSuggestions.GET(0, "хлеб");
        checkStatusCode404(response);
    }
}
