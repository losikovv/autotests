package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.*;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shadowcat.actions.Actions;
import ru.instamart.api.model.shadowcat.conditions.Condition;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Promotion extends BaseObject {

    @JsonSchema(required = true)
    private Actions actions;

    @JsonProperty(value = "aggregation_func")
    @JsonSchema(required = true)
    private String aggregationFunc;

    @JsonIgnore
    private Condition conditions;

    private String description;

    @JsonProperty(value = "description_internal")
    private String descriptionInternal;

    private String match;

    @JsonSchema(required = true)
    private int id;

    @JsonProperty(value = "is_active")
    private boolean isActive;

    @JsonProperty(value = "is_auto_accepted")
    private boolean isAutoAccepted;

    @JsonSchema(required = true)
    @JsonProperty(value = "promo_code_prefix")
    private String promoCodePrefix;

    @JsonSchema(required = true)
    private String name;

    @JsonProperty(value = "user_id")
    @JsonSchema(required = true)
    private int userId;

    private String[] period;

    @JsonProperty(value = "started_at")
    private String startedAt;

    @JsonProperty(value = "stopped_at")
    private String stoppedAt;
}
