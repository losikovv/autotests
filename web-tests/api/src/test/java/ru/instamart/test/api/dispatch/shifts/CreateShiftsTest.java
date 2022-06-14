package ru.instamart.test.api.dispatch.shifts;

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
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.response.ErrorTypeResponse;
import ru.instamart.api.response.shifts.PlanningPeriodsSHPResponse;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("Shifts")
@Feature("Endpoints")
public class CreateShiftsTest extends RestBase {

    private List<PlanningPeriodsSHPResponse> planningPeriod;

    @BeforeClass(alwaysRun = true,
            description = "Оформляем смену")
    public void preconditions() {
        UserData user = UserManager.getShp6Shopper2();
        shopperApp.authorisation(user);
        shiftsApi.getShopperInfo();
        planningPeriod = shiftsApi.getPlanningPeriod();
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseIDs({@CaseId(1), @CaseId(123)})
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание смены с ПП в будущем")
    public void creationOfShift201() {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(planningPeriod.get(0).getBaseGuaranteedPayroll())
                                .id(planningPeriod.get(0).getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка ответа", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(shiftResponse.getPlanningPeriods().get(0).getId(), planningPeriod.get(0).getId(), softAssert);
            compareTwoObjects(shiftResponse.getPlanningPeriods().get(0).getGuaranteedPayroll(), planningPeriod.get(0).getBaseGuaranteedPayroll(), softAssert);
            softAssert.assertAll();
        });
    }

    @CaseId(10)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание свободной смены (с нулевой оплатой)")
    public void creationOfShiftInZeroGuaranteedPayroll() {
        PlanningPeriodsSHPResponse planningPeriodItem = planningPeriod.get(2);
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID)
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
        Allure.step("Проверка периодов планирования", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(shiftResponse.getPlanningPeriods().get(0).getId(), planningPeriodItem.getId(), softAssert);
            compareTwoObjects(shiftResponse.getPlanningPeriods().get(0).getGuaranteedPayroll(), planningPeriodItem.getBaseGuaranteedPayroll(), softAssert);
            softAssert.assertAll();
        });
    }

    @CaseId(11)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            description = "Создание смен на день больше чем максимальное установленное количество часов")
    public void create422() {
        List<ShiftsRequest.PlanningPeriods> planningPeriods = new ArrayList<>();
        planningPeriods.stream()
                .filter(item -> shiftsApi.shiftsId().contains(item.getId()))
                .limit(10)
                .forEach(item -> planningPeriods.add(ShiftsRequest.PlanningPeriods.builder()
                        .guaranteedPayroll(0)
                        .id(item.getId())
                        .build())
                );

        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriods(planningPeriods)
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode(response, 201);
        ShiftResponse shiftResponse = response.as(ShiftResponse.class);
        Allure.step("Проверка ответа статуса State", () -> {
            assertEquals(shiftResponse.getState(), "new", "State пришел не отличной от new");
        });
    }

    @CaseId(137)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "creationOfShift201",
            description = "Партнер не может создать смену на одно время для двух магазинов")
    public void createTwoShifts422() {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(3944)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(planningPeriod.get(0).getBaseGuaranteedPayroll())
                                .id(planningPeriod.get(0).getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode422(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);
        Allure.step("Поверка сообщения об ошибке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(errorTypeResponse.getStatus(), 422, "Error status message not valid");
            softAssert.assertEquals(errorTypeResponse.getTitle(), "Some shifts already planned for this time", "Error title message not valid");
            softAssert.assertEquals(errorTypeResponse.getType(), "shift-intersection", "Error type message not valid");
            softAssert.assertAll();
        });
    }

    @CaseId(137)
    @Story("Создание смены")
    @Test(groups = {"api-shifts"},
            dependsOnMethods = "creationOfShift201",
            description = "Партнер не может создать смену на одно время для двух магазинов")
    public void createShiftsBeforeOneMinutesToStart422() {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID)
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(planningPeriod.get(0).getBaseGuaranteedPayroll())
                                .id(planningPeriod.get(0).getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode422(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);

        Allure.step("", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(errorTypeResponse.getStatus(), 422, "Error status message not valid");
            softAssert.assertEquals(errorTypeResponse.getTitle(), "Some shifts already planned for this time", "Error title message not valid");
            softAssert.assertEquals(errorTypeResponse.getType(), "shift-intersection", "Error type message not valid");
            softAssert.assertAll();
        });
    }
}
