
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class CityV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("name_from")
    private String nameFrom;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("name_in")
    private String nameIn;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("name_to")
    private String nameTo;

    @JsonSchema(required = true)
    private String slug;

    private Boolean locked;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
