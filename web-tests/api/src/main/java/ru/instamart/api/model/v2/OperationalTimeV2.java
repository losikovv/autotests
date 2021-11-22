package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalTimeV2 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "week_day")
    private String weekDay;

    @JsonSchema(required = true)
    @JsonProperty(value = "starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "ends_at")
    private String endsAt;
}
