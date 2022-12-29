package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.*;
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
import ru.instamart.jdbc.dao.stf.StoresDao;

import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;
import static ru.instamart.kraken.config.EnvironmentProperties.Env.isProduction;

@Epic("ApiV2")
@Feature("Города")
public class CitiesV2Test extends RestBase {

    @TmsLink("2141")
    @Story("Получение городов")
    @Test(description = "Получаем города без параметров с 1 символом в запросе",
            groups = {API_INSTAMART_REGRESS, "api-instamart-smoke", API_INSTAMART_PROD, "api-v2", "api-bff"})
    public void getCities400() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .keyword("м")
                .build());
        checkStatusCode400(response);
        checkError(response, "keyword должен состоять из 2 или более символов");
    }

    @TmsLink("1407")
    @Story("Получение городов")
    @Test(description = "Получаем города без параметров",
            groups = {API_INSTAMART_REGRESS, "api-instamart-smoke", API_INSTAMART_PROD, "api-v2", "api-bff"})
    public void getCities() {
        final Response response = CitiesV2Request.GET(new CitiesV2Request.CitiesParams());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(10, citiesFromResponse.size());
    }

    @TmsLink("1408")
    @Story("Получение городов")
    @Test(description = "Получаем города, где есть самовывоз",
            groups = {API_INSTAMART_REGRESS, "api-v2", API_INSTAMART_PROD})
    public void getCitiesWithPickup() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .withPickup(1)
                .perPage(1000)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
        if (!isProduction()) {
            List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
            compareTwoObjects(StoresDao.INSTANCE.getUniqueCitiesCountByShippingMethod(ShippingMethodV2.PICKUP.getMethod()), citiesFromResponse.size());
        }
    }

    @TmsLink("1409")
    @Story("Получение городов")
    @Test(description = "Получаем все города",
            groups = {API_INSTAMART_REGRESS, "api-v2", API_INSTAMART_PROD, "api-bff"}
            //, dependsOnMethods = "getCitiesWithPickup"
    )
    public void getAllCities() {
        final Response response = CitiesV2Request.GET(CitiesV2Request.CitiesParams.builder()
                .perPage(1000)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV2Response.class);
//       todo считать только включенные города из базы
//        if (!isProduction()) {
//            List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
//            compareTwoObjects(CitiesDao.INSTANCE.getCount(), citiesFromResponse.size());
//        }
    }

    @TmsLinks(value = {@TmsLink("1410"), @TmsLink("1411"), @TmsLink("1412"), @TmsLink("1413")})
    @Story("Получение городов")
    @Test(description = "Получаем города по имени",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
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

    @TmsLinks(value = {@TmsLink("1414"), @TmsLink("1416")})
    @Story("Получение городов")
    @Test(description = "Получаем все города с невалидными параметрами",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            dataProvider = "citiesInvalidParams",
            dataProviderClass = RestDataProvider.class)
    public void getAllCitiesWithInvalidParams(CitiesV2Request.CitiesParams params) {
        final Response response = CitiesV2Request.GET(params);
        checkStatusCode200(response);
        List<CityV2> citiesFromResponse = response.as(CitiesV2Response.class).getCities();
        compareTwoObjects(citiesFromResponse.size(), 0);
    }

    @TmsLink("1415")
    @Story("Получение городов")
    @Test(description = "Получаем все города с определенной страницы",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"})
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
