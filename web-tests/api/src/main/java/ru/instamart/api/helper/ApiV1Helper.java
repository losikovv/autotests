package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.response.v1.OperationalZonesV1Response;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class ApiV1Helper {

    @Step("Получаем список всех операционных зон")
    public List<OperationalZoneV1> getAllOperationalZones() {
        log.debug("Получаем список всех операционных зон");
        Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        return response.as(OperationalZonesV1Response.class).getOperationalZones();
    }
}
