package ru.instamart.api.model.v2.simple_ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.instamart.api.response.BaseResponseObject;

public @Data class TitleV2 extends BaseResponseObject {
	@JsonProperty("native_ad_asset_type_id")
	private String nativeAdAssetTypeId;
	private String text;
}