package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShippingTeamMembersItemV2 extends BaseObject {
	@JsonProperty("tips_link_url")
	private String tipsLinkUrl;
	private String role;
	private String phone;
	private String name;
	@JsonProperty("role_description")
	private String roleDescription;
}