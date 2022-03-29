package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.CityV1;
import ru.instamart.api.request.v1.CitiesV1Request;
import ru.instamart.api.response.v1.CitiesV1Response;
import ru.instamart.api.response.v1.CityV1Response;
import ru.instamart.jdbc.dao.CitiesDao;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkCity;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Города")
public class CitiesV1Test extends RestBase {

    private Long cityId;
    private Long lockedCityId;
    private String cityName;
    private CitiesV1Request.City city;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(1133)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка всех городов")
    public void getAllCities() {
        final Response response = CitiesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CitiesV1Response.class);
        List<CityV1> cities = response.as(CitiesV1Response.class).getCities();
        int citiesCountFromDb = CitiesDao.INSTANCE.getCount();
        compareTwoObjects(cities.size(), citiesCountFromDb);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1124)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города c обязательными параметрами")
    public void createCityWithRequiredParams() {
        cityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesV1Request.POST(CitiesV1Request.City.builder()
                .name(cityName)
                .build());
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, CityV1Response.class);
        CityV1 cityFromResponse = response.as(CityV1Response.class).getCity();
        cityId = cityFromResponse.getId();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(cityFromResponse.getName(), cityName, softAssert);
        compareTwoObjects(cityFromResponse.getSlug(), cityName.toLowerCase(), softAssert);
        compareTwoObjects(cityFromResponse.getLocked(), false, softAssert);
        softAssert.assertAll();
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1125)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города со всеми возможными параметрами")
    public void createCityWithAllParams() {
        CitiesV1Request.City city = CitiesV1Request.City.builder()
                .name("Autotest-" + Generate.literalString(6))
                .nameFrom("Autotest-" + Generate.literalString(6))
                .nameTo("Autotest-" + Generate.literalString(6))
                .nameIn("Autotest-" + Generate.literalString(6))
                .slug("Autotest-" + Generate.literalString(6))
                .build();
        final Response response = CitiesV1Request.POST(city);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, CityV1Response.class);
        CityV1 cityFromResponse = response.as(CityV1Response.class).getCity();
        lockedCityId = cityFromResponse.getId();
        checkCity(cityFromResponse, city);
    }

    @CaseId(1126)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города c пустыми обязательными параметрами")
    public void createCityWithEmptyRequiredParams() {
        final Response response = CitiesV1Request.POST(CitiesV1Request.City.builder().build());
        checkStatusCode400(response);
        checkErrorText(response, "Не указан обязательный параметр city");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1127)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города с обязательными параметрами",
            dependsOnMethods = "createCityWithRequiredParams")
    public void editCityWithRequiredParams() {
        String updatedCityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesV1Request.PUT(CitiesV1Request.City.builder()
                .name(updatedCityName)
                .build(), cityId);
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, CityV1Response.class);
        CityV1 cityFromResponse = response.as(CityV1Response.class).getCity();
        cityId = cityFromResponse.getId();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(cityFromResponse.getName(), updatedCityName, softAssert);
        compareTwoObjects(cityFromResponse.getSlug(), cityName.toLowerCase(), softAssert);
        compareTwoObjects(cityFromResponse.getLocked(), false, softAssert);
        softAssert.assertAll();
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1128)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города c пустыми обязательными параметрами",
            dependsOnMethods = "createCityWithRequiredParams")
    public void editCityWithEmptyRequiredParams() {
        final Response response = CitiesV1Request.PUT(CitiesV1Request.City.builder().build(), cityId);
        checkStatusCode400(response);
        checkErrorText(response, "Не указан обязательный параметр city");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1129)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города со всеми возможными параметрами",
            dependsOnMethods = {"editCityWithRequiredParams", "editCityWithEmptyRequiredParams"})
    public void editCityWithAllParams() {
        city = CitiesV1Request.City.builder()
                .name("Autotest-" + Generate.literalString(6))
                .nameFrom("Autotest-" + Generate.literalString(6))
                .nameTo("Autotest-" + Generate.literalString(6))
                .nameIn("Autotest-" + Generate.literalString(6))
                .slug("Autotest-" + Generate.literalString(6))
                .build();
        final Response response = CitiesV1Request.PUT(city, cityId);
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, CityV1Response.class);
        CityV1 cityFromResponse = response.as(CityV1Response.class).getCity();
        checkCity(cityFromResponse, city);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2470)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование несуществующего города")
    public void editNonExistentCity() {
        final Response response = CitiesV1Request.PUT(CitiesV1Request.City.builder().build(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2466)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Блокировка города для изменений города",
            dependsOnMethods = "createCityWithAllParams")
    public void lockCity() {
        final Response response = CitiesV1Request.PUT(lockedCityId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CityV1Response.class);
        compareTwoObjects(response.as(CityV1Response.class).getCity().getLocked(), true);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1130)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Блокировка города для изменений города",
            dependsOnMethods = "lockCity")
    public void editLockedCity() {
        final Response response = CitiesV1Request.PUT(CitiesV1Request.City.builder()
                .name("Autotest-" + Generate.literalString(6))
                .build(), lockedCityId);
        checkStatusCode403(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2467)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение города",
            dependsOnMethods = "editCityWithAllParams")
    public void getCity() {
        final Response response = CitiesV1Request.GET(cityId);
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, CityV1Response.class);
        CityV1 cityFromResponse = response.as(CityV1Response.class).getCity();
        checkCity(cityFromResponse, city);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2469)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего города")
    public void getNonExistentCity() {
        final Response response = CitiesV1Request.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1131)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление города",
            dependsOnMethods = "getCity")
    public void deleteCity() {
        final Response response = CitiesV1Request.DELETE(cityId);
        checkStatusCode(response, 204);
        Assert.assertTrue(CitiesDao.INSTANCE.findById(cityId).isEmpty(), "Город не удалился");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(1132)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление заблокированного для изменений города",
            dependsOnMethods = "editLockedCity")
    public void deleteLockedCity() {
        final Response response = CitiesV1Request.DELETE(lockedCityId);
        checkStatusCode403(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
        Assert.assertTrue(CitiesDao.INSTANCE.findById(lockedCityId).isPresent(), "Город удалился");;
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2471)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующего города")
    public void deleteNonExistentCity() {
        final Response response = CitiesV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @AfterClass(alwaysRun = true)
    public void clearData() {
        if (Objects.nonNull(lockedCityId)) CitiesDao.INSTANCE.delete(lockedCityId);
    }
}
