package ru.instamart.k8s.rails_response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.k8s.rails_response.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class Retailer extends BaseObject {

	@JsonProperty("retailer_agreement_id")
	private int retailerAgreementId;

	@JsonProperty("color")
	private String color;

	@JsonProperty("price_acceptable_discrepancy")
	private int priceAcceptableDiscrepancy;

	@JsonProperty("inn")
	private String inn;

	@JsonProperty("description")
	private String description;

	@JsonProperty("created_at")
	private Object createdAt;

	@JsonProperty("home_page_departments_depth")
	private int homePageDepartmentsDepth;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("check_price_discrepancy")
	private boolean checkPriceDiscrepancy;

	@JsonProperty("contract_type")
	private String contractType;

	@JsonProperty("environment")
	private String environment;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("logo_background_color")
	private String logoBackgroundColor;

	@JsonProperty("legal_address")
	private String legalAddress;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("name")
	private String name;

	@JsonProperty("short_name")
	private String shortName;

	@JsonProperty("secondary_color")
	private String secondaryColor;

	@JsonProperty("id")
	private int id;

	@JsonProperty("position")
	private int position;

	@JsonProperty("legal_name")
	private String legalName;

	@JsonProperty("key")
	private String key;

	@JsonProperty("slug")
	private String slug;
}