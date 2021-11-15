package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class CitiesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.CITIES)
    public static Response POST(String cityName) {
        Map<String, String> params = new HashMap<>();
        params.put("utf-8", "✓");
        params.put("authenticity_token", SessionFactory.getSession(SessionType.ADMIN).getToken());
        params.put("city[name]", cityName);
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.CITIES);
    }

    @Step("{method} /" + AdminEndpoints.CITY)
    public static Response DELETE(String cityName) {
        Map<String, String> params = new HashMap<>();
        params.put("authenticity_token", SessionFactory.getSession(SessionType.ADMIN).getToken());
        params.put("_method", "delete");
        return givenWithAuth()
                .formParams(params)
                .delete(AdminEndpoints.CITY, cityName);
    }
}
