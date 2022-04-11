package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdjustmentV1 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty("adjustable_id")
    private Integer adjustableId;

    @JsonSchema(required = true)
    @JsonProperty("adjustable_type")
    private String adjustableType;

    @JsonSchema(required = true)
    private Double amount;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private Boolean eligible;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String label;

    @JsonSchema(required = true)
    private Boolean mandatory;

    @JsonSchema(required = true)
    @JsonProperty("originator_id")
    private Integer originatorId;

    @JsonSchema(required = true)
    @JsonProperty("originator_type")
    private String originatorType;

    @Null
    @JsonProperty("promotion_code")
    private Object promotionCode;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("source_id")
    private Integer sourceId;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("source_type")
    private String sourceType;

    private String state;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;
}
