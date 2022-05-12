package ru.instamart.api.request.v1.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.List;
import java.util.Objects;

public class UsersV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.USERS)
    public static Response GET(String query) {
        RequestSpecification requestSpecification = givenWithAuth();
        if (Objects.nonNull(query)) {
            requestSpecification.queryParam("search", query);
        }
        return requestSpecification.get(ApiV1Endpoints.Admin.USERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.USERS)
    public static Response POST(UserRequest user) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(user)
                .post(ApiV1Endpoints.Admin.USERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Users.BY_ID)
    public static Response PUT(UserRequest user, Long userId) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(user)
                .put(ApiV1Endpoints.Admin.Users.BY_ID, userId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Users.BY_ID)
    public static Response DELETE(Long userId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.Users.BY_ID, userId);
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserRequest {
        private User user;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class User {
        private String email;
        private Boolean b2b;
        @JsonProperty("preferred_card_payment_method")
        private String preferredCardPaymentMethod;
        @JsonProperty("customer_comment")
        private String customerComment;
        @JsonProperty("role_ids")
        private List<Long> roleIds;
        private String password;
        @JsonProperty("password_confirmation")
        private String passwordConfirmation;
        @JsonProperty("config_attriburtes")
        private ConfigAttributes configAttributes;
        @JsonProperty("promo_terms_accepted")
        private Boolean promoTermsAccepted;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ConfigAttributes {
        @JsonProperty("send_emails")
        private Boolean sendEmails;
    }
}
