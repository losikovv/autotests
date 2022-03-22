
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class WelcomeBannerV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    private String image;

    @JsonSchema(required = true)
    @JsonProperty("image_height")
    private Integer imageHeight;

    @JsonSchema(required = true)
    @JsonProperty("image_width")
    private Integer imageWidth;

    @JsonSchema(required = true)
    @JsonProperty("message_body")
    private String messageBody;

    @JsonSchema(required = true)
    @JsonProperty("message_color")
    private String messageColor;

    @JsonSchema(required = true)
    @JsonProperty("message_title")
    private String messageTitle;

    @JsonSchema(required = true)
    private String slug;
}
