package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class EanV1 extends BaseObject {
    @JsonSchema(required = true)
    private String value;

    @JsonSchema(required = true)
    @JsonProperty("offer_retailer_sku")
    private String offerRetailerSku;
}
