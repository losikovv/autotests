
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("date_created")
    private String dateCreated;

    @JsonSchema(required = true)
    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("shipment_number")
    private String shipmentNumber;

    @JsonSchema(required = true)
    @JsonProperty("status_id")
    private String statusId;

    @JsonSchema(required = true)
    @JsonProperty("status_id_name")
    private String statusIdName;

    @JsonSchema(required = true)
    @JsonProperty("type_id")
    private Long typeId;

    @JsonSchema(required = true)
    @JsonProperty("type_name")
    private String typeName;

    @JsonSchema(required = true)
    @JsonProperty("unique_id")
    private String uniqueId;
}
