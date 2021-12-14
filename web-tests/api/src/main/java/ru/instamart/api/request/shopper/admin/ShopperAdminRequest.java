package ru.instamart.api.request.shopper.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAdminEndpoints;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAdminRequestBase;
import ru.instamart.api.request.shopper.app.ShopperSHPRequest;
import ru.sbermarket.common.Mapper;

import java.util.UUID;

@SuppressWarnings("unchecked")
public class ShopperAdminRequest extends ShopperAdminRequestBase {

    public static class ShiftAssignments {
        @Step("{method} /" + ShopperAdminEndpoints.SHIFT_ASSIGNMENTS)
        public static Response POST(final int shiftId,
                                    final int shopperId,
                                    final String starts_at,
                                    final String ends_at) {
            JSONObject requestParams = new JSONObject();
            JSONObject shiftAssignment = new JSONObject();
            requestParams.put("shift_assignment", shiftAssignment);
            shiftAssignment.put("shift_id", shiftId);
            shiftAssignment.put("shopper_id", shopperId);
            shiftAssignment.put("starts_at", starts_at);
            shiftAssignment.put("ends_at", ends_at);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAdminEndpoints.SHIFT_ASSIGNMENTS);
        }

        @Step("{method} /" + ShopperAdminEndpoints.SHIFT_ASSIGNMENT)
        public static Response PUT(final int shiftAssignmentId,
                                   final int shiftId,
                                   final int shopperId,
                                   final String starts_at,
                                   final String ends_at) {
            JSONObject requestParams = new JSONObject();
            JSONObject shiftAssignment = new JSONObject();
            requestParams.put("shift_assignment", shiftAssignment);
            shiftAssignment.put("shift_id", shiftId);
            shiftAssignment.put("shopper_id", shopperId);
            shiftAssignment.put("starts_at", starts_at);
            shiftAssignment.put("ends_at", ends_at);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .put(ShopperAdminEndpoints.SHIFT_ASSIGNMENT, shiftAssignmentId);
        }
    }

    public static class Shoppers {
        @Step("{method} /" + ShopperAdminEndpoints.SHOPPERS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.SHOPPERS);
        }


        @Step("{method} /" + ShopperAdminEndpoints.SHOPPERS)
        public static Response GET(ShipmentsParams shipments) {
            //shipments?store_id=16&assembly_state=shipped&includes[assembly]=items
            return givenWithAuth()
                    .formParams(Mapper.INSTANCE.objectToMap(shipments))
                    .get(ShopperAdminEndpoints.SHOPPERS);
        }

        @Step("{method} /" + ShopperAdminEndpoints.SHOPPER)
        public static Response PATCH(final int shopperId,
                                    final String status) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status", status);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .patch(ShopperAdminEndpoints.SHOPPER, shopperId);
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

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULES_WITH_PARAMS)
        public static Response GET(final int sid, final String date) {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROUTE_SCHEDULES_WITH_PARAMS, sid, date);
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

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE_SCHEDULE)
        public static Response DELETE(final int routeScheduleId) {
            return givenWithAuth()
                    .delete(ShopperAdminEndpoints.ROUTE_SCHEDULE, routeScheduleId);
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

        @Step("{method} /" + ShopperAdminEndpoints.SHIFTS)
        public static Response POST(final String date,
                                    final int position,
                                    final int storeId,
                                    final int tariffId) {
            JSONObject requestParams = new JSONObject();
            JSONObject shift = new JSONObject();
            requestParams.put("shift", shift);
            shift.put("date", date);
            shift.put("position", position);
            shift.put("store_id", storeId);
            shift.put("tariff_id", tariffId);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAdminEndpoints.SHIFTS);
        }
    }

    public static class Tariffs {
        @Step("{method} /" + ShopperAdminEndpoints.TARIFFS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.TARIFFS);
        }

        @Step("{method} /" + ShopperAdminEndpoints.TARIFFS)
        public static Response POST() {
            JSONObject requestParams = new JSONObject();
            JSONObject tariff = new JSONObject();
            requestParams.put("tariff", tariff);
            tariff.put("title", UUID.randomUUID());
            tariff.put("shift_schedule", "5/2");
            tariff.put("shift_time_of_day", "день");
            tariff.put("shift_starts_at", "01:00");
            tariff.put("shift_ends_at", "23:00");
            tariff.put("shift_payroll", "1000");
            tariff.put("role_id", 2);
            tariff.put("retailerId", 1);
            tariff.put("OperationalZoneId", 2);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAdminEndpoints.TARIFFS);
        }
    }

    public static class Routes {
        @Step("{method} /" + ShopperAdminEndpoints.ROUTE)
        public static Response GET(final int routeId) {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROUTE, routeId);
        }

        @Step("{method} /" + ShopperAdminEndpoints.ROUTES)
        public static Response POST(final int routeScheduleId,
                                    final int driverId,
                                    final int... shipmentIds) {
            JSONObject requestParams = getRouteRequestBody(routeScheduleId, driverId, shipmentIds);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .post(ShopperAdminEndpoints.ROUTES);
        }

        private static JSONObject getRouteRequestBody(int routeScheduleId, int driverId, int... shipmentIds) {
            JSONObject requestParams = new JSONObject();
            JSONObject route = new JSONObject();
            JSONArray routePoints = new JSONArray();
            requestParams.put("route", route);
            route.put("route_schedule_id", routeScheduleId);
            route.put("driver_id", driverId);
            route.put("route_points", routePoints);
            for (int shipmentId : shipmentIds) {
                JSONObject routePoint = new JSONObject();
                routePoint.put("shipment_id", shipmentId);
                routePoints.add(routePoint);
            }
            return requestParams;
        }

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE)
        public static Response PUT(final int routeId,
                                   final int routeScheduleId,
                                   final int driverId,
                                   final int... shipmentIds) {
            JSONObject requestParams = getRouteRequestBody(routeScheduleId, driverId, shipmentIds);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .put(ShopperAdminEndpoints.ROUTE, routeId);
        }

        @Step("{method} /" + ShopperAdminEndpoints.ROUTE)
        public static Response DELETE(final  int routeId) {
            return givenWithAuth()
                    .delete(ShopperAdminEndpoints.ROUTE, routeId);
        }

        public static class Lock {
            @Step("{method} /" + ShopperAdminEndpoints.Routes.LOCK)
            public static Response POST(final int routeId) {
                return givenWithAuth()
                        .post(ShopperAdminEndpoints.Routes.LOCK, routeId);
            }

            @Step ("{method} /" + ShopperAdminEndpoints.Routes.LOCK)
            public static Response DELETE(final int routeId) {
                return givenWithAuth()
                        .delete(ShopperAdminEndpoints.Routes.LOCK, routeId);
            }
        }

        public static class Visibility {
            @Step ("{method} /" + ShopperAdminEndpoints.Routes.VISIBILITY)
            public static Response POST(final int routeId) {
                return givenWithAuth()
                        .post(ShopperAdminEndpoints.Routes.VISIBILITY, routeId);
            }

            @Step ("{method} /" + ShopperAdminEndpoints.Routes.VISIBILITY)
            public static Response DELETE(final int routeId) {
                return givenWithAuth()
                        .delete(ShopperAdminEndpoints.Routes.VISIBILITY, routeId);
            }
        }
    }

    public static class Roles {
        @Step("{method} /" + ShopperAdminEndpoints.ROLES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.ROLES);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class ShipmentsParams {

        @JsonProperty("store_id")
        private final Integer storeId;
        @JsonProperty("assembly_state")
        private final String assembly_state;
        @JsonProperty("includes[assembly]")
        private final String includesAssembly;
        private final String number;
    }
}
