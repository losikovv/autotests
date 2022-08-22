package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.util.Objects;

public final class SearchesV2Request extends ApiV2RequestBase {

    public static class Suggestions {
        /**
         * Получение топ поисковых подсказок
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.SUGGESTIONS)
        public static Response GET(int sid) {
            return GET(sid, null);
        }

        /**
         * Получение поисковых подсказок по слову
         */
        @Step("{method} /" + ApiV2EndPoints.Searches.SUGGESTIONS)
        public static Response GET(int sid, String query) {
            final JSONObject jsonObject = new JSONObject();
            if (Objects.nonNull(sid)) jsonObject.put("sid", sid);
            if (Objects.nonNull(query)) jsonObject.put("q", query);
            return givenWithSpec()
                    .queryParams(Mapper.INSTANCE.objectToMap(jsonObject))
                    .get(ApiV2EndPoints.Searches.SUGGESTIONS);
        }

        public static class TopPhrases {

            @Step("{method} /" + ApiV2EndPoints.Searches.Suggestions.TOP_PHRASES)
            public static Response GET() {
                return givenWithAuth()
                        .get(ApiV2EndPoints.Searches.Suggestions.TOP_PHRASES);
            }

            @Step("{method} /" + ApiV2EndPoints.Searches.Suggestions.TOP_PHRASES)
            public static Response GET(int sid) {
                return givenWithAuth()
                        .queryParam("sid", sid)
                        .get(ApiV2EndPoints.Searches.Suggestions.TOP_PHRASES);
            }
        }
    }
}
