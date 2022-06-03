
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.IconV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminShippingCategoryV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @Null
    @JsonSchema(required = true)
    private String description;

    @Null
    @JsonSchema(required = true)
    private IconV2 icon;

    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    private Object instructions;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("payment_method_ids")
    private List<Long> paymentMethodIds;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_ids")
    private List<Long> shippingMethodIds;

    @JsonSchema(required = true)
    private String slug;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;
}
