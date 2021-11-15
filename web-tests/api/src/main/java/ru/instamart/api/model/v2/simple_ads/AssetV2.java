package ru.instamart.api.model.v2.simple_ads;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssetV2 extends BaseResponseObject {
	private ExtV2 ext;
	private DataV2 data;
	private ImageV2 image;
	private TitleV2 title;
	private LinkV2 link;
}