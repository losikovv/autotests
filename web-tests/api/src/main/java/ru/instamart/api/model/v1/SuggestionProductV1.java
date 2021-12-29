
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuggestionProductV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private List<ImageV1> images;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String permalink;

    @JsonSchema(required = true)
    private String sku;
}
