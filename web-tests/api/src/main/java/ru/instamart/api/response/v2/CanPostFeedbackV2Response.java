package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class CanPostFeedbackV2Response extends BaseResponseObject {

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("can_post_feedback")
    private boolean canPostFeedback;
}