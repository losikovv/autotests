
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SeoV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("default_meta_description")
    private String defaultMetaDescription;

    @JsonSchema(required = true)
    @JsonProperty("default_seo_title")
    private String defaultSeoTitle;
}
