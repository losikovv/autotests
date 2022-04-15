
package ru.instamart.api.model.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Segment extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("contact_name")
    private String contactName;

    @JsonSchema(required = true)
    @JsonProperty("contact_phone")
    private String contactPhone;

    @JsonSchema(required = true)
    @JsonProperty("contact_uuid")
    private String contactUuid;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("location_end")
    private LocationEnd locationEnd;

    @JsonSchema(required = true)
    @JsonProperty("location_start")
    private LocationStart locationStart;

    @JsonSchema(required = true)
    @JsonProperty("plan_ended_at")
    private String planEndedAt;

    @JsonSchema(required = true)
    @JsonProperty("plan_started_at")
    private String planStartedAt;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private List<Shipment> shipments;

    @JsonSchema(required = true)
    @JsonProperty("store_address")
    private String storeAddress;

    @JsonSchema(required = true)
    @JsonProperty("store_name")
    private String storeName;

    @JsonSchema(required = true)
    private Integer type;

    @JsonSchema(required = true)
    @JsonProperty("workflow_id")
    private Long workflowId;
}
