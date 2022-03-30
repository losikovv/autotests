package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.FaqGroupV1;
import ru.instamart.api.request.v1.FaqGroupsV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v1.FaqGroupV1Response;
import ru.instamart.api.response.v1.FaqGroupsV1Response;
import ru.instamart.jdbc.dao.SpreeFaqGroupsDao;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Группы FAQ")
public class FaqGroupsV1Test extends RestBase {

    private Long faqGroupId;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2193)
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка групп FAQ")
    public void getAllFaqGroups() {
        final Response response = FaqGroupsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FaqGroupsV1Response.class);
        List<FaqGroupV1> faqGroups = response.as(FaqGroupsV1Response.class).getFaqGroups();
        compareTwoObjects(faqGroups.size(), SpreeFaqGroupsDao.INSTANCE.getCount());
    }

    @CaseId(2194)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание группы FAQ")
    public void createFaqGroup() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsV1Request.POST(name);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, FaqGroupV1Response.class);
        FaqGroupV1 faqGroupResponse = response.as(FaqGroupV1Response.class).getFaqGroup();
        faqGroupId = faqGroupResponse.getId();
        compareTwoObjects(faqGroupResponse.getName(), name);
    }

    @CaseId(2195)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание группы FAQ с пустым названием")
    public void createFaqGroupWithEmptyName() {
        final Response response = FaqGroupsV1Request.POST("");
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects(error.getErrors().getName(), "не может быть пустым");
    }

    @CaseId(2196)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование группы FAQ",
            dependsOnMethods = "createFaqGroup")
    public void editFaqGroup() {
        name = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsV1Request.PUT(name, faqGroupId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FaqGroupV1Response.class);
        compareTwoObjects(response.as(FaqGroupV1Response.class).getFaqGroup().getName(), name);
    }

    @CaseId(2197)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование группы FAQ с пустым названием",
            dependsOnMethods = "editFaqGroup")
    public void editFaqGroupWithEmptyName() {
        final Response response = FaqGroupsV1Request.PUT(" ", faqGroupId);
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects(error.getErrors().getName(), "не может быть пустым");
    }

    @CaseId(2506)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование несуществующей группы FAQ")
    public void editNonExistentFaqGroup() {
        final Response response = FaqGroupsV1Request.PUT("fgdfgd", 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2508)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение позиции группы FAQ",
            dependsOnMethods = "editFaqGroupWithEmptyName")
    public void changeFaqGroupPosition() {
        final Response response = FaqGroupsV1Request.POST(faqGroupId, 30);
        checkStatusCode(response, 204);
    }

    @CaseId(2504)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение группы FAQ",
            dependsOnMethods = "changeFaqGroupPosition")
    public void getFaqGroup() {
        final Response response = FaqGroupsV1Request.GET(faqGroupId);
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, FaqGroupV1Response.class);
        FaqGroupV1 faqGroupFromResponse = response.as(FaqGroupV1Response.class).getFaqGroup();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(faqGroupFromResponse.getName(), name, softAssert);
        compareTwoObjects(faqGroupFromResponse.getPosition(), 30, softAssert);
        softAssert.assertAll();
    }

    @CaseId(2506)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующей группы FAQ")
    public void getNonExistentFaqGroup() {
        final Response response = FaqGroupsV1Request.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2198)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление группы FAQ",
            dependsOnMethods = "getFaqGroup")
    public void deleteFaqGroup() {
        final Response response = FaqGroupsV1Request.DELETE(faqGroupId);
        checkStatusCode(response, 204);
        Assert.assertTrue(SpreeFaqGroupsDao.INSTANCE.findById(faqGroupId).isEmpty(), "Не удалилась группа FAQ");
    }

    @CaseId(2507)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующей группы FAQ")
    public void deleteNonExistentFaqGroup() {
        final Response response = FaqGroupsV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
