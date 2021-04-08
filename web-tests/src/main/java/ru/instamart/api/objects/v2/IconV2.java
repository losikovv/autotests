package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class IconV2 extends BaseObject {
    @JsonProperty(value = "mini_url")
    private String miniUrl;
    @JsonProperty(value = "normal_url")
    private String normalUrl;
    @JsonProperty(value = "small_url")
    private String smallUrl;
    @JsonProperty(value = "product_url")
    private String productUrl;
    @JsonProperty(value = "preview_url")
    private String previewUrl;
    @JsonProperty(value = "original_url")
    private String originalUrl;
    private String url;
}