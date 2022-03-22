
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetailerPromoCardV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("action_title")
    private String actionTitle;

    @JsonSchema(required = true)
    @JsonProperty("action_url")
    private String actionUrl;

    @JsonSchema(required = true)
    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    @JsonProperty("background_image")
    private String backgroundImage;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String logo;

    @JsonSchema(required = true)
    @JsonProperty("message_body")
    private String messageBody;

    @JsonSchema(required = true)
    @JsonProperty("message_color")
    private String messageColor;

    @JsonSchema(required = true)
    @JsonProperty("message_title")
    private String messageTitle;
}
