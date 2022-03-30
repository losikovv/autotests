
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SubscriptionV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("expired_date")
    private String expiredDate;

    @JsonSchema(required = true)
    @JsonProperty("free_deliveries_info")
    private FreeDeliveriesInfoV1 freeDeliveriesInfo;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("packet_kind")
    private String packetKind;

    @JsonSchema(required = true)
    private ServiceV1 service;
}
