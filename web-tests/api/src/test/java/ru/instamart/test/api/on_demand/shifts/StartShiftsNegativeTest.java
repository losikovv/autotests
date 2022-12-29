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
import ru.instamart.jdbc.dao.shifts.ShiftsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("Shifts")
@Feature("Endpoints")
public class StartShiftsNegativeTest extends RestBase {

    private int planningPeriodId;

    @BeforeClass(alwaysRun = true, description = "Оформляем смену")
    public void preconditions() {
        UserData user = UserManager.getShp6Universal1();
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

    @Skip
    @TmsLink("29")
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Событие \"Начать смену\". Партнер вне зоны территории смены (с включенной проверкой геолокации)")
    public void startShift422() {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId, 55.646977, 38.650011);
        checkStatusCode422(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);
        Allure.step("Проверка ошибки при начале смены", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(errorTypeResponse.getStatus(), 422, "Error status message not valid");
            softAssert.assertEquals(errorTypeResponse.getTitle(), "Partner is outside area", "Error title message not valid");
            softAssert.assertEquals(errorTypeResponse.getType(), "shift-partner-outside-area", "Error type message not valid");
            softAssert.assertAll();
        });
    }
}
