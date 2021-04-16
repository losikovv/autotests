package ru.instamart.tests.api.shopper.admin.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.responses.shopper.admin.*;
import ru.instamart.core.testdata.UserManager;

import static org.junit.Assert.assertFalse;
import static ru.instamart.api.checkpoints.ShopperApiCheckpoints.checkStatusCode200;

public class ShopperAdminWithAuthTest extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
    }

    @Test(  description = "Список сотрудников",
            groups = {"api-shopper-regress"})
    public void getShoppers200() {
        Response response = ShopperAdminRequest.Shoppers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(ShoppersSHPResponse.class).getShoppers().isEmpty());
    }

    @Test(  description = "Список магазинов",
            groups = {"api-shopper-regress"})
    public void getStores200() {
        Response response = ShopperAdminRequest.Stores.GET();
        checkStatusCode200(response);
        assertFalse(response.as(StoresSHPResponse.class).getStores().isEmpty());
    }

    @Test(  description = "Список маршрутов",
            groups = {"api-shopper-regress"})
    public void getRouteSchedules200() {
        Response response = ShopperAdminRequest.RouteSchedules.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RouteSchedulesSHPResponse.class).getRouteSchedules().isEmpty());
    }

    @Test(  description = "Список операционных зон",
            groups = {"api-shopper-regress"})
    public void getOperationalZones200() {
        Response response = ShopperAdminRequest.OperationalZones.GET();
        checkStatusCode200(response);
        assertFalse(response.as(OperationalZonesSHPResponse.class).getOperationalZones().isEmpty());
    }

    @Test(  description = "Список ретейлеров",
            groups = {"api-shopper-regress"})
    public void getRetailers200() {
        Response response = ShopperAdminRequest.Retailers.GET();
        checkStatusCode200(response);
        assertFalse(response.as(RetailersSHPResponse.class).getRetailers().isEmpty());
    }
}
