package ru.instamart.test.api.dispatch.shifts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.response.shifts.PlanningPeriodsSHPResponse;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

@Epic("Shifts")
@Feature("Endpoints")
public class StartShiftsTest extends RestBase {

    private Integer planningArea;
    private List<PlanningPeriodsSHPResponse> planningPeriod;

    @BeforeClass(alwaysRun = true,
            description = "Получаем оформленный заказ")
    public void preconditions() {
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        shiftsApi.getShopperInfo();
        planningArea = shiftsApi.getPlanningArea().getId();
        planningPeriod = shiftsApi.getPlanningPeriod();
    }

    @AfterClass(alwaysRun = true)
    public void after() {
//        List<ShiftResponse> shifts = shiftsApi.shifts();
//        shifts.stream()
//                .forEach(item->shiftsApi.cancelShifts(item.getId()));
    }

    @CaseId(29)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание смены с ПП в будущем")
    public void startShift() {
        System.out.println();
    }
}
