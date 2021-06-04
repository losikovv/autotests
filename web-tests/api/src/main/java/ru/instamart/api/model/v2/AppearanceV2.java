package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppearanceV2 extends BaseObject {
    @JsonProperty(value = "background_color")
    private String backgroundColor;
    @JsonProperty(value = "image_color")
    private String imageColor;
    @JsonProperty(value = "black_theme")
    private String blackTheme;
    @JsonProperty(value = "logo_image")
    private String logoImage;
    @JsonProperty(value = "side_image")
    private String sideImage;
    @JsonProperty(value = "mini_logo_image")
    private String miniLogoImage;
}
