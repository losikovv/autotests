package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoints.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.requests.v2.SearchesV2Request;
import ru.instamart.api.responses.v2.SearchSuggestionsV2Response;

import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Поиск")
public class SearchesV2Test extends RestBase {

    @CaseId(3)
    @Test(  description = "Получаем поисковые подсказки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getSearchSuggestions() {
        response = SearchesV2Request.Suggestions.GET(1, "");
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(SearchSuggestionsV2Response.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
