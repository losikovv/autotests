
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminShippingMethodV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("admin_name")
    private String adminName;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("deleted_at")
    private String deletedAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("display_on")
    private Object displayOn;

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String kind;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind_id")
    private Long shippingMethodKindId;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("tracking_url")
    private String trackingUrl;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;
}
