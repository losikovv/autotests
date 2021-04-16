package ru.instamart.api.requests.shopper.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAdminEndpoints;
import ru.instamart.api.requests.ShopperAdminRequestBase;

public class ShopperAdminRequest extends ShopperAdminRequestBase {
    public static class Shoppers {
        @Step("{method} /" + ShopperAdminEndpoints.SHOPPERS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.SHOPPERS);
        }
    }

    public static class Stores {
        @Step("{method} /" + ShopperAdminEndpoints.STORES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.STORES);
        }
    }

    public static class RouteSchedules {
        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROUTE_SCHEDULES);
        }
    }

    public static class OperationalZones {
        @Step("{method} /" + ShopperAdminEndpoints.OPERATIONAL_ZONES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.OPERATIONAL_ZONES);
        }
    }

    public static class Retailers {
        @Step("{method} /" + ShopperAdminEndpoints.RETAILERS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.RETAILERS);
        }
    }
}
