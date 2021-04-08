package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Image extends BaseObject {
    @JsonProperty(value = "mini_url")
    private String miniUrl;
    @JsonProperty(value = "small_url")
    private String smallUrl;
    @JsonProperty(value = "product_url")
    private String productUrl;
    @JsonProperty(value = "preview_url")
    private String previewUrl;
    @JsonProperty(value = "original_url")
    private String originalUrl;
    @JsonProperty(value = "default_url")
    private String defaultUrl;
}
