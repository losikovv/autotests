package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.TaxonomiesAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreeTaxonomiesDao;
import ru.instamart.jdbc.entity.stf.SpreeTaxonomiesEntity;
import ru.instamart.kraken.data.Generate;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Категории")
public class TaxonomiesAdminTest extends RestBase {

    private Long taxonomyId;
    private String name;

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @TmsLink("1887")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"}, description = "Получение списка категорий")
    public void getAllTaxonomies() {
        final Response response = TaxonomiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @TmsLink("1888")
    @Issue("INFRADEV-16984")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание новой категорий")
    public void createTaxonomy() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = TaxonomiesAdminRequest.POST(name);
        checkStatusCode302(response);
        taxonomyId = SpreeTaxonomiesDao.INSTANCE.getIdByName(name);
        checkFieldIsNotEmpty(taxonomyId, "id категории");
    }

    @TmsLink("1889")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание новой категорий с пустым названием")
    public void createTaxonomyWithEmptyName() {
        final Response response = TaxonomiesAdminRequest.POST("");
        checkStatusCode(response, 200, ContentType.HTML);
        Long taxonomyId = SpreeTaxonomiesDao.INSTANCE.getIdByName("");
        Assert.assertNull(taxonomyId, "Создалась категория с пустым названием");
    }

    @TmsLink("1890")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Редактирование категории",
            dependsOnMethods = "createTaxonomy")
    public void editTaxonomy() {
        name = "Autotest-" + Generate.literalString(6);
        final Response response = TaxonomiesAdminRequest.PATCH(name, taxonomyId);
        checkStatusCode302(response);
        SpreeTaxonomiesEntity taxonomyFromDb = SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).get();
        compareTwoObjects(taxonomyFromDb.getName(), name);
    }

    @TmsLink("1891")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Редактирование категории с пустым названием",
            dependsOnMethods = "editTaxonomy")
    public void editTaxonomyWithEmptyName() {
        final Response response = TaxonomiesAdminRequest.PATCH(" ", taxonomyId);
        checkStatusCode(response, 200, ContentType.HTML);
        SpreeTaxonomiesEntity taxonomyFromDb = SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).get();
        compareTwoObjects(taxonomyFromDb.getName(), name);
    }

    @TmsLink("1892")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Удаление категории",
            dependsOnMethods = "editTaxonomyWithEmptyName")
    public void deleteTaxonomy() {
        final Response response = TaxonomiesAdminRequest.DELETE(taxonomyId);
        checkStatusCode302(response);
        Assert.assertTrue(SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).isEmpty(), "Категория не удалилась");
    }
}
