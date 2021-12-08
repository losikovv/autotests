package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.instamart.utils.Mapper;

public class StoresAdminRequest extends AdminRequestBase {

   /* @Step("{method} /" + AdminEndpoints.STORES)
    public static Response POST(Store store) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(store))
                .post(AdminEndpoints.STORES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Store {
        @JsonProperty("store[retailer_id]")
        private Integer retailerId;
        @JsonProperty("store[operational_zone_id]")
        private Integer operationalZoneId;
        @JsonProperty("store[city_id]")
        private Integer cityId;
        @JsonProperty("store[time_zone]")
        private String timeZone;
        @JsonProperty("store[config_attributes][inn]")
        private String inn;
        @JsonProperty("store[config_attributes][ogrn]")
        private String ogrn;
        @JsonProperty("store[config_attributes][legal_name]")
        private String legalName;
        @JsonProperty("store[config_attributes][email]")
        private String email;
        @JsonProperty("store[config_attributes][phone]")
        private String phone;
        @JsonProperty("store[tenant_ids][]")
        private Object tenantIds;
        @JsonProperty("store[config_attributes][lifepay_identifier]")
        private String lifepayIdentifier;
        @JsonProperty("store[config_attributes][import_key_postfix]")
        private Integer importKeyPostFix;
        @JsonProperty("store[config_attributes][shipment_base_kilos]")
        private Integer shipmentBaseKilos;
        @JsonProperty("store[config_attributes][shipment_base_items_count]")
        private Integer shipmentBaseItemsCount;
        @JsonProperty("store[config_attributes][min_order_amount]")
        private Integer minOrderAmount;
        @JsonProperty("store[config_attributes][min_first_order_amount]")
        private Integer minFirstOrderAmount;
        @JsonProperty("store[config_attributes][min_order_amount_pickup]")
        private Integer minOrderAmountPickup;
        @JsonProperty("store[config_attributes][min_first_order_amount_pickup]")
        private Integer minFirstOrderAmountPickup;
        @JsonProperty("store[config_attributes][seconds_for_assembly_item]")
        private Integer secondsForAssemblyItem;
        @JsonProperty("store[config_attributes][additional_seconds_for_assembly]")
        private Integer additionalSecondsForAssembly;
        @JsonProperty("store[config_attributes][disallow_order_editing_hours]")
        private Integer disallowOrderEditingHours;
        @JsonProperty("store[config_attributes][hours_order_edit_locked]")
        private Integer hoursOrderEditLocked;
        @JsonProperty("store[config_attributes][external_assembly_enabled]")
        private Integer externalAssemblyEnabled;
        @JsonProperty("store[config_attributes][external_assembly_kind]")
        private String externalAssemblyKind;
        @JsonProperty("store[on_demand]")
        private Integer onDemand;
        @JsonProperty("store[opening_time]")
        private String openingTime;
        @JsonProperty("store[closing_time]")
        private String closingTime;
        @JsonProperty("store[on_demand_closing_delta]")
        private Integer onDemandClosingDelta;
        @JsonProperty("store[has_conveyor]")
        private Integer hasConveyor;
        @JsonProperty("store[auto_routing]")
        private Integer autoRouting;
    } */
}
