package ru.instamart.api.response.webhook_site;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenResponse extends BaseResponseObject {

	@JsonProperty("redirect")
	private boolean redirect;

	@JsonProperty("default_content_type")
	private String defaultContentType;

	@JsonProperty("cors")
	private boolean cors;

	@JsonProperty("ip")
	private String ip;

	@JsonProperty("default_status")
	private Integer defaultStatus;

	@JsonProperty("description")
	private Object description;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("timeout")
	private Integer timeout;

	@JsonProperty("password")
	private Boolean password;

	@JsonProperty("premium")
	private Boolean premium;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("user_id")
	private Object userId;

	@JsonProperty("alias")
	private Object alias;

	@JsonProperty("expiry")
	private Boolean expiry;

	@JsonProperty("actions")
	private Boolean actions;

	@JsonProperty("user_agent")
	private String userAgent;

	@JsonProperty("default_content")
	private String defaultContent;

	@JsonProperty("premium_expires_at")
	private Object premiumExpiresAt;
}