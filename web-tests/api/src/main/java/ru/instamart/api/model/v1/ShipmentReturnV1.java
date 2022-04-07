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
public class ShipmentReturnV1 extends BaseObject {
    @JsonSchema(required = true)
    private String uuid;
    @JsonSchema(required = true)
    private String state;
    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;
    @JsonSchema(required = true)
    private Double quantity;
    @JsonSchema(required = true)
    private String kind;
    @Null
    @JsonSchema(required = true)
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonSchema(required = true)
    @JsonProperty("author_full_name")
    private String authorFullName;
    @JsonSchema(required = true)
    private Double amount;
    @JsonProperty("through_employee")
    private Boolean throughEmployee;
    @JsonSchema(required = true)
    @JsonProperty("item_returns")
    private List<ItemReturnV1> itemReturns;
}
