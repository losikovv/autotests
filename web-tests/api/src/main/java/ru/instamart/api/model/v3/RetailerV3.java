
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV3 extends BaseObject {

    @JsonSchema(required = true)
    private AppearanceV3 appearance;

    @JsonSchema(required = true)
    private Boolean available;

    @JsonSchema(required = true)
    private String color;

    @JsonSchema(required = true)
    private String environment;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("retailer_agreement")
    private RetailerAgreementV3 retailerAgreement;

    @JsonSchema(required = true)
    private String slug;
}
