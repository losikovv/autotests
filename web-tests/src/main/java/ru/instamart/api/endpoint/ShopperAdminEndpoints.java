package ru.instamart.api.endpoint;

public final class ShopperAdminEndpoints {

    public static final String OPERATIONAL_ZONES = "v1/operational_zones";
    public static final String RETAILERS = "v1/retailers";
    public static final String ROLES = "v1/roles";
    public static final String ROUTE = "v1/routes/{routeId}";
    public static final String ROUTES = "v1/routes";
    public static final String ROUTE_SCHEDULE = "v1/route_schedules/{routeScheduleId}";
    public static final String ROUTE_SCHEDULES = "v1/route_schedules";
    public static final String ROUTE_SCHEDULES_WITH_PARAMS = "v1/route_schedules?store_ids[]={sid}&date={date}";
    public static final String SHIFT_ASSIGNMENT = "/v1/shift_assignments/{shiftAssignmentId}";
    public static final String SHIFT_ASSIGNMENTS = "/v1/shift_assignments";
    public static final String SHIFTS = "v1/shifts";
    public static final String SHIPMENTS = "v1/shipments?store_id={sid}&date={date}";
    public static final String SHOPPER = "v1/shoppers/{shopperId}";
    public static final String SHOPPERS = "v1/shoppers";
    public static final String STORES = "v1/stores";
    public static final String TARIFFS = "v1/tariffs";

    public static final class Routes {
        public static final String LOCK = "v1/routes/{routeId}/lock";
        public static final String VISIBILITY = "v1/routes/{routeId}/visibility";
    }
}
