
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserShipmentDeliveryWindowV1 extends BaseObject {

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
    @JsonProperty("items_count_balance")
    private Long itemsCountBalance;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @JsonSchema(required = true)
    @JsonProperty("weight_balance")
    private Long weightBalance;
}
