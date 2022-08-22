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
import ru.instamart.kraken.common.Mapper;

import java.util.HashMap;
import java.util.Map;

public class ZonesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.ZONES)
    public static Response POST(Zone zone) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(zone))
                .post(AdminEndpoints.ZONES);
    }

    @Step("{method} /" + AdminEndpoints.ZONE)
    public static Response DELETE(Long zoneId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .post(AdminEndpoints.ZONE, zoneId);
    }

    @Step("{method} /" + AdminEndpoints.ZONE)
    public static Response PATCH(Zone zone, Long zoneId) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.putAll(Mapper.INSTANCE.objectToMap(zone));
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.ZONE, zoneId);
    }

    @Step("{method} /" + AdminEndpoints.ZONES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.ZONES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class Zone {

        @JsonProperty(value = "zone[name]")
        private String zoneName;
        @JsonProperty(value = "zone[description]")
        private String zoneDescription;
        @JsonProperty(value = "zone[kind]")
        private String zoneKind;
        @JsonProperty(value = "zone[state_ids][]")
        private Long zoneStateId;
        @JsonProperty(value = "zone[country_ids][]")
        private Long zoneCountryId;
    }
}
