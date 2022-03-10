package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.TaxonomiesAdminRequest;
import ru.instamart.jdbc.dao.SpreeTaxonomiesDao;
import ru.instamart.jdbc.entity.SpreeTaxonomiesEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Категории")
public class TaxonomiesAdminTest extends RestBase {

    private Long taxonomyId;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1887)
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка категорий")
    public void getAllTaxonomies() {
        final Response response = TaxonomiesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(1888)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой категорий")
    public void createTaxonomy() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = TaxonomiesAdminRequest.POST(name);
        checkStatusCode302(response);
        taxonomyId = SpreeTaxonomiesDao.INSTANCE.getIdByName(name);
        checkFieldIsNotEmpty(taxonomyId, "id категории");
    }

    @CaseId(1889)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой категорий с пустым названием")
    public void createTaxonomyWithEmptyName() {
        final Response response = TaxonomiesAdminRequest.POST("");
        checkStatusCode(response, 200, ContentType.HTML);
        Long taxonomyId = SpreeTaxonomiesDao.INSTANCE.getIdByName("");
        Assert.assertNull(taxonomyId, "Создалась категория с пустым названием");
    }

    @CaseId(1890)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование категории",
            dependsOnMethods = "createTaxonomy")
    public void editTaxonomy() {
        name = "Autotest-" + Generate.literalString(6);
        final Response response = TaxonomiesAdminRequest.PATCH(name, taxonomyId);
        checkStatusCode302(response);
        SpreeTaxonomiesEntity taxonomyFromDb = SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).get();
        compareTwoObjects(taxonomyFromDb.getName(), name);
    }

    @CaseId(1891)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование категории с пустым названием",
            dependsOnMethods = "editTaxonomy")
    public void editTaxonomyWithEmptyName() {
        final Response response = TaxonomiesAdminRequest.PATCH(" ", taxonomyId);
        checkStatusCode(response, 200, ContentType.HTML);
        SpreeTaxonomiesEntity taxonomyFromDb = SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).get();
        compareTwoObjects(taxonomyFromDb.getName(), name);
    }

    @CaseId(1892)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление категории",
            dependsOnMethods = "editTaxonomyWithEmptyName")
    public void deleteTaxonomy() {
        final Response response = TaxonomiesAdminRequest.DELETE(taxonomyId);
        checkStatusCode302(response);
        Assert.assertTrue(SpreeTaxonomiesDao.INSTANCE.findById(taxonomyId).isEmpty(), "Категория не удалилась");
    }
}
