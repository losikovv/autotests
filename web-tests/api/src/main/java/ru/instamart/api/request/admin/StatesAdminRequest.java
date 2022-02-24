package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.Countries.STATES)
    public static Response POST(Long countryId, String stateName, String abbreviation) {
        Map<String, String> params = new HashMap<>();
        if(Objects.nonNull(stateName)) params.put("state[name]", stateName);
        if(Objects.nonNull(abbreviation)) params.put("state[abbr]", abbreviation);
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.Countries.STATES, countryId);
    }

    @Step("{method} /" + AdminEndpoints.Countries.STATE)
    public static Response DELETE(Long countryId, Long stateId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .post(AdminEndpoints.Countries.STATE, countryId, stateId);
    }

    @Step("{method} /" + AdminEndpoints.Countries.STATE)
    public static Response PATCH(Long countryId, Long stateId, String stateName, String abbreviation) {
        Map<String, Object> params = new HashMap<>();
        if(Objects.nonNull(stateName)) params.put("state[name]", stateName);
        if(Objects.nonNull(abbreviation)) params.put("state[abbr]", abbreviation);
        params.put("_method", "patch");
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.Countries.STATE, countryId, stateId);
    }

    @Step("{method} /" + AdminEndpoints.Countries.STATES)
    public static Response GET(Long countryId) {
        return givenWithAuth()
                .get(AdminEndpoints.Countries.STATES, countryId);
    }
}
