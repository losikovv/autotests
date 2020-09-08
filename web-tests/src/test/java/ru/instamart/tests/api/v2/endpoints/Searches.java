package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.responses.SearchSuggestionsResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Searches extends RestBase {

    @Test(  description = "Получаем поисковые подсказки",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 3)
    public void getSearchSuggestions() {
        response = Requests.getSearchSuggestions(1, "");

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(SearchSuggestionsResponse.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
