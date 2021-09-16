package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ReviewIssueV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ReviewIssuesV2Response extends BaseResponseObject {

    @JsonProperty("review_issues")
    private List<ReviewIssueV2> reviewIssues;
}
