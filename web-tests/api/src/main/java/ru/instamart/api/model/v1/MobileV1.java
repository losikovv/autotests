
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MobileV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("close_button_text")
    private String closeButtonText;

    @JsonSchema(required = true)
    private String header;

    private String promocode;

    @JsonSchema(required = true)
    private String text;

    @JsonProperty("link_text")
    private String linkText;

    private String link;

    @JsonProperty("open_link_in_new_tab")
    private Boolean openLinkInNewTab;
}
