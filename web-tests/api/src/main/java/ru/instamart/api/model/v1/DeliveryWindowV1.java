
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV1 extends BaseObject {

    private Boolean active;
    private Boolean available;
    @JsonProperty("closing_time_gap")
    private String closingTimeGap;
    private String date;
    @JsonProperty("ends_at")
    private String endsAt;
    @JsonProperty("human_interval")
    private String humanInterval;
    private Long id;
    private String kind;
    private Long serial;
    @JsonProperty("shipment_max_kilos")
    private Double shipmentMaxKilos;
    @JsonProperty("shipment_min_kilos")
    private Double shipmentMinKilos;
    @JsonProperty("shipments_count")
    private Long shipmentsCount;
    @JsonProperty("shipments_excess_items_count")
    private Long shipmentsExcessItemsCount;
    @JsonProperty("shipments_excess_kilos")
    private Double shipmentsExcessKilos;
    @JsonProperty("shipments_items_count_limit")
    private Long shipmentsItemsCountLimit;
    @JsonProperty("shipments_kilos_limit")
    private Double shipmentsKilosLimit;
    @JsonProperty("shipments_limit")
    private Long shipmentsLimit;
    @JsonProperty("shipments_total_items_count")
    private Long shipmentsTotalItemsCount;
    @JsonProperty("shipments_total_kilos")
    private Double shipmentsTotalKilos;
    @JsonProperty("starts_at")
    private String startsAt;
    @JsonProperty("store_zones")
    private List<StoreZoneV1> storeZones;
    @JsonProperty("surge_amount")
    private Double surgeAmount;
    @JsonProperty("time_zone")
    private String timeZone;
}
