package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class PromotionLimitV2 extends BaseObject {
	@JsonProperty("max_payment_amount")
	private double maxPaymentAmount;
	@JsonProperty("account_amount")
	private double accountAmount;
	private int step;
	@JsonProperty("description_html")
	private String descriptionHtml;
	private String type;
	@JsonProperty("max_payment_percentage")
	private double maxPaymentPercentage;
	@JsonProperty("max_value")
	private int maxValue;
}