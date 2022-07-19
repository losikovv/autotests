package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.jdbc.dao.shifts.ShiftsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsCancelTest extends RestBase {
    private int planningPeriodId;

    @BeforeClass(alwaysRun = true,
            description = "Авторизация")
    public void auth() {
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем смену")
    public void preconditions() {
        planningPeriodId = shiftsApi.createShift().getId();
    }

    @AfterMethod(alwaysRun = true)
    public void after(){
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(30)
    @Story("Отмена смены")
    @Test(groups = {"api-shifts"},
            description = "Партнер отменяет new смену")
    public void cancelShift200() {
        final Response response = ShiftsRequest.Cancel.PATCH(planningPeriodId);
        checkStatusCode200(response);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка статуса смены", () -> {
            assertEquals(shiftResponse.getState(), "canceled", "State смены не в статусе \"canceled\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }

    @CaseId(71)
    @Story("Отмена смены")
    @Test(groups = {"api-shifts"},
            description = "Отменить ready to start смену")
    public void cancelReadyToStartShift200() {
        ShiftsDao.INSTANCE.updateState(planningPeriodId);
        final Response response = ShiftsRequest.Cancel.PATCH(planningPeriodId);
        checkStatusCode200(response);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка статуса смены", () -> {
            assertEquals(shiftResponse.getState(), "canceled", "State смены не в статусе \"canceled\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }
}