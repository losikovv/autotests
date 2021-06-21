package ru.instamart.api.model.v2.simple_ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtV2 extends BaseResponseObject {
	@JsonProperty("assert_type")
	private String assetType;
}