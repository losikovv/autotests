package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

@Epic("Shifts")
@Feature("Endpoints")
public class DeleteShiftsTest extends RestBase {
    private int planningPeriodId;

    @BeforeClass(alwaysRun = true,
            description = "Авторизация")
    public void auth() {
        UserData user = UserManager.getShp6Shopper2();
        shopperApp.authorisation(user);
        planningPeriodId = shiftsApi.createSecondDaysShift().getId();
    }

    @CaseId(59)
    @Story("Удаление смены")
    @Test(groups = {"api-shifts"},
            description = "Удаление смены со статусом new, до начала смены более чем указано в параметре BEFORE_SHIFT_DELETE_TIMEOUT_MINUTES = 1440")
    public void cancelShift200() {
        final Response response = ShiftsRequest.DELETE(planningPeriodId);
        checkStatusCode(response, 204);
    }
}
