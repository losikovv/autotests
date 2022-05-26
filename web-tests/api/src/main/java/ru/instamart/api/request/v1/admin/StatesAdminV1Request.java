package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.Objects;

public class StatesAdminV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.Countries.STATES)
    public static Response POST(Long countryId, String stateName, String abbreviation) {
        JSONObject body = new JSONObject();
        JSONObject state = new JSONObject();
        if(Objects.nonNull(stateName)) state.put("name", stateName);
        if(Objects.nonNull(abbreviation)) state.put("abbr", abbreviation);
        body.put("state", state);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.Countries.STATES, countryId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Countries.STATE)
    public static Response DELETE(Long countryId, Long stateId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.Countries.STATE, countryId, stateId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Countries.STATE)
    public static Response PATCH(Long countryId, Long stateId, String stateName, String abbreviation) {
        JSONObject body = new JSONObject();
        JSONObject state = new JSONObject();
        if(Objects.nonNull(stateName)) state.put("name", stateName);
        if(Objects.nonNull(abbreviation)) state.put("abbr", abbreviation);
        body.put("state", state);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(ApiV1Endpoints.Admin.Countries.STATE, countryId, stateId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Countries.STATES)
    public static Response GET(Long countryId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Countries.STATES, countryId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.Countries.STATE)
    public static Response GET(Long countryId, Long stateId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.Countries.STATE, countryId, stateId);
    }
}
