package ru.instamart.api.model.v2.simple_ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImageV2 extends BaseResponseObject {
	@JsonProperty("native_ad_asset_type_id")
	private String nativeAdAssetTypeId;
	private String url;
}