package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.SearchSuggestionsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Searches extends RestBase {

    @CaseId(3)
    @Test(  description = "Получаем поисковые подсказки",
            groups = {"api-v2-smoke"})
    public void getSearchSuggestions() {
        response = ApiV2Requests.Searches.Suggestions.GET(1, "");
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(SearchSuggestionsResponse.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
