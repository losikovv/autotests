package ru.instamart.api.request.surgelevel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import ru.instamart.api.endpoint.SurgelevelEndpoints;
import ru.instamart.api.request.SurgelevelRequestBase;

import java.util.List;

public class BoostSurgeRequest extends SurgelevelRequestBase {

    @Step("{method} /" + SurgelevelEndpoints.BOOST)
    public static Response POST(final PostBoost postBoost) {
        return givenWithAuth()
                .body(postBoost)
                .contentType(ContentType.JSON)
                .post(SurgelevelEndpoints.BOOST);
    }

    @Step("{method} /" + SurgelevelEndpoints.BOOST)
    public static Response POST_NO_AUTH(final PostBoost postBoost) {
        return givenWithSpec()
                .body(postBoost)
                .contentType(ContentType.JSON)
                .post(SurgelevelEndpoints.BOOST);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class PostBoost {
        @JsonProperty("store_uuids")
        @Singular
        private final List<String> storeUuids;
        private final String action;
        private final Integer duration;
    }
}
