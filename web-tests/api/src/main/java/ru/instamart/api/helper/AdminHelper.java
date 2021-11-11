package ru.instamart.api.helper;

import io.restassured.response.Response;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.kraken.data.StaticPageData;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
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

    public void createStaticPage(StaticPageData data) {
        Response response = PagesAdminRequest.POST(data);
        checkStatusCode302(response);
    }

    public void deleteStaticPage(Integer pageId) {
        Response response = PagesAdminRequest.DELETE(pageId);
        checkStatusCode302(response);
    }

    public void addCompany(final String inn, final String companyName, final String ownerEmail) {
        final Response response = CompaniesV1Request.POST(inn, companyName, ownerEmail);
        checkStatusCode200(response);
    }
}
