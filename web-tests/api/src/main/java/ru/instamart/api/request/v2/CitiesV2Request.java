package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.sbermarket.common.Mapper;

public class CitiesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.CITIES)
    public static Response GET(CitiesParams params) {
        return givenWithSpec()
                .queryParams(Mapper.INSTANCE.objectToMap(params))
                .get(ApiV2EndPoints.CITIES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CitiesParams {
        private String keyword;
        @JsonProperty(value = "with_pickup")
        private Integer withPickup;
        private Integer page;
        @JsonProperty(value = "per_page")
        private Integer perPage;
    }
}
