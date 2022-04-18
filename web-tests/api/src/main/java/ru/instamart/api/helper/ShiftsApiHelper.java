package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.request.shifts.PlanningAreasRequest;
import ru.instamart.api.request.shifts.RegionsRequest;
import ru.instamart.api.request.shifts.ShiftsRequest;
import ru.instamart.api.request.shopper.app.ShopperSHPRequest;
import ru.instamart.api.response.shifts.PlanningAreaShiftsItemSHPResponse;
import ru.instamart.api.response.shifts.PlanningPeriodsSHPResponse;
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.api.response.shopper.app.ShopperSHPResponse;
import ru.instamart.kraken.data.StartPointsTenants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class ShiftsApiHelper {
    private final ThreadLocal<Integer> currentRegion = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentSid = new ThreadLocal<>();
    private final ThreadLocal<Integer> planningArea = new ThreadLocal<>();
    private final ThreadLocal<Integer> planningPeriodId = new ThreadLocal<>();

    @Step("Получаем информацию о сбрщике")
    public ShopperSHPResponse getShopperInfo() {
        final Response response = ShopperSHPRequest.GET();
        checkStatusCode200(response);
        ShopperSHPResponse shpResponse = response.as(ShopperSHPResponse.class);
        List<ShopperSHPResponse.Included> stores = shpResponse.getIncluded().stream()
                .filter(item -> item.getType().equals("stores"))
                .collect(Collectors.toList());
        currentRegion.set(stores.get(0).getAttributes().getOperationalZoneId());
        return shpResponse;
    }

    @Step("Получаем все активные смены")
    public List<ShiftResponse> shifts() {
        final Response response = ShiftsRequest.GET();
        checkStatusCode200(response);
        var shiftResponse = response.as(ShiftResponse[].class);
        return Arrays.asList(shiftResponse);
    }

    @Step("Список планировочных районов области.")
    public PlanningAreaShiftsItemSHPResponse getPlanningArea() {
        final Response response = RegionsRequest.GET(currentRegion.get());
        checkStatusCode200(response);
        var planningAreaShifts = Arrays.asList(response.getBody().as(PlanningAreaShiftsItemSHPResponse[].class));
        var planningAreaShiftsItem = planningAreaShifts.get(0);
        planningArea.set(planningAreaShiftsItem.getId());
        return planningAreaShiftsItem;
    }

    @Step("Перечень периодов планирования области планирования.")
    public List<PlanningPeriodsSHPResponse> getPlanningPeriod() {
        final Response response = PlanningAreasRequest.GET(planningArea.get(), RoleSHP.UNIVERSAL);
        checkStatusCode200(response);
        return Arrays.asList(response.as(PlanningPeriodsSHPResponse[].class));
    }

    @Step("Создание новой смены для партнёра Универсала.")
    public void postShift(PlanningPeriodsSHPResponse planning) {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(planningArea.get())
                .role(RoleSHP.UNIVERSAL.getRole())
                .planningPeriod(
                        ShiftsRequest.PlanningPeriods.builder()
                                .guaranteedPayroll(planning.getBaseGuaranteedPayroll())
                                .id(planning.getId())
                                .build()
                )
                .build();
        final Response response = ShiftsRequest.POST(postShift);
        checkStatusCode(response, 201);
    }

    @Step("Активация смены партнера Универсала")
    public void activateShiftsPartner(StartPointsTenants startPointsTenants) {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId.get(), startPointsTenants.getLat(), startPointsTenants.getLon());
        checkStatusCode200(response);
    }

    @Step("Отмена смены")
    public void cancelShifts(final long id) {
        final Response response = ShiftsRequest.Cancel.PATCH(id);
        checkStatusCode200(response);
    }


    @Step("Начало смены для сборщика универсала")
    public void startOfShift(StartPointsTenants startPointsTenants) {
        getShopperInfo();
        getPlanningArea();
        var planningPeriodItem = getPlanningPeriod();
        var planningitem = planningPeriodItem.get(1);
        planningPeriodId.set(planningitem.getId());
        log.debug("Shifts accept: {}", planningPeriodId.get());
        postShift(planningitem);
        activateShiftsPartner(startPointsTenants);
    }
}
