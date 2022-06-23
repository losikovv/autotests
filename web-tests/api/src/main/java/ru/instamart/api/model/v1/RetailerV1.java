
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV1 extends BaseObject {

    private AppearanceV1 appearance;

    private String color;

    @Null
    private String description;

    private String icon;

    @JsonSchema(required = true)
    private Long id;

    @JsonProperty("is_agent_contract_types")
    private Boolean isAgentContractTypes;

    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    private String logo;

    @JsonSchema(required = true)
    private String name;

    @JsonProperty("secondary_color")
    private String secondaryColor;

    private List<Object> services;

    private String slug;
}
