
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DesktopV1 extends BaseObject {

    @JsonSchema(required = true)
    private String link;

    @JsonSchema(required = true)
    @JsonProperty("link_text")
    private String linkText;

    @JsonSchema(required = true)
    @JsonProperty("open_link_in_new_tab")
    private Boolean openLinkInNewTab;

    private String promocode;

    @JsonSchema(required = true)
    private String text;
}
