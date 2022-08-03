package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.StateV1;
import ru.instamart.api.request.v1.admin.StatesAdminV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v1.admin.StateV1Response;
import ru.instamart.api.response.v1.admin.StatesV1Response;
import ru.instamart.jdbc.dao.stf.SpreeStatesDao;
import ru.instamart.jdbc.entity.stf.SpreeStatesEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.Optional;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Регионы/Области")
public class StatesV1Test extends RestBase {

    private Long stateId;
    private String name;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2067)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка регионов")
    public void getAllStates() {
        final Response response = StatesAdminV1Request.GET(1L);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StatesV1Response.class);
        List<StateV1> statesFromResponse = response.as(StatesV1Response.class).getStates();
        compareTwoObjects(statesFromResponse.size(), SpreeStatesDao.INSTANCE.getCount());
    }

    @CaseId(2068)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового региона")
    public void createState() {
        String name = "Autotest-" + Generate.literalString(6);
        final Response response = StatesAdminV1Request.POST(1L, name, name);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, StateV1Response.class);
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
        final Response response = StatesAdminV1Request.POST(1L, null, name);
        checkStatusCode422(response);
        String error = response.as(ErrorResponse.class).getErrors().getName();
        compareTwoObjects(error, "не может быть пустым");
    }

    @CaseId(2070)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование региона",
            dependsOnMethods = "createState")
    public void editState() {
        name = "Autotest-" + Generate.literalString(6);
        final Response response = StatesAdminV1Request.PATCH(1L, stateId, name, name);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StateV1Response.class);
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
        final Response response = StatesAdminV1Request.PATCH(1L, stateId, "", "Autotest-" + Generate.literalString(6));
        checkStatusCode422(response);
        String error = response.as(ErrorResponse.class).getErrors().getName();
        compareTwoObjects(error, "не может быть пустым");
    }

    @CaseId(2694)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование несуществующего региона")
    public void editNonExistentState() {
        final Response response = StatesAdminV1Request.PATCH(1L, 0L, "", "Autotest-" + Generate.literalString(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2693)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение региона",
            dependsOnMethods = {"editStateWithoutName", "editState"})
    public void getState() {
        final Response response = StatesAdminV1Request.GET(1L, stateId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StateV1Response.class);
        compareTwoObjects(response.as(StateV1Response.class).getState().getName(), name);
    }

    @CaseId(2695)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего региона")
    public void getNonExistentState() {
        final Response response = StatesAdminV1Request.GET(1L, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2072)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление региона",
            dependsOnMethods = "getState",
            enabled = false) //todo починить 403 (похоже не хватает какой-то роли админу)
    public void deleteState() {
        final Response response = StatesAdminV1Request.DELETE(1L, stateId);
        checkStatusCode(response, 204);
        Assert.assertTrue(SpreeStatesDao.INSTANCE.findById(stateId).isEmpty(), "Регион не удалился");
    }

    @CaseId(2696)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующего региона")
    public void deleteNonExistentState() {
        final Response response = StatesAdminV1Request.DELETE(1L, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
