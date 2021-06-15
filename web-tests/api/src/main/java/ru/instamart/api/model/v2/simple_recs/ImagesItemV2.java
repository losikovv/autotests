package ru.instamart.api.model.v2.simple_recs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImagesItemV2 extends BaseResponseObject {
    @JsonProperty("small_url")
    private String smallUrl;
    @JsonProperty("product_url")
    private String productUrl;
    @JsonProperty("original_url")
    private String originalUrl;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("is_placeholder")
    private boolean isPlaceholder;
    @JsonProperty("position")
    private int position;
    @JsonProperty("mini_url")
    private String miniUrl;
}