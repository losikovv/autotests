package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppearanceV2 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    @JsonProperty(value = "image_color")
    private String imageColor;

    @JsonSchema(required = true)
    @JsonProperty(value = "black_theme")
    private Boolean blackTheme;

    @JsonSchema(required = true)
    @JsonProperty(value = "logo_image")
    private String logoImage;

    @JsonSchema(required = true)
    @JsonProperty(value = "side_image")
    private String sideImage;

    @JsonSchema(required = true)
    @JsonProperty(value = "mini_logo_image")
    private String miniLogoImage;
}
