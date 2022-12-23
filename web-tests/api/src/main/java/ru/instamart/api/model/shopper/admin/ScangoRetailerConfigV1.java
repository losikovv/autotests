package ru.instamart.api.model.shopper.admin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScangoRetailerConfigV1 extends BaseObject {
	private String endpoint;
	private String engine;
	private boolean available;
	@Singular
	@JsonProperty("custom_settings")
	private List<CustomSettingsItemV1> customSettings;
	private int id;
	@JsonProperty("webhooks_auth_token")
	private String webhooksAuthToken;
}