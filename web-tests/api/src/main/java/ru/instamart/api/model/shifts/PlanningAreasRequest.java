package ru.instamart.api.model.shifts;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ShiftsV1Endpoints;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.request.ShiftsRequestBase;

import java.util.Objects;

import static ru.instamart.kraken.util.TimeUtil.*;

public class PlanningAreasRequest extends ShiftsRequestBase {

    @Step("{method} /" + ShiftsV1Endpoints.PlanningAreas.PLANNING_PERIODS)
    public static Response GET(final long id) {
        return GET(id, null);
    }

    @Step("{method} /" + ShiftsV1Endpoints.PlanningAreas.PLANNING_PERIODS)
    public static Response GET(final long id, final RoleSHP role) {
        RequestSpecification given = givenWithAuth();
        if(Objects.nonNull(role)) {
            given.queryParam("role", role.getRole());
        }
        return given
                .queryParam("started_at", getZonedDate())
                .queryParam("ended_at", getZonedFutureDate(1L))
                .get(ShiftsV1Endpoints.PlanningAreas.PLANNING_PERIODS, id);
    }
}
