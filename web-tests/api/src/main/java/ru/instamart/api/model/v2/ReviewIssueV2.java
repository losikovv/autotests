package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ReviewIssueV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    @JsonProperty(value = "comment_needed")
    private Boolean commentNeeded;

    @JsonProperty(value = "min_rate")
    private Integer minRate;

    @JsonProperty(value = "max_rate")
    private Integer maxRate;
}
