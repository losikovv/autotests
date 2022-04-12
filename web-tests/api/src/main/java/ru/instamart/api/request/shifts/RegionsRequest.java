package ru.instamart.api.request.shifts;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShiftsV1Endpoints;
import ru.instamart.api.request.ShiftsRequestBase;

public class RegionsRequest extends ShiftsRequestBase {
    @Step("{method} /" + ShiftsV1Endpoints.Regions.PLANNING_AREAS)
    public static Response GET(long id) {
        return givenWithAuth()
                .get(ShiftsV1Endpoints.Regions.PLANNING_AREAS, id);
    }
}
