package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.instamart.utils.Mapper;

import java.util.HashMap;
import java.util.Map;

public class CitiesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.CITIES)
    public static Response POST(City city) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(city))
                .post(AdminEndpoints.CITIES);
    }

    @Step("{method} /" + AdminEndpoints.CITY)
    public static Response DELETE(String citySlug) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .delete(AdminEndpoints.CITY, citySlug);
    }

    @Step("{method} /" + AdminEndpoints.CITY)
    public static Response PATCH(City city, String citySlug) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.putAll(Mapper.INSTANCE.objectToMap(city));
        return givenWithAuth()
                .formParams(params)
                .patch(AdminEndpoints.CITY, citySlug);
    }

    @Step("{method} /" + AdminEndpoints.CITIES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.CITIES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class City {
        @JsonProperty("city[name]")
        private String name;
        @JsonProperty("city[name_in]")
        private String nameIn;
        @JsonProperty("city[name_from]")
        private String nameFrom;
        @JsonProperty("city[name_to]")
        private String nameTo;
        @JsonProperty("city[slug]")
        private String slug;
        @JsonProperty("city[locked]")
        private Integer locked;

        public City(String name) {
            this.name = name;
        }
    }
}
