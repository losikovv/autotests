package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.admin.*;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperAdminWithAuthTest extends RestBase {
    private final Integer sid = EnvironmentProperties.DEFAULT_SHOPPER_SID;
    private final Integer shopperId = 1;
    private final Integer driverId = 2;
    private final String routeScheduleStatus = "disabled";
    private final String shopperStatus = "disabled";
    private final String today = getDateFromMSK().toString();
    private Integer routeId;
    private Integer routeScheduleId;
    private Integer tariffId;
    private Integer shiftId;
    private Integer shiftAssignmentId;

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @CaseId(23)
    @Test(description = "Список сотрудников",
            groups = {"api-shopper-regress"})
    public void getShoppers200() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(ShoppersSHPResponse.class).getShoppers().isEmpty(), "Список сотрудников пустой");
    }

    @CaseId(24)
    @Test(description = "Список магазинов",
            groups = {"api-shopper-regress"})
    public void getStores200() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode200(response);
        assertFalse(response.as(StoresSHPResponse.class).getStores().isEmpty(), "Список магазинов пустой");
    }

    @CaseId(25)
    @Test(description = "Список расписаний",
            groups = {"api-shopper-regress"})
    public void getRouteSchedules200() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode200(response);
        List<RouteScheduleV1> routeSchedules = response.as(RouteSchedulesSHPResponse.class).getRouteSchedules();
        assertFalse(routeSchedules.isEmpty(), "Список расписаний пустой");
    }

    @CaseId(26)
    @Test(description = "Список операционных зон",
            groups = {"api-shopper-regress"})
    public void getOperationalZones200() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode200(response);
        assertFalse(response.as(OperationalZonesSHPResponse.class).getOperationalZones().isEmpty(), "Список операционных зон пустой");
    }

    @CaseId(27)
    @Test(description = "Список ретейлеров",
            groups = {"api-shopper-regress"})
    public void getRetailers200() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RetailersSHPResponse.class).getRetailers().isEmpty(), "Список ритейлеров пустой");
    }

    @CaseId(28)
    @Test(description = "Список доставок",
            groups = {"api-shopper-regress"})
    public void getShipments200() {
        Response response = ShopperAdminRequest.Shipments.GET(sid, getDateFromMSK().minusDays(1).toString());
        checkStatusCode200(response);
    }

    @CaseId(29)
    @Test(description = "Список смен",
            groups = {"api-shopper-regress"})
    public void getShifts200() {
        Response response = ShopperAdminRequest.Shifts.GET();
        checkStatusCode200(response);
        assertFalse(response.as(ShiftsSHPResponse.class).getShifts().isEmpty());
    }

    @CaseId(31)
    @Test(description = "Список тарифов",
            groups = {"api-shopper-regress"})
    public void getTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.GET();
        checkStatusCode200(response);
        assertFalse(response.as(TariffsSHPResponse.class).getTariffs().isEmpty());
    }

    @CaseId(84)
    @Test(description = "Создание расписания на день",
            groups = {"api-shopper-regress"})
    public void postRouteSchedules200() {
        shopperAdmin.deleteRouteScheduleIfExists(sid, today);

        Response response = ShopperAdminRequest.RouteSchedules.POST(sid, today, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        routeScheduleId = routeSchedule.getId();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sid, routeSchedule.getStore().getId(), "Сид магазина не совпадает");
        softAssert.assertEquals(today, routeSchedule.getDate(), "Дата в расписании не совпадает");
        softAssert.assertEquals(routeScheduleStatus, routeSchedule.getStatus(), "Статус расписания не совпадает");
        softAssert.assertAll();
    }

    @CaseId(85)
    @Test(description = "Создание маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRouteSchedules200")
    public void postRoutes200() {
        Response response = ShopperAdminRequest.Routes.POST(routeScheduleId, driverId);

        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();
        routeId = route.getId();

        assertEquals(driverId, route.getDriver().getId(), "ИД водитеоя не совпадает");
    }

    @CaseId(30)
    @Test(description = "Получение инфы о маршруте",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void getRoute200() {
        Response response = ShopperAdminRequest.Routes.GET(routeId);

        checkStatusCode200(response);

        assertEquals(routeId, response.as(RouteSHPResponse.class).getRoute().getId(), "ИД маршрута не совпадает");
    }

    @CaseId(32)
    @Test(description = "Изменение расписания",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "getRouteSchedules200")
    public void patchRouteSchedule200() {
        Response response = ShopperAdminRequest.RouteSchedules.PATCH(routeScheduleId, routeScheduleStatus);

        checkStatusCode200(response);

        RouteScheduleV1 routeSchedule = response.as(RouteScheduleSHPResponse.class).getRouteSchedule();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(routeScheduleId, routeSchedule.getId(), "ИД расписания водителя не совпадает");
        softAssert.assertEquals(routeScheduleStatus, routeSchedule.getStatus(), "Статус расписания подителя не совпадает");
        softAssert.assertAll();
    }

    @CaseId(37)
    @Test(description = "Получение всех доступных ролей",
            groups = {"api-shopper-regress"})
    public void getRoles200() {
        Response response = ShopperAdminRequest.Roles.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RolesSHPResponse.class).getRoles().isEmpty(), "Список ролей пуст");
    }

    @CaseId(86)
    @Test(description = "Создание тарифа",
            groups = {"api-shopper-regress"})
    public void postTariffs200() {
        Response response = ShopperAdminRequest.Tariffs.POST();
        checkStatusCode200(response);

        TariffV1 tariff = response.as(TariffSHPResponse.class).getTariff();
        tariffId = tariff.getId();
    }

    @CaseId(88)
    @Test(description = "Создание смены",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postTariffs200")
    public void postShifts200() {
        Response response = ShopperAdminRequest.Shifts.POST(today, 0, sid, tariffId);
        checkStatusCode200(response);

        ShiftV1 shift = response.as(ShiftSHPResponse.class).getShift();
        shiftId = shift.getId();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(today, shift.getDate(), "Дата смены не совпадает");
        softAssert.assertEquals(sid, shift.getStoreId(), "SID не совпадает");
        softAssert.assertEquals(tariffId, shift.getTariffId(), "Тариф смены не верен");
        softAssert.assertAll();
    }

    @CaseId(33)
    @Test(description = "Создание назначения в смену",
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

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftId, shiftAssignment.getShiftId(), "shiftId не совпадает");
        softAssert.assertEquals(shopperId, shiftAssignment.getShopperId(), "shopperId не совпадает");
        softAssert.assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt), "startAt не совпадает");
        softAssert.assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt), "endAt не совпадает");
        softAssert.assertAll();
    }

    @CaseId(34)
    @Test(description = "Изменение назначения в смену",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postShiftAssignments200")
    public void putShiftAssignments200() {
        String startsAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String endsAt = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.SECONDS).toString();

        Response response = ShopperAdminRequest.ShiftAssignments.PUT(shiftAssignmentId, shiftId, shopperId, startsAt, endsAt);

        checkStatusCode200(response);

        ShiftAssignmentV1 shiftAssignment = response.as(ShiftAssignmentSHPResponse.class).getShiftAssignment();

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shiftAssignmentId, shiftAssignment.getId(), "shiftAssignmentId not equals");
        softAssert.assertEquals(shiftId, shiftAssignment.getShiftId(), "shiftId не совпадает");
        softAssert.assertEquals(shopperId, shiftAssignment.getShopperId(), "shopperId не совпадает");
        softAssert.assertTrue(shiftAssignment.getStartsAt().startsWith(startsAt), "startAt не совпадает");
        softAssert.assertTrue(shiftAssignment.getEndsAt().startsWith(endsAt), "endsAt не совпадает");
        softAssert.assertAll();
    }

    @CaseId(35)
    @Test(description = "Изменение маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void putRoutes200() {
        Response response = ShopperAdminRequest.Routes.PUT(routeId, routeScheduleId, driverId);
        checkStatusCode200(response);

        RouteV1 route = response.as(RouteSHPResponse.class).getRoute();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(routeId, route.getId(), "id маршрута не совпадает");
        softAssert.assertEquals(driverId, route.getDriver().getId(), "id водителя не совпадает");
        softAssert.assertAll();
    }

    @CaseId(36)
    @Test(description = "Создание блокировки для маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "postRoutes200")
    public void postRoutesLock200() {
        Response response = ShopperAdminRequest.Routes.Lock.POST(routeId);
        checkStatusCode200(response);
    }

    @CaseId(41)
    @Test(description = "Удаление блокировки для маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = {"postRoutesLock200"})
    public void deleteRoutesLock200() {
        Response response = ShopperAdminRequest.Routes.Lock.DELETE(routeId);
        checkStatusCode200(response);
    }

    @CaseId(38)
    @Test(description = "Удаление маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = {"postRoutes200", "getRoute200", "postRoutesLock200", "putRoutes200"})
    public void deleteRoute200() {
        Response response = ShopperAdminRequest.Routes.DELETE(routeId);
        checkStatusCode200(response);
    }

    @CaseId(40)
    @Test(description = "Изменения` шоппера",
            groups = {"api-shopper-regress"})
    public void patchShopper200() {
        Response response = ShopperAdminRequest.Shoppers.PATCH(shopperId, shopperStatus);
        checkStatusCode200(response);

        ShopperV1 shopper = response.as(ShopperAdminSHPResponse.class).getShopper();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shopperId, shopper.getId(), "shopperId не совпадает");
        softAssert.assertEquals(shopperStatus, shopper.getStatus(), "status не совпадает");
        softAssert.assertAll();
    }

    @CaseId(42)
    @Test(description = "Создание видимости для маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = {"postRoutes200"})
    public void postRoutesVisibility200() {
        Response response = ShopperAdminRequest.Routes.Visibility.POST(routeId);
        checkStatusCode200(response);
    }

    @CaseId(89)
    @Test(description = "Создание видимости для маршрута",
            groups = {"api-shopper-regress"},
            dependsOnMethods = {"postRoutesVisibility200"})
    public void deleteRoutesVisibility200() {
        Response response = ShopperAdminRequest.Routes.Visibility.DELETE(routeId);
        checkStatusCode200(response);
    }
}
