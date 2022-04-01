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
import ru.instamart.api.model.v1.FaqV1;
import ru.instamart.api.request.v1.FaqGroupsV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v1.FaqGroupV1Response;
import ru.instamart.api.response.v1.FaqGroupsV1Response;
import ru.instamart.api.response.v1.FaqV1Response;
import ru.instamart.api.response.v1.FaqsV1Response;
import ru.instamart.jdbc.dao.SpreeFaqDao;
import ru.instamart.jdbc.dao.SpreeFaqGroupsDao;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkFaq;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Группы FAQ")
public class FaqGroupsV1Test extends RestBase {

    private Long faqGroupId;
    private Long faqId;
    private String name;
    private String text;

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

    @CaseId(2517)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание FAQ",
            dependsOnMethods = "getFaqGroup")
    public void createFaq() {
        String text = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsV1Request.Faq.POST(faqGroupId, text, 0);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, FaqV1Response.class);
        FaqV1 faqResponse = response.as(FaqV1Response.class).getFaq();
        faqId = faqResponse.getId();
        checkFaq(faqResponse, text, faqGroupId, 0);
    }

    @CaseId(2518)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование FAQ",
            dependsOnMethods = "createFaq")
    public void editFaq() {
        text = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsV1Request.Faq.PUT(faqGroupId, faqId, text, 0);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FaqV1Response.class);
        FaqV1 faqResponse = response.as(FaqV1Response.class).getFaq();
        checkFaq(faqResponse, text, faqGroupId, 0);
    }

    @CaseId(2519)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование несуществующего FAQ",
            dependsOnMethods = "createFaqGroup")
    public void editNonExistentFaq() {
        String text = "Autotest-" + Generate.literalString(6);
        final Response response = FaqGroupsV1Request.Faq.PUT(faqGroupId, 0L, text, 10);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2521)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение позиции FAQ",
            dependsOnMethods = "editFaq")
    public void updateFaqPosition() {
        final Response response = FaqGroupsV1Request.Faq.POST(faqGroupId, faqId, 45);
        checkStatusCode(response, 204);
    }

    @CaseId(2520)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение FAQ",
            dependsOnMethods = "updateFaqPosition")
    public void getFaq() {
        final Response response = FaqGroupsV1Request.Faq.GET(faqGroupId, faqId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FaqV1Response.class);
        FaqV1 faqResponse = response.as(FaqV1Response.class).getFaq();
        checkFaq(faqResponse, text, faqGroupId, 45);
    }

    @CaseId(2522)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего FAQ",
            dependsOnMethods = "createFaqGroup")
    public void getNonExistentFaq() {
        final Response response = FaqGroupsV1Request.Faq.GET(faqGroupId, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2523)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех FAQ",
            dependsOnMethods = "updateFaqPosition")
    public void getAllFaqs() {
        final Response response = FaqGroupsV1Request.Faq.GET(faqGroupId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FaqsV1Response.class);
        List<FaqV1> faqsFromResponse =  response.as(FaqsV1Response.class).getFaqs();
        compareTwoObjects(faqsFromResponse.size(), SpreeFaqDao.INSTANCE.getCount(faqGroupId));
    }

    @CaseId(2524)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление FAQ",
            dependsOnMethods = {"getAllFaqs", "getFaq"})
    public void deleteFaq() {
        final Response response = FaqGroupsV1Request.Faq.DELETE(faqGroupId, faqId);
        checkStatusCode(response, 204);
        Assert.assertTrue(SpreeFaqDao.INSTANCE.findById(faqId).isEmpty(), "FAQ не удалился");
    }

    @CaseId(2525)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующего FAQ",
            dependsOnMethods = "createFaqGroup")
    public void deleteNonExistentFaq() {
        final Response response = FaqGroupsV1Request.Faq.DELETE(faqGroupId, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2198)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление группы FAQ",
            dependsOnMethods = "deleteFaq")
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
