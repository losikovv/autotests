package ru.instamart.tests.api.v2.endpoints;

import ru.instamart.api.checkpoints.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.requests.v2.SearchesRequest;
import ru.instamart.api.responses.v2.SearchSuggestionsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class SearchesV2Test extends RestBase {

    @CaseId(3)
    @Test(  description = "Получаем поисковые подсказки",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getSearchSuggestions() {
        response = SearchesRequest.Suggestions.GET(1, "");
        InstamartApiCheckpoints.checkStatusCode200(response);
        assertNotNull(response.as(SearchSuggestionsResponse.class).getSuggestion(),
                "Не отображаются поисковые подсказки");
    }
}
