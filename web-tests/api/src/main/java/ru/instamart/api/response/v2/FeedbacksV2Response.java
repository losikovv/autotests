package ru.instamart.api.response.v2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbacksV2Response  extends BaseResponseObject {

	@JsonProperty("meta")
	private MetaV2 meta;

	@JsonProperty("feedbacks")
	private List<Object> feedbacks;
}