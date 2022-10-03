package ru.instamart.api.request.shopper.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAdminEndpoints;
import ru.instamart.api.request.ShopperAdminRequestBase;
import ru.instamart.kraken.common.Mapper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.CarType;

import java.util.UUID;

@SuppressWarnings("unchecked")
public class   ShopperAdminRequest extends ShopperAdminRequestBase {

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
            return givenWithAuth()
                    .formParams(Mapper.INSTANCE.objectToMap(shipments))
                    .get(ShopperAdminEndpoints.SHOPPERS);
        }

        @Step("{method} /" + ShopperAdminEndpoints.Shoppers.BY_ID)
        public static Response PATCH(final int shopperId,
                                     final String status) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status", status);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .patch(ShopperAdminEndpoints.Shoppers.BY_ID, shopperId);
        }

        @Step("{method} /" + ShopperAdminEndpoints.SHOPPERS)
        public static Response POST(final ShoppersParameter shoppers) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToMap(shoppers))
                    .post(ShopperAdminEndpoints.SHOPPERS);
        }
    }

    public static class Stores {
        @Step("{method} /" + ShopperAdminEndpoints.STORES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAdminEndpoints.STORES);
        }


        @Step("{method} /" + ShopperAdminEndpoints.Stores.ESTIMATOR_SETTINGS)
        public static Response GET(String retailerUUID) {
            return givenWithAuth().get(ShopperAdminEndpoints.Stores.ESTIMATOR_SETTINGS, retailerUUID);
        }

        @Step("{method} /" + ShopperAdminEndpoints.Stores.ESTIMATOR_SETTINGS)
        public static Response PUT(String retailerUUID, PutEstimatorSettings parameters) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(parameters)
                    .put(ShopperAdminEndpoints.Stores.ESTIMATOR_SETTINGS, retailerUUID);
        }

        public static class DispatchSettings {
            @Step("{method} /" + ShopperAdminEndpoints.Stores.DISPATCH_SETTINGS)
            public static Response GET(String retailerUUID) {
                return givenWithAuth().get(ShopperAdminEndpoints.Stores.DISPATCH_SETTINGS, retailerUUID);
            }

            @Step("{method} /" + ShopperAdminEndpoints.Stores.DISPATCH_SETTINGS)
            public static Response PUT(String retailerUUID, PutDispatchSettings parameters) {
                return givenWithAuth()
                        .contentType(ContentType.JSON)
                        .body(parameters)
                        .put(ShopperAdminEndpoints.Stores.DISPATCH_SETTINGS, retailerUUID);
            }
        }

    }

    public static class Vehicles {

        @Step("{method} /" + ShopperAdminEndpoints.VEHICLES)
        public static Response POST(final VehiclesParameter vehicles) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(Mapper.INSTANCE.objectToMap(vehicles))
                    .post(ShopperAdminEndpoints.VEHICLES);
        }
    }

    public static class OrderServiceSettings{
        @Step("{method} /" + ShopperAdminEndpoints.OrderServiceSettings.ORDER_SERVICE_SETTINGS)
        public static Response GET(final String storeUuid){
            return givenWithAuth().get(ShopperAdminEndpoints.OrderServiceSettings.ORDER_SERVICE_SETTINGS, storeUuid);
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

            public static class DispatchSettings {
                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.DISPATCH_SETTINGS)
                public static Response GET(Integer zoneId) {
                    return givenWithAuth()
                            .get(ShopperAdminEndpoints.OperationalZones.DISPATCH_SETTINGS, zoneId);
                }
            }


            public static class CandidatesSettings {
                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.CANDIDATES_SETTING)
                public static Response GET(Integer zoneId) {
                    return givenWithAuth()
                            .get(ShopperAdminEndpoints.OperationalZones.CANDIDATES_SETTING, zoneId);
                }

                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.CANDIDATES_SETTING)
                public static Response PUT(Integer zoneId, Integer normalShiftThreshold, Integer surgedShiftThreshold) {

                    JSONObject body = new JSONObject();
                    JSONObject settings = new JSONObject();
                    JSONObject candidateSettings = new JSONObject();

                    settings.put("surged_shift_threshold", surgedShiftThreshold);
                    settings.put("normal_shift_threshold", normalShiftThreshold);
                    settings.put("operational_zone_id", zoneId);

                    candidateSettings.put("settings", settings);
                    body.put("candidates_settings", candidateSettings);

                    return givenWithAuth()
                            .contentType(ContentType.JSON)
                            .body(body)
                            .put(ShopperAdminEndpoints.OperationalZones.CANDIDATES_SETTING, zoneId);
                }
            }

            public static class WorkflowSettings {
                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.WORKFLOW_SETTINGS)
                public static Response GET(Integer zoneId) {
                    return givenWithAuth()
                            .get(ShopperAdminEndpoints.OperationalZones.WORKFLOW_SETTINGS, zoneId);
                }

                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.WORKFLOW_SETTINGS)
                public static Response PUT(PutWorkflowSettings parameters) {
                    return givenWithAuth()
                            .contentType(ContentType.JSON)
                            .body(parameters)
                            .put(ShopperAdminEndpoints.OperationalZones.WORKFLOW_SETTINGS, EnvironmentProperties.DEFAULT_ID_ZONE);
                }
            }

            public static class EstimatorSettings {
                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.ESTIMATOR_SETTINGS)
                public static Response GET(Integer zoneId) {
                    return givenWithAuth()
                            .get(ShopperAdminEndpoints.OperationalZones.ESTIMATOR_SETTINGS, zoneId);
                }

                @Step("{method} /" + ShopperAdminEndpoints.OperationalZones.ESTIMATOR_SETTINGS)
                public static Response PUT(PutRegionEstimatorSettings parameters) {
                    return givenWithAuth()
                            .contentType(ContentType.JSON)
                            .body(parameters)
                            .put(ShopperAdminEndpoints.OperationalZones.ESTIMATOR_SETTINGS, EnvironmentProperties.DEFAULT_ID_ZONE);
                }
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
            public static Response DELETE(final int routeId) {
                return givenWithAuth()
                        .delete(ShopperAdminEndpoints.ROUTE, routeId);
            }

            public static class Lock {
                @Step("{method} /" + ShopperAdminEndpoints.Routes.LOCK)
                public static Response POST(final int routeId) {
                    return givenWithAuth()
                            .post(ShopperAdminEndpoints.Routes.LOCK, routeId);
                }

                @Step("{method} /" + ShopperAdminEndpoints.Routes.LOCK)
                public static Response DELETE(final int routeId) {
                    return givenWithAuth()
                            .delete(ShopperAdminEndpoints.Routes.LOCK, routeId);
                }
            }

            public static class Visibility {
                @Step("{method} /" + ShopperAdminEndpoints.Routes.VISIBILITY)
                public static Response POST(final int routeId) {
                    return givenWithAuth()
                            .post(ShopperAdminEndpoints.Routes.VISIBILITY, routeId);
                }

                @Step("{method} /" + ShopperAdminEndpoints.Routes.VISIBILITY)
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

        public static class Scango {
            @Step("{method} /" + ShopperAdminEndpoints.Scango.ENGINES)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperAdminEndpoints.Scango.ENGINES);
            }
        }

    public static class RoutingSettings{

        @Step("{method} /" + ShopperAdminEndpoints.RoutingSettings.ROUTING_SETTINGS)
        public static Response PATCH(final int storeId,final String schedule_type) {
            JSONObject requestParams = new JSONObject();
            JSONObject store = new JSONObject();
            requestParams.put("store",store);
            store.put("id", storeId);
            store.put("schedule_type", schedule_type);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .patch(ShopperAdminEndpoints.RoutingSettings.ROUTING_SETTINGS, storeId);
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

        @Builder
        @Data
        @EqualsAndHashCode
        public static class PutWorkflowSettings {
            @JsonProperty("workflow_settings")
            private Settings settings;
        }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class Settings {
            @JsonProperty("settings")
            private WorkflowParameters workflowParameters;
        }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class WorkflowParameters {
            @JsonProperty("operational_zone_id")
            private Integer operationalZoneId;
            @JsonProperty("increasing_late_assembly_percentage")
            private Number increasingLateAssemblyPercentage;
            @JsonProperty("segment_assembly_tardiness_sec")
            private Integer segmentAssemblyTardinessSec;
            @JsonProperty("server_offer_storage_time_sec")
            private Integer serverOfferStorageTimeSec;
            @JsonProperty("time_to_accept_offer_sec")
            private Integer timeToAcceptOfferSec;
            @JsonProperty("timeout_segment_pass_to_client_sec")
            private Integer timeoutSegmentPassToClientSec;
        }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class PutEstimatorSettings {
            @JsonProperty("estimator_settings")
            private SettingsRes settingsRes;
        }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class SettingsRes {
            @JsonProperty("settings")
            private EstimatorParameters estimatorParameters;
        }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class EstimatorParameters {
            @JsonProperty("store_uuid")
            private String storeUuid;
            @JsonProperty("avg_parking_min_vehicle")
            private Integer avgParkingMinVehicle;
            @JsonProperty("average_speed_for_straight_distance_to_client_min")
            private Integer averageSpeedForStraightDistanceToClientMin;
            @JsonProperty("additional_factor_for_straight_distance_to_client_min")
            private Integer additionalFactorForStraightDistanceToClientMin;
            @JsonProperty("order_receive_time_from_assembly_to_delivery_min")
            private Integer orderReceiveTimeFromAssemblyToDeliveryMin;
            @JsonProperty("avg_to_place_min_external")
            private Integer avgToPlaceMinExternal;
            @JsonProperty("order_transfer_time_from_assembly_to_delivery_min")
            private Integer orderTransferTimeFromAssemblyToDeliveryMin;
            @JsonProperty("order_transfer_time_from_delivery_to_client_min")
            private Integer orderTransferTimeFromDeliveryToClientMin;
        }

        @Builder
        @Data
        @EqualsAndHashCode
            public static class PutDispatchSettings {
            @JsonProperty("dispatch_settings")
            private SettingsDispatch settingsDispatch;
    }

        @Builder
        @Data
        @EqualsAndHashCode
            public static class SettingsDispatch {
            @JsonProperty("settings")
            private DispatchParameters dispatchParameters;
    }

        @Builder
        @Data
        @EqualsAndHashCode
        public static class DispatchParameters {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("store_id")
        private Integer storeId;
        @JsonProperty("max_order_assign_retry_count")
        private Integer maxOrderAssignRetryCount;
        @JsonProperty("avg_parking_min_vehicle")
        private Integer avgParkingMinVehicle;
        @JsonProperty("max_current_order_assign_queue")
        private Integer maxCurrentOrderAssignQueue;
        @JsonProperty("order_weight_threshold_to_assign_to_vehicle_gramms")
        private Integer orderWeightThresholdToAssignToVehicleGramms;
        @JsonProperty("average_speed_for_straight_distance_to_client_min")
        private Integer averageSpeedForStraightDistanceToClientMin;
        @JsonProperty("additional_factor_for_straight_distance_to_client_min")
        private Integer additionalFactorForStraightDistanceToClientMin;
        @JsonProperty("order_transfer_time_from_assembly_to_delivery_min")
        private Integer orderTransferTimeFromAssemblyToDeliveryMin;
        @JsonProperty("avg_to_place_min")
        private Integer avgToPlaceMin;
        @JsonProperty("avg_to_place_min_external")
        private Integer avgToPlaceMinExternal;
        @JsonProperty("offer_seen_timeout_sec")
        private Integer offerSeenTimeoutSec;
        @JsonProperty("last_position_expire")
        private Integer lastPositionExpire;
        @JsonProperty("taxi_delivery_only")
        private boolean taxiDeliveryOnly;
        @JsonProperty("place_location_center")
        private boolean placeLocationCenter;
        @JsonProperty("order_transfer_time_from_delivery_to_client_min")
        private Integer orderTransferTimeFromDeliveryToClientMin;
        @JsonProperty("order_receive_time_from_assembly_to_delivery_min")
        private Integer orderReceiveTimeFromAssemblyToDeliveryMin;
        @JsonProperty("offer_server_timeout_sec")
        private Integer offerServerTimeoutSec;
        @JsonProperty("external_assembliers_presented")
        private boolean externalAssembliersPresented;
        @JsonProperty("gap_taxi_punish_min")
        private Integer gapTaxiPunishMin;
        @JsonProperty("taxi_available")
        private boolean taxiAvailable;
        @JsonProperty("max_waiting_time_for_courier_min")
        private Integer maxWaitingTimeForCourierMin;
    }

    @JsonTypeName("shopper")
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
    @Builder
    @Data
    @EqualsAndHashCode
    public static class ShoppersParameter {
        //contract_date: null or in format 2022-08-04
        @JsonProperty("contract_date")
        private String contractDate;
        //contract_number: null
        @JsonProperty("contract_number")
        private Integer contractNumber;
        //employment_type: null
        @JsonProperty("employment_type")
        private EmploymentType employmentType;
        //inn: null
        private String inn;
        //login: "sdgsdgs"
        private String login;
        //name: "asdasd"
        private String name;
        //partnership_id: null
        //EmploymentType=agent/outsource
        @JsonProperty("partnership_id")
        private Integer partnershipId;
        //phone: "+79236569227"
        private String phone;
        //roles: [1]
        //0: 1
        private Integer[] roles;
        //status: "enabled"
        private String status;
        //store_uuid: "386793e2-b453-43d5-878e-da70b213f8b3"
        @JsonProperty("store_uuid")
        private String storeUuid;
        private String password;

        public enum EmploymentType {
            ip,
            self_employed,
            agent,
            outsource,
            external_employee;
        }
    }

    @Builder
    @Data
    @EqualsAndHashCode
    public static class VehiclesParameter {
        //kind: "truck"
        private CarType kind;
        //model: "asda"
        private String model;
        //number: "В222ВВ22"
        private String number;
        //shopper_id: 2
        @JsonProperty("shopper_id")
        private Integer shopperId;
        //volume: 12
        private Integer volume;
    }

    @Builder
    @Data
    @EqualsAndHashCode
    public static class PutRegionEstimatorSettings {
        @JsonProperty("estimator_settings")
        private SettingsResRegion settingsResRegion;
    }

    @Builder
    @Data
    @EqualsAndHashCode
    public static class SettingsResRegion {
        @JsonProperty("settings")
        private EstimatorRegionParameters estimatorRegionParameters;
    }

    @Builder
    @Data
    @EqualsAndHashCode
    public static class EstimatorRegionParameters {
        @JsonProperty("operational_zone_id")
        private Integer operationalZoneId;
        @JsonProperty("correction_factor")
        private Number correctionFactor;
        @JsonProperty("increasing_factor")
        private Integer increasingFactor;
        @JsonProperty("minimum_segment_length")
        private Integer minimumSegmentLength;
    }
}