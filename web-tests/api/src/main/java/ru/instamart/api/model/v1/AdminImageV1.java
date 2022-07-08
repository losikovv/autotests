package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminImageV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("mini_url")
    private String miniUrl;

    @JsonSchema(required = true)
    @JsonProperty("small_url")
    private String smallUrl;

    @JsonSchema(required = true)
    @JsonProperty("product_url")
    private String productUrl;

    @JsonSchema(required = true)
    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonSchema(required = true)
    @JsonProperty("original_url")
    private String originalUrl;

    @JsonSchema(required = true)
    @JsonProperty("variant_id")
    private Integer variantId;

    @Null
    @JsonSchema(required = true)
    private String alt;

    @JsonSchema(required = true)
    private Integer position;
}
