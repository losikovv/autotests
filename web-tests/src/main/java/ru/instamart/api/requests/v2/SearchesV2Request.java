package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class SearchesV2Request {

    public static class Suggestions {
        /**
         * Получение топ поисковых подсказок
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.SUGGESTIONS)
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.Searches.SUGGESTIONS, sid);
        }
        /**
         * Получение поисковых подсказок по слову
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.Suggestions.BY_QUERY)
        public static Response GET(int sid, String query) {
            return givenCatch().get(ApiV2EndPoints.Searches.Suggestions.BY_QUERY, sid, query);
        }
    }
}
