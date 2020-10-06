package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.v2.responses.SearchSuggestionsResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Searches extends RestBase {

    @Test(  description = "Получаем поисковые подсказки",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 3)
    public void getSearchSuggestions() {
        response = ApiV2Requests.getSearchSuggestions(1, "");

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(SearchSuggestionsResponse.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
