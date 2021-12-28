
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImageV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("is_placeholder")
    private Boolean isPlaceholder;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("mini_url")
    private String miniUrl;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("original_url")
    private String originalUrl;

    @Null
    private Object position;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("preview_url")
    private String previewUrl;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("product_url")
    private String productUrl;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("small_url")
    private String smallUrl;
}
