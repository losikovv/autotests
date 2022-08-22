package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.common.Mapper;

public class SimpleAdsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.SIMPLE_ADS)
    public static Response POST(SimpleAdsV2 simpleAdsV2) {
        return givenWithAuth()
                .body(Mapper.INSTANCE.objectToMap(simpleAdsV2))
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.SIMPLE_ADS);
    }

    @Step("{method} /" + ApiV2EndPoints.SIMPLE_ADS)
    public static Response POST(SimpleAdsV2 simpleAdsV2, Header header) {
        return givenWithAuth()
                .body(Mapper.INSTANCE.objectToMap(simpleAdsV2))
                .header(header)
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.SIMPLE_ADS);
    }

    @Step("{method} /" + ApiV2EndPoints.SIMPLE_ADS)
    public static Response POST(SimpleAdsV2 simpleAdsV2, Headers headers) {
        return givenWithAuth()
                .body(Mapper.INSTANCE.objectToMap(simpleAdsV2))
                .headers(headers)
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.SIMPLE_ADS);
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SimpleAdsV2 {
        private Ext ext;
        private Context context;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class App {
        private AppExt ext;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Context {
        private App app;
        private User user;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Ext {
        @JsonProperty("native_ad_type_id")
        private String nativeAdTypeId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class AppExt {
        @JsonProperty("store_id")
        private int storeId;
        @JsonProperty("tenant_id")
        private int tenantId;
        @JsonProperty("category_id")
        private int categoryId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UserExt {
        @JsonProperty("anonymous_id")
        private String anonymousId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Geo {
        private double lon;
        private double lat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class User {
        private Geo geo;
        private UserExt ext;
    }
}
