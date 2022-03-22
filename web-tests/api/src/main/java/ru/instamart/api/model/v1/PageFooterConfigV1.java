
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageFooterConfigV1 extends BaseObject {

    @JsonSchema(required = true)
    private String companyName;

    @JsonSchema(required = true)
    private String facebookPage;

    @JsonSchema(required = true)
    private String instagramPage;

    @JsonSchema(required = true)
    private String okPage;

    @JsonSchema(required = true)
    private String openingHours;

    @JsonSchema(required = true)
    private String twitterPage;

    @JsonSchema(required = true)
    private String vkontaktePage;
}
