package ru.instamart.api.model.v2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class NoSberIdV2  extends BaseObject {

	@JsonProperty("urls")
	private UrlsV2 urls;

	@JsonProperty("description")
	private String description;

	@JsonProperty("button_title")
	private String buttonTitle;

	@JsonProperty("title")
	private String title;

	@JsonProperty("banners")
	private List<BannersItemV2> banners;
}