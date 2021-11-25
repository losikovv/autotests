
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
public class DeliveryTimeV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("closing_time_gap")
    private Object closingTimeGap;

    @JsonSchema(required = true)
    private String end;

    @Null
    @JsonSchema(required = true)
    private Object kind;

    @JsonSchema(required = true)
    @JsonProperty("orders_limit")
    private Object ordersLimit;

    @JsonSchema(required = true)
    @JsonProperty("shipment_max_kilos")
    private Object shipmentMaxKilos;

    @JsonSchema(required = true)
    @JsonProperty("shipment_min_kilos")
    private Object shipmentMinKilos;

    @JsonSchema(required = true)
    @JsonProperty("shipments_excess_items_count")
    private Object shipmentsExcessItemsCount;

    @JsonSchema(required = true)
    @JsonProperty("shipments_excess_kilos")
    private Object shipmentsExcessKilos;

    @JsonSchema(required = true)
    private String start;

    @JsonSchema(required = true)
    @JsonProperty("store_zone_ids")
    private List<Object> storeZoneIds;

    @JsonSchema(required = true)
    @JsonProperty("surge_amount")
    private Object surgeAmount;
}
