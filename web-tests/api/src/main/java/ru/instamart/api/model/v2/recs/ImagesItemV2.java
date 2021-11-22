package ru.instamart.api.model.v2.recs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImagesItemV2 extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("small_url")
    private String smallUrl;

    @JsonSchema(required = true)
    @JsonProperty("product_url")
    private String productUrl;

    @JsonSchema(required = true)
    @JsonProperty("original_url")
    private String originalUrl;

    @JsonSchema(required = true)
    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    @JsonProperty("mini_url")
    private String miniUrl;
}