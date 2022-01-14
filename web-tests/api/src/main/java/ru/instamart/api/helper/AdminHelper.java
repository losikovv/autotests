package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.admin.ShippingMethodsRequest;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.MarketingPricers;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.NominalPricers;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.kraken.data.user.UserManager;

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

    public void createStaticPage(PagesAdminRequest.Page page) {
        final Response response = PagesAdminRequest.POST(page);
        checkStatusCode302(response);
    }

    public void deleteStaticPage(Long pageId) {
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

    public ShippingMethodsResponse getShippingMethods() {
        final var response = ShippingMethodsV1Request.GET();
        checkStatusCode200(response);

        return response.as(ShippingMethodsResponse.class);
    }

    public void createShippingMethod(final ShippingMethodsRequest.ShippingMethod shippingMethod) {
        final var response = ShippingMethodsRequest.POST(shippingMethod);
        checkStatusCode302(response);
    }

    public PricersV1Response getMarketingRule(final int methodId) {
        final var response = MarketingPricers.GET(methodId);
        checkStatusCode200(response);

        return response.as(PricersV1Response.class);
    }

    public PricerV1Response createMarketingRule(final int id) {
        final var response = MarketingPricers.POST(id);
        checkStatusCode200(response);

        return response.as(PricerV1Response.class);
    }

    public void deleteMarketingRule(final int id) {
        final var response = MarketingPricers.DELETE(id);
        checkStatusCode200(response);
    }

    public PricersV1Response getNominalRule(final int methodId) {
        final var response = NominalPricers.GET(methodId);
        checkStatusCode200(response);

        return response.as(PricersV1Response.class);
    }

    public PricerV1Response createNominalRule(final int id) {
        final var response = NominalPricers.POST(id);
        checkStatusCode200(response);

        return response.as(PricerV1Response.class);
    }

    public void deleteNominalRule(final int id) {
        final var response = NominalPricers.DELETE(id);
        checkStatusCode200(response);
    }

    public void createPricerRule(final int ruleId, final ShippingMethodsV1Request.Rules rules) {
        final var response = ShippingMethodsV1Request.Rules.POST(ruleId, rules);
        checkStatusCode200(response);
    }

    public void createPricerCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators calculators) {
        final var response = ShippingMethodsV1Request.Calculators.POST(ruleId, calculators);
        checkStatusCode200(response);
    }

    public void updateRule(final int ruleId, final ShippingMethodsV1Request.Rules rules) {
        final var response = ShippingMethodsV1Request.Rules.PUT(ruleId, rules);
        checkStatusCode200(response);
    }

    public void updateCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators calculators) {
        final var response = ShippingMethodsV1Request.Calculators.PUT(ruleId, calculators);
        checkStatusCode200(response);
    }

    @Step("Авторизация администратором")
    public void authAdmin() {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminAllRoles());
    }
    @Step("Авторизация администратором для API")
    public void authAdminApi() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdminAllRoles());
    }
}
