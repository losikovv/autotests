
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppearanceV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    @JsonProperty("black_theme")
    private Boolean blackTheme;

    @JsonSchema(required = true)
    @JsonProperty("image_color")
    private String imageColor;

    @JsonSchema(required = true)
    @JsonProperty("logo_image")
    private String logoImage;

    @JsonSchema(required = true)
    @JsonProperty("mini_logo_image")
    private String miniLogoImage;

    @JsonSchema(required = true)
    @JsonProperty("side_image")
    private String sideImage;
}
