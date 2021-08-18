package ru.instamart.api.model.v2.credit_cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreditCardV2 extends BaseObject {
	@JsonProperty("cc_type")
	private String ccType;
	@JsonProperty("payment_tool")
	private PaymentToolV2 paymentTool;
	private String month;
	private String year;
	private Boolean eligible;
	private String name;
	private Integer id;
	private String title;
	@JsonProperty("last_digits")
	private String lastDigits;
}