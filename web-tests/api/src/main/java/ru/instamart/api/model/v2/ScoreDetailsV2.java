package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreDetailsV2 extends BaseObject {
    @JsonProperty("feedback_count")
    private Integer feedbackCount;
    @JsonProperty("feedback_source")
    private String feedbackSource;
}
