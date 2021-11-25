package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImageV2 extends BaseObject {
    @JsonProperty(value = "mini_url")
    private String miniUrl;

    @JsonProperty(value = "small_url")
    private String smallUrl;

    @JsonProperty(value = "product_url")
    private String productUrl;

    @JsonProperty(value = "preview_url")
    private String previewUrl;

    @JsonSchema(required = true)
    @JsonProperty(value = "original_url")
    private String originalUrl;

    @JsonProperty(value = "default_url")
    private String defaultUrl;

    @JsonProperty(value = "is_placeholder")
    private Boolean isPlaceholder;

    private Integer position;
}
