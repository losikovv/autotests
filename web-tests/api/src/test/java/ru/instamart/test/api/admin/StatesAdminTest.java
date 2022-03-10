package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.StatesAdminRequest;
import ru.instamart.jdbc.dao.SpreeStatesDao;
import ru.instamart.jdbc.entity.SpreeStatesEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Optional;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Регионы/Области")
public class StatesAdminTest extends RestBase {

    private Long stateId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(2067)
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка регионов")
    public void getAllStates() {
        final Response response = StatesAdminRequest.GET(1L);
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseId(2068)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового региона")
    public void createState() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = StatesAdminRequest.POST(1L, name, name);
        checkStatusCode302(response);
        SpreeStatesEntity state  = SpreeStatesDao.INSTANCE.getStateByAbbr(name);
        checkFieldIsNotEmpty(state, "регион в БД");
        compareTwoObjects(state.getName(), name);
        stateId = state.getId();
    }

    @CaseId(2069)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового региона с пустым названием")
    public void createStateWithEmptyName() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = StatesAdminRequest.POST(1L, null, name);
        checkStatusCode(response, 200, ContentType.HTML);
        SpreeStatesEntity state  = SpreeStatesDao.INSTANCE.getStateByAbbr(name);
        Assert.assertNull(state);
    }

    @CaseId(2070)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование региона",
            dependsOnMethods = "createState")
    public void editState() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = StatesAdminRequest.PATCH(1L, stateId, name, name);
        checkStatusCode302(response);
        Optional<SpreeStatesEntity> state = SpreeStatesDao.INSTANCE.findById(stateId);
        Assert.assertFalse(state.isEmpty());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(state.get().getName(), name, softAssert);
        compareTwoObjects(state.get().getAbbr(), name, softAssert);
        softAssert.assertAll();
    }

    @CaseId(2071)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование региона с пустым именем",
            dependsOnMethods = "createState")
    public void editStateWithoutName() {
        final Response response = StatesAdminRequest.PATCH(1L, stateId, "", "Autotest-" + Generate.literalString(6));
        checkStatusCode(response, 200, "text/html; charset=utf-8");
        Optional<SpreeStatesEntity> state = SpreeStatesDao.INSTANCE.findById(stateId);
        Assert.assertFalse(state.isEmpty());
        checkFieldIsNotEmpty(state.get().getName(), "имя региона в БД");
    }

    @CaseId(2072)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление региона",
            dependsOnMethods = {"editStateWithoutName", "editState"})
    public void deleteState() {
        final Response response = StatesAdminRequest.DELETE(1L, stateId);
        checkStatusCode302(response);
        Assert.assertTrue(SpreeStatesDao.INSTANCE.findById(stateId).isEmpty(), "Регион не удалился");
    }
}
