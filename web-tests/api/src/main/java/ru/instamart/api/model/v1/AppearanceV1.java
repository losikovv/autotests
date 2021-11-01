
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppearanceV1 extends BaseObject {

    @JsonProperty("background_color")
    private String backgroundColor;
    @JsonProperty("black_theme")
    private Boolean blackTheme;
    @JsonProperty("image_color")
    private String imageColor;
    @JsonProperty("logo_image")
    private String logoImage;
    @JsonProperty("mini_logo_image")
    private String miniLogoImage;
    @JsonProperty("side_image")
    private String sideImage;
}
