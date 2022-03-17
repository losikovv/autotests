
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
public class PromotionCardV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("background_color")
    private String backgroundColor;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("background_image")
    private String backgroundImage;

    @Null
    @JsonSchema(required = true)
    private CategoryV1 category;

    @JsonSchema(required = true)
    @JsonProperty("full_description")
    private String fullDescription;

    @JsonSchema(required = true)
    @JsonProperty("human_type")
    private String humanType;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("message_color")
    private String messageColor;

    @JsonSchema(required = true)
    private Boolean published;

    @JsonSchema(required = true)
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonSchema(required = true)
    @JsonProperty("store_ids")
    private List<Integer> storeIds;

    @JsonSchema(required = true)
    @JsonProperty("tenant_ids")
    private List<String> tenantIds;

    private List<StoreTenantV1> tenants;

    @JsonSchema(required = true)
    private String title;

    @JsonSchema(required = true)
    private String type;

    private PromotionV1 promotion;
}
