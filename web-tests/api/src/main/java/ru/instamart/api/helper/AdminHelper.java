package ru.instamart.api.helper;

import io.restassured.response.Response;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.response.v1.OperationalZoneV1Response;
import ru.instamart.api.response.v1.DeliveryWindowsV1Response;
import ru.instamart.kraken.data.StaticPageData;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;


public class AdminHelper {

    public void createCity(CitiesAdminRequest.City city) {
        final Response response = CitiesAdminRequest.POST(city);
        checkStatusCode302(response);
    }

    public void deleteCity(String cityName) {
        final Response response = CitiesAdminRequest.DELETE(cityName);
        checkStatusCode302(response);
    }

    public void createStaticPage(StaticPageData data) {
        final Response response = PagesAdminRequest.POST(data);
        checkStatusCode302(response);
    }

    public void deleteStaticPage(Integer pageId) {
        final Response response = PagesAdminRequest.DELETE(pageId);
        checkStatusCode302(response);
    }

    public void addCompany(final String inn, final String companyName, final String ownerEmail) {
        final Response response = CompaniesV1Request.POST(inn, companyName, ownerEmail);
        checkStatusCode200(response);
    }

    public void checkDeliveryWindows(Integer storeId) {
        final Response responseForGet = StoresV1Request.DeliveryWindows.GET(storeId);
        checkStatusCode200(responseForGet);
        DeliveryWindowsV1Response deliveryWindowsV1Response = responseForGet.as(DeliveryWindowsV1Response.class);
        if (deliveryWindowsV1Response.getDeliveryWindows().size() == 0) {
            final Response responseForGenerate = StoresV1Request.DeliveryWindows.POST(storeId);
            checkStatusCode200(responseForGenerate);
        }
    }

    public OperationalZoneV1 addOperationalZones(String zoneName) {
        final Response response = OperationalZonesV1Request.POST(zoneName);
        checkStatusCode200(response);
        return response.as(OperationalZoneV1Response.class).getOperationalZone();
    }
}
