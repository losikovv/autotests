package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.Objects;

public class NotificationsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.NOTIFICATIONS)
    public static Response POST(Events events) {
        RequestSpecification body = givenWithAuth();
        if (Objects.nonNull(events)) {
            body.body(events);
        }
        return body
                .post(ApiV2EndPoints.NOTIFICATIONS);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Events {
        @JsonProperty("event")
        private Event event;

        @JsonProperty("required")
        private String required;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Event {

        @JsonProperty("payload")
        private Payload payload;

        @JsonProperty("type")
        private String type;

        @JsonProperty("required")
        private String required;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Payload {

        @JsonProperty("order_id")
        private String orderId;
    }
}
