package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ReviewIssueV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ReviewIssuesV2Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("review_issues")
    private List<ReviewIssueV2> reviewIssues;
}
