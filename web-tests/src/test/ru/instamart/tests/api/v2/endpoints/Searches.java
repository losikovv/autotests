package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.SearchSuggestionsResponse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Searches extends RestBase {

    @Test(  description = "Получаем поисковые подсказки",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 3)
    public void getSearchSuggestions() {
        response = ApiV2Requests.getSearchSuggestions(1, "");
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(SearchSuggestionsResponse.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
