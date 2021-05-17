package ru.instamart.tests.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.helper.ShopperAdminApiHelper;
import ru.instamart.api.model.shopper.admin.*;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.*;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.data.pagesdata.EnvironmentData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperAdminWithAuthTest extends RestBase {
    private Integer routeId;
    private Integer routeScheduleId;
    private Integer tariffId;
    private Integer shiftId;
    private Integer shiftAssignmentId;
    private final ShopperAdminApiHelper shopperAdmin = new ShopperAdminApiHelper();
    private final Integer sid = EnvironmentData.INSTANCE.getDefaultShopperSid();
    private final Integer shopperId = 1;
    private final Integer driverId = 2;
    private final String routeScheduleStatus = "disabled";
    private final String today = LocalDate.now().toString();

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @CaseId(23)
    @Test(  description = "Список сотрудников",
            groups = {"api-shopper-regress"})
    public void getShoppers200() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(ShoppersSHPResponse.class).getShoppers().isEmpty());
    }

    @CaseId(24)
    @Test(  description = "Список магазинов",
            groups = {"api-shopper-regress"})
    public void getStores200() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode200(response);
        assertFalse(response.as(StoresSHPResponse.class).getStores().isEmpty());
    }

    @CaseId(25)
    @Test(  description = "Список расписаний",
            groups = {"api-shopper-regress"})
    public void getRouteSchedules200() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode200(response);
        List<RouteScheduleV1> routeSchedules = response.as(RouteSchedulesSHPResponse.class).getRouteSchedules();
        assertFalse(routeSchedules.isEmpty());
    }

    @CaseId(26)
    @Test(  description = "Список операционных зон",
            groups = {"api-shopper-regress"})
    public void getOperationalZones200() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode200(response);
        assertFalse(response.as(OperationalZonesSHPResponse.class).getOperationalZones().isEmpty());
    }

    @CaseId(27)
    @Test(  description = "Список ретейлеров",
            groups = {"api-shopper-regress"})
    public void getRetailers200() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RetailersSHPResponse.class).getRetailers().isEmpty());
    }

    @CaseId(28)
    @Test(  description = "Список доставок",
            groups = {"api-shopper-regress"})
    public void getShipments200() {
        Response response = ShopperAdminRequest.Shipments.GET(sid,  LocalDate.now().minusDays(1).toString());
        checkStatusCode200(response);
    }

    @CaseId(29)
    @Test(  description = "Список смен",
            groups = {"api-shopper-regress"})
    public void getShifts200() {
        Response response = ShopperAdminRequest.Shifts.GET();
        checkStatusCode200(response);
        assertFalse(response.as(ShiftsSHPResponse.class).getShifts().isEmpty());
    }

    @CaseId(31)
    @Test(  description = "Список тарифов",
            groups = {"api-shopper-regress"})
    public void getTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.GET();
        checkStatusCode200(response);
        assertFalse(response.as(TariffsSHPResponse.class).getTariffs().isEmpty());
    }

    @CaseId(84)
    @Test(  description = "Создание расписания на день",
            groups = {"api-shopper-regress"})
    public void postRouteSchedules200() {
        shopperAdmin.deleteRouteScheduleIfExists(sid, today);

        Response response = ShopperAdminRequest.RouteSchedules.POST(sid, today, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        routeScheduleId = routeSchedule.getId();

        assertEquals(sid, routeSchedule.getStore().getId());
        assertEquals(today, routeSchedule.getDate());
        assertEquals(routeScheduleStatus, routeSchedule.getStatus());
    }

    @CaseId(85)
    @Test(  description = "Создание маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRouteSchedules200")
    public void postRoutes200() {
        Response response = ShopperAdminRequest.Routes.POST(routeScheduleId, driverId);

        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();
        routeId = route.getId();

        assertEquals(driverId, route.getDriver().getId());
    }

    @CaseId(30)
    @Test(  description = "Получение инфы о маршруте",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void getRoute200() {
        Response response = ShopperAdminRequest.Routes.GET(routeId);

        checkStatusCode200(response);

        assertEquals(routeId, response.as(RouteSHPResponse.class).getRoute().getId());
    }

    @CaseId(32)
    @Test(  description = "Изменение расписания",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "getRouteSchedules200")
    public void patchRouteSchedule200() {
        Response response = ShopperAdminRequest.RouteSchedules.PATCH(routeScheduleId, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        assertEquals(routeScheduleId, routeSchedule.getId());
        assertEquals(routeScheduleStatus, routeSchedule.getStatus());
    }

    @CaseId(37)
    @Test(  description = "Получение всех доступных ролей",
            groups = {"api-shopper-regress"})
    public void getRoles200() {
        Response response = ShopperAdminRequest.Roles.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RolesSHPResponse.class).getRoles().isEmpty());
    }

    @CaseId(86)
    @Test(  description = "Создание тарифа",
            groups = {"api-shopper-regress"})
    public void postTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.POST();

        checkStatusCode200(response);

        TariffV1 tariff = response.as(TariffSHPResponse.class).getTariff();
        tariffId = tariff.getId();
    }

    @CaseId(88)
    @Test(  description = "Создание смены",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postTariffs200")
    public void postShifts200() {
        Response response = ShopperAdminRequest.Shifts.POST(today, 0, sid, tariffId);

        checkStatusCode200(response);

        ShiftV1 shift = response.as(ShiftSHPResponse.class).getShift();
        shiftId = shift.getId();

        assertEquals(today, shift.getDate());
        assertEquals(sid, shift.getStoreId());
        assertEquals(tariffId, shift.getTariffId());
    }

    @CaseId(33)
    @Test(  description = "Создание назначения в смену",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postShifts200")
    public void postShiftAssignments200() {
        String startsAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String endsAt = LocalDateTime.now().plusHours(8).truncatedTo(ChronoUnit.SECONDS).toString();

        Response response = ShopperAdminRequest.ShiftAssignments.POST(
                shiftId,
                shopperId,
                startsAt,
                endsAt);

        checkStatusCode200(response);

        ShiftAssignmentV1 shiftAssignment = response.as(ShiftAssignmentSHPResponse.class).getShiftAssignment();
        shiftAssignmentId = shiftAssignment.getId();

        assertEquals(shiftId, shiftAssignment.getShiftId());
        assertEquals(shopperId, shiftAssignment.getShopperId());
        assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt));
        assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt));
    }

    @CaseId(34)
    @Test(  description = "Изменение назначения в смену",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postShiftAssignments200")
    public void putShiftAssignments200() {
        String startsAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String endsAt = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.SECONDS).toString();

        Response response = ShopperAdminRequest.ShiftAssignments.PUT(shiftAssignmentId, shiftId, shopperId, startsAt, endsAt);

        checkStatusCode200(response);

        ShiftAssignmentV1 shiftAssignment = response.as(ShiftAssignmentSHPResponse.class).getShiftAssignment();

        assertEquals(shiftAssignmentId, shiftAssignment.getId());
        assertEquals(shiftId, shiftAssignment.getShiftId());
        assertEquals(shopperId, shiftAssignment.getShopperId());
        assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt));
        assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt));
    }

    @CaseId(35)
    @Test(  description = "Изменение маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void putRoutes200() {
        Response response = ShopperAdminRequest.Routes.PUT(routeId, routeScheduleId, driverId);

        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();

        assertEquals(routeId, route.getId());
        assertEquals(driverId, route.getDriver().getId());
    }

    @CaseId(36)
    @Test(  description = "Создание блокировки для маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void postRoutesLock200() {
        Response response = ShopperAdminRequest.Routes.Lock.POST(routeId);
        checkStatusCode200(response);
    }
}
