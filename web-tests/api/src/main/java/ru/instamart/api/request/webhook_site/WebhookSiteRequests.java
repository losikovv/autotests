package ru.instamart.api.request.webhook_site;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.WebhookSiteEndpoints;
import ru.instamart.api.request.WebhookSiteBase;
import ru.sbermarket.common.Mapper;

import java.util.Objects;

public class WebhookSiteRequests extends WebhookSiteBase {
    @Step("{method} /" + WebhookSiteEndpoints.Token.REQUESTS)
    public static Response GET(final String token, final RequestParams params) {
        RequestSpecification requestSpecification = givenWithSpec();
        if (Objects.nonNull(params)) {
            requestSpecification.queryParams(Mapper.INSTANCE.objectToMap(params));
        }
        return requestSpecification
                .get(WebhookSiteEndpoints.Token.REQUESTS, token);
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class RequestParams {
        /**
         * either newest or oldest (default)
         */
        private final Sorting sorting;
        /**
         * amount of requests returned, defaults to 50 (max 100)
         */
        @JsonProperty("per_page")
        private final int perPage;
        /**
         * page number to retrieve (default 1)
         */
        private final int page;
        /**
         * filter requests by date, format yyyy-MM-dd HH:mm:ss
         */
        @JsonProperty("date_from")
        private final int dateFrom;
        /**
         * filter requests by date, format yyyy-MM-dd HH:mm:ss
         */
        @JsonProperty("date_to")
        private final int dateTo;
        /**
         * filter requests by a query string search (see below for examples)
         */
        private final String query;

    }

    public enum Sorting {
        NEWEST,
        OLDEST
    }
}
