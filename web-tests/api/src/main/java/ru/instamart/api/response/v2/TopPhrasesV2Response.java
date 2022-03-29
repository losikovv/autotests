package ru.instamart.api.response.v2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.TopPhrasesItemV2;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class TopPhrasesV2Response extends BaseResponseObject {

	@JsonProperty("top_phrases")
	private List<TopPhrasesItemV2> topPhrases;
}