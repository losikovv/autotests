package ru.instamart.api.model.shadowcat.conditions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Condition extends BaseObject {
    private SimpleCondition conditionType;
    @JsonProperty(value = "inclusion_type")
    private String inclusionType;

    private enum SimpleCondition {
    }
}


