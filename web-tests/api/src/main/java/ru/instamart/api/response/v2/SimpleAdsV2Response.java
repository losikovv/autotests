package ru.instamart.api.response.v2;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.simple_ads.MediaItemV2;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleAdsV2Response extends BaseResponseObject {
	@Singular
	private List<MediaItemV2> media;

}