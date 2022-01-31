package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentV2 extends BaseObject {
    private String number;

    @JsonSchema(required = true)
    private String state;

    @JsonProperty(value = "is_finalization_needed")
    private Boolean isFinalizationNeeded;

    @Null
    @JsonProperty(value = "finalization_url")
    private Object finalizationUrl;
}
