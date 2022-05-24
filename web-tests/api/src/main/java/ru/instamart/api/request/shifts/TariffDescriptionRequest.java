package ru.instamart.api.request.shifts;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShiftsV1Endpoints;
import ru.instamart.api.request.ShiftsRequestBase;

public class TariffDescriptionRequest extends ShiftsRequestBase {

    @Step("{method} /" + ShiftsV1Endpoints.Shifts.TARIFF_DESCRIPTION)
    public static Response GET() {
        return givenWithAuth()
                .get(ShiftsV1Endpoints.Shifts.TARIFF_DESCRIPTION);
    }
}
