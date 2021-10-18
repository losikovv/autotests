package ru.instamart.api.model.v2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProfileUserV2 extends BaseObject {

	@JsonProperty("has_confirmed_phone")
	private Boolean hasConfirmedPhone;

	@JsonProperty("promo_terms_accepted")
	private Boolean promoTermsAccepted;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("privacy_terms")
	private Boolean privacyTerms;

	@JsonProperty("b2b")
	private Boolean b2b;

	@JsonProperty("display_email")
	private String displayEmail;

	@JsonProperty("completed_orders_count")
	private Integer completedOrdersCount;

	@JsonProperty("id")
	private String id;

	@JsonProperty("current_phone")
	private String currentPhone;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("config")
	private ProfileConfigV2 config;

	@JsonProperty("email")
	private String email;

	@JsonProperty("promo_terms_changed_at")
	private Object promoTermsChangedAt;

	@JsonProperty("attached_services")
	private List<String> attachedServices;
}