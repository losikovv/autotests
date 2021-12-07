
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.OfferV1;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PreReplacementV1 extends BaseObject {
    private List<OfferV1> offers;

    @JsonProperty("replace_policy")
    private String replacePolicy;

    private String sku;
}
