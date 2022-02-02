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
import ru.sbermarket.common.Mapper;

import java.util.HashMap;
import java.util.Map;

public class ManufacturingCountriesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.MANUFACTURING_COUNTRIES)
    public static Response POST(ManufacturingCountry country) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(country))
                .post(AdminEndpoints.MANUFACTURING_COUNTRIES);
    }

    @Step("{method} /" + AdminEndpoints.ManufacturingCountries.BY_PERMALINK)
    public static Response DELETE(String countryPermalink) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .post(AdminEndpoints.ManufacturingCountries.BY_PERMALINK, countryPermalink);
    }

    @Step("{method} /" + AdminEndpoints.ManufacturingCountries.BY_PERMALINK)
    public static Response PATCH(ManufacturingCountry country, String countryPermalink) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.putAll(Mapper.INSTANCE.objectToMap(country));
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.ManufacturingCountries.BY_PERMALINK, countryPermalink);
    }

    @Step("{method} /" + AdminEndpoints.MANUFACTURING_COUNTRIES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.MANUFACTURING_COUNTRIES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ManufacturingCountry {
        @JsonProperty("manufacturing_country[name]")
        private String name;
        @JsonProperty("manufacturing_country[permalink]")
        private String permalink;
    }
}
