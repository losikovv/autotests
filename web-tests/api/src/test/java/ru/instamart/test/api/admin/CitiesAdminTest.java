package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
import ru.sbermarket.qase.annotation.CaseId;

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
        admin.authAdmin();
    }

    @CaseId(1133)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка всех городов")
    public void getAllCities() {
        final Response response = CitiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1124)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города c обязательными параметрами")
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

    @CaseId(1125)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города со всеми возможными параметрами")
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

    @CaseId(1126)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Создание города c пустыми обязательными параметрами")
    public void createCityWithEmptyRequiredParams() {
        final Response response = CitiesAdminRequest.POST(new CitiesAdminRequest.City());
        checkStatusCode400(response);
    }

    @CaseId(1127)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города с обязательными параметрами",
            dependsOnMethods = "createCityWithRequiredParams")
    public void editCityWithRequiredParams() {
        String updatedCityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(updatedCityName), cityName);
        checkStatusCode302(response);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(updatedCityName);
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
    }

    @CaseId(1128)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города c пустыми обязательными параметрами",
            dependsOnMethods = "createCityWithRequiredParams")
    public void editCityWithEmptyRequiredParams() {
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(), cityName);
        checkStatusCode400(response);
    }

    @CaseId(1129)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование города со всеми возможными параметрами",
            dependsOnMethods = {"editCityWithRequiredParams", "editCityWithEmptyRequiredParams"})
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

    @CaseId(1130)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование заблокированного для изменений города",
            dependsOnMethods = "createCityWithAllParams")
    public void editLockedCity() {
        String cityName = "Autotest-" + Generate.literalString(6);
        final Response response = CitiesAdminRequest.PATCH(new CitiesAdminRequest.City(cityName), lockedCitySlug);
        checkStatusCode302(response);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(cityName);
        Assert.assertNull(cityFromDb, "Заблокированный для редактирования город обновился");
    }

    @CaseId(1131)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление города",
            dependsOnMethods = "editCityWithAllParams")
    public void deleteCity() {
        admin.deleteCity(updatedCitySlug);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(updatedCityName);
        Assert.assertNull(cityFromDb, "Город не удалился");
    }

    @CaseId(1132)
    @Story("Список городов в настройках")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление заблокированного для изменений города",
            dependsOnMethods = "createCityWithAllParams")
    public void deleteLockedCity() {
        admin.deleteCity(lockedCitySlug);
        CitiesEntity cityFromDb = CitiesDao.INSTANCE.getCityByName(lockedCityName);
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        CitiesDao.INSTANCE.deleteCityByName(lockedCityName);
    }
}
