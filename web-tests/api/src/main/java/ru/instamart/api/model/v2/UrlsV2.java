package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UrlsV2 extends BaseObject {

	@JsonProperty("how_to_url")
	private String howToUrl;

	@JsonProperty("personal_agreement_url")
	private String personalAgreementUrl;

	@JsonProperty("marketing_agreement_url")
	private String marketingAgreementUrl;
}