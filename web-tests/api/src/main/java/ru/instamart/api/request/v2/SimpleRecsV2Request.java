package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.utils.Mapper;

public class SimpleRecsV2Request extends ApiV2RequestBase {

    public static class Personal {
        @Step("{method} /" + ApiV2EndPoints.SimpleRecs.PERSONAL)
        public static Response POST(SimpleRecsV2 simpleRecs) {
            return givenWithSpec()
                    .body(Mapper.INSTANCE.objectToMap(simpleRecs))
                    .contentType(ContentType.JSON)
                    .post(ApiV2EndPoints.SimpleRecs.PERSONAL);
        }
    }

    public static class Complementary {
        @Step("{method} /" + ApiV2EndPoints.SimpleRecs.COMPLEMENTARY)
        public static Response POST(SimpleRecsV2 simpleRecs) {
            return givenWithSpec()
                    .body(Mapper.INSTANCE.objectToMap(simpleRecs))
                    .contentType(ContentType.JSON)
                    .post(ApiV2EndPoints.SimpleRecs.COMPLEMENTARY);
        }
    }

    public static class Substitute {
        @Step("{method} /" + ApiV2EndPoints.SimpleRecs.SUBSTITUTE)
        public static Response POST(SimpleRecsV2 simpleRecs) {
            return givenWithSpec()
                    .body(Mapper.INSTANCE.objectToMap(simpleRecs))
                    .contentType(ContentType.JSON)
                    .post(ApiV2EndPoints.SimpleRecs.SUBSTITUTE);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class App {
        private AppExt ext;
        private String domain;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
     public static class AppExt {
        @JsonProperty("category_id")
        private String categoryId;
        @JsonProperty("store_id")
        private Integer storeId;
        @JsonProperty("tenant_id")
        private Integer tenantId;
        private String sku;
        @JsonProperty("order_number")
        private String orderNumber;
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
        @JsonProperty("place")
        private String place;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Geo {
        private Double lon;
        private Double lat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SimpleRecsV2 {
        private Ext ext;
        private Context context;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class User {
        private String id;
        private Geo geo;
        private UserExt ext;
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
}
