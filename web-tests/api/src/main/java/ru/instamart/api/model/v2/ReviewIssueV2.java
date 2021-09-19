package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ReviewIssueV2 extends BaseObject {
    private Integer id;
    private String position;
    private String description;
    @JsonProperty(value = "comment_needed")
    private Boolean commentNeeded;
}
