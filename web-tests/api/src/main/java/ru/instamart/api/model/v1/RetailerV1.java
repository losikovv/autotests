
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV1 extends BaseObject {

    private AppearanceV1 appearance;
    private String color;
    private String description;
    private String icon;
    private Long id;
    @JsonProperty("is_agent_contract_types")
    private Boolean isAgentContractTypes;
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;
    private String logo;
    @JsonProperty("logo_background_color")
    private String logoBackgroundColor;
    private String name;
    @JsonProperty("secondary_color")
    private String secondaryColor;
    private List<Object> services;
    private String slug;
}
