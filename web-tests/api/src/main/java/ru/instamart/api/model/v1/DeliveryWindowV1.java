
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryWindowV1 extends BaseObject {

    private Boolean active;

    @JsonSchema(required = true)
    private Boolean available;

    @Null
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
    @JsonProperty("shipment_max_kilos")
    private Double shipmentMaxKilos;

    @JsonProperty("shipment_min_kilos")
    private Double shipmentMinKilos;

    @JsonProperty("shipments_count")
    private Long shipmentsCount;

    @JsonProperty("shipments_excess_items_count")
    private Integer shipmentsExcessItemsCount;

    @JsonProperty("shipments_excess_kilos")
    private Double shipmentsExcessKilos;

    @JsonProperty("shipments_items_count_limit")
    private Long shipmentsItemsCountLimit;

    @JsonProperty("shipments_kilos_limit")
    private Double shipmentsKilosLimit;

    @JsonProperty("shipments_limit")
    private Integer shipmentsLimit;

    @JsonProperty("shipments_total_items_count")
    private Long shipmentsTotalItemsCount;

    @JsonProperty("shipments_total_kilos")
    private Double shipmentsTotalKilos;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;

    @JsonProperty("store_zones")
    private List<StoreZoneV1> storeZones;

    @JsonSchema(required = true)
    @JsonProperty("surge_amount")
    private Double surgeAmount;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;
}
