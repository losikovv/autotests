package ru.instamart.api.helper;

import io.restassured.response.Response;
import ru.instamart.api.request.admin.CitiesAdminRequest;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;


public class AdminHelper {

    public void createCity(String cityName) {
        Response response = CitiesAdminRequest.POST(cityName);
        checkStatusCode302(response);
    }

    public void deleteCity(String cityName) {
        Response response = CitiesAdminRequest.DELETE(cityName);
        checkStatusCode302(response);
    }
}
