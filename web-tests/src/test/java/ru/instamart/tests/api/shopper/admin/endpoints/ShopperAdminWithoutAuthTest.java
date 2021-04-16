package ru.instamart.tests.api.shopper.admin.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.shopper.admin.ShopperAdminRequest;

import static ru.instamart.api.checkpoints.ShopperApiCheckpoints.checkStatusCode401;

public class ShopperAdminWithoutAuthTest extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void clearAuth() {
        SessionFactory.clearSession(SessionType.SHOPPER_ADMIN);
    }

    @Test(  description = "Список сотрудников без авторизации",
            groups = {"api-shopper-regress"})
    public void getShoppers401() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode401(response);
    }

    @Test(  description = "Список магазинов без авторизации",
            groups = {"api-shopper-regress"})
    public void getStores401() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode401(response);
    }

    @Test(  description = "Список маршрутов без авторизации",
            groups = {"api-shopper-regress"})
    public void getRouteSchedules401() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode401(response);
    }

    @Test(  description = "Список операционных зон без авторизации",
            groups = {"api-shopper-regress"})
    public void getOperationalZones401() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode401(response);
    }

    @Test(  description = "Список ретейлеров без авторизации",
            groups = {"api-shopper-regress"})
    public void getRetailers401() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode401(response);
    }
}
