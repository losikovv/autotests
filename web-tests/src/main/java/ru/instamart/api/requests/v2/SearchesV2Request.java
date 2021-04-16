package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class SearchesV2Request extends ApiV2RequestBase {

    public static class Suggestions {
        /**
         * Получение топ поисковых подсказок
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.SUGGESTIONS)
        public static Response GET(int sid) {
            return givenWithSpec().get(ApiV2EndPoints.Searches.SUGGESTIONS, sid);
        }
        /**
         * Получение поисковых подсказок по слову
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.Suggestions.BY_QUERY)
        public static Response GET(int sid, String query) {
            return givenWithSpec().get(ApiV2EndPoints.Searches.Suggestions.BY_QUERY, sid, query);
        }
    }
}
