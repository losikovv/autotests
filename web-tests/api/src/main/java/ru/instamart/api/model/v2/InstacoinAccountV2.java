
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class InstacoinAccountV2 extends BaseObject {

    @JsonSchema(required = true)
    private Double amount;

    @JsonSchema(required = true)
    @JsonProperty("rules_link")
    private String rulesLink;

    @NotEmpty
    @JsonSchema(required = true)
    private List<ShortTermV2> terms;
}
