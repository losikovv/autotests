
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV1 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean active;

    @JsonSchema(required = true)
    private Boolean available;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("closing_time_gap")
    private Object closingTimeGap;

    @JsonSchema(required = true)
    private String date;

    @JsonSchema(required = true)
    @JsonProperty("ends_at")
    private String endsAt;

    @JsonSchema(required = true)
    @JsonProperty("human_interval")
    private String humanInterval;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String kind;

    @JsonSchema(required = true)
    private Long serial;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("shipment_max_kilos")
    private Double shipmentMaxKilos;

    @JsonSchema(required = true)
    @JsonProperty("shipment_min_kilos")
    private Double shipmentMinKilos;

    @JsonSchema(required = true)
    @JsonProperty("shipments_count")
    private Long shipmentsCount;

    @JsonSchema(required = true)
    @JsonProperty("shipments_excess_items_count")
    private Long shipmentsExcessItemsCount;

    @JsonSchema(required = true)
    @JsonProperty("shipments_excess_kilos")
    private Double shipmentsExcessKilos;

    @JsonSchema(required = true)
    @JsonProperty("shipments_items_count_limit")
    private Long shipmentsItemsCountLimit;

    @JsonSchema(required = true)
    @JsonProperty("shipments_kilos_limit")
    private Double shipmentsKilosLimit;

    @JsonSchema(required = true)
    @JsonProperty("shipments_limit")
    private Long shipmentsLimit;

    @JsonSchema(required = true)
    @JsonProperty("shipments_total_items_count")
    private Long shipmentsTotalItemsCount;

    @JsonSchema(required = true)
    @JsonProperty("shipments_total_kilos")
    private Double shipmentsTotalKilos;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty("store_zones")
    private List<StoreZoneV1> storeZones;

    @JsonSchema(required = true)
    @JsonProperty("surge_amount")
    private Double surgeAmount;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;
}
