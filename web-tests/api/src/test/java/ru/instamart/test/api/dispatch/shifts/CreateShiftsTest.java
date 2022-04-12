package ru.instamart.test.api.dispatch.shifts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.api.response.shopper.shifts.PlanningPeriodsSHPResponse;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

@Epic("Shifts")
@Feature("Endpoints")
public class CreateShiftsTest extends RestBase {

    private Integer planningArea;
    private PlanningPeriodsSHPResponse planningPeriodItem;

    @BeforeClass(alwaysRun = true,
            description = "Получаем оформленный заказ")
    public void preconditions() {
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        shopperApp.getShopperInfo();
        planningArea = shopperApp.getPlanningArea().getId();
        planningPeriodItem = shopperApp.getPlanningPeriod().get(0);
    }

    @CaseIDs({@CaseId(1), @CaseId(123)})
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание смены с ПП в будущем")
    public void creationOfShift201() {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(planningArea)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(planningPeriodItem.getBaseGuaranteedPayroll())
                                .id(planningPeriodItem.getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftResponse.getPlanningPeriods().get(0).getId(), planningPeriodItem.getId(),
                "Id планируемого периода не совпадает");
        softAssert.assertEquals(shiftResponse.getPlanningPeriods().get(0).getGuaranteedPayroll(), planningPeriodItem.getBaseGuaranteedPayroll(),
                "Гарантированная оплата не равна заданному");
        softAssert.assertAll();
    }

    @CaseId(10)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание свободной смены (с нулевой оплатой)")
    public void creationOfShiftInZeroGuaranteedPayroll() {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(planningArea)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(0)
                                .id(planningPeriodItem.getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftResponse.getPlanningPeriods().get(0).getId(), planningPeriodItem.getId(),
                "Id планируемого периода не совпадает");
        softAssert.assertEquals(shiftResponse.getPlanningPeriods().get(0).getGuaranteedPayroll(), planningPeriodItem.getBaseGuaranteedPayroll(),
                "Гарантированная оплата не равна заданному");
        softAssert.assertAll();
    }

}
