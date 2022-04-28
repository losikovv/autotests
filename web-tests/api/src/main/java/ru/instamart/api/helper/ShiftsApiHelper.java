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
import ru.instamart.jdbc.dao.shifts.ShiftsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCFutureDate;

@Slf4j
public class ShiftsApiHelper {
    private final ThreadLocal<Integer> currentRegion = new ThreadLocal<>();
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
        var planningAreaShiftsFilter = planningAreaShifts.stream().filter(item->item.getId()==EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID).collect(Collectors.toList());
        assertTrue(planningAreaShiftsFilter.size()>0, "Не найден данные дефолтной зоны");
        return planningAreaShiftsFilter.get(0);
    }

    @Step("Перечень периодов планирования области планирования.")
    public List<PlanningPeriodsSHPResponse> getPlanningPeriod() {
        final Response response = PlanningAreasRequest.GET(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID, RoleSHP.UNIVERSAL);
        checkStatusCode200(response);
        return Arrays.asList(response.as(PlanningPeriodsSHPResponse[].class));
    }

    @Step("Перечень периодов планирования области планирования.")
    public List<PlanningPeriodsSHPResponse> getPlanningPeriod(final String startedAt, final String endedAt) {
        final Response response = PlanningAreasRequest.GET(
                EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID, RoleSHP.UNIVERSAL, startedAt,endedAt);
        checkStatusCode200(response);
        return Arrays.asList(response.as(PlanningPeriodsSHPResponse[].class));
    }

    @Step("Создание новой смены для партнёра Универсала.")
    public ShiftResponse postShift(PlanningPeriodsSHPResponse planning) {
        final ShiftsRequest.PostShift postShift = ShiftsRequest.PostShift.builder()
                .planningAreaId(EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID)
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
        return response.as(ShiftResponse.class);
    }

    @Step("Активация смены партнера Универсала")
    public void activateShiftsPartner(StartPointsTenants startPointsTenants) {
        final Response response = ShiftsRequest.Start.PATCH(planningPeriodId.get(), startPointsTenants.getLat(), startPointsTenants.getLon());
        checkStatusCode200(response);
    }

    @Step("Отмена смены")
    public void cancelShifts(final long id) {
        final Response response = ShiftsRequest.Cancel.PATCH(id);
        checkStatusCode200or422(response);
    }

    @Step("Создание ближайшей смены")
    public ShiftResponse createShift(){
        getShopperInfo();
        var shifts = shifts().stream().map(item->item.getPlanningPeriods().get(0).getId()).collect(Collectors.toList());
        var planningPeriodItem = getPlanningPeriod();
        List<PlanningPeriodsSHPResponse> collect = planningPeriodItem.stream()
                .filter(item -> !shifts.contains(item.getId()))
                .collect(Collectors.toList());
        var planningitem = collect.get(1);
        planningPeriodId.set(planningitem.getId());
        log.debug("Shifts accept: {}", planningPeriodId.get());
        return postShift(planningitem);
    }

    @Step("Создание смены до начала которой более 1440 минут")
    public ShiftResponse createSecondDaysShift(){
        getShopperInfo();
        var shifts = shifts().stream().map(item->item.getPlanningPeriods().get(0).getId()).collect(Collectors.toList());
        var planningPeriodItem = getPlanningPeriod(getZonedUTCFutureDate(1L), getZonedUTCFutureDate(2L));
        List<PlanningPeriodsSHPResponse> collect = planningPeriodItem.stream()
                .filter(item -> !shifts.contains(item.getId()))
                .collect(Collectors.toList());
        var planningitem = collect.get(1);
        planningPeriodId.set(planningitem.getId());
        log.debug("Shifts accept: {}", planningPeriodId.get());
        return postShift(planningitem);
    }

    @Step("Начало смены для сборщика универсала")
    public Integer startOfShift(StartPointsTenants startPointsTenants) {
        createShift();
        ShiftsDao.INSTANCE.updateState(planningPeriodId.get());
        activateShiftsPartner(startPointsTenants);
        return planningPeriodId.get();
    }
}
