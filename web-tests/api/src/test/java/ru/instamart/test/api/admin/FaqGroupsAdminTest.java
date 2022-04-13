package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.FaqGroupsAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreeFaqGroupsDao;
import ru.instamart.jdbc.entity.stf.SpreeFaqGroupsEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Группы FAQ")
public class FaqGroupsAdminTest extends RestBase {

    private Long faqGroupId;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(2193)
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка групп FAQ")
    public void getAllFaqGroups() {
        final Response response = FaqGroupsAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @Deprecated
    @Test(groups = {},
            description = "Создание группы FAQ")
    public void createFaqGroup() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsAdminRequest.POST(name);
        checkStatusCode302(response);
        faqGroupId = SpreeFaqGroupsDao.INSTANCE.getIdByName(name);
        checkFieldIsNotEmpty(faqGroupId, "id группы FAQ");
    }

    @Deprecated
    @Test(groups = {},
            description = "Создание группы FAQ с пустым названием")
    public void createFaqGroupWithEmptyName() {
        final Response response = FaqGroupsAdminRequest.POST("");
        checkStatusCode(response, 200, ContentType.HTML);
        Long faqGroupId = SpreeFaqGroupsDao.INSTANCE.getIdByName("");
        Assert.assertNull(faqGroupId, "Создалась группа FAQ с пустым названием");
    }

    @Deprecated
    @Test(groups = {},
            description = "Редактирование группы FAQ",
            dependsOnMethods = "createFaqGroup")
    public void editFaqGroup() {
        name = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsAdminRequest.PATCH(name, faqGroupId);
        checkStatusCode302(response);
        SpreeFaqGroupsEntity faqGroupFromDb = SpreeFaqGroupsDao.INSTANCE.findById(faqGroupId).get();
        compareTwoObjects(faqGroupFromDb.getName(), name);
    }

    @Deprecated
    @Test(groups = {},
            description = "Редактирование группы FAQ с пустым названием",
            dependsOnMethods = "editFaqGroup")
    public void editFaqGroupWithEmptyName() {
        final Response response = FaqGroupsAdminRequest.PATCH(" ", faqGroupId);
        checkStatusCode(response, 200, ContentType.HTML);
        SpreeFaqGroupsEntity faqGroupFromDb = SpreeFaqGroupsDao.INSTANCE.findById(faqGroupId).get();
        compareTwoObjects(faqGroupFromDb.getName(), name);
    }

    @Deprecated
    @Test(groups = {},
            description = "Удаление группы FAQ",
            dependsOnMethods = "editFaqGroupWithEmptyName")
    public void deleteFaqGroup() {
        final Response response = FaqGroupsAdminRequest.DELETE(faqGroupId);
        checkStatusCode302(response);
        Assert.assertTrue(SpreeFaqGroupsDao.INSTANCE.findById(faqGroupId).isEmpty(), "Не удалилась группа FAQ");
    }
}
