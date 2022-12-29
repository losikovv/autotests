package ru.instamart.test.api.admin.deprecated;

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
import ru.instamart.jdbc.dao.stf.SpreeManufacturingCountriesDao;
import ru.instamart.jdbc.entity.stf.SpreeManufacturingCountriesEntity;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Deprecated //Задача выпиливания FEP-3591
@Feature("Страны производства")
public class ManufacturingCountriesAdminTest extends RestBase {

    private String permalink;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @TmsLink("1886")
    @Skip
    @Story("Список стран производства в настройках")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"}, description = "Получение списка всех стран производства")
    public void getAllManufacturingCountries() {
        final Response response = ManufacturingCountriesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @TmsLink("1879")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание новой страны производства только с обязательными параметрами")
    public void createManufacturingCountryWithRequiredParams() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = ManufacturingCountriesAdminRequest.POST(ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .name(name)
                .build());
        checkStatusCode302(response);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(name);
        compareTwoObjects(manufacturingCountryFromDb.getPermalink(), name.toLowerCase());
        admin.deleteManufacturingCountries(name.toLowerCase());
    }

    @TmsLink("1880")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @TmsLink("1881")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание страны производства без обязательных параметров")
    public void createManufacturingCountryWithoutRequiredParams() {
        final Response response = ManufacturingCountriesAdminRequest.POST(ManufacturingCountriesAdminRequest.ManufacturingCountry.builder()
                .build());
        checkStatusCode400(response);
    }

    @TmsLink("1882")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @TmsLink("1883")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @TmsLink("1885")
    @Skip
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Удаление страны производства",
            dependsOnMethods = "editManufacturingCountryWithRequiredParams")
    public void deleteManufacturingCountry() {
        admin.deleteManufacturingCountries(permalink);
        SpreeManufacturingCountriesEntity manufacturingCountryFromDb = SpreeManufacturingCountriesDao.INSTANCE.getManufacturingCountryByName(name);
        Assert.assertNull(manufacturingCountryFromDb);
    }
}
