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
import ru.instamart.api.model.shopper.admin.RouteScheduleV1;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.shopper.admin.*;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.common.pagesdata.EnvironmentData;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperAdminWithAuthTest extends RestBase {
    private Integer routeId;
    private Integer routeScheduleId;
    private final String routeScheduleStatus = "enabled";
    private final Integer sid = EnvironmentData.INSTANCE.getDefaultShopperSid();
    private final String yesterday = LocalDate.now().minusDays(1).toString();

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
        response.prettyPeek();
        List<RouteScheduleV1> routeSchedules = response.as(RouteSchedulesSHPResponse.class).getRouteSchedules();
        assertFalse(routeSchedules.isEmpty());
        routeScheduleId = routeSchedules.get(0).getId();
        routeId = routeSchedules.get(0).getRoutes().get(0).getId();
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
        Response response = ShopperAdminRequest.Shipments.GET(sid, yesterday);
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

    @CaseId(30)
    @Test(  description = "Маршрут",
            groups = {"api-shopper-regress"},
            dependsOnMethods = "getRouteSchedules200")
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

}
