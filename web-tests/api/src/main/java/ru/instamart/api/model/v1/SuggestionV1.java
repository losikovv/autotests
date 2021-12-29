
package ru.instamart.api.model.v1;

import java.util.List;
import javax.validation.constraints.NotEmpty;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuggestionV1 extends BaseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<SuggestionOfferV1> offers;

    @JsonSchema(required = true)
    private List<String> searches;

    @JsonSchema(required = true)
    private List<Object> taxons;
}
