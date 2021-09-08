package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode401;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperAdminWithoutAuthTest extends RestBase {
    private final Integer routeId = 1;
    private final Integer routeScheduleId = 1;
    private final Integer sid = EnvironmentProperties.DEFAULT_SHOPPER_SID;
    private final String yesterday = getDateFromMSK().minusDays(1).toString();

    @BeforeMethod(alwaysRun = true)
    public void clearAuth() {
        SessionFactory.clearSession(SessionType.SHOPPER_ADMIN);
    }

    @CaseId(74)
    @Test(  description = "Список сотрудников без авторизации",
            groups = {"api-shopper-regress"})
    public void getShoppers401() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode401(response);
    }

    @CaseId(75)
    @Test(  description = "Список магазинов без авторизации",
            groups = {"api-shopper-regress"})
    public void getStores401() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode401(response);
    }

    @CaseId(76)
    @Test(  description = "Список маршрутов без авторизации",
            groups = {"api-shopper-regress"})
    public void getRouteSchedules401() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode401(response);
    }

    @CaseId(77)
    @Test(  description = "Список операционных зон без авторизации",
            groups = {"api-shopper-regress"})
    public void getOperationalZones401() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode401(response);
    }

    @CaseId(78)
    @Test(  description = "Список ретейлеров без авторизации",
            groups = {"api-shopper-regress"})
    public void getRetailers401() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode401(response);
    }

    @CaseId(79)
    @Test(  description = "Список доставок без авторизации",
            groups = {"api-shopper-regress"})
    public void getShipments401() {
        Response response = ShopperAdminRequest.Shipments.GET(sid, yesterday);
        checkStatusCode401(response);
    }

    @CaseId(80)
    @Test(  description = "Список смен без авторизации",
            groups = {"api-shopper-regress"})
    public void getShifts401() {
        Response response = ShopperAdminRequest.Shifts.GET();
        checkStatusCode401(response);
    }

    @CaseId(82)
    @Test(  description = "Список тарифов без авторизации",
            groups = {"api-shopper-regress"})
    public void getTariffs401() {
        Response response = ShopperAdminRequest.Tariffs.GET();
        checkStatusCode401(response);
    }

    @CaseId(81)
    @Test(  description = "Маршрут без авторизации",
            groups = {"api-shopper-regress"})
    public void getRoute401() {
        Response response = ShopperAdminRequest.Routes.GET(routeId);
        checkStatusCode401(response);
    }

    @CaseId(83)
    @Test(  description = "Изменение расписания без авторизации",
            groups = {"api-shopper-regress"})
    public void patchRouteSchedule401() {
        Response response = ShopperAdminRequest.RouteSchedules.PATCH(routeScheduleId, "enabled");
        checkStatusCode401(response);
    }
}
