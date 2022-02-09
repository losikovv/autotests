package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.ManufacturingCountriesAdminRequest;
import ru.instamart.jdbc.dao.SpreeManufacturingCountriesDao;
import ru.instamart.jdbc.entity.SpreeManufacturingCountriesEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.AdminHelper.deleteManufacturingCountries;

@Epic("Admin")
@Feature("Страны производства")
public class ManufacturingCountriesAdminTest extends RestBase {

    private String permalink;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authAdmin();
    }

    @CaseId(1886)
    @Story("Список стран производства в настройках")
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка всех стран производства")
    public void getAllManufacturingCountries() {
        final Response response = ManufacturingCountriesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1879)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой страны производства только с обязательными параметрами")
    public void createManufacturingCountryWithRequiredParams() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = ManufacturingCountriesAdminRequest.POST(ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .name(name)
                .build());
        checkStatusCode302(response);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(name);
        compareTwoObjects(manufacturingCountryFromDb.getPermalink(), name.toLowerCase());
        deleteManufacturingCountries(name.toLowerCase());
    }

    @CaseId(1880)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой страны производства со всеми параметрами")
    public void createManufacturingCountryWithAllParams() {
        permalink = "Autotest-" + Generate.literalString(6);
        ManufacturingCountriesAdminRequest.ManufacturingCountry country = ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .name("Autotest-" + Generate.literalString(6))
                .permalink(permalink)
                .build();
        final Response response = ManufacturingCountriesAdminRequest.POST(country);
        checkStatusCode302(response);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(country.getName());
        compareTwoObjects(manufacturingCountryFromDb.getPermalink(), country.getPermalink());
    }

    @CaseId(1881)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание страны производства без обязательных параметров")
    public void createManufacturingCountryWithoutRequiredParams() {
        final Response response = ManufacturingCountriesAdminRequest.POST(ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .build());
        checkStatusCode400(response);
    }

    @CaseId(1882)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование страны производства со всеми параметрами",
            dependsOnMethods = "createManufacturingCountryWithAllParams")
    public void editManufacturingCountryWithAllParams() {
        ManufacturingCountriesAdminRequest.ManufacturingCountry country = ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .name("Autotest-" + Generate.literalString(6))
                .permalink("Autotest-" + Generate.literalString(6))
                .build();
        final Response response = ManufacturingCountriesAdminRequest.PATCH(country, permalink);
        checkStatusCode302(response);
        permalink = country.getPermalink();
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(country.getName());
        compareTwoObjects(manufacturingCountryFromDb.getPermalink(), permalink);
    }

    @CaseId(1883)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование страны производства со всеми параметрами",
            dependsOnMethods = "editManufacturingCountryWithAllParams")
    public void editManufacturingCountryWithRequiredParams() {
        name = "Autotest-" + Generate.literalString(6);
        ManufacturingCountriesAdminRequest.ManufacturingCountry country = ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .name(name)
                .build();
        final Response response = ManufacturingCountriesAdminRequest.PATCH(country, permalink);
        checkStatusCode302(response);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(country.getName());
        compareTwoObjects(manufacturingCountryFromDb.getPermalink(), permalink);
    }

    @CaseId(1885)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление страны производства",
            dependsOnMethods = "editManufacturingCountryWithRequiredParams")
    public void deleteManufacturingCountry() {
        deleteManufacturingCountries(permalink);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(name);
        Assert.assertNull(manufacturingCountryFromDb);
    }
}
