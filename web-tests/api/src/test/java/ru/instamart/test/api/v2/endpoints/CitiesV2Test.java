package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.CityV2;
import ru.instamart.api.request.v2.CitiesV2Request;
import ru.instamart.api.response.v2.CitiesV2Response;
import ru.instamart.jdbc.dao.CitiesDao;
import ru.instamart.jdbc.dao.StoresDao;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;

@Epic("ApiV2")
@Feature("Города")
public class CitiesV2Test extends RestBase {

    @CaseId(2141)
    @Story("Получение городов")
    @Test(description = "Получаем города без параметров с 1 символом в запросе",
            groups = {"api-instamart-regress", "api-instamart-smoke", "api-instamart-prod"})
    public void getCities400() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .keyword("м")
                .build());
        checkStatusCode400(response);
        checkError(response, "keyword должен состоять из 2 или более символов");
    }

    @CaseId(1407)
    @Story("Получение городов")
    @Test(description = "Получаем города без параметров",
            groups = {"api-instamart-regress", "api-instamart-smoke", "api-instamart-prod"})
    public void getCities() {
        final Response response = CitiesV2Request.GET(new CitiesV2Request.CitiesParams());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(10, citiesFromResponse.size());
    }

    @CaseId(1408)
    @Story("Получение городов")
    @Test(description = "Получаем города, где есть самовывоз",
            groups = {"api-instamart-regress"})
    public void getCitiesWithPickup() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .withPickup(1)
                .perPage(1000)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(StoresDao.INSTANCE.getUniqueCitiesCountByShippingMethod(ShippingMethodV2.PICKUP.getMethod()), citiesFromResponse.size());
    }

    @CaseId(1409)
    @Story("Получение городов")
    @Test(description = "Получаем все города",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getCitiesWithPickup")
    public void getAllCities() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .perPage(1000)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(CitiesDao.INSTANCE.getCount(), citiesFromResponse.size());
    }

    @CaseIDs(value = {@CaseId(1410), @CaseId(1411), @CaseId(1412), @CaseId(1413)})
    @Story("Получение городов")
    @Test(description = "Получаем города по имени",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "citiesNameData",
            dataProviderClass = RestDataProvider.class)
    public void getAllCitiesByName(String keyword) {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .keyword(keyword)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        citiesFromResponse.forEach(c -> Assert.assertTrue(c.getName().toLowerCase().contains(keyword.toLowerCase()), "Пришел неверный город"));
    }

    @CaseIDs(value = {@CaseId(1414), @CaseId(1416)})
    @Story("Получение городов")
    @Test(description = "Получаем все города с невалидными параметрами",
            groups = {"api-instamart-regress"},
            dataProvider = "citiesInvalidParams",
            dataProviderClass = RestDataProvider.class)
    public void getAllCitiesWithInvalidParams(CitiesV2Request.CitiesParams params) {
        final Response response = CitiesV2Request.GET(params);
        checkStatusCode200(response);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(citiesFromResponse.size(), 0);
    }

    @CaseId(1415)
    @Story("Получение городов")
    @Test(description = "Получаем все города с определенной страницы",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getAllCitiesForPage() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .page(2)
                .perPage(3)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        CitiesV2Response citiesFromResponse = response.as(CitiesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(citiesFromResponse.getCities().size(), 3, softAssert);
        compareTwoObjects(citiesFromResponse.getMeta().getPerPage(), 3, softAssert);
        compareTwoObjects(citiesFromResponse.getMeta().getCurrentPage(), 2, softAssert);
        softAssert.assertAll();
    }
}
