package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class InstallmentPlanV1 extends BaseObject {

    @Null
    @JsonProperty("sber_order_created_at")
    private String sberOrderCreatedAt;

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String state;

    @Null
    @JsonProperty("sber_order_id")
    private String sberOrderId;
}
