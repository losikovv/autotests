package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.ErrorTypeResponse;
import ru.instamart.api.response.shifts.PlanningPeriodsSHPResponse;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.jdbc.dao.shifts.ShiftsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_3;
import static ru.instamart.kraken.data.StartPointsTenants.METRO_9;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsPlanTest extends RestBase {

    private List<PlanningPeriodsSHPResponse> planningPeriod;
    private int planningPeriodId;

    @BeforeClass(alwaysRun = true,
            description = "Оформляем смену")
    public void preconditions() {
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        //
        shiftsApi.createShift();
        planningPeriodId = shiftsApi.shifts().get(0).getId();
        ShiftsDao.INSTANCE.updateState(planningPeriodId);
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(28)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Событие \"Начать смену\". Партнер на границе территории смены (с включенной проверкой геолокации)")
    public void startShift200() {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId, METRO_3.getLat(), METRO_3.getLon());
        checkStatusCode200(response);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка статуса смены ", () -> {
            assertEquals(shiftResponse.getState(), "in_progress", "State смены не в статусе \"in_progress\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }

    @CaseId(38)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "startShift200",
            description = "Событие \"Начать смену\". Партнер имеет незавершенные смены")
    public void startShift() {
        ShiftResponse shift = shiftsApi.createShift(2);
        ShiftsDao.INSTANCE.updateState(shift.getId());
        final Response response = ShiftsRequest.Start.PATCH(shift.getId(), METRO_9.getLat(), METRO_9.getLon());
        checkStatusCode422(response);
        var shiftResponse = response.as(ErrorTypeResponse.class);
        Allure.step("Проверка статуса смены", () -> {
            assertEquals(shiftResponse.getStatus(), 422, "status не 422");
            assertEquals(shiftResponse.getTitle(), "Has shift in progress", "Title ошибки не равен назначенному");
            assertEquals(shiftResponse.getType(), "shift-has-in-progress", "Type ошибки не равен назначенному");
        });
    }

    @CaseId(75)
    @Story("Пауза смены")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "startShift",
            description = "Взять паузу в in_progress смене (лимит не израсходован)")
    public void pauseShifts200() {
        final Response response = ShiftsRequest.Pause.POST(planningPeriodId);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = shiftsApi.shifts().get(0);
        Allure.step("Проверка статуса смены", () -> {
            final SoftAssert sa = new SoftAssert();
            sa.assertEquals(shiftResponse.getId(), planningPeriodId, "planningPeriodId не совпадает");
            sa.assertEquals(shiftResponse.getState(), "on_pause", "state не равен on_pause");
            sa.assertAll();
        });
    }

    @CaseId(77)
    @Story("Пауза смены")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "pauseShifts200",
            description = "Взять паузу в in_progress смене (лимит израсходован)")
    public void secondPauseShifts200() {
        final Response resp = ShiftsRequest.Pause.DELETE(planningPeriodId);
        checkStatusCode(resp, 204);
        final Response response = ShiftsRequest.Pause.POST(planningPeriodId, true);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = shiftsApi.shifts().get(0);
        Allure.step("Проверка статуса смены", () -> {
            assertEquals(shiftResponse.getState(), "on_pause", "State смены не в статусе \"on_pause\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }

    @Skip
    @CaseId(55)
    @Story("Завершение смены")
    @Test(groups = {"api-shifts"},
            description = "Завершение смены досрочно, более чем 10 мин до планового окончания, с указанием причины")
    public void stopShifts200() {
        final Response response = ShiftsRequest.Stop.PATCH(planningPeriodId);
        simplyAwait(30);//Без него падает с 422 слишком рано заканчивает смену
        checkStatusCode200(response);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка статуса смены", () -> {
            assertEquals(shiftResponse.getState(), "completed", "State смены не в статусе \"completed\"");
            assertEquals(shiftResponse.getId(), planningPeriodId, "Id отличается от planningPeriodId");
        });
    }
}