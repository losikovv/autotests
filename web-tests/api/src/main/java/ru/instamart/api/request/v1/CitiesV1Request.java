package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;


public class CitiesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.CITIES)
    public static Response POST(CitiesV1Request.City city) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(city)
                .post(ApiV1Endpoints.Admin.CITIES);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Cities.CITY)
    public static Response DELETE(Long cityId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.Cities.CITY, cityId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Cities.CITY)
    public static Response PUT(CitiesV1Request.City city, Long cityId) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(city)
                .put(ApiV1Endpoints.Admin.Cities.CITY, cityId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Cities.LOCK)
    public static Response PUT(Long cityId) {
        return givenWithAuth()
                .put(ApiV1Endpoints.Admin.Cities.LOCK, cityId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Cities.CITY)
    public static Response GET(Long cityId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Cities.CITY, cityId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.CITIES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.CITIES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class City {
        private String name;
        @JsonProperty("name_in")
        private String nameIn;
        @JsonProperty("name_from")
        private String nameFrom;
        @JsonProperty("name_to")
        private String nameTo;
        private String slug;
    }
}
