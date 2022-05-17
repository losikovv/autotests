
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppearanceV3 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    @JsonProperty("logo_image")
    private String logoImage;

    @JsonSchema(required = true)
    @JsonProperty("mini_logo_image")
    private String miniLogoImage;
}
