package ru.instamart.api.request.shopper.admin;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAdminEndpoints;
import ru.instamart.api.request.ShopperAdminRequestBase;

@SuppressWarnings("unchecked")
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

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULES)
        public static Response POST(final int sid,
                                   final String date,
                                   final String status) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("store_id", sid);
            requestParams.put("date", date);
            requestParams.put("status", status);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAdminEndpoints.ROUTE_SCHEDULES);
        }

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULE)
        public static Response PUT(final int routeScheduleId,
                                   final int sid,
                                   final String date,
                                   final String status) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("store_id", sid);
            requestParams.put("date", date);
            requestParams.put("status", status);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .put(ShopperAdminEndpoints.ROUTE_SCHEDULE, routeScheduleId);
        }

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULE)
        public static Response PATCH(final int routeScheduleId, final String status) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status", status);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .patch(ShopperAdminEndpoints.ROUTE_SCHEDULE, routeScheduleId);
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

    public static class Shipments {
        @Step("{method} /" + ShopperAdminEndpoints.SHIPMENTS)
        public static Response GET(final int sid, final String date) {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.SHIPMENTS, sid, date);
        }
    }

    public static class Shifts {
        @Step("{method} /" + ShopperAdminEndpoints.SHIFTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.SHIFTS);
        }
    }

    public static class Tariffs {
        @Step("{method} /" + ShopperAdminEndpoints.TARIFFS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.TARIFFS);
        }
    }

    public static class Routes {
        @Step("{method} /" + ShopperAdminEndpoints.ROUTE)
        public static Response GET(final int routeId) {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROUTE, routeId);
        }
    }

    public static class Roles {
        @Step("{method} /" + ShopperAdminEndpoints.ROLES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROLES);
        }
    }
}
