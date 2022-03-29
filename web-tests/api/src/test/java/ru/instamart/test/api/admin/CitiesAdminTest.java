package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.jdbc.dao.CitiesDao;
import ru.instamart.jdbc.entity.CitiesEntity;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkCityInDb;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Города")
public class CitiesAdminTest extends RestBase {

    private String cityName;
    private String updatedCityName;
    private String updatedCitySlug;
    private String lockedCityName;
    private String lockedCitySlug;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1133)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка всех городов")
    public void getAllCities() {
        final Response response = CitiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {}, description = "Создание города c обязательными параметрами")
    public void createCityWithRequiredParams() {
        cityName = "Autotest-" + Generate.literalString(6);
        admin.createCity(new CitiesAdminRequest.City(cityName));
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(cityName);
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(cityName.toLowerCase(), cityFromDb.getSlug(), softAssert);
        compareTwoObjects(0, cityFromDb.getLocked(), softAssert);
        softAssert.assertAll();
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {}, description = "Создание города со всеми возможными параметрами")
    public void createCityWithAllParams() {
        lockedCityName = "Autotest-" + Generate.literalString(6);
        lockedCitySlug = "Autotest-" + Generate.literalString(6);
        CitiesAdminRequest.City city = CitiesAdminRequest.City.builder()
                .name(lockedCityName)
                .nameFrom("Autotest-" + Generate.literalString(6))
                .nameTo("Autotest-" + Generate.literalString(6))
                .nameIn("Autotest-" + Generate.literalString(6))
                .slug(lockedCitySlug)
                .locked(1)
                .build();
        admin.createCity(city);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(lockedCityName);
        checkCityInDb(city, cityFromDb);
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {}, description = "Создание города c пустыми обязательными параметрами")
    public void createCityWithEmptyRequiredParams() {
        final Response response = CitiesAdminRequest.POST(new CitiesAdminRequest.City());
        checkStatusCode400(response);
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Редактирование города с обязательными параметрами")
    public void editCityWithRequiredParams() {
        String updatedCityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(updatedCityName), cityName);
        checkStatusCode302(response);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(updatedCityName);
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Редактирование города c пустыми обязательными параметрами")
    public void editCityWithEmptyRequiredParams() {
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(), cityName);
        checkStatusCode400(response);
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Редактирование города со всеми возможными параметрами")
    public void editCityWithAllParams() {
        updatedCityName = "Autotest-" + Generate.literalString(6);
        updatedCitySlug = "Autotest-" + Generate.literalString(6);
        CitiesAdminRequest.City city = CitiesAdminRequest.City.builder()
                .name(updatedCityName)
                .nameFrom("Autotest-" + Generate.literalString(6))
                .nameTo("Autotest-" + Generate.literalString(6))
                .nameIn("Autotest-" + Generate.literalString(6))
                .slug(updatedCitySlug)
                .locked(0)
                .build();
        final Response response = CitiesAdminRequest.PATCH(city, cityName);
        checkStatusCode302(response);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(updatedCityName);
        checkCityInDb(city, cityFromDb);
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Редактирование заблокированного для изменений города")
    public void editLockedCity() {
        String cityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(cityName), lockedCitySlug);
        checkStatusCode302(response);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(cityName);
        Assert.assertNull(cityFromDb, "Заблокированный для редактирования город обновился");
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Удаление города")
    public void deleteCity() {
        admin.deleteCity(updatedCitySlug);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(updatedCityName);
        Assert.assertNull(cityFromDb, "Город не удалился");
    }

    @Deprecated
    @Story("Список городов в настройках")
    @Test(groups = {},
            description = "Удаление заблокированного для изменений города")
    public void deleteLockedCity() {
        admin.deleteCity(lockedCitySlug);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(lockedCityName);
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
    }

    @AfterClass(enabled = false)
    public void clearData() {
        if(Objects.nonNull(lockedCityName)) CitiesDao.INSTANCE.deleteCityByName(lockedCityName);
    }
}
